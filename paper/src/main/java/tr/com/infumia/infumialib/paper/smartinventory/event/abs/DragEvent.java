package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine drag events.
 */
public interface DragEvent extends IconEvent {

  /**
   * obtains the added.
   *
   * @return added.
   */
  @NotNull
  Map<Integer, ItemStack> added();

  /**
   * obtains the drag.
   *
   * @return drag.
   */
  @NotNull
  DragType drag();

  /**
   * obtains the event.
   *
   * @return event.
   */
  @NotNull
  InventoryDragEvent getEvent();

  /**
   * obtains the new cursor.
   *
   * @return new cursor.
   */
  @NotNull
  Optional<ItemStack> newCursor();

  /**
   * obtains the slots.
   *
   * @return slots.
   */
  @NotNull
  Set<Integer> slots();
}
