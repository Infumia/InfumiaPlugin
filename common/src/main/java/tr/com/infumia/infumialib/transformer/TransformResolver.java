package tr.com.infumia.infumialib.transformer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.reflection.clazz.ClassOf;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.resolvers.InMemoryWrappedResolver;

/**
 * an abstract class that represents transform resolvers.
 */
public abstract class TransformResolver {

  /**
   * the current object.
   */
  @Nullable
  private TransformedObject currentObject;

  /**
   * the parent object.
   */
  @Nullable
  private TransformedObject parentObject;

  /**
   * the registry.
   */
  @NotNull
  @Getter
  private TransformRegistry registry = new TransformRegistry()
    .withDefaultTransformers();

  /**
   * obtains the current object.
   *
   * @return current object.
   */
  @Nullable
  public final TransformedObject getCurrentObject() {
    return this.currentObject;
  }

  /**
   * obtains the parent object.
   *
   * @return parent object.
   */
  @Nullable
  public final TransformedObject getParentObject() {
    return this.parentObject;
  }

  /**
   * sets the current object.
   *
   * @param currentObject the current object to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformResolver withCurrentObject(@Nullable final TransformedObject currentObject) {
    this.currentObject = currentObject;
    return this;
  }

  /**
   * sets the parent object.
   *
   * @param parentObject the parent object to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformResolver withParentObject(@Nullable final TransformedObject parentObject) {
    this.parentObject = parentObject;
    return this;
  }

  /**
   * deserializes the object and converts it into object class.
   *
   * @param object the object to deserialize.
   * @param targetClass the target class to deserialize.
   * @param genericSource the generic source to deserialize.
   * @param genericTarget the generic target to deserialize.
   * @param defaultValue the default value of the field.
   * @param <T> type of the deserialized object class.
   *
   * @return deserialized object.
   */
  @SuppressWarnings("unchecked")
  @Nullable
  @Contract("null, _, _, _, _ -> null; !null, _, _, _, _ -> !null")
  public <T> T deserialize(@Nullable final Object object, @Nullable final GenericDeclaration genericSource,
                           @NotNull final Class<T> targetClass, @Nullable final GenericDeclaration genericTarget,
                           @Nullable final Object defaultValue)
    throws TransformException {
    if (object == null) {
      return null;
    }
    final var source = genericSource == null
      ? GenericDeclaration.of(object)
      : genericSource;
    var target = genericTarget == null
      ? GenericDeclaration.ofReady(targetClass)
      : genericTarget;
    if (target.isPrimitive()) {
      target = GenericDeclaration.ofReady(target.toWrapper().orElse(null));
    }
    final var objectClass = object.getClass();
    try {
      if (object instanceof String && target.isEnum()) {
        final var targetClassOf = new ClassOf<>(targetClass);
        final var stringObject = (String) object;
        try {
          final var valueOf = targetClassOf.getMethod("valueOf", String.class).orElseThrow();
          final var enumValue = valueOf.call(stringObject);
          if (enumValue.isPresent()) {
            return targetClass.cast(enumValue.get());
          }
        } catch (final Exception e) {
          final var enumValues = (Enum<?>[]) targetClass.getEnumConstants();
          for (final var value : enumValues) {
            if (stringObject.equalsIgnoreCase(value.name())) {
              return targetClass.cast(value);
            }
          }
        }
        final var error = String.format("no enum value for name %s (available: %s)",
          stringObject, Arrays.stream(targetClass.getEnumConstants())
            .map(item -> ((Enum<?>) item).name())
            .collect(Collectors.joining(", ")));
        throw new TransformException(error);
      }
      if (source.isEnum() && targetClass == String.class) {
        final var name = new ClassOf<>(objectClass)
          .getMethodByName("name")
          .orElseThrow()
          .of(object)
          .call()
          .orElseThrow(() ->
            new TransformException(String.format("Something went wrong when getting method called name in %s",
              objectClass)));
        return targetClass.cast(name);
      }
    } catch (final Exception exception) {
      throw new RuntimeException(String.format("Failed to resolve enum %s <> %s",
        object.getClass(), targetClass), exception);
    }
    if (TransformedObject.class.isAssignableFrom(targetClass)) {
      final var transformedObject = TransformerPool.create((Class<? extends TransformedObject>) targetClass);
      return (T) transformedObject
        .withResolver(new InMemoryWrappedResolver(
          this,
          this.deserialize(object, source, Map.class, GenericDeclaration.of(Map.class, String.class, Object.class), defaultValue))
          .withParentObject(this.currentObject))
        .update();
    }
    final var serializerOptional = this.registry.getSerializer(targetClass);
    if (object instanceof Map<?, ?> && serializerOptional.isPresent()) {
      final var deserialization = TransformedData.deserialization(this, (Map<String, Object>) object);
      //noinspection rawtypes
      final ObjectSerializer serializer = serializerOptional.get();
      final Optional<?> value;
      if (defaultValue == null) {
        value = serializer.deserialize(deserialization, genericTarget);
      } else {
        value = serializer.deserialize(defaultValue, deserialization, genericTarget);
      }
      return value.map(targetClass::cast).orElse(null);
    }
    if (genericTarget != null) {
      if (object instanceof Collection<?> && Collection.class.isAssignableFrom(targetClass)) {
        final var declaration = genericTarget.getSubTypeAt(0).orElseThrow(() ->
          new TransformException(String.format("Something went wrong when getting sub types(0) of %s", genericTarget)));
        if (declaration.getType() == null) {
          throw new TransformException(String.format("Something went wrong when getting type of %s", genericTarget));
        }
        final var targetList = (Collection<Object>) TransformerPool.createInstance(targetClass);
        ((Collection<?>) object).stream()
          .map(item -> this.deserialize(item, GenericDeclaration.of(item), declaration.getType(), declaration, defaultValue))
          .forEach(targetList::add);
        return targetClass.cast(targetList);
      }
      if (object instanceof Map<?, ?> && Map.class.isAssignableFrom(targetClass)) {
        final var keyDeclaration = genericTarget.getSubTypeAt(0).orElseThrow(() ->
          new TransformException(String.format("Something went wrong when getting sub types(0) of %s", genericTarget)));
        if (keyDeclaration.getType() == null) {
          throw new TransformException(String.format("Something went wrong when getting type of %s", keyDeclaration));
        }
        final var valueDeclaration = genericTarget.getSubTypeAt(1).orElseThrow(() ->
          new TransformException(String.format("Something went wrong when getting sub types(1) of %s", genericTarget)));
        if (valueDeclaration.getType() == null) {
          throw new TransformException(String.format("Something went wrong when getting type of %s", valueDeclaration));
        }
        final var map = (Map<Object, Object>) TransformerPool.createInstance(targetClass);
        ((Map<Object, Object>) object).forEach((key, value) -> map.put(
          this.deserialize(key, GenericDeclaration.of(key), keyDeclaration.getType(), keyDeclaration, defaultValue),
          this.deserialize(value, GenericDeclaration.of(value), valueDeclaration.getType(), valueDeclaration, defaultValue)));
        return targetClass.cast(map);
      }
    }
    final var transformerOptional = this.registry.getTransformer(source, target);
    if (transformerOptional.isEmpty()) {
      if (targetClass.isPrimitive() && GenericDeclaration.isWrapperBoth(targetClass, objectClass)) {
        return (T) GenericDeclaration.toPrimitive(object);
      }
      if (targetClass.isPrimitive() || GenericDeclaration.ofReady(targetClass).hasWrapper()) {
        final var simplified = this.serialize(object, GenericDeclaration.ofReady(objectClass), false);
        return this.deserialize(simplified, GenericDeclaration.of(simplified), targetClass, GenericDeclaration.ofReady(targetClass), defaultValue);
      }
      try {
        return targetClass.cast(object);
      } catch (final ClassCastException exception) {
        throw new TransformException(String.format("Cannot resolve %s to %s (%s => %s): %s",
          object.getClass(), targetClass, source, target, object), exception);
      }
    }
    //noinspection rawtypes
    final Transformer transformer = transformerOptional.get();
    final Object transformed;
    if (defaultValue == null) {
      transformed = transformer.transform(object).orElse(null);
    } else {
      transformed = transformer.transformWithField(object, defaultValue)
        .orElse(transformer.transform(object)
          .orElse(null));
    }
    return targetClass.isPrimitive()
      ? (T) GenericDeclaration.toPrimitive(transformed)
      : targetClass.cast(transformed);
  }

