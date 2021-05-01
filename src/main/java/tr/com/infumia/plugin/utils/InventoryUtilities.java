package tr.com.infumia.plugin.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class InventoryUtilities {

  public boolean isInventoryFull(@NotNull final Player player, @NotNull final ItemStack item) {
    if (item.getType() == Material.AIR) {
      return false;
    }
    if (item.getAmount() > 5000) {
      return true;
    }
    if (player.getInventory().firstEmpty() >= 0 && item.getAmount() <= item.getMaxStackSize()) {
      return false;
    }
    if (item.getAmount() > item.getMaxStackSize()) {
      final var clone = item.clone();
      clone.setAmount(item.getMaxStackSize());
      if (InventoryUtilities.isInventoryFull(player, clone)) {
        return true;
      }
      clone.setAmount(item.getAmount() - item.getMaxStackSize());
      return InventoryUtilities.isInventoryFull(player, clone);
    }
    final var all = player.getInventory().all(item);
    var amount = item.getAmount();
    for (final var element : all.values()) {
      amount -= element.getMaxStackSize() - element.getAmount();
    }
    return amount > 0;
  }

  public boolean isInventoryNotFull(@NotNull final Player player, @NotNull final ItemStack item) {
    return !InventoryUtilities.isInventoryFull(player, item);
  }
}
