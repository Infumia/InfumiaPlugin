package tr.com.infumia.infumialib.reflection;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine {@link java.lang.reflect.Method}.
 */
public interface RefMethod extends RefMethodExecuted, RefAnnotated, RefModifiable {

  /**
   * calls the method with the given parameters as a static.
   *
   * @param parameters the parameters to call.
   *
   * @return value of the method.
   */
  @Override
  @NotNull
  default Optional<Object> call(@Nullable final Object... parameters) {
    return this.of(null).call(parameters);
  }

  /**
   * obtains the method's name.
   *
   * @return method's name.
   */
  @NotNull
  String getName();

  /**
   * obtains the parameter types of the method.
   *
   * @return parameter types.
   */
  @NotNull
  Class<?>[] getParameterTypes();

  /**
   * obtains the return type of the method.
   *
   * @return return type.
   */
  @NotNull
  Class<?> getReturnType();

  /**
   * applies the given object to create a {@link RefMethodExecuted} object.
   *
   * @param object the object to apply.
   *
   * @return a {@link RefMethodExecuted} object.
   */
  @NotNull
  RefMethodExecuted of(@Nullable Object object);
}
