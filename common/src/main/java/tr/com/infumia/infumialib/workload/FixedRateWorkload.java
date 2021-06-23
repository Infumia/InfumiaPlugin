package tr.com.infumia.infumialib.workload;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract implementation for {@link ConditionalWorkload} and,
 * computes the workload per the given tick.
 */
public abstract class FixedRateWorkload extends ConditionalWorkload<Integer> {

  /**
   * the checked.
   */
  private final AtomicInteger checked = new AtomicInteger(0);

  /**
   * ctor.
   *
   * @param ticksPerExecution the tick per execution.
   */
  protected FixedRateWorkload(final int ticksPerExecution) {
    super(ticksPerExecution);
  }

  @Override
  public final boolean test(@NotNull final Integer ticksPerExecution) {
    return this.checked.incrementAndGet() % ticksPerExecution == 0;
  }
}
