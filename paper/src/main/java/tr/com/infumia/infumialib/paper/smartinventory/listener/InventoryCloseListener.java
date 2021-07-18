package tr.com.infumia.infumialib.paper.smartinventory.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;

/**
 * a class that represents inventory close listeners.
 */
@RequiredArgsConstructor
public final class InventoryCloseListener implements Listener {

  /**
   * listens inventory close events.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onInventoryClose(final InventoryCloseEvent event) {
    SmartInventory.getHolder(event.getPlayer().getUniqueId()).ifPresent(holder ->
      holder.getPage().close(holder));
  }
}
