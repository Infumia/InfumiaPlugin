package tr.com.infumia.infumialib.paper.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * a class that wraps {@link BukkitRunnable}.
 */
@RequiredArgsConstructor
public final class RunnableWrapper extends BukkitRunnable {

  /**
   * the delegate.
   */
  @NotNull
  private final Consumer<BukkitRunnable> consumer;

  /**
   * the predicate.
   */
  @NotNull
  private final Predicate<BukkitRunnable> predicate;

  /**
   * ctor.
   *
   * @param consumer the consumer.
   */
  public RunnableWrapper(@NotNull final Consumer<BukkitRunnable> consumer) {
    this(consumer, runnable -> true);
  }

  @Override
  public void run() {
    if (this.predicate.test(this)) {
      this.consumer.accept(this);
    }
  }
}
