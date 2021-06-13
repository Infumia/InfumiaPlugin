package tr.com.infumia.infumialib.paper.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for {@link org.bukkit.inventory.Inventory}.
 */
@UtilityClass
public class InventoryUtilities {

  /**
   * checks if the player's inventory able to get the given item.
   *
   * @param player the player to check.
   * @param item the item to check.
   *
   * @return {@code true} if the player's inventory can't get the given item.
   */
  public boolean isInventoryFull(@NotNull final Player player, @NotNull final ItemStack item) {
    if (item.getType() == Material.AIR) {
      return false;
    }
    final var itemAmount = item.getAmount();
    if (itemAmount > 5000) {
      return true;
    }
    final var inventory = player.getInventory();
    final var maxStackSize = item.getMaxStackSize();
    if (inventory.firstEmpty() >= 0 && itemAmount <= maxStackSize) {
      return false;
    }
    if (itemAmount > maxStackSize) {
      final var clone = item.clone();
      clone.setAmount(maxStackSize);
      if (InventoryUtilities.isInventoryFull(player, clone)) {
        return true;
      }
      clone.setAmount(itemAmount - maxStackSize);
      return InventoryUtilities.isInventoryFull(player, clone);
    }
    final var all = inventory.all(item);
    var amount = itemAmount;
    for (final var element : all.values()) {
      amount -= element.getMaxStackSize() - element.getAmount();
    }
    return amount > 0;
  }

  /**
   * checks if the player's inventory able to get the given item.
   *
   * @param player the player to check.
   * @param item the item to check.
   *
   * @return {@code true} if the player's inventory can get the given item.
   */
  public boolean isInventoryNotFull(@NotNull final Player player, @NotNull final ItemStack item) {
    return !InventoryUtilities.isInventoryFull(player, item);
  }
}
