package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine smart events.
 */
public interface SmartEvent {

  /**
   * cancels the vent.
   */
  default void cancel() {
  }

  /**
   * closes the inventory.
   */
  default void close() {
  }

  /**
   * obtains the contents.
   *
   * @return contents.
   */
  @NotNull
  InventoryContents contents();
}
