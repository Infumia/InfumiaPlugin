package tr.com.infumia.infumialib.paper.bukkititembuilder;

import com.cryptomorin.xseries.XItemStack;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.Keys;
import tr.com.infumia.infumialib.transformer.TransformedData;

/**
 * a class that represents leather armor item builders.
 * <p>
 * serialization:
 * <pre>
 * color: 'red, green, blue' (leather's color) (for 8 and newer versions)
 * </pre>
 */
public final class LeatherArmorItemBuilder extends Builder<LeatherArmorItemBuilder, LeatherArmorMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  LeatherArmorItemBuilder(@NotNull final LeatherArmorMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new leather armor item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created leather armor item builder instance.
   */
  @NotNull
  public static LeatherArmorItemBuilder from(@NotNull final LeatherArmorMeta itemMeta,
                                             @NotNull final ItemStack itemStack) {
    return new LeatherArmorItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates leather armor item builder from serialized data.
   *
   * @param data the data to create.
   *
   * @return a newly created leather armor item builder instance.
   */
  @NotNull
  public static LeatherArmorItemBuilder from(@NotNull final TransformedData data) {
    return LeatherArmorItemBuilder.getDeserializer().apply(data).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given data is incorrect!\n%s", data)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return LeatherArmorItemBuilder.DESERIALIZER;
  }

  @NotNull
  @Override
  public LeatherArmorItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final TransformedData data) {
    super.serialize(data);
    final var color = this.getItemMeta().getColor();
    data.add(Keys.COLOR_KEY, String.format("%d, %d, %d",
      color.getRed(), color.getGreen(), color.getBlue()), String.class);
  }

  /**
   * sets color of the armor.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public LeatherArmorItemBuilder setColor(@Nullable final Color color) {
    this.getItemMeta().setColor(color);
    return this.getSelf();
  }

  /**
   * sets color of the armor.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public LeatherArmorItemBuilder setColor(@Nullable final String color) {
    return this.setColor(XItemStack.parseColor(color));
  }

  /**
   * a class that represents deserializer of {@link LeatherArmorMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull TransformedData, @NotNull Optional<LeatherArmorItemBuilder>> {

    @NotNull
    @Override
    public Optional<LeatherArmorItemBuilder> apply(@NotNull final TransformedData data) {
      final var itemStack = Builder.getItemStackDeserializer().apply(data);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asLeatherArmor();
      data.get(Keys.SKULL_TEXTURE_KEY, String.class)
        .ifPresent(builder::setColor);
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(data));
    }
  }
}
