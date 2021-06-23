package tr.com.infumia.infumialib.paper.smartinventory.util;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * represents the position (row + column) of a slot in an inventory.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SlotPos {

  /**
   * the column.
   */
  private final int column;

  /**
   * the row.
   */
  private final int row;

  /**
   * creates a simple slot position instance.
   *
   * @param row the row to create.
   * @param column the column to create.
   *
   * @return a simple slot position instance.
   */
  @NotNull
  public static SlotPos of(final int row, final int column) {
    return new SlotPos(column, row);
  }
}
