package tr.com.infumia.infumialib.transformer;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

/**
 * a class that represents transformed data.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransformedData {

  /**
   * the deserialized map.
   */
  private final Map<String, Object> deserializedMap;

  /**
   * the resolver.
   */
  @NotNull
  private final TransformResolver resolver;

  /**
   * the serialization.
   */
  private final boolean serialization;

  /**
   * the serialized map.
   */
  private final Map<String, Object> serializedMap;

  /**
   * creates a new transformed data instance for deserialization.
   *
   * @param resolver the resolver to create.
   * @param map the map to create.
   *
   * @return a transformed data instance for deserialization.
   */
  @NotNull
  public static TransformedData deserialization(@NotNull final TransformResolver resolver,
                                                @NotNull final Map<String, Object> map) {
    return new TransformedData(new ConcurrentHashMap<>(map), resolver, false, new ConcurrentHashMap<>());
  }

  /**
   * creates a new transformed data instance for serialization.
   *
   * @param resolver the resolver to create.
   *
   * @return a transformed data instance for serialization.
   */
  @NotNull
  public static TransformedData serialization(@NotNull final TransformResolver resolver) {
    return new TransformedData(new ConcurrentHashMap<>(), resolver, true, new ConcurrentHashMap<>());
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   */
  public void add(@NotNull final String path, @Nullable final Object value) {
    if (this.canDeserialize()) {
      return;
    }
    if (value == null) {
      this.remove(path);
    } else {
      this.serializedMap.put(path, this.resolver.serialize(value, null, true));
    }
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   * @param cls the cls to add.
   * @param <T> type of the value class.
   */
  public <T> void add(@NotNull final String path, @Nullable final Object value, @NotNull final Class<T> cls) {
    if (this.canDeserialize()) {
      return;
    }
    if (value == null) {
      this.remove(path);
    } else {
      this.serializedMap.put(path, this.resolver.serialize(value, GenericDeclaration.of(cls), true));
    }
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   * @param keyClass the key class to add.
   * @param valueClass the value class to add.
   * @param <K> type of the key class.
   * @param <V> type of the value class.
   */
  @SuppressWarnings("unchecked")
  public <K, V> void addAsMap(@NotNull final String path, @Nullable final Map<K, V> value,
                              @NotNull final Class<K> keyClass, @NotNull final Class<V> valueClass) {
    if (this.canDeserialize()) {
      return;
    }
    if (value == null) {
      this.remove(path);
    } else {
      this.serializedMap.put(path, this.resolver.serializeMap(
        (Map<Object, Object>) value,
        GenericDeclaration.of(value.getClass(), keyClass, valueClass),
        true));
    }
  }

  /**
   * adds the value to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   * @param elementClass the element class to add.
   * @param <T> type of the element class.
   */
  public <T> void addCollection(@NotNull final String path, @Nullable final Collection<T> value,
                                @NotNull final Class<T> elementClass) {
    if (this.canDeserialize()) {
      return;
    }
    if (value == null) {
      this.remove(path);
    } else {
      this.serializedMap.put(path, this.resolver.serializeCollection(
        value,
        GenericDeclaration.of(value.getClass(), elementClass),
        true));
    }
  }

  /**
   * adds formatted string to the path.
   *
   * @param path the path to add.
   * @param value the value to add.
   * @param args the args to add.
   */
  public void addFormatted(@NotNull final String path, @Nullable final String value, @NotNull final Object... args) {
    if (value == null) {
      this.remove(path);
    } else {
      this.add(path, MessageFormat.format(value, args));
    }
  }

  /**
   * checks if the deserialized map contains the key.
   *
   * @param key the key to check.
   *
   * @return {@code true} if the deserialized map contains the key.
   */
  public boolean containsKey(@NotNull final String key) {
    return this.canDeserialize() && this.deserializedMap.containsKey(key);
  }

  /**
   * gets a value from deserialized map.
   *
   * @param key the key to get.
   * @param objectClass the object class to get.
   * @param <T> type of the value.
   *
   * @return obtained value.
   */
  @NotNull
  public <T> Optional<T> get(@NotNull final String key, @NotNull final Class<T> objectClass) {
    return this.get(key, objectClass, null);
  }

  /**
   * gets a value from deserialized map.
   *
   * @param key the key to get.
   * @param objectClass the object class to get.
   * @param defaultValue the default value to get.
   * @param <T> type of the value.
   *
   * @return obtained value.
   */
  @NotNull
  public <T> Optional<T> get(@NotNull final String key, @NotNull final Class<T> objectClass,
                             @Nullable final T defaultValue) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      objectClass,
      null,
      defaultValue));
  }

  /**
   * gets a value from deserialized map as list.
   *
   * @param key the key to get.
   * @param elementClass the element class to get.
   * @param <T> type of the elements of list.
   *
   * @return obtained list value.
   */
  @SuppressWarnings("unchecked")
  @NotNull
  public <T> Optional<List<T>> getAsList(@NotNull final String key, @NotNull final Class<T> elementClass) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      List.class,
      GenericDeclaration.of(List.class, elementClass),
      null));
  }

  /**
   * gets a value from deserialized map as map.
   *
   * @param key the key to get.
   * @param keyClass the key class to get.
   * @param valueClass the value class to get.
   * @param <K> type of the keys of map.
   * @param <V> type of the values of map.
   *
   * @return obtained map value.
   */
  @NotNull
  public <K, V> Optional<Map<K, V>> getAsMap(@NotNull final String key, @NotNull final Class<K> keyClass,
                                             @NotNull final Class<V> valueClass) {
    if (this.canSerialize()) {
      return Optional.empty();
    }
    final var object = this.deserializedMap.get(key);
    if (object == null) {
      return Optional.empty();
    }
    //noinspection unchecked
    return Optional.of(this.resolver.deserialize(
      object,
      GenericDeclaration.of(object),
      Map.class,
      GenericDeclaration.of(Map.class, keyClass, valueClass),
      null));
  }

  /**
   * obtains deserialized map.
   *
   * @return deserialized map.
   */
  @NotNull
  public Map<String, Object> getDeserializedMap() {
    return Collections.unmodifiableMap(this.deserializedMap);
  }

  /**
   * obtains serialized map.
   *
   * @return serialized map.
   */
  @NotNull
  public Map<String, Object> getSerializedMap() {
    return Collections.unmodifiableMap(this.serializedMap);
  }

  /**
   * removes the path from {@link #serializedMap}.
   *
   * @param path the path to remove.
   */
  public void remove(@NotNull final String path) {
    if (this.canSerialize()) {
      this.serializedMap.remove(path);
    }
  }

  /**
   * checks if the data can deserialize.
   *
   * @return {@code true} if data can deserialize.
   */
  private boolean canDeserialize() {
    return !this.serialization;
  }

  /**
   * checks if the data can serialize.
   *
   * @return {@code true} if data can serialize.
   */
  private boolean canSerialize() {
    return this.serialization;
  }
}
