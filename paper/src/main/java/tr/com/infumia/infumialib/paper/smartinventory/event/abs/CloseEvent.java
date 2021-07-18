package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine close event.
 */
public interface CloseEvent extends PageEvent {

  /**
   * obtains the event.
   *
   * @return event.
   */
  @NotNull
  InventoryCloseEvent getEvent();
}
