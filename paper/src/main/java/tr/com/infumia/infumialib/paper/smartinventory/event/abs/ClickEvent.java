package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import java.util.Optional;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine click events.
 */
public interface ClickEvent extends IconEvent {

  /**
   * obtains the action.
   *
   * @return action.
   */
  @NotNull
  InventoryAction action();

  /**
   * obtains the click.
   *
   * @return click.
   */
  @NotNull
  ClickType click();

  /**
   * obtains the column.
   *
   * @return column.
   */
  int column();

  /**
   * obtains the current.
   *
   * @return the current.
   */
  @NotNull
  Optional<ItemStack> current();

  /**
   * obtains the cursor.
   *
   * @return cursor.
   */
  @NotNull
  Optional<ItemStack> cursor();

  /**
   * obtains the event.
   *
   * @return event.
   */
  @NotNull
  InventoryClickEvent getEvent();

  /**
   * obtains the row.
   *
   * @return row.
   */
  int row();

  /**
   * obtains the slot.
   *
   * @return slot.
   */
  @NotNull
  InventoryType.SlotType slot();
}
