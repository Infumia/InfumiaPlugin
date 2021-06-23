package tr.com.infumia.infumialib.reflection;

import java.lang.reflect.Field;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine {@link Field}.
 */
public interface RefField extends RefFieldExecuted, RefAnnotated, RefModifiable {

  /**
   * gets name of the field.
   *
   * @return name of the field.
   */
  @NotNull
  String getName();

  /**
   * obtains the real field.
   *
   * @return the real field.
   */
  @NotNull
  Field getRealField();

  /**
   * gets the type of the field.
   *
   * @return a {@link Class} that's type of the field
   */
  @NotNull
  Class<?> getType();

  /**
   * gets the field's object as a static.
   *
   * @return static field value.
   */
  @Override
  @NotNull
  default Optional<Object> getValue() {
    return this.of(null).getValue();
  }

  /**
   * sets the given object to the static field.
   *
   * @param value object to set.
   */
  @Override
  default void setValue(@Nullable final Object value) {
    this.of(null).setValue(value);
  }

  /**
   * applies the given object to create a {@link RefFieldExecuted} object.
   *
   * @param object the object to apply.
   *
   * @return a {@link RefFieldExecuted} object.
   */
  @NotNull
  RefFieldExecuted of(@Nullable Object object);
}
