package tr.com.infumia.infumialib.transformer.declarations;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.reflection.clazz.ClassOf;
import tr.com.infumia.infumialib.transformer.TransformedObject;
import tr.com.infumia.infumialib.transformer.annotations.Comment;
import tr.com.infumia.infumialib.transformer.annotations.Exclude;
import tr.com.infumia.infumialib.transformer.annotations.Names;
import tr.com.infumia.infumialib.transformer.annotations.Version;

/**
 * a class that represents transformed class declarations.
 */
@ToString
@EqualsAndHashCode
public final class TransformedObjectDeclaration {

  /**
   * the caches.
   */
  private static final Map<Class<?>, TransformedObjectDeclaration> CACHES =
    new ConcurrentHashMap<>();

  /**
   * the fields.
   */
  @NotNull
  private final Map<String, FieldDeclaration> fields;

  /**
   * the header.
   */
  @Nullable
  @Getter
  private final Comment header;

  /**
   * the object class.
   */
  @NotNull
  @Getter
  private final Class<?> objectClass;

  /**
   * the transformer version.
   */
  @Nullable
  @Getter
  @Setter
  private Version version;

  /**
   * ctor.
   *
   * @param fields the fields.
   * @param header the header.
   * @param objectClass the object class.
   * @param version the version.
   */
  private TransformedObjectDeclaration(@NotNull final Map<String, FieldDeclaration> fields,
                                       @Nullable final Comment header, @NotNull final Class<?> objectClass,
                                       @Nullable final Version version) {
    this.fields = fields;
    this.header = header;
    this.objectClass = objectClass;
    this.version = version;
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls,
                                                @Nullable final TransformedObject object) {
    return TransformedObjectDeclaration.CACHES.computeIfAbsent(cls, clazz -> {
      final var classOf = new ClassOf<>(clazz);
      final var map = new LinkedHashMap<String, FieldDeclaration>();
      for (final var field : classOf.getDeclaredFields()) {
        if (field.getName().startsWith("this$") ||
          field.hasAnnotation(Exclude.class)) {
          continue;
        }
        final var declaration = FieldDeclaration.of(
          Names.Calculated.calculateNames(clazz),
          object,
          clazz,
          field);
        map.merge(declaration.getPath(), declaration, (f1, f2) -> {
          if (f1.getMigration() != null) {
            return f2;
          }
          throw new IllegalStateException(String.format("Duplicate key %s", f1));
        });
      }
      return new TransformedObjectDeclaration(
        map,
        classOf.getAnnotation(Comment.class).orElse(null),
        clazz,
        classOf.getAnnotation(Version.class).orElse(null));
    });
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final TransformedObject object) {
    return TransformedObjectDeclaration.of(object.getClass(), object);
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls) {
    return TransformedObjectDeclaration.of(cls, null);
  }

  /**
   * obtains the fields.
   *
   * @return fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getAllFields() {
    return Collections.unmodifiableMap(this.fields);
  }

  /**
   * obtains the migrated fields.
   *
   * @return migrated fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getMigratedFields() {
    final var map = new HashMap<String, FieldDeclaration>();
    for (final var entry : this.fields.entrySet()) {
      if (entry.getValue().isMigrated(this)) {
        if (map.put(entry.getKey(), entry.getValue()) != null) {
          throw new IllegalStateException("Duplicate key");
        }
      }
    }
    return Collections.unmodifiableMap(map);
  }

  /**
   * obtains the non migrated fields.
   *
   * @return non migrated fields.
   */
  @NotNull
  public Map<String, FieldDeclaration> getNonMigratedFields() {
    final var map = new HashMap<String, FieldDeclaration>();
    for (final var entry : this.fields.entrySet()) {
      if (entry.getValue().isNotMigrated(this)) {
        if (map.put(entry.getKey(), entry.getValue()) != null) {
          throw new IllegalStateException("Duplicate key");
        }
      }
    }
    return map;
  }

  /**
   * obtains the version as integer.
   *
   * @return version as integer.
   */
  public int getVersionInteger() {
    return this.version == null ? 1 : this.version.value();
  }
}
