package tr.com.infumia.plugin.functions;

import org.jetbrains.annotations.NotNull;

/**
 * a functional interface to determine tri consumers.
 *
 * @param <X> type of the left object.
 * @param <Y> type of the middle object.
 * @param <Z> type of the right object.
 */
@FunctionalInterface
public interface TriConsumer<X, Y, Z> {

  /**
   * runs the function with the given parameters.
   */
  void accept(@NotNull X left, @NotNull Y middle, @NotNull Z right);
}
