package tr.com.infumia.infumialib.workload;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * a class that stores and distributes {@link WorkloadThread} instances.
 */
public final class WorkloadDistributor implements Runnable {

  /**
   * the work load thread map.
   */
  private final Map<Long, WorkloadThread> map = new HashMap<>();

  /**
   * the next work load id.
   */
  private long nextId = 0L;

  /**
   * creates a new {@link WorkloadThread} instance.
   *
   * @param nanoPerTick the nano per tick.
   *
   * @return a new {@link WorkloadThread} instance.
   */
  @NotNull
  public WorkloadThread createThread(final long nanoPerTick) {
    final var thread = new WorkloadThread(++this.nextId, nanoPerTick);
    this.map.put(this.nextId, thread);
    return thread;
  }

  @Override
  public void run() {
    this.map.values().forEach(WorkloadThread::run);
  }
}
