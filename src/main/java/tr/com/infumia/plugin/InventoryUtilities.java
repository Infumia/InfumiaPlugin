/*
 * MIT License
 *
 * Copyright (c) 2021 Infumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.plugin;

import java.util.Map;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class InventoryUtilities {

  public boolean isInventoryNotFull(@NotNull final Player player, @NotNull final ItemStack item) {
    return !InventoryUtilities.isInventoryFull(player, item);
  }

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
      final ItemStack clone = item.clone();
      clone.setAmount(item.getMaxStackSize());
      if (InventoryUtilities.isInventoryFull(player, clone)) {
        return true;
      }
      clone.setAmount(item.getAmount() - item.getMaxStackSize());
      return InventoryUtilities.isInventoryFull(player, clone);
    }
    final Map<Integer, ? extends ItemStack> all = player.getInventory().all(item);
    int amount = item.getAmount();
    for (final ItemStack element : all.values()) {
      amount -= element.getMaxStackSize() - element.getAmount();
    }
    return amount > 0;
  }
}
