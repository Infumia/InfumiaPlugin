package tr.com.infumia.infumialib.transformer.declarations;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.reflection.RefField;
import tr.com.infumia.infumialib.transformer.TransformedObject;
import tr.com.infumia.infumialib.transformer.annotations.Comment;
import tr.com.infumia.infumialib.transformer.annotations.Migration;
import tr.com.infumia.infumialib.transformer.annotations.Names;
import tr.com.infumia.infumialib.transformer.annotations.Variable;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;

/**
 * a class that represents field declarations.
 */
@Getter
@ToString
@EqualsAndHashCode
public final class FieldDeclaration {

  /**
   * the caches.
   */
  private static final Map<Key, FieldDeclaration> CACHES = new ConcurrentHashMap<>();

  /**
   * the comment.
   */
  @Nullable
  private final Comment comment;

  /**
   * the field.
   */
  @NotNull
  private final RefField field;

  /**
   * the generic declaration.
   */
  @NotNull
  private final GenericDeclaration genericDeclaration;

  /**
   * the migration.
   */
  @Nullable
  private final Migration migration;

  /**
   * the object.
   */
  @Nullable
  private final TransformedObject object;

  /**
   * the path.
   */
  @NotNull
  private final String path;

  /**
   * the variable.
   */
  @Nullable
  private final Variable variable;

  /**
   * the hide variable.
   */
  @Setter
  private boolean hideVariable;

  /**
   * the starting value.
   */
  @Nullable
  @Setter
  private Object startingValue;

  /**
   * ctor.
   *
   * @param comment the comment.
   * @param field the field.
   * @param genericDeclaration the generic declaration.
   * @param migration the migration.
   * @param object the object.
   * @param path the path.
   * @param variable the variable.
   * @param startingValue the starting value.
   */
  private FieldDeclaration(@Nullable final Comment comment, @NotNull final RefField field,
                           @NotNull final GenericDeclaration genericDeclaration, @Nullable final Migration migration,
                           @Nullable final TransformedObject object, @NotNull final String path,
                           @Nullable final Variable variable, @Nullable final Object startingValue) {
    this.comment = comment;
    this.field = field;
    this.genericDeclaration = genericDeclaration;
    this.migration = migration;
    this.object = object;
    this.path = path;
    this.variable = variable;
    this.startingValue = startingValue;
  }

  /**
   * creates a new field declaration.
   *
   * @param parent the parent to create.
   * @param object the object to create
   * @param cls the cls to create.
   * @param field the field to create.
   *
   * @return a newly created field declaration.
   */
  @NotNull
  public static FieldDeclaration of(@Nullable final Names parent, @Nullable final TransformedObject object,
                                    @NotNull final Class<?> cls, @NotNull final RefField field) {
    return FieldDeclaration.CACHES.computeIfAbsent(Key.of(cls, field.getName()), cache ->
      new FieldDeclaration(
        field.getAnnotation(Comment.class).orElse(null),
        field,
        GenericDeclaration.of(field),
        field.getAnnotation(Migration.class).orElse(null),
        object,
        Names.Calculated.calculatePath(parent, field),
        field.getAnnotation(Variable.class).orElse(null),
        Optional.ofNullable(object)
          .flatMap(o -> field.of(o).getValue())
          .orElse(null)));
  }

  /**
   * gets the value of the field.
   *
   * @return value of the field.
   */
  @Nullable
  public Object getValue() throws TransformException {
    if (this.hideVariable) {
      return this.startingValue;
    }
    try {
      return this.field.of(this.object).getValue().orElseThrow();
    } catch (final Exception exception) {
      throw new TransformException("Failed to use #getValue", exception);
    }
  }

  /**
   * sets the field value to the value.
   *
   * @param value the value to set.
   */
  public void setValue(@Nullable final Object value) {
    this.field.of(this.object).setValue(value);
  }

  /**
   * checks if field is migrated.
   *
   * @param declaration the declaration to get version from.
   *
   * @return {@code true} if field is migrated.
   */
  public boolean isMigrated(@NotNull final TransformedObjectDeclaration declaration) {
    return this.isMigrated(declaration.getVersionInteger());
  }

  /**
   * checks if field is migrated.
   *
   * @param version the version to check.
   *
   * @return {@code true} if field is migrated.
   */
  public boolean isMigrated(final int version) {
    return this.migration != null && version >= this.migration.value();
  }

  /**
   * checks if field is not migrated.
   *
   * @param declaration the declaration to get version from.
   *
   * @return {@code true} if field is not migrated.
   */
  public boolean isNotMigrated(@NotNull final TransformedObjectDeclaration declaration) {
    return !this.isMigrated(declaration);
  }

  /**
   * checks if field is migrated.
   *
   * @param version the version to check.
   *
   * @return {@code true} if field is migrated.
   */
  public boolean isNotMigrated(final int version) {
    return !this.isMigrated(version);
  }

  /**
   * a class that represents keys.
   */
  @Getter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
  private static final class Key {

    /**
     * the class.
     */
    @NotNull
    private final Class<?> cls;

    /**
     * the field name.
     */
    @NotNull
    private final String fieldName;
  }
}
