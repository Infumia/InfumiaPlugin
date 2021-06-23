package tr.com.infumia.infumialib.paper.transformer.serializers;

import java.util.Optional;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.ItemStackBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.ItemStackUtil;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

/**
 * a class that represents serializer of {@link ItemStackBuilder}.
 */
public final class ItemStackSerializer implements ObjectSerializer<ItemStack> {

  @NotNull
  @Override
  public Optional<ItemStack> deserialize(@NotNull final TransformedData transformedData,
                                         @Nullable final GenericDeclaration declaration) {
    return ItemStackUtil.deserialize(KeyUtil.Holder.transformedData(transformedData));
  }

  @NotNull
  @Override
  public Optional<ItemStack> deserialize(@NotNull final ItemStack field,
                                         @NotNull final TransformedData transformedData,
                                         @Nullable final GenericDeclaration declaration) {
    return this.deserialize(transformedData, declaration);
  }

  @Override
  public void serialize(@NotNull final ItemStack itemStack, @NotNull final TransformedData transformedData) {
    ItemStackUtil.serialize(ItemStackBuilder.from(itemStack), KeyUtil.Holder.transformedData(transformedData));
  }

  @Override
  public boolean supports(@NotNull final Class<?> cls) {
    return cls == ItemStack.class;
  }
}
