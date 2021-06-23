package tr.com.infumia.infumialib.paper.smartinventory.listener;

import tr.com.infumia.infumialib.paper.smartinventory.SmartHolder;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgOpenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * a class that represents inventory open listeners.
 */
public final class InventoryOpenListener implements Listener {

  /**
   * listens the inventory open events.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onInventoryOpen(final InventoryOpenEvent event) {
    final var holder = event.getInventory().getHolder();
    if (!(holder instanceof SmartHolder)) {
      return;
    }
    final var smartHolder = (SmartHolder) holder;
    smartHolder.getPage().accept(new PgOpenEvent(smartHolder.getContents(), event, smartHolder.getPlugin()));
  }
}
