/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
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

package tr.com.infumia.infumialib.paper.bukkititembuilder;

import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents banner item builders.
 */
public final class BannerItemBuilder extends Builder<BannerItemBuilder, BannerMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemStack the item stack.
   * @param meta the meta.
   */
  BannerItemBuilder(@NotNull final BannerMeta meta, @NotNull final ItemStack itemStack) {
    super(meta, itemStack);
  }

  /**
   * creates a new banner item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created banner item builder instance.
   */
  @NotNull
  public static BannerItemBuilder from(@NotNull final BannerMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new BannerItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates banner item builder from serialized holder.
   *
   * @param holder the holder to create.
   *
   * @return a newly created banner item builder instance.
   */
  @NotNull
  public static BannerItemBuilder from(@NotNull final KeyUtil.Holder<?> holder) {
    return BannerItemBuilder.getDeserializer().apply(holder).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given holder is incorrect!\n%s", holder)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return BannerItemBuilder.DESERIALIZER;
  }

  /**
   * adds patterns to the banner.
   *
   * @param patterns the patterns to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder addPatterns(@NotNull final Pattern... patterns) {
    Arrays.stream(patterns).forEach(this.getItemMeta()::addPattern);
    return this.getSelf();
  }

  @NotNull
  @Override
  public BannerItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    super.serialize(holder);
    final var patterns = new HashMap<String, Object>();
    this.getItemMeta().getPatterns()
      .forEach(pattern -> patterns.put(pattern.getPattern().name(), pattern.getColor().name()));
    holder.addAsMap(KeyUtil.PATTERNS_KEY, patterns, String.class, Object.class);
  }

  /**
   * removes patterns from the banner.
   *
   * @param index the index to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder removePatterns(final int... index) {
    Arrays.stream(index).forEach(this.getItemMeta()::removePattern);
    return this.getSelf();
  }

  /**
   * sets base color of the banner.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public BannerItemBuilder setBaseColor(@NotNull final DyeColor color) {
    this.getItemMeta().setBaseColor(color);
    return this.getSelf();
  }

  /**
   * sets pattern of the banner.
   *
   * @param index the index to set.
   * @param pattern the pattern to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPattern(final int index, @NotNull final Pattern pattern) {
    this.getItemMeta().setPattern(index, pattern);
    return this.getSelf();
  }

  /**
   * sets pattern of the banner.
   *
   * @param patterns the patterns to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPatterns(@NotNull final Pattern... patterns) {
    return this.setPatterns(List.of(patterns));
  }

  /**
   * sets pattern of the banner.
   *
   * @param patterns the patterns to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BannerItemBuilder setPatterns(@NotNull final List<Pattern> patterns) {
    this.getItemMeta().setPatterns(patterns);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link BannerMeta}.
   */
  public static final class Deserializer implements
    Function<KeyUtil.@NotNull Holder<?>, @NotNull Optional<BannerItemBuilder>> {

    @NotNull
    @Override
    public Optional<BannerItemBuilder> apply(@NotNull final KeyUtil.Holder<?> holder) {
      final var itemStack = getItemStackDeserializer().apply(holder);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asBanner();
      holder.getAsMap(KeyUtil.PATTERNS_KEY, String.class, Object.class)
        .ifPresent(patterns -> patterns.forEach((key, value) -> {
          var type = PatternType.getByIdentifier(key);
          if (type == null) {
            try {
              type = PatternType.valueOf(key.toUpperCase(Locale.ENGLISH));
            } catch (final Exception e) {
              type = PatternType.BASE;
            }
          }
          DyeColor color;
          try {
            color = DyeColor.valueOf(String.valueOf(value).toUpperCase(Locale.ENGLISH));
          } catch (final Exception e) {
            color = DyeColor.WHITE;
          }
          builder.addPatterns(new Pattern(color, type));
        }));
      return Optional.of(getItemMetaDeserializer(builder).apply(holder));
    }
  }
}
