package tr.com.infumia.infumialib.paper.bukkititembuilder.util;

import com.cryptomorin.xseries.XMaterial;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.bukkititembuilder.BannerItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.BookItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.Buildable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.Builder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.CrossbowItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.FireworkItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.ItemStackBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.LeatherArmorItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.MapItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.PotionItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.SkullItemBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.SpawnEggItemBuilder;

/**
 * a class that contains utility methods for {@link ItemStack}.
 */
public final class ItemStackUtil {

  /**
   * ctor.
   */
  private ItemStackUtil() {
  }

  /**
   * deserializes the given holder into item stack.
   *
   * @param holder the holder to serialize.
   *
   * @return serialized object.
   */
  @NotNull
  public static Optional<ItemStack> deserialize(@NotNull final KeyUtil.Holder<?> holder) {
    return Builder.getSimpleItemStackDeserializer().apply(holder)
      .flatMap(builder -> {
        if (builder.isFirework()) {
          return FireworkItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isLeatherArmor()) {
          return LeatherArmorItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isMap()) {
          return MapItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isPotion()) {
          return PotionItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isBanner()) {
          return BannerItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isBook()) {
          return BookItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isCrossbow()) {
          return CrossbowItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isSkull()) {
          return SkullItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        if (builder.isSpawnEgg()) {
          return SpawnEggItemBuilder.getDeserializer().apply(holder)
            .map(Buildable::getItemStack);
        }
        return ItemStackBuilder.getDeserializer().apply(holder)
          .map(Buildable::getItemStack);
      });
  }

  /**
   * parses the given material string into a new material.
   *
   * @param materialString the material string to parse.
   *
   * @return parsed material.
   */
  @NotNull
  public static Optional<Material> parseMaterial(@NotNull final String materialString) {
    if (Builder.VERSION <= 7) {
      return Optional.ofNullable(Material.getMaterial(materialString));
    }
    final var xMaterial = XMaterial.matchXMaterial(materialString);
    if (xMaterial.isEmpty()) {
      return Optional.empty();
    }
    final var material = Optional.ofNullable(xMaterial.get().parseMaterial());
    if (material.isEmpty()) {
      return Optional.empty();
    }
    return material;
  }

  /**
   * serializes the given item stack into map.
   *
   * @param itemStack the item stack to serialize.
   * @param holder the holder to serialize.
   */
  public static void serialize(@NotNull final ItemStack itemStack, @NotNull final KeyUtil.Holder<?> holder) {
    ItemStackUtil.serialize(ItemStackBuilder.from(itemStack), holder);
  }

  /**
   * serializes the given item stack into map.
   *
   * @param builder the item stack to builder.
   * @param holder the holder to serialize.
   */
  public static void serialize(@NotNull final Builder<?, ?> builder, @NotNull final KeyUtil.Holder<?> holder) {
    if (builder.isFirework()) {
      builder.asFirework().serialize(holder);
    } else if (builder.isLeatherArmor()) {
      builder.asLeatherArmor().serialize(holder);
    } else if (builder.isMap()) {
      builder.asMap().serialize(holder);
    } else if (builder.isPotion()) {
      builder.asPotion().serialize(holder);
    } else if (builder.isBanner()) {
      builder.asBanner().serialize(holder);
    } else if (builder.isBook()) {
      builder.asBook().serialize(holder);
    } else if (builder.isCrossbow()) {
      builder.asCrossbow().serialize(holder);
    } else if (builder.isSkull()) {
      builder.asSkull().serialize(holder);
    } else if (builder.isSpawnEgg()) {
      builder.asSpawnEgg().serialize(holder);
    } else {
      builder.serialize(holder);
    }
  }
}
