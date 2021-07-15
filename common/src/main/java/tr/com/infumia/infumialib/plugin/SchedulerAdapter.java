package tr.com.infumia.infumialib.plugin;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public interface SchedulerAdapter {

  @NotNull
  Executor async();

  @NotNull
  SchedulerTask asyncLater(@NotNull Runnable task, long delay, @NotNull TimeUnit unit);

  @NotNull
  SchedulerTask asyncRepeating(@NotNull Runnable task, long interval, @NotNull TimeUnit unit);

  default void executeAsync(@NotNull final Runnable task) {
    this.async().execute(task);
  }

  default void executeSync(@NotNull final Runnable task) {
    this.sync().execute(task);
  }

  void shutdownExecutor();

  void shutdownScheduler();

  @NotNull
  Executor sync();
}
