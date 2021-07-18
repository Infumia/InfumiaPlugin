package tr.com.infumia.infumialib.paper.smartinventory.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.event.PlyrQuitEvent;

/**
 * a class that represents player quit listeners.
 */
@RequiredArgsConstructor
public final class PlayerQuitListener implements Listener {

  /**
   * listens the player quit event.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onPlayerQuit(final PlayerQuitEvent event) {
    SmartInventory.getHolder(event.getPlayer()).ifPresent(holder -> {
      final var page = holder.getPage();
      page.accept(new PlyrQuitEvent(holder.getContents(), event));
      page.inventory().stopTick(event.getPlayer().getUniqueId());
    });
  }
}
