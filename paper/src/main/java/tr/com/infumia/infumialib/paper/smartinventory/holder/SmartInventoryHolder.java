package tr.com.infumia.infumialib.paper.smartinventory.holder;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.Page;
import tr.com.infumia.infumialib.paper.smartinventory.SmartHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that implements {@link SmartHolder}.
 */
@Getter
@RequiredArgsConstructor
public final class SmartInventoryHolder implements SmartHolder {

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  /**
   * the active.
   */
  @Setter
  private boolean active = true;

  @NotNull
  @Override
  public Inventory getInventory() {
    return this.contents.getTopInventory();
  }

  @NotNull
  @Override
  public Page getPage() {
    return this.contents.page();
  }

  @NotNull
  @Override
  public Player getPlayer() {
    return this.contents.player();
  }

  @NotNull
  @Override
  public Plugin getPlugin() {
    return this.getPage().inventory().getPlugin();
  }
}
