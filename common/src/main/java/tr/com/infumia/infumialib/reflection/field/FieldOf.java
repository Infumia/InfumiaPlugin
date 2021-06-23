package tr.com.infumia.infumialib.reflection.field;

import tr.com.infumia.infumialib.reflection.RefField;
import tr.com.infumia.infumialib.reflection.RefFieldExecuted;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation for {@link RefField}.
 */
@Log
@RequiredArgsConstructor
public final class FieldOf implements RefField {

  /**
   * the field.
   */
  @NotNull
  private final Field field;

  @Override
  public <A extends Annotation> Optional<A> getAnnotation(@NotNull final Class<A> annotationClass) {
    return Optional.ofNullable(this.field.getDeclaredAnnotation(annotationClass));
  }

  @NotNull
  @Override
  public String getName() {
    return this.field.getName();
  }

  @NotNull
  @Override
  public Field getRealField() {
    return this.field;
  }

  @NotNull
  @Override
  public Class<?> getType() {
    return this.field.getType();
  }

  @NotNull
  @Override
  public RefFieldExecuted of(@Nullable final Object object) {
    return new FieldExecuted(object);
  }

  @Override
  public boolean hasFinal() {
    return Modifier.isFinal(this.field.getModifiers());
  }

  @Override
  public boolean hasPrivate() {
    return Modifier.isPrivate(this.field.getModifiers());
  }

  @Override
  public boolean hasPublic() {
    return Modifier.isPublic(this.field.getModifiers());
  }

  @Override
  public boolean hasStatic() {
    return Modifier.isStatic(this.field.getModifiers());
  }

  /**
   * an implementation for {@link RefFieldExecuted}.
   */
  @RequiredArgsConstructor
  private final class FieldExecuted implements RefFieldExecuted {

    /**
     * the object.
     */
    @Nullable
    private final Object object;

    @NotNull
    @Override
    public Optional<Object> getValue() {
      final var accessible = FieldOf.this.field.isAccessible();
      try {
        FieldOf.this.field.setAccessible(true);
        return Optional.ofNullable(FieldOf.this.field.get(this.object));
      } catch (final IllegalAccessException exception) {
        FieldOf.log.log(Level.SEVERE, "FieldExecuted#getValue()", exception);
        return Optional.empty();
      } finally {
        FieldOf.this.field.setAccessible(accessible);
      }
    }

    @Override
    public void setValue(@Nullable final Object value) {
      final boolean accessible = FieldOf.this.field.isAccessible();
      try {
        FieldOf.this.field.setAccessible(true);
        FieldOf.this.field.set(this.object, value);
      } catch (final IllegalAccessException exception) {
        FieldOf.log.log(Level.SEVERE, "FieldExecuted#setValue(Object)", exception);
      } finally {
        FieldOf.this.field.setAccessible(accessible);
      }
    }
  }
}
