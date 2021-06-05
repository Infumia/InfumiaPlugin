package tr.com.infumia.infumialib.paper.utils;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.InfumiaLib;

@UtilityClass
public class NiceItemUtil {

  private final int VERSION = InfumiaLib.SERVER_VERSION.getMajor();

  @NotNull
  public ItemStack getGreenItemStack() {
    if (NiceItemUtil.VERSION < 13) {
      return new ItemStack(Material.valueOf("WOOL"), 1, (byte) 5);
    }
    return ItemStackBuilder.from(XMaterial.GREEN_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getRedItemStack() {
    if (NiceItemUtil.VERSION < 13) {
      return new ItemStack(Material.valueOf("WOOL"), 1, (byte) 14);
    }
    return ItemStackBuilder.from(XMaterial.RED_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getWhiteItemStack() {
    if (NiceItemUtil.VERSION < 13) {
      return new ItemStack(Material.valueOf("WOOL"), 1);
    }
    return ItemStackBuilder.from(XMaterial.WHITE_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getYellowItemStack() {
    if (NiceItemUtil.VERSION < 13) {
      return new ItemStack(Material.valueOf("WOOL"), 1, (byte) 4);
    }
    return ItemStackBuilder.from(XMaterial.YELLOW_CONCRETE).getItemStack();
  }
}
