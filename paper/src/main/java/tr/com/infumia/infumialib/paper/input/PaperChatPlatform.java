package tr.com.infumia.infumialib.paper.input;

import com.google.common.base.Preconditions;
import tr.com.infumia.infumialib.input.ChatInput;
import tr.com.infumia.infumialib.input.ChatPlatform;
import tr.com.infumia.infumialib.input.ChatSender;
import tr.com.infumia.infumialib.input.ChatTask;
import io.papermc.paper.event.player.AsyncChatEvent;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation of {@link ChatPlatform}.
 */
@RequiredArgsConstructor
public final class PaperChatPlatform implements ChatPlatform<Player>, Listener {

  /**
   * the input.
   */
  @NotNull
  private final AtomicReference<ChatInput<?, Player>> input = new AtomicReference<>();

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  /**
   * creates a new builder instance.
   *
   * @param sender the sender to create.
   * @param <T> type of the value.
   *
   * @return a newly created builder instance.
   *
   * @throws IllegalArgumentException if the server has not any plugin.
   */
  @NotNull
  public static <T> ChatInput.Builder<T, Player> builder(@NotNull final Player sender) {
    final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
    Preconditions.checkArgument(plugins.length != 0, "not found any plugin");
    return PaperChatPlatform.builder(plugins[0], sender);
  }

  /**
   * creates a new builder instance.
   *
   * @param plugin the plugin to create.
   * @param sender the sender to create.
   * @param <T> type of the value.
   *
   * @return a newly created builder instance.
   */
  @NotNull
  public static <T> ChatInput.Builder<T, Player> builder(@NotNull final Plugin plugin,
                                                         @NotNull final Player sender) {
    return PaperChatPlatform.builder(new PaperChatPlatform(plugin), sender);
  }

  /**
   * creates a new builder instance.
   *
   * @param platform the platform to create.
   * @param sender the sender to create.
   * @param <T> type of the value.
   *
   * @return a newly created builder instance.
   */
  @NotNull
  public static <T> ChatInput.Builder<T, Player> builder(@NotNull final ChatPlatform<Player> platform,
                                                         @NotNull final Player sender) {
    return PaperChatPlatform.builder(platform, new PprChatSender(sender));
  }

  /**
   * creates a new builder instance.
   *
   * @param platform the platform to create.
   * @param sender the sender to create.
   * @param <T> type of the value.
   *
   * @return a newly created builder instance.
   */
  @NotNull
  public static <T> ChatInput.Builder<T, Player> builder(@NotNull final ChatPlatform<Player> platform,
                                                         @NotNull final ChatSender<Player> sender) {
    return ChatInput.builder(platform, sender);
  }

  @NotNull
  @Override
  public ChatTask createRunTaskLater(@NotNull final Runnable runnable, final long time) {
    return new PprChatTask(Bukkit.getScheduler().runTaskLater(this.plugin, runnable, time));
  }

  @Override
  public void init(@NotNull final ChatInput<?, Player> input) {
    this.input.set(input);
    Bukkit.getPluginManager().registerEvents(this, this.plugin);
  }

  @Override
  public void unregisterListeners() {
    HandlerList.unregisterAll(this);
  }

  /**
   * runs when the player sends a chat message.
   *
   * @param event the event to handle.
   */
  @EventHandler(priority = EventPriority.LOWEST)
  public void whenChat(@NotNull final AsyncChatEvent event) {
    this.input.get().onChat(new PprChatEvent(event, new PprChatSender(event.getPlayer())));
  }

  /**
   * runs when the player quits the game.
   *
   * @param event the event to handle.
   */
  @EventHandler
  public void whenQuit(@NotNull final PlayerQuitEvent event) {
    this.input.get().onQuit(new PprQuitEvent(new PprChatSender(event.getPlayer())));
  }
}
