package tr.com.infumia.infumialib.paper.smartinventory.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import tr.com.infumia.infumialib.paper.smartinventory.SmartHolder;
import tr.com.infumia.infumialib.paper.smartinventory.event.IcDragEvent;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;

/**
 * a class that represents inventory drag listeners.
 */
public final class InventoryDragListener implements Listener {

  /**
   * listens inventory drag events.
   *
   * @param event the event to listen.
   */
  @EventHandler(priority = EventPriority.LOW)
  public void onInventoryDrag(final InventoryDragEvent event) {
    final var holder = event.getInventory().getHolder();
    if (!(holder instanceof SmartHolder)) {
      return;
    }
    final var smartHolder = (SmartHolder) holder;
    final var inventory = event.getInventory();
    final var contents = smartHolder.getContents();
    for (final var slot : event.getRawSlots()) {
      final var pos = SlotPos.of(slot / 9, slot % 9);
      contents.get(pos).ifPresent(icon ->
        icon.accept(new IcDragEvent(contents, event, icon, smartHolder.getPlugin())));
      if (slot >= inventory.getSize() || contents.isEditable(pos)) {
        continue;
      }
      event.setCancelled(true);
      break;
    }
  }
}
