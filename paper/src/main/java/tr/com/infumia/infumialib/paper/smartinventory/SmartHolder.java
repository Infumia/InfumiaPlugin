package tr.com.infumia.infumialib.paper.smartinventory;

import org.bukkit.entity.Player;
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

  /**
   * obtains the page.
   *
   * @return page.
   */
  @NotNull
  Page getPage();

  /**
   * obtains the player.
   *
   * @return player.
   */
  @NotNull
  Player getPlayer();

  /**
   * obtains the plugin.
   *
   * @return plugin.
   */
  @NotNull
  Plugin getPlugin();

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
