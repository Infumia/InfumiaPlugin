package tr.com.infumia.infumialib.paper.smartinventory.opener;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryOpener;
import tr.com.infumia.infumialib.paper.smartinventory.holder.SmartInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * an {@link InventoryType#CHEST} implementation for {@link InventoryOpener}.
 */
public final class ChestInventoryOpener implements InventoryOpener {

  @NotNull
  @Override
  public Inventory open(@NotNull final InventoryContents contents) {
    final var page = contents.page();
    if (page.column() != 9) {
      throw new IllegalArgumentException(
        String.format("The column count for the chest inventory must be 9, found: %s.", page.column()));
    }
    if (page.row() < 1 && page.row() > 6) {
      throw new IllegalArgumentException(
        String.format("The row count for the chest inventory must be between 1 and 6, found: %s", page.row()));
    }
    final var holder = new SmartInventoryHolder(contents);
    holder.setActive(true);
    final var handle = Bukkit.createInventory(holder, page.row() * page.column(), page.title());
    this.fill(handle, contents);
    contents.player().openInventory(handle);
    return handle;
  }

  @Override
  public boolean supports(@NotNull final InventoryType type) {
    return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
  }
}
