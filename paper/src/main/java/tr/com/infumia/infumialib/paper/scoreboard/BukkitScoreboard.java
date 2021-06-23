package tr.com.infumia.infumialib.paper.scoreboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents initializer for Bukkit's scoreboard system.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitScoreboard implements Listener, AutoCloseable {

  /**
   * the plugin.
   */
  @NotNull
  @Getter
  private final Plugin plugin;

  /**
   * the thread.
   */
  @NotNull
  private final BukkitScoreboardThread thread;

  /**
   * initiate the scoreboard system.
   *
   * @param plugin the plugin to initiate.
   * @param tick the tick to initiate.
   *
   * @return a bukkit scoreboard sender instance to use.
   */
  @NotNull
  public static BukkitScoreboard create(@NotNull final Plugin plugin, final long tick) {
    return new BukkitScoreboard(plugin, new BukkitScoreboardThread(new BukkitScoreboardSender(plugin), tick));
  }

  @Override
  public void close() {
    this.thread.interrupt();
    this.thread.getSender().close();
    HandlerList.unregisterAll(this);
  }

  /**
   * obtains the sender.
   *
   * @return sender.
   */
  @NotNull
  public BukkitScoreboardSender getSender() {
    return this.thread.getSender();
  }

  /**
   * runs when a player quits.
   *
   * @param event the event to handle.
   */
  @EventHandler
  public void handle(final PlayerQuitEvent event) {
    this.getSender().onQuit(event.getPlayer());
  }

  /**
   * runs when the plugin disables.
   *
   * @param event the event to handle.
   */
  @EventHandler
  public void handle(final PluginDisableEvent event) {
    this.close();
  }

  /**
   * registers the listener and starts the thread.
   */
  public void setup() {
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    this.thread.start();
  }
}
