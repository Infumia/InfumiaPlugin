package tr.com.infumia.infumialib.paper.smartinventory.event;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.InitEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents page init events.
 */
@RequiredArgsConstructor
public final class PgInitEvent implements InitEvent {

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
