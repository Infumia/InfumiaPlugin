package tr.com.infumia.infumialib.reflection;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine {@link java.lang.reflect.Constructor}.
 *
 * @param <T> the result instance's type.
 */
public interface RefConstructed<T> extends RefAnnotated, RefModifiable {

  /**
   * creates a new instance from the given parameters.
   *
   * @param parameters the parameters to create.
   *
   * @return the new object.
   */
  @NotNull
  Optional<T> create(@Nullable Object... parameters);
}
