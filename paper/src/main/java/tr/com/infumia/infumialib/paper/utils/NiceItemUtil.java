package tr.com.infumia.infumialib.paper.utils;

import com.cryptomorin.xseries.XMaterial;
import tr.com.infumia.infumialib.paper.bukkititembuilder.ItemStackBuilder;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class NiceItemUtil {

  @NotNull
  public ItemStack getGreenItemStack() {
    if (Versions.MAJOR < 13) {
      return Objects.requireNonNull(XMaterial.GREEN_WOOL.parseItem(), "green wool");
    }
    return ItemStackBuilder.from(XMaterial.GREEN_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getRedItemStack() {
    if (Versions.MAJOR < 13) {
      return Objects.requireNonNull(XMaterial.RED_WOOL.parseItem(), "red wool");
    }
    return ItemStackBuilder.from(XMaterial.RED_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getWhiteItemStack() {
    if (Versions.MAJOR < 13) {
      return Objects.requireNonNull(XMaterial.WHITE_WOOL.parseItem(), "white wool");
    }
    return ItemStackBuilder.from(XMaterial.WHITE_CONCRETE).getItemStack();
  }

  @NotNull
  public ItemStack getYellowItemStack() {
    if (Versions.MAJOR < 13) {
      return Objects.requireNonNull(XMaterial.YELLOW_WOOL.parseItem(), "yello wool");
    }
    return ItemStackBuilder.from(XMaterial.YELLOW_CONCRETE).getItemStack();
  }
}
