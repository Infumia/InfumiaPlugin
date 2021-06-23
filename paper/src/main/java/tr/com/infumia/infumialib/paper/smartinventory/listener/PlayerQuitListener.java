package tr.com.infumia.infumialib.paper.smartinventory.listener;

import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.event.PlyrQuitEvent;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents player quit listeners.
 */
@RequiredArgsConstructor
public final class PlayerQuitListener implements Listener {

  /**
   * the stop tick function.
   */
  @NotNull
  private final Consumer<UUID> stopTickFunction;

  /**
   * listens the player quit event.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onPlayerQuit(final PlayerQuitEvent event) {
    SmartInventory.getHolder(event.getPlayer()).ifPresent(holder -> {
      holder.getPage().accept(new PlyrQuitEvent(holder.getContents(), event));
      this.stopTickFunction.accept(event.getPlayer().getUniqueId());
    });
  }
}
