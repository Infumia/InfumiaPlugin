package tr.com.infumia.plugin.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that contains utility methods for {@link BukkitTask}.
 */
@UtilityClass
public class TaskUtilities {

  /**
   * the plugin.
   */
  @Nullable
  private Plugin plugin;

  /**
   * runs the given job.
   *
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask async(@NotNull final Runnable job) {
    return Bukkit.getScheduler().runTaskAsynchronously(TaskUtilities.getPlugin(), job);
  }

  /**
   * runs the given job with a delay.
   *
   * @param delay the delay to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask asyncLater(final long delay, @NotNull final Runnable job) {
    return Bukkit.getScheduler().runTaskLaterAsynchronously(TaskUtilities.getPlugin(), job, delay);
  }

  /**
   * runs the given job with a period.
   *
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask asyncTimer(final long period, @NotNull final Runnable job) {
    return TaskUtilities.asyncTimerLater(0L, period, job);
  }

  /**
   * runs the given job with a period, then if the job returns {@code false} stops the task.
   *
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask asyncTimer(final long period, @NotNull final Supplier<Boolean> job) {
    return TaskUtilities.asyncTimerLater(0L, period, job);
  }

  /**
   * runs the given job with a delay and period, then if the job returns {@code false} stops the task.
   *
   * @param delay the delay to run.
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask asyncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> job) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        if (!job.get()) {
          this.cancel();
        }
      }
    }.runTaskTimerAsynchronously(TaskUtilities.getPlugin(), delay, period);
  }

  /**
   * runs the given job with a delay and period.
   *
   * @param delay the delay to run.
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask asyncTimerLater(final long delay, final long period, @NotNull final Runnable job) {
    return Bukkit.getScheduler().runTaskTimerAsynchronously(TaskUtilities.getPlugin(), job, delay, period);
  }

  /**
   * initiates the task utilities.
   *
   * @param plugin the plugin to initiate.
   */
  public void init(@NotNull final Plugin plugin) {
    TaskUtilities.plugin = plugin;
  }

  /**
   * runs the given job for all objects.
   *
   * @param objects the objects to run for all.
   * @param job the run argument to run for all.
   * @param <T> type of the object.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public <T> BukkitTask runForAll(@NotNull final List<T> objects, @NotNull final Consumer<T> job) {
    return TaskUtilities.runForAll(1, objects, job);
  }

  /**
   * runs the given job for all objects.
   *
   * @param perTick the per tick to run for all.
   * @param objects the objects to run for all.
   * @param job the run argument to run for all.
   * @param <T> type of the object.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public <T> BukkitTask runForAll(final int perTick, final List<T> objects, @NotNull final Consumer<T> job) {
    return TaskUtilities.runForAll(perTick, objects, job, () -> {
    });
  }

  /**
   * runs the given job for all objects.
   *
   * @param perTick the per tick to run for all.
   * @param objects the objects to run for all.
   * @param job the run argument to run for all.
   * @param onDone the on done to run for all.
   * @param <T> type of the object.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public <T> BukkitTask runForAll(final int perTick, @NotNull final List<T> objects, @NotNull final Consumer<T> job,
                                  @NotNull final Runnable onDone) {
    return new BukkitRunnable() {
      private int current = 0;

      @Override
      public void run() {
        IntStream.range(0, perTick)
          .takeWhile(i -> this.current < objects.size())
          .mapToObj(i -> objects.get(this.current))
          .forEach(object -> {
            try {
              job.accept(object);
            } catch (final RuntimeException e) {
              TaskUtilities.getPlugin().getLogger().log(
                Level.SEVERE,
                "TaskUtilities#forAll() iteration failed for object: " +
                  object + (object != null ? " (" + object.getClass().getName() + ")" : ""),
                e);
            }
            this.current++;
          });
        if (this.current >= objects.size()) {
          this.cancel();
          onDone.run();
        }
      }
    }.runTaskTimer(TaskUtilities.getPlugin(), 1L, 1L);
  }

  /**
   * runs the given job.
   *
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask sync(@NotNull final Runnable job) {
    return Bukkit.getScheduler().runTask(TaskUtilities.getPlugin(), job);
  }

  /**
   * runs the given job with a delay.
   *
   * @param delay the delay to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask syncLater(final long delay, @NotNull final Runnable job) {
    return Bukkit.getScheduler().runTaskLater(TaskUtilities.getPlugin(), job, delay);
  }

  /**
   * runs the job with a period.
   *
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask syncTimer(final long period, @NotNull final Runnable job) {
    return TaskUtilities.syncTimerLater(0L, period, job);
  }

  /**
   * runs the job with a period, then if the job returns {@code false} stops the task.
   *
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask syncTimer(final long period, @NotNull final Supplier<Boolean> job) {
    return TaskUtilities.syncTimerLater(0L, period, job);
  }

  /**
   * runs the given job with a delay and period, then if the job returns {@code false} stops the task.
   *
   * @param delay the delay to run.
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> job) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        if (!job.get()) {
          this.cancel();
        }
      }
    }.runTaskTimer(TaskUtilities.getPlugin(), delay, period);
  }

  /**
   * runs the given job with a delay and period.
   *
   * @param delay the delay to run.
   * @param period the period to run.
   * @param job the job to run.
   *
   * @return a newly run bukkit task instance.
   */
  @NotNull
  public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Runnable job) {
    return Bukkit.getScheduler().runTaskTimer(TaskUtilities.getPlugin(), job, delay, period);
  }

  /**
   * obtains the plugin.
   *
   * @return plugin.
   *
   * @throws RuntimeException if {@link #plugin} is null.
   */
  @NotNull
  private Plugin getPlugin() {
    return Optional.ofNullable(TaskUtilities.plugin)
      .orElseThrow(() ->
        new RuntimeException("Use TaskUtilities#init(Plugin) first!"));
  }
}