  /**
   * obtains all keys of the parent object.
   *
   * @return all keys.
   */
  @NotNull
  public List<String> getAllKeys() {
    return this.currentObject == null
      ? Collections.emptyList()
      : this.currentObject.getAllKeys();
  }

  /**
   * gets value at path.
   *
   * @param path the path to get.
   *
   * @return value at path.
   */
  @NotNull
  public abstract Optional<Object> getValue(@NotNull String path);

  /**
   * gets value at path.
   *
   * @param path the path to get.
   * @param cls the cls to get.
   * @param genericType the generic type to get.
   * @param defaultValue the default value of the field.
   * @param <T> type of the value.
   *
   * @return value at path.
   */
  @NotNull
  public <T> Optional<T> getValue(@NotNull final String path, @NotNull final Class<T> cls,
                                  @Nullable final GenericDeclaration genericType, @Nullable final Object defaultValue) {
    return this.getValue(path)
      .map(value -> this.deserialize(value, GenericDeclaration.of(value), cls, genericType, defaultValue));
  }

  /**
   * checks if the object can transform to string list.
   *
   * @param object the object to check.
   * @param declaration the generic declaration to check.
   *
   * @return {@code true} if the object can be converted to string list.
   */
  public boolean isToListObject(@NotNull final Object object, @Nullable final GenericDeclaration declaration) {
    if (object instanceof Class<?>) {
      return this.registry.getTransformer(declaration, GenericDeclaration.ofReady(List.class)).isPresent();
    }
    return this.isToListObject(object.getClass(), declaration);
  }

