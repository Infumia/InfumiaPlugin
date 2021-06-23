package tr.com.infumia.infumialib.paper.smartinventory.event;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.TickEvent;

/**
 * a class that represents page tick events.
 */
@RequiredArgsConstructor
public final class PgTickEvent implements TickEvent {

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  @NotNull
  @Override
  public InventoryContents contents() {
    return this.contents;
  }
}
