package tr.com.infumia.infumialib.paper.smartinventory.holder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.SmartHolder;

/**
 * a class that implements {@link SmartHolder}.
 */
@Getter
@RequiredArgsConstructor
public final class SmartInventoryHolder implements SmartHolder {

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  /**
   * the active.
   */
  @Setter
  private boolean active = true;
}
