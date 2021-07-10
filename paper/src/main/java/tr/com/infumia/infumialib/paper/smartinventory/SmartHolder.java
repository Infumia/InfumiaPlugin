package tr.com.infumia.infumialib.paper.smartinventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine inventory holders.
 */
public interface SmartHolder extends InventoryHolder {

  /**
   * obtains the contents.
   *
   * @return contents.
   */
  @NotNull
  InventoryContents getContents();

  @Override
  @NotNull
  default Inventory getInventory() {
    return this.getContents().getTopInventory();
  }

  /**
   * obtains the page.
   *
   * @return page.
   */
  @NotNull
  default Page getPage() {
    return this.getContents().page();
  }

  /**
   * obtains the player.
   *
   * @return player.
   */
  @NotNull
  default Player getPlayer() {
    return this.getContents().player();
  }

  /**
   * obtains the plugin.
   *
   * @return plugin.
   */
  @NotNull
  default Plugin getPlugin() {
    return this.getPage().inventory().getPlugin();
  }

  /**
   * checks if the holder is active.
   *
   * @return {@code true} if the holder is active.
   */
  boolean isActive();

  /**
   * sets the active.
   *
   * @param active the active to set.
   */
  void setActive(boolean active);
}