  /**
   * checks if the object can transform to string.
   *
   * @param object the object to check.
   * @param declaration the generic declaration to check.
   *
   * @return {@code true} if the object can be converted to string.
   */
  public boolean isToStringObject(@NotNull final Object object, @Nullable final GenericDeclaration declaration) {
    if (object instanceof Class<?>) {
      final var cls = (Class<?>) object;
      return cls.isEnum() ||
        this.registry.getTransformer(declaration, GenericDeclaration.ofReady(String.class)).isPresent();
    }
    return object.getClass().isEnum() ||
      this.isToStringObject(object.getClass(), declaration);
  }

  /**
   * checks if the value is valid or not.
   *
   * @param declaration the declaration to check.
   * @param value the value to check.
   *
   * @return {@code true} if the value is valid.
   */
  public boolean isValid(@NotNull final FieldDeclaration declaration, @Nullable final Object value) {
    return true;
  }

  /**
   * loads the values into stream.
   *
   * @param inputStream the input stream to load.
   * @param declaration the declaration to load.
   *
   * @throws Exception if something goes wrong when loading the values.
   */
  public abstract void load(@NotNull InputStream inputStream, @NotNull TransformedObjectDeclaration declaration)
    throws Exception;

  /**
   * checks if the path exists.
   *
   * @param path the field path to check.
   *
   * @return {@code true} if the path exists.
   */
  public boolean pathExists(@NotNull final String path) {
    return this.getValue(path).isPresent();
  }

  /**
   * removes the value to path.
   *
   * @param path the path to remove.
   * @param genericType the generic type to remove.
   * @param field the field to remove.
   */
  public abstract void removeValue(@NotNull String path, @Nullable GenericDeclaration genericType,
                                   @Nullable FieldDeclaration field);

