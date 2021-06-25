package tr.com.infumia.infumialib.transformer.resolvers;

import eu.okaeri.hjson.CommentType;
import eu.okaeri.hjson.HjsonOptions;
import eu.okaeri.hjson.JsonArray;
import eu.okaeri.hjson.JsonObject;
import eu.okaeri.hjson.JsonValue;
import eu.okaeri.hjson.Stringify;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.postprocessor.PostProcessor;
import tr.com.infumia.infumialib.transformer.postprocessor.SectionSeparator;

/**
 * a class that represents HJson file configuration.
 */
@RequiredArgsConstructor
public class HJsonJson extends TransformResolver {

  /**
   * the read options.
   */
  private static final HjsonOptions READ_OPTIONS = new HjsonOptions()
    .setOutputComments(true);

  /**
   * the comment prefix.
   */
  @NotNull
  private final String commentPrefix;

  /**
   * the section separator.
   */
  private final String sectionSeparator;

  /**
   * the json.
   */
  private JsonObject json = new JsonObject();

  /**
   * ctor.
   *
   * @param commentPrefix the comment prefix.
   */
  public HJsonJson(@NotNull final String commentPrefix) {
    this(commentPrefix, SectionSeparator.NONE);
  }

  /**
   * ctor.
   */
  public HJsonJson() {
    this("# ");
  }

  @NotNull
  @Override
  public List<String> getAllKeys() {
    final var keys = new ArrayList<String>();
    this.json.forEach(member -> keys.add(member.getName()));
    return Collections.unmodifiableList(keys);
  }

  @NotNull
  @Override
  public Optional<Object> getValue(@NotNull final String path) {
    return this.fromJsonValue(this.json.get(path));
  }

  @Override
  public void load(@NotNull final InputStream inputStream, @NotNull final TransformedObjectDeclaration declaration) {
    this.json = JsonValue.readHjson(PostProcessor.of(inputStream).getContext(), HJsonJson.READ_OPTIONS).asObject();
  }

  @Override
  public boolean pathExists(@NotNull final String path) {
    return this.json.has(path);
  }

  @Override
  public void removeValue(@NotNull final String path, @Nullable final GenericDeclaration genericType,
                          @Nullable final FieldDeclaration field) {
    this.json.remove(path);
  }

  @Override
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value == null) {
      return null;
    }
    final var genericsDeclaration = GenericDeclaration.of(value);
    if (genericsDeclaration.getType() == char.class || genericsDeclaration.getType() == Character.class) {
      return super.serialize(value, genericType, false);
    }
    return super.serialize(value, genericType, conservative);
  }

  @NotNull
  @Override
  public Map<?, ?> serializeMap(@NotNull final Map<?, ?> value, @Nullable final GenericDeclaration genericType,
                                final boolean conservative)
    throws TransformException {
    final var map = new LinkedHashMap<>();
    final var keyDeclaration = genericType == null
      ? null
      : genericType.getSubTypeAt(0).orElse(null);
    final var valueDeclaration = genericType == null
      ? null
      : genericType.getSubTypeAt(1).orElse(null);
    value.forEach((key1, value1) -> {
      final var key = this.serialize(key1, keyDeclaration, false);
      final var kValue = this.serialize(value1, valueDeclaration, conservative);
      map.put(key, kValue);
    });
    return map;
  }

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.json.set(path, this.toJsonValue(this.serialize(value, genericType, true)));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream,
                    @NotNull final TransformedObjectDeclaration declaration) {
    this.addComments(this.json, declaration, null);
    final var header = declaration.getHeader();
    final var comments = header == null
      ? null
      : header.value();
    final var comment = PostProcessor.createComment(this.commentPrefix, comments);
    this.json.setFullComment(CommentType.BOL, comment.isEmpty()
      ? ""
      : comment + this.sectionSeparator);
    PostProcessor.of(this.json.toString(Stringify.HJSON_COMMENTS)).write(outputStream);
  }

  /**
   * adds comment to the path.
   *
   * @param jsonValue the json value to add.
   * @param declaration the declaration to add.
   * @param path the path to add.
   */
  private void addComments(@NotNull final JsonValue jsonValue, @NotNull final TransformedObjectDeclaration declaration,
                           @Nullable final String path) {
    final var field = declaration.getNonMigratedFields().get(path);
    if (jsonValue instanceof JsonObject) {
      final var jsonObject = (JsonObject) jsonValue;
      if (field == null) {
        jsonObject.names().forEach(name ->
          this.addComments(jsonObject.get(name), declaration, name));
      } else {
        final var transformedObjectDeclaration = TransformedObjectDeclaration.of(field.getGenericDeclaration().getType());
        jsonObject.names().forEach(name ->
          this.addComments(
            jsonObject.get(name),
            transformedObjectDeclaration,
            name));
      }
    }
    if (jsonValue instanceof JsonArray && field != null) {
      field.getGenericDeclaration().getSubTypeAt(0).ifPresent(arrayType -> {
        final var transformedObjectDeclaration = TransformedObjectDeclaration.of(arrayType.getType());
        ((JsonArray) jsonValue).forEach(item -> this.addComments(item, transformedObjectDeclaration, null));
      });
    }
    if (field == null) {
      return;
    }
    final var comment = field.getComment();
    if (comment == null) {
      return;
    }
    final var comments = PostProcessor.createComment(this.commentPrefix, comment.value());
    jsonValue.setFullComment(CommentType.BOL, comments.isEmpty()
      ? ""
      : this.sectionSeparator + comments);
  }

  /**
   * converts the value into the primitive object.
   *
   * @param value the value to convert.
   *
   * @return converted value.
   */
  @NotNull
  private Optional<Object> fromJsonValue(@NotNull final JsonValue value) {
    if (value.isNull()) {
      return Optional.empty();
    }
    if (value instanceof JsonArray) {
      final var values = new ArrayList<>();
      ((JsonArray) value).forEach(item ->
        this.fromJsonValue(item).ifPresent(values::add));
      return Optional.of(values);
    }
    if (value instanceof JsonObject) {
      final var map = new LinkedHashMap<String, Object>();
      final var object = (JsonObject) value;
      object.forEach(member ->
        this.fromJsonValue(member.getValue()).ifPresent(memberValue ->
          map.put(member.getName(), memberValue)));
      return Optional.of(map);
    }
    return Optional.ofNullable(value.asRaw());
  }

  /**
   * converts the object into a json value.
   *
   * @param object the object to convert.
   *
   * @return converted value.
   */
  @SuppressWarnings("unchecked")
  @NotNull
  private JsonValue toJsonValue(@Nullable final Object object) {
    if (object == null) {
      return JsonValue.valueOf(null);
    }
    if (object instanceof String) {
      return JsonValue.valueOf((String) object);
    }
    if (object instanceof Collection<?>) {
      final var array = new JsonArray();
      ((Collection<?>) object).forEach(item -> array.add(this.toJsonValue(item)));
      return array;
    }
    if (object instanceof Map<?, ?>) {
      final var map = new JsonObject();
      ((Map<String, ?>) object).forEach((key, value) -> map.add(key, this.toJsonValue(value)));
      return map;
    }
    if (object instanceof Number || object instanceof Boolean) {
      return JsonValue.valueOf(object);
    }
    throw new IllegalArgumentException(String.format("Cannot transform element: %s [%s]",
      object, object.getClass()));
  }
}
