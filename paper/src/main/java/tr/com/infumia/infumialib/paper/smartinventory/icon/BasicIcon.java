package tr.com.infumia.infumialib.paper.smartinventory.icon;

import tr.com.infumia.infumialib.paper.smartinventory.Handle;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.IconEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation for {@link Icon}.
 */
@RequiredArgsConstructor
public final class BasicIcon implements Icon {

  /**
   * the handle list.
   */
  private final Collection<Handle<? extends IconEvent>> handles = new ArrayList<>();

  /**
   * the can see.
   */
  @NotNull
  private Predicate<InventoryContents> canSee = contents -> true;

  /**
   * the can use.
   */
  @NotNull
  private Predicate<InventoryContents> canUse = contents -> true;

  /**
   * the fallback.
   */
  @NotNull
  private ItemStack fallback = new ItemStack(Material.AIR);

  /**
   * the item.
   */
  @NotNull
  private ItemStack item;

  @Override
  public <T extends IconEvent> void accept(@NotNull final T event) {
    final var contents = event.contents();
    if (this.canSee.test(contents) && this.canUse.test(contents)) {
      this.handles.stream()
        .filter(target -> target.type().isAssignableFrom(event.getClass()))
        .map(target -> (Handle<T>) target)
        .forEach(target -> target.accept(event));
    }
  }

  @NotNull
  @Override
  public ItemStack calculateItem(@NotNull final InventoryContents contents) {
    final ItemStack calculated;
    if (this.canSee.test(contents)) {
      calculated = this.getItem();
    } else {
      calculated = this.fallback;
    }
    return calculated;
  }

  @NotNull
  @Override
  public Icon canSee(@NotNull final Predicate<InventoryContents> predicate) {
    this.canSee = predicate;
    return this;
  }

  @NotNull
  @Override
  public Icon canUse(@NotNull final Predicate<InventoryContents> predicate) {
    this.canUse = predicate;
    return this;
  }

  @NotNull
  @Override
  public Icon fallback(@NotNull final ItemStack fallback) {
    this.fallback = fallback;
    return this;
  }

  @NotNull
  @Override
  public ItemStack getItem() {
    return this.item;
  }

  @NotNull
  @Override
  public <T extends IconEvent> Icon handle(@NotNull final Handle<T> handle) {
    this.handles.add(handle);
    return this;
  }

  @NotNull
  @Override
  public Icon handles(@NotNull final Collection<Handle<? extends IconEvent>> handles) {
    this.handles.addAll(handles);
    return this;
  }

  @NotNull
  @Override
  public Icon item(@NotNull final ItemStack item) {
    this.item = item;
    return this;
  }
}
