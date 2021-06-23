package tr.com.infumia.infumialib.workload;

import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract implementation for {@link Workload} and,
 * reschedules if {@link ConditionalScheduleWorkload#test(Object)} returns {@code true}.
 *
 * @param <T> the type of the element.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ConditionalScheduleWorkload<T> implements Workload, Predicate<T> {

  /**
   * the element to test {@link Workload#shouldExecute()}.
   */
  @NotNull
  @Getter
  private final T element;

  @Override
  public final boolean reschedule() {
    return this.test(this.element);
  }
}
