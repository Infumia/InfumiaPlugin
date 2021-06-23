package tr.com.infumia.infumialib.paper.scoreboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents Bukkit scoreboard thread..
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class BukkitScoreboardThread extends Thread {

  /**
   * the sender.
   */
  @NotNull
  private final BukkitScoreboardSender sender;

  /**
   * the tick.
   */
  private final long tick;

  @Override
  public void run() {
    while (true) {
      try {
        this.tick();
        //noinspection BusyWait
        Thread.sleep(this.tick * 50);
      } catch (final InterruptedException ignored) {
      }
    }
  }

  /**
   * runs every {@link #tick} times 50.
   */
  private void tick() {
    this.sender.getScoreboards().forEach(scoreboard -> {
      try {
        scoreboard.tick();
      } catch (final Exception e) {
        e.printStackTrace();
        throw new IllegalStateException(String.format("There was an error updating %s's scoreboard.",
          scoreboard.getUniqueId()));
      }
    });
  }
}
