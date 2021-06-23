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

import com.cryptomorin.xseries.SkullUtils;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents skull item builders.
 * <p>
 * serialization:
 * <pre>
 * texture: string (texture can be username, textures.minecraft.net url or base64) (for 8 and newer versions)
 * </pre>
 */
public final class SkullItemBuilder extends Builder<SkullItemBuilder, SkullMeta> {

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
  SkullItemBuilder(@NotNull final SkullMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new skull item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created skull item builder instance.
   */
  @NotNull
  public static SkullItemBuilder from(@NotNull final SkullMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new SkullItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates skull item builder from serialized holder.
   *
   * @param holder the holder to create.
   *
   * @return a newly created skull item builder instance.
   */
  @NotNull
  public static SkullItemBuilder from(@NotNull final KeyUtil.Holder<?> holder) {
    return SkullItemBuilder.getDeserializer().apply(holder).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given holder is incorrect!\n%s", holder)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return SkullItemBuilder.DESERIALIZER;
  }

  @NotNull
  @Override
  public SkullItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    super.serialize(holder);
    holder.add(KeyUtil.SKULL_TEXTURE_KEY, SkullUtils.getSkinValue(this.getItemMeta()), String.class);
  }

  /**
   * removes owner of the skull.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public SkullItemBuilder removeOwner() {
    if (VERSION < 13) {
      this.getItemMeta().setOwner(null);
    } else {
      this.getItemMeta().setOwningPlayer(null);
    }
    return this.getSelf();
  }

  /**
   * sets owner of the skull.
   *
   * @param texture the texture to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public SkullItemBuilder setOwner(@NotNull final String texture) {
    SkullUtils.applySkin(this.getItemMeta(), texture);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link SkullMeta}.
   */
  public static final class Deserializer implements
    Function<KeyUtil.@NotNull Holder<?>, @NotNull Optional<SkullItemBuilder>> {

    @NotNull
    @Override
    public Optional<SkullItemBuilder> apply(@NotNull final KeyUtil.Holder<?> holder) {
      final var itemStack = getItemStackDeserializer().apply(holder);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asSkull();
      holder.get(KeyUtil.SKULL_TEXTURE_KEY, String.class)
        .ifPresent(builder::setOwner);
      return Optional.of(getItemMetaDeserializer(builder).apply(holder));
    }
  }
}
