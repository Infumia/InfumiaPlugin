package tr.com.infumia.infumialib.functions;

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
   *
   * @param left the left to run.
   * @param middle the middle to run.
   * @param right the right to run.
   */
  void accept(@NotNull X left, @NotNull Y middle, @NotNull Z right);
}
