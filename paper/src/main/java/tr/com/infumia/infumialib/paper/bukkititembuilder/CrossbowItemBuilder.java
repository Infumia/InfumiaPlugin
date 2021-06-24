package tr.com.infumia.infumialib.paper.bukkititembuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.ItemStackUtil;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;

/**
 * a class that represents crossbow item builders.
 * <p>
 * serialization
 * <pre>
 * projectiles: (main section)
 *   0: (item section)
 *     material: DIAMOND
 * </pre>
 */
public final class CrossbowItemBuilder extends Builder<CrossbowItemBuilder, CrossbowMeta> {

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
  CrossbowItemBuilder(@NotNull final CrossbowMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new crossbow item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created crossbow item builder instance.
   */
  @NotNull
  public static CrossbowItemBuilder from(@NotNull final CrossbowMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new CrossbowItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates crossbow item builder from serialized holder.
   *
   * @param holder the holder to create.
   *
   * @return a newly created crossbow item builder instance.
   */
  @NotNull
  public static CrossbowItemBuilder from(@NotNull final KeyUtil.Holder<?> holder) {
    return CrossbowItemBuilder.getDeserializer().apply(holder).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given holder is incorrect!\n%s", holder)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return CrossbowItemBuilder.DESERIALIZER;
  }

  /**
   * adds charged projectile to the crossbow.
   *
   * @param projectiles the projectileS to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public CrossbowItemBuilder addChargedProjectile(@NotNull final ItemStack... projectiles) {
    final var itemMeta = this.getItemMeta();
    for (final var projectile : projectiles) {
      itemMeta.addChargedProjectile(projectile);
    }
    return this.getSelf();
  }

  @NotNull
  @Override
  public CrossbowItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    super.serialize(holder);
    final var projectiles = new HashMap<String, Object>();
    final var chargedProjectiles = this.getItemMeta().getChargedProjectiles();
    IntStream.range(0, chargedProjectiles.size()).forEach(index -> {
      final var projectile = chargedProjectiles.get(index);
      final var section = KeyUtil.Holder.map(new HashMap<>());
      ItemStackUtil.serialize(projectile, section);
      projectiles.put(String.valueOf(index), section.getHolder());
    });
    holder.addAsMap(KeyUtil.PROJECTILES_KEY, projectiles, String.class, Object.class);
  }

  /**
   * sets charged projectile of the item.
   *
   * @param projectiles the projectiles to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public CrossbowItemBuilder setChargedProjectiles(@Nullable final ItemStack... projectiles) {
    return this.setChargedProjectiles(List.of(projectiles));
  }

  /**
   * sets charged projectile of the item.
   *
   * @param projectiles the projectiles to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public CrossbowItemBuilder setChargedProjectiles(@Nullable final List<ItemStack> projectiles) {
    this.getItemMeta().setChargedProjectiles(projectiles);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link CrossbowMeta}.
   */
  public static final class Deserializer implements
    Function<KeyUtil.@NotNull Holder<?>, @NotNull Optional<CrossbowItemBuilder>> {

    @NotNull
    @Override
    public Optional<CrossbowItemBuilder> apply(@NotNull final KeyUtil.Holder<?> holder) {
      final var itemStack = Builder.getItemStackDeserializer().apply(holder);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asCrossbow();
      final var collect = holder.getAsMap(KeyUtil.PROJECTILES_KEY, String.class, Object.class)
        .stream()
        .map(KeyUtil.Holder::map)
        .map(ItemStackUtil::deserialize)
        .flatMap(Optional::stream)
        .collect(Collectors.toList());
      builder.setChargedProjectiles(collect);
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(holder));
    }
  }
}
