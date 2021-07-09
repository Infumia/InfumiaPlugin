package tr.com.infumia.infumialib.paper.smartinventory.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * represents the position (row + column) of a slot in an inventory.
 */
@Getter
@EqualsAndHashCode
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
   * ctor.
   *
   * @param row the row.
   * @param column the column.
   * @param absolute the absolute.
   */
  private SlotPos(final int row, final int column, final boolean absolute) {
    if (absolute) {
      this.row = Math.abs(row);
      this.column = Math.abs(column);
    } else {
      this.row = row;
      this.column = column;
    }
  }

  /**
   * creates a simple slot position instance.
   *
   * @param row the row to create.
   * @param column the column to create.
   * @param absolute the absolute to create.
   *
   * @return a simple slot position instance.
   */
  @NotNull
  public static SlotPos of(final int row, final int column, final boolean absolute) {
    return new SlotPos(row, column, absolute);
  }

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
    return SlotPos.of(row, column, false);
  }

  /**
   * adds column to position.
   *
   * @param column the column to add.
   * @param absolute the absolute to add.
   *
   * @return a new slot position with the column offset.
   */
  @NotNull
  public SlotPos addColumn(final int column, final boolean absolute) {
    return SlotPos.of(this.row, this.column + column, absolute);
  }

  /**
   * adds column to position.
   *
   * @param column the column to add.
   *
   * @return a new slot position with the column offset.
   */
  @NotNull
  public SlotPos addColumn(final int column) {
    return this.addColumn(column, false);
  }

  /**
   * adds row to position.
   *
   * @param row the row to add.
   * @param absolute the absolute to add.
   *
   * @return a new slot position with the row offset.
   */
  @NotNull
  public SlotPos addRow(final int row, final boolean absolute) {
    return SlotPos.of(this.row + row, this.column, absolute);
  }

  /**
   * adds row to position.
   *
   * @param row the row to add.
   *
   * @return a new slot position with the row offset.
   */
  @NotNull
  public SlotPos addRow(final int row) {
    return this.addRow(row, false);
  }

  /**
   * reverses the position.
   *
   * @param absolute the absolute to reverse.
   *
   * @return a new reversed slot position.
   */
  @NotNull
  public SlotPos reverse(final boolean absolute) {
    return SlotPos.of(this.column, this.row, absolute);
  }

  /**
   * reverses the position.
   *
   * @return a new reversed slot position.
   */
  @NotNull
  public SlotPos reverse() {
    return this.reverse(false);
  }

  /**
   * converts row and column into index.
   *
   * @return index.
   */
  public int toIndex() {
    return this.row * 9 + this.column;
  }

  @NotNull
  @Override
  public String toString() {
    return String.format("SlotPos{row=%d, column=%d}",
      this.row, this.column);
  }
}
