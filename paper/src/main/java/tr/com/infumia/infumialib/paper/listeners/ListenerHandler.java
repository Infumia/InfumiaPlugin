package tr.com.infumia.infumialib.paper.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract listener class that helps to create and register listener.
 *
 * @param <E> type of the event to handle.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ListenerHandler<E extends Event> implements Listener {

  /**
   * the plugin.
   */
  @NotNull
  @Getter(AccessLevel.PROTECTED)
  private final Plugin plugin;

  /**
   * the plugin manager.
   */
  @NotNull
  private final PluginManager pluginManager;

  /**
   * ctor.
   *
   * @param plugin the plugin.
   */
  protected ListenerHandler(@NotNull final Plugin plugin) {
    this(plugin, plugin.getServer().getPluginManager());
  }

  /**
   * registers the listener.
   */
  public final void register() {
    this.pluginManager.registerEvents(this, this.plugin);
  }

  /**
   * unregisters the listener.
   */
  public final void unregister() {
    HandlerList.unregisterAll(this);
  }

  /**
   * handles the event.
   *
   * @param event the event to handle.
   */
  public abstract void handle(@NotNull E event);
}
