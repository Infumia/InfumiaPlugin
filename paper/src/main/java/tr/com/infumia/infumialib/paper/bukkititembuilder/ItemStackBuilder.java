package tr.com.infumia.infumialib.paper.bukkititembuilder;

import com.cryptomorin.xseries.XMaterial;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.transformer.TransformedData;

/**
 * a class that represents regular item stack builders.
 * <p>
 * serialization:
 * <pre>
 * material: string (item's type) (for 8 and newer versions)
 *
 * amount: integer (item's amount) (for 8 and newer versions)
 *
 * damage: integer (item's damage. known as durability) (for 8 and newer versions)
 *
 * data: integer (item's data) (for 12 and older versions)
 *
 * name: string (item's name) (for 8 and newer versions)
 *
 * lore: string list (item's name) (for 8 and newer versions)
 *   - 'test lore'
 *
 * enchants: (enchantment section) (for 8 and newer versions)
 *   DAMAGE_ALL: integer (enchantment's level)
 *
 * flags: (string list) (for 8 and newer versions)
 *   - 'HIDE_ENCHANTS'
 *
 * custom-model-data: (integer) (for 14 and newer versions)
 * </pre>
 */
public final class ItemStackBuilder extends Builder<ItemStackBuilder, ItemMeta> {

  /**
   * the item stack deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemStack the item stack.
   */
  private ItemStackBuilder(@NotNull final ItemStack itemStack) {
    super(Objects.requireNonNull(itemStack.getItemMeta(), String.format("ItemMeta of %s couldn't get!", itemStack)),
      itemStack);
  }

  /**
   * creates item stack builder from {@link XMaterial}.
   *
   * @param material the material to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final XMaterial material) {
    final var parsed = material.parseMaterial();
    if (parsed == null) {
      throw new IllegalStateException(String.format("Material from the %s cannot be null!",
        material.name()));
    }
    return ItemStackBuilder.from(parsed);
  }

  /**
   * creates item stack builder from {@link Material}.
   *
   * @param material the material to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final Material material) {
    return ItemStackBuilder.from(new ItemStack(material));
  }

  /**
   * creates item stack builder from {@link ItemStack}.
   *
   * @param itemStack the item stack to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final ItemStack itemStack) {
    return new ItemStackBuilder(itemStack);
  }

  /**
   * creates item stack builder from NBT Json string.
   *
   * @param nbtJson the nbt json to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final String nbtJson) {
    return ItemStackBuilder.from(NBTEditor.getItemFromTag(NBTEditor.getNBTCompound(nbtJson)));
  }

  /**
   * creates item stack builder from serialized data.
   *
   * @param data the data to create.
   *
   * @return a newly created item stack builder instance.
   */
  @NotNull
  public static ItemStackBuilder from(@NotNull final TransformedData data) {
    return ItemStackBuilder.getDeserializer().apply(data).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given data is incorrect!\n%s", data)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return ItemStackBuilder.DESERIALIZER;
  }

  @Override
  @NotNull
  public ItemStackBuilder getSelf() {
    return this;
  }

  /**
   * a class that represents deserializer of {@link ItemMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull TransformedData, @NotNull Optional<ItemStackBuilder>> {

    @NotNull
    @Override
    public Optional<ItemStackBuilder> apply(@NotNull final TransformedData data) {
      return Builder.getItemStackDeserializer().apply(data)
        .map(ItemStackBuilder::from)
        .map(Builder::getItemMetaDeserializer)
        .map(deserializer -> deserializer.apply(data));
    }
  }
}
