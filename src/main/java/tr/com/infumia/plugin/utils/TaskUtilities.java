package tr.com.infumia.plugin.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class TaskUtilities {

  @Nullable
  private Plugin plugin;

  @NotNull
  public BukkitTask async(@NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTaskAsynchronously(TaskUtilities.getPlugin());
  }

  @NotNull
  public BukkitTask asyncLater(final long delay, @NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTaskLaterAsynchronously(TaskUtilities.getPlugin(), delay);
  }

  @NotNull
  public BukkitTask asyncTimer(final long period, @NotNull final Runnable runnable) {
    return TaskUtilities.asyncTimerLater(0L, period, runnable);
  }

  @NotNull
  public BukkitTask asyncTimer(final long period, @NotNull final Supplier<Boolean> supplier) {
    return TaskUtilities.asyncTimerLater(0L, period, supplier);
  }

  @NotNull
  public BukkitTask asyncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> supplier) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        if (!supplier.get()) {
          this.cancel();
        }
      }
    }.runTaskTimerAsynchronously(TaskUtilities.getPlugin(), delay, period);
  }

  @NotNull
  public BukkitTask asyncTimerLater(final long delay, final long period, @NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTaskTimerAsynchronously(TaskUtilities.getPlugin(), delay, period);
  }

  @NotNull
  public <T> BukkitTask forAll(@NotNull final List<T> objects, @NotNull final Consumer<T> runArgument) {
    return TaskUtilities.forAll(1, objects, runArgument);
  }

  @NotNull
  public <T> BukkitTask forAll(final int perTick, final List<T> objects, @NotNull final Consumer<T> runArgument) {
    return TaskUtilities.forAll(perTick, objects, runArgument, () -> {
    });
  }

  @NotNull
  public <T> BukkitTask forAll(final int perTick, @NotNull final List<T> objects,
                               @NotNull final Consumer<T> runArgument, @NotNull final Runnable onDone) {
    return new BukkitRunnable() {
      private int current = 0;

      @Override
      public void run() {
        IntStream.range(0, perTick)
          .takeWhile(i -> this.current < objects.size())
          .mapToObj(i -> objects.get(this.current))
          .forEach(object -> {
            try {
              runArgument.accept(object);
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

  public void init(@NotNull final Plugin plugin) {
    TaskUtilities.plugin = plugin;
  }

  @NotNull
  public BukkitTask sync(@NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTask(TaskUtilities.getPlugin());
  }

  @NotNull
  public BukkitTask syncLater(final long delay, @NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTaskLater(TaskUtilities.getPlugin(), delay);
  }

  @NotNull
  public BukkitTask syncTimer(final long period, @NotNull final Runnable runnable) {
    return TaskUtilities.syncTimerLater(0L, period, runnable);
  }

  @NotNull
  public BukkitTask syncTimer(final long period, @NotNull final Supplier<Boolean> supplier) {
    return TaskUtilities.syncTimerLater(0L, period, supplier);
  }

  @NotNull
  public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Supplier<Boolean> supplier) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        if (!supplier.get()) {
          this.cancel();
        }
      }
    }.runTaskTimer(TaskUtilities.getPlugin(), delay, period);
  }

  @NotNull
  public BukkitTask syncTimerLater(final long delay, final long period, @NotNull final Runnable runnable) {
    return new BukkitRunnable() {
      @Override
      public void run() {
        runnable.run();
      }
    }.runTaskTimer(TaskUtilities.getPlugin(), delay, period);
  }

  @NotNull
  private Plugin getPlugin() {
    return Optional.ofNullable(TaskUtilities.plugin)
      .orElseThrow(() ->
        new RuntimeException("Use TaskUtilities#init(Plugin) first!"));
  }
}
