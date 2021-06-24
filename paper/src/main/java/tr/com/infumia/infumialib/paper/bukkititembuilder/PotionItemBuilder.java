package tr.com.infumia.infumialib.paper.bukkititembuilder;

import com.cryptomorin.xseries.XPotion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.Keys;
import tr.com.infumia.infumialib.transformer.TransformedData;

/**
 * a class that represents potion item builders.
 * <p>
 * serialization:
 * <pre>
 * color: integer (as rgb) (for 11 and newer versions)
 *
 * custom-effects: (string list) (for 9 and newer versions)
 *   - 'effect type name as string, effect duration as integer, effect amplifier as integer'
 *
 * base-effect: 'potion type name as string, potion has extended as boolean, potion is upgraded as boolean' (for 9 and newer versions)
 *
 * level: integer (potion level) (for 8 and older versions)
 *
 * base-effect: 'potion type name as string, potion has extended as boolean, potion is splash as boolean' (for 8 and older versions)
 * </pre>
 */
public final class PotionItemBuilder extends Builder<PotionItemBuilder, PotionMeta> {

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
  PotionItemBuilder(@NotNull final PotionMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new potion item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created potion item builder instance.
   */
  @NotNull
  public static PotionItemBuilder from(@NotNull final PotionMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new PotionItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates potion item builder from serialized data.
   *
   * @param data the data to create.
   *
   * @return a newly created potion item builder instance.
   */
  @NotNull
  public static PotionItemBuilder from(@NotNull final TransformedData data) {
    return PotionItemBuilder.getDeserializer().apply(data).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given data is incorrect!\n%s", data)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return PotionItemBuilder.DESERIALIZER;
  }

  /**
   * adds a custom potion effect to this potion.
   *
   * @param effect the effect to add.
   * @param overwrite true if any existing effect of the same type should be overwritten.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder addCustomEffect(@NotNull final PotionEffect effect, final boolean overwrite) {
    if (Builder.VERSION >= 9) {
      this.getItemMeta().addCustomEffect(effect, overwrite);
    }
    return this.getSelf();
  }

  /**
   * adds a custom potion effect to this potion.
   *
   * @param effects the effects to add.
   * @param overwrite true if any existing effect of the same type should be overwritten.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder addCustomEffects(@NotNull final Collection<String> effects, final boolean overwrite) {
    if (Builder.VERSION >= 9) {
      effects.stream()
        .map(XPotion::parsePotionEffectFromString)
        .filter(Objects::nonNull)
        .forEach(effect -> this.addCustomEffect(effect, overwrite));
    }
    return this.getSelf();
  }

  /**
   * removes all custom potion effects from this potion.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder clearCustomEffects() {
    this.getItemMeta().clearCustomEffects();
    return this.getSelf();
  }

  @NotNull
  @Override
  public PotionItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final TransformedData data) {
    super.serialize(data);
    final var itemStack = this.getItemStack(false);
    final var itemMeta = this.getItemMeta();
    if (Builder.VERSION >= 9) {
      final var customEffects = itemMeta.getCustomEffects();
      final var effects = customEffects.stream()
        .map(effect ->
          String.format("%s, %d, %d", effect.getType().getName(), effect.getDuration(), effect.getAmplifier()))
        .collect(Collectors.toCollection(() -> new ArrayList<>(customEffects.size())));
      data.addCollection(Keys.CUSTOM_EFFECTS_KEY, effects, String.class);
      final var potionData = itemMeta.getBasePotionData();
      data.add(Keys.BASE_EFFECT_KEY, String.format("%s, %s, %s",
        potionData.getType().name(), potionData.isExtended(), potionData.isUpgraded()), String.class);
      if (Builder.VERSION >= 11) {
        final var color = itemMeta.getColor();
        if (itemMeta.hasColor() && color != null) {
          data.add(Keys.COLOR_KEY, color.asRGB(), int.class);
        }
      }
    } else if (itemStack.getDurability() != 0) {
      final var potion = Potion.fromItemStack(itemStack);
      data.add(Keys.LEVEL_KEY, potion.getLevel(), int.class);
      data.add(Keys.BASE_EFFECT_KEY, String.format("%s, %s, %s",
        potion.getType().name(), potion.hasExtendedDuration(), potion.isSplash()), String.class);
    }
  }

  /**
   * Removes a custom potion effect from this potion.
   *
   * @param type the potion effect type to remove
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder removeCustomEffect(@NotNull final PotionEffectType type) {
    this.getItemMeta().removeCustomEffect(type);
    return this.getSelf();
  }

  /**
   * sets the underlying potion data.
   *
   * @param data the data to set the base potion state to.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder setBasePotionData(@NotNull final PotionData data) {
    if (Builder.VERSION >= 9) {
      this.getItemMeta().setBasePotionData(data);
    }
    return this.getSelf();
  }

  /**
   * sets the underlying potion data.
   *
   * @param data the data to set the base potion state to.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder setBasePotionData(@NotNull final String data) {
    if (Builder.VERSION < 9) {
      return this.getSelf();
    }
    if (data.isEmpty()) {
      return this.getSelf();
    }
    final var split = data.split(",");
    PotionType type;
    try {
      type = PotionType.valueOf(split[0].trim().toUpperCase(Locale.ROOT));
    } catch (final Exception e) {
      type = PotionType.UNCRAFTABLE;
    }
    final var extended = split.length != 1 && Boolean.parseBoolean(split[1].trim());
    final var upgraded = split.length > 2 && Boolean.parseBoolean(split[2].trim());
    final var potionData = new PotionData(type, extended, upgraded);
    return this.setBasePotionData(potionData);
  }

  /**
   * sets the underlying potion data.
   *
   * @param data the data to set the base potion state to.
   * @param level the level to set the base potion state to.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder setBasePotionData(@NotNull final String data, final int level) {
    if (Builder.VERSION >= 9) {
      return this.setBasePotionData(data);
    }
    if (data.isEmpty()) {
      return this.getSelf();
    }
    final var split = data.split(",");
    PotionType type;
    try {
      type = PotionType.valueOf(split[0].trim().toUpperCase(Locale.ROOT));
    } catch (final Exception e) {
      type = PotionType.SLOWNESS;
    }
    final var extended = split.length != 1 && Boolean.parseBoolean(split[1].trim());
    final var splash = split.length > 2 && Boolean.parseBoolean(split[2].trim());
    return this.setItemStack(new Potion(type, level, splash, extended).toItemStack(1));
  }

  /**
   * sets the potion color. A custom potion color will alter the display of the potion in an inventory slot.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder setColor(@Nullable final Color color) {
    if (Builder.VERSION >= 11) {
      this.getItemMeta().setColor(color);
    }
    return this.getSelf();
  }

  /**
   * sets the potion color. A custom potion color will alter the display of the potion in an inventory slot.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public PotionItemBuilder setColor(final int color) {
    return this.setColor(Color.fromRGB(color));
  }

  /**
   * moves a potion effect to the top of the potion effect list.
   * <p>
   * this causes the client to display the potion effect in the potion's name.
   *
   * @param type the type to move.
   *
   * @return {@code this} for builder chain.
   *
   * @deprecated since version 1.9, use {@link #setBasePotionData(PotionData)}.
   */
  @Deprecated
  @NotNull
  public PotionItemBuilder setMainEffect(@NotNull final PotionEffectType type) {
    this.getItemMeta().setMainEffect(type);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link PotionMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull TransformedData, @NotNull Optional<PotionItemBuilder>> {

    @NotNull
    @Override
    public Optional<PotionItemBuilder> apply(@NotNull final TransformedData data) {
      final var itemStack = Builder.getItemStackDeserializer().apply(data);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asPotion();
      final var level = data.get(Keys.LEVEL_KEY, int.class)
        .orElse(1);
      final var baseEffect = data.get(Keys.BASE_EFFECT_KEY, String.class);
      final var color = data.get(Keys.COLOR_KEY, int.class);
      final var customEffects = data.getAsList(Keys.CUSTOM_EFFECTS_KEY, String.class)
        .orElse(Collections.emptyList());
      color.ifPresent(builder::setColor);
      builder.addCustomEffects(customEffects, true);
      baseEffect.ifPresent(s -> builder.setBasePotionData(s, level));
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(data));
    }
  }
}
