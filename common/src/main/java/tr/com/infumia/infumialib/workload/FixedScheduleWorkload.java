package tr.com.infumia.infumialib.workload;

import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract implementation for {@link ConditionalScheduleWorkload} and,
 * computes the workload the given number times.
 */
public abstract class FixedScheduleWorkload extends ConditionalScheduleWorkload<AtomicLong> {

  /**
   * ctor.
   *
   * @param numberOfExecutions the number of executions.
   */
  protected FixedScheduleWorkload(@NotNull final AtomicLong numberOfExecutions) {
    super(numberOfExecutions);
  }

  /**
   * ctor.
   *
   * @param numberOfExecutions the number of executions.
   */
  protected FixedScheduleWorkload(final long numberOfExecutions) {
    this(new AtomicLong(numberOfExecutions));
  }

  @Override
  public final boolean test(final AtomicLong atomicInteger) {
    return atomicInteger.decrementAndGet() > 0L;
  }
}
