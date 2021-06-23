package tr.com.infumia.infumialib.paper.smartinventory.event;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.CloseEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents page close events.
 */
@RequiredArgsConstructor
public final class PgCloseEvent implements CloseEvent {

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  /**
   * the event.
   */
  @NotNull
  private final InventoryCloseEvent event;

  @NotNull
  @Override
  public InventoryContents contents() {
    return this.contents;
  }

  @NotNull
  @Override
  public InventoryCloseEvent getEvent() {
    return this.event;
  }
}
