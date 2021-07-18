package tr.com.infumia.infumialib.paper.smartinventory.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.event.IcClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgBottomClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgOutsideClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;

/**
 * a class that represents inventory click listeners.
 */
public final class InventoryClickListener implements Listener {

  /**
   * listens inventory click events.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onInventoryClick(final InventoryClickEvent event) {
    SmartInventory.getHolder(event.getWhoClicked().getUniqueId()).ifPresent(holder -> {
      if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
        event.setCancelled(true);
        return;
      }
      final var page = holder.getPage();
      final var contents = holder.getContents();
      final var plugin = holder.getPlugin();
      final var clicked = event.getClickedInventory();
      if (clicked == null) {
        page.accept(new PgOutsideClickEvent(contents, event, plugin));
        return;
      }
      final var player = event.getWhoClicked();
      if (clicked.equals(player.getOpenInventory().getBottomInventory())) {
        page.accept(new PgBottomClickEvent(contents, event, plugin));
        return;
      }
      final var current = event.getCurrentItem();
      if (current == null || current.getType() == Material.AIR) {
        page.accept(new PgClickEvent(contents, event, plugin));
        return;
      }
      final var slot = event.getSlot();
      final var row = slot / 9;
      final var column = slot % 9;
      if (!page.checkBounds(row, column)) {
        return;
      }
      final var slotPos = SlotPos.of(row, column);
      if (!contents.isEditable(slotPos)) {
        event.setCancelled(true);
      }
      contents.get(slotPos).ifPresent(item ->
        item.accept(new IcClickEvent(contents, event, item, plugin)));
      if (!contents.isEditable(slotPos) && player instanceof Player) {
        ((Player) player).updateInventory();
      }
    });
  }
}