  /**
   * serializes the object.
   *
   * @param value the value to serialize.
   * @param genericType the generic type to serialize.
   * @param conservative the conservative to serialize.
   *
   * @return serialized object.
   *
   * @throws TransformException if something goes wrong when serializing the object.
   */
  @SuppressWarnings("unchecked")
  @Nullable
  @Contract("null, _, _ -> null; !null, _, _ -> !null")
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value == null) {
      return null;
    }
    if (TransformedObject.class.isAssignableFrom(value.getClass())) {
      return ((TransformedObject) value).asMap(this, conservative);
    }
    final var serializerType = genericType != null ? genericType.getType() : value.getClass();
    if (serializerType == null) {
      throw new TransformException(String.format("Something went wrong when getting type of %s or %s",
        genericType, value));
    }
    final var serializerOptional = this.registry.getSerializer(serializerType);
    if (serializerOptional.isEmpty()) {
      if (conservative && (serializerType.isPrimitive() || GenericDeclaration.ofReady(serializerType).hasWrapper())) {
        return value;
      }
      if (serializerType.isPrimitive()) {
        final var wrappedPrimitive = GenericDeclaration.ofReady(serializerType).toWrapper().orElseThrow();
        return this.serialize(wrappedPrimitive.cast(value), GenericDeclaration.ofReady(wrappedPrimitive), false);
      }
      if (genericType == null) {
        final var valueDeclaration = GenericDeclaration.of(value);
        if (this.isToStringObject(serializerType, valueDeclaration)) {
          return this.deserialize(value, null, String.class, null, value);
        }
      }
      if (this.isToStringObject(serializerType, genericType)) {
        return this.deserialize(value, genericType, String.class, null, value);
      }
      if (this.isToListObject(serializerType, genericType)) {
        return this.deserialize(value, genericType, List.class, GenericDeclaration.ofReady(List.class), value);
      }
      if (value instanceof Collection<?>) {
        return this.serializeCollection((Collection<?>) value, genericType, conservative);
      }
      if (value instanceof Map<?, ?>) {
        return this.serializeMap((Map<?, ?>) value, genericType, conservative);
      }
      throw new TransformException(String.format("Cannot serialize type %s (%s): '%s' [%s]",
        serializerType, genericType, value, value.getClass()));
    }
    //noinspection rawtypes
    final ObjectSerializer serializer = serializerOptional.get();
    final var serializationData = TransformedData.serialization(this);
    serializer.serialize(value, serializationData);
    final var serializationMap = serializationData.getSerializedMap();
    if (!conservative) {
      final var newSerializationMap = new LinkedHashMap<String, Object>();
      serializationMap.forEach((mKey, mValue) ->
        newSerializationMap.put(mKey, this.serialize(mValue, GenericDeclaration.of(mValue), false)));
      return newSerializationMap;
    }
    return serializationMap;
  }

  /**
   * serializes collection.
   *
   * @param value the value to serialize.
   * @param genericType the generic type to serialize.
   * @param conservative the conservative to serialize.
   *
   * @return simplified collection.
   *
   * @throws TransformException if something goes wrong when simplifying the value.
   */
  @NotNull
  public Collection<?> serializeCollection(@NotNull final Collection<?> value,
                                           @Nullable final GenericDeclaration genericType, final boolean conservative)
    throws TransformException {
    final var collectionSubtype = genericType == null
      ? null
      : genericType.getSubTypeAt(0).orElse(null);
    return value.stream()
      .map(collectionElement -> this.serialize(collectionElement, collectionSubtype, conservative))
      .collect(Collectors.toList());
  }

  /**
   * serializes map.
   *
   * @param value the value to serialize.
   * @param genericType the generic type to serialize.
   * @param conservative the conservative to serialize.
   *
   * @return simplified map.
   *
   * @throws TransformException if something goes wrong when simplifying the value.
   */
  @NotNull
  public Map<?, ?> serializeMap(@NotNull final Map<?, ?> value, @Nullable final GenericDeclaration genericType,
                                final boolean conservative)
    throws TransformException {
    final var keyDeclaration = genericType == null
      ? null
      : genericType.getSubTypeAt(0).orElse(null);
    final var valueDeclaration = genericType == null
      ? null
      : genericType.getSubTypeAt(1).orElse(null);
    return value.entrySet().stream()
      .map(entry -> Map.entry(
        this.serialize(entry.getKey(), keyDeclaration, conservative),
        this.serialize(entry.getValue(), valueDeclaration, conservative)
      ))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
  }

  /**
   * sets the value to path.
   *
   * @param path the path to set.
   * @param value the value to set.
   * @param genericType the generic type to set.
   * @param field the field to set.
   */
  public abstract void setValue(@NotNull String path, @Nullable Object value, @Nullable GenericDeclaration genericType,
                                @Nullable FieldDeclaration field);

  /**
   * sets the registry.
   *
   * @param registry the registry to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformResolver withRegistry(@NotNull final TransformRegistry registry) {
    this.registry = registry;
    return this;
  }

  /**
   * registers the pack.
   *
   * @param packs the packs to register.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public TransformResolver withTransformerPacks(@NotNull final TransformPack... packs) {
    this.registry.withTransformPacks(packs);
    return this;
  }

  /**
   * writes the steam.
   *
   * @param outputStream the output steam to write.
   * @param declaration the declaration to write.
   *
   * @throws Exception if something goes wrong when writing the stream.
   */
  public abstract void write(@NotNull OutputStream outputStream, @NotNull TransformedObjectDeclaration declaration)
    throws Exception;
}
