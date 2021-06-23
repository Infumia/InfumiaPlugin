package tr.com.infumia.infumialib.workload;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jetbrains.annotations.NotNull;

/**
 * a class that stores and computes {@link Workload} instances.
 */
public final class WorkloadThread implements Runnable {

  /**
   * the work deque.
   */
  private final Queue<Workload> deque = new ConcurrentLinkedQueue<>();

  /**
   * the maximum nano per tick.
   */
  private final long maxNanosPerTick;

  /**
   * the work thread id.
   */
  private final long workThreadId;

  /**
   * ctor.
   *
   * @param workThreadId the work thread id.
   * @param maxNanosPerTick the maximum nano per tick.
   */
  WorkloadThread(final long workThreadId, final long maxNanosPerTick) {
    this.workThreadId = workThreadId;
    this.maxNanosPerTick = maxNanosPerTick;
  }

  @Override
  public void run() {
    final var stopTime = System.nanoTime() + this.maxNanosPerTick;
    final var first = this.deque.poll();
    if (first == null) {
      return;
    }
    this.computeWorkload(first);
    Workload workload;
    while ((workload = this.deque.poll()) != null && System.nanoTime() <= stopTime) {
      this.computeWorkload(workload);
      if (!first.reschedule() && first.equals(workload)) {
        break;
      }
    }
  }

  /**
   * runs {@link Workload#compute()} if {@link Workload#shouldExecute()} is {@code true}.
   * also reschedules if {@link Workload#reschedule()} is {@code true}.
   *
   * @param workload the workload to compute.
   */
  private void computeWorkload(@NotNull final Workload workload) {
    if (workload.shouldExecute()) {
      workload.compute();
    }
    if (workload.reschedule()) {
      this.deque.add(workload);
    }
  }
}
