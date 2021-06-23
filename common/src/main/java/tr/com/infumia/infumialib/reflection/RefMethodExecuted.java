package tr.com.infumia.infumialib.reflection;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine applied {@link java.lang.reflect.Method}.
 */
public interface RefMethodExecuted {

  /**
   * calls the method with the given parameters.
   *
   * @param parameters the parameters to call.
   *
   * @return the value of the method.
   */
  @NotNull
  Optional<Object> call(@Nullable Object... parameters);
}
