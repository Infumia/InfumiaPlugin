package tr.com.infumia.infumialib.paper.smartinventory;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.stream.IntStream;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.smartinventory.content.BasicSlotIterator;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;

/**
 * represents the content of an inventory.
 * <p>
 * this contains several methods which let you get and modify
 * the content of the inventory.
 * <p>
 * for example, you can get the item at a given slot by
 * using {@link InventoryContents#get(SlotPos)}. You can
 * also fill an entire column with the use of the method
 * {@link InventoryContents#fillColumn(int, Icon)}.
 */
public interface InventoryContents {

  /**
   * adds an item to the <b>first empty slot</b> of the inventory.
   *
   * <b>Warning:</b> if there is already a stack of the same item,
   * this will not add the item to the stack, this will always
   * add the item into an empty slot.
   *
   * @param item the item to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents add(@NotNull final Icon item) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[0].length; column++) {
        if (all[row][column] == null) {
          this.set(row, column, item);
          return this;
        }
      }
    }
    return this;
  }

  /**
   * returns a 2D array of {@link Icon} containing
   * all the items of the inventory.
   * <p>
   * the {@link Icon}s can be null when there is no
   * item in the corresponding slot.
   *
   * @return the items of the inventory.
   */
  @NotNull
  Icon[][] all();

  /**
   * applie the consumer on a rectangle inside the inventory using the given
   * positions.
   * <p>
   * the created rectangle will have its top-left position at
   * the given <b>from slot index</b> and its bottom-right position at
   * the given <b>to slot index</b>.
   *
   * @param fromColumn the from column to apply.
   * @param fromRow the from row to apply.
   * @param toColumn the to column to apply.
   * @param toRow the to row to apply.
   * @param apply the apply to accept row and column
   *
   * @return {@code this}, for chained calls
   */
  @NotNull
  default InventoryContents applyRect(final int fromRow, final int fromColumn, final int toRow, final int toColumn,
                                      @NotNull final ObjIntConsumer<Integer> apply) {
    for (var row = fromRow; row <= toRow; row++) {
      for (var column = fromColumn; column <= toColumn; column++) {
        apply.accept(row, column);
      }
    }
    return this;
  }

  /**
   * applies the consumer on a rectangle inside the inventory using the given
   * positions. applies only when the {@link Icon} exist in this GUI.
   * <p>
   * the created rectangle will have its top-left position at
   * the given <b>from slot index</b> and its bottom-right position at
   * the given <b>to slot index</b>.
   *
   * @param fromColumn the from column to apply.
   * @param fromRow the from row to apply.
   * @param toColumn the to column to apply.
   * @param toRow the to row to apply.
   * @param apply the apply to accept each slot
   *
   * @return {@code this}, for chained calls
   */
  @NotNull
  default InventoryContents applyRect(final int fromRow, final int fromColumn, final int toRow, final int toColumn,
                                      @NotNull final Consumer<Icon> apply) {
    return this.applyRect(fromRow, fromColumn, toRow, toColumn, (row, column) ->
      this.get(row, column).ifPresent(apply));
  }

  /**
   * clears the page.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents clear() {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[0].length; column++) {
        final var icon = all[row][column];
        if (icon != null && icon.getItem().getType() != Material.AIR) {
          this.set(row, column, null);
        }
      }
    }
    return this;
  }

  /**
   * fills the inventory with the given item.
   *
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fill(@NotNull final Icon item) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[row].length; column++) {
        this.set(row, column, item);
      }
    }
    return this;
  }

  /**
   * fills the inventory borders with the given item.
   *
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillBorders(@NotNull final Icon item) {
    this.fillRect(0, 0, this.page().row() - 1, this.page().column() - 1, item);
    return this;
  }

  /**
   * fills the given inventory column with the given item.
   *
   * @param column the column to fill.
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillColumn(final int column, @NotNull final Icon item) {
    final var all = this.all();
    if (column < 0 || column >= all[0].length) {
      return this;
    }
    IntStream.range(0, all.length).forEach(row ->
      this.set(row, column, item));
    return this;
  }

  /**
   * fills the empty slots of the inventory with the given item.
   *
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillEmpties(@NotNull final Icon item) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[row].length; column++) {
        final var icon = all[row][column];
        if (icon == null || icon.getItem().getType() == Material.AIR) {
          this.set(row, column, item);
        }
      }
    }
    return this;
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the first slot.
   *
   * @param pattern the filling pattern.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPattern(Pattern, int) to fill the pattern from the provided slot index.
   * @see #fillPattern(Pattern, int, int) to fill the pattern from the provided row and column.
   * @see #fillPattern(Pattern, SlotPos) to fill the pattern from the provided slot pos.
   */
  @NotNull
  default InventoryContents fillPattern(@NotNull final Pattern<Icon> pattern) {
    return this.fillPattern(pattern, 0, 0);
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the given slot index.
   *
   * @param pattern the filling pattern.
   * @param startIndex the start slot index for the filling.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPattern(Pattern) to fill the pattern from the first slot.
   * @see #fillPattern(Pattern, int, int) to fill the pattern from the provided row and column.
   * @see #fillPattern(Pattern, SlotPos) to fill the pattern from the provided slot pos.
   */
  @NotNull
  default InventoryContents fillPattern(@NotNull final Pattern<Icon> pattern, final int startIndex) {
    final var count = this.page().column();
    return this.fillPattern(pattern, startIndex / count, startIndex % count);
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the given slot position based on the provided row and column.
   *
   * @param pattern the filling pattern.
   * @param startRow the start row of the slot for filling.
   * @param startColumn the start column of the slot for filling.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPattern(Pattern) to fill the pattern from the first slot.
   * @see #fillPattern(Pattern, int) to fill the pattern from the provided slot index.
   * @see #fillPattern(Pattern, SlotPos) to fill the pattern from the provided slot pos.
   */
  @NotNull
  default InventoryContents fillPattern(@NotNull final Pattern<Icon> pattern, final int startRow,
                                        final int startColumn) {
    for (var row = 0; row < pattern.getRowCount(); row++) {
      for (var column = 0; column < pattern.getColumnCount(); column++) {
        final var finalRow = startRow + row;
        final var finalColumn = startColumn + column;
        pattern.getObject(row, column).ifPresent(icon ->
          this.set(finalRow, finalColumn, icon));
      }
    }
    return this;
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the given slot position.
   *
   * @param pattern the filling pattern.
   * @param startPos the start position of the slot for filling.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPattern(Pattern) to fill the pattern from the first slot.
   * @see #fillPattern(Pattern, int) to fill the pattern from the provided slot index.
   * @see #fillPattern(Pattern, int, int) to fill the pattern from the provided row and column.
   */
  @NotNull
  default InventoryContents fillPattern(@NotNull final Pattern<Icon> pattern, @NotNull final SlotPos startPos) {
    return this.fillPattern(pattern, startPos.getRow(), startPos.getColumn());
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the first slot and end at the last slot.
   * if the pattern is not big enough, it will wrap around to the other side and repeat the pattern.
   * <p>
   * the top-left corner of the specified inventory area is also the top-left corner of the specified pattern.
   *
   * <b>for this to work the pattern needs to be created with {@code wrapAround} enabled.</b>
   *
   * @param pattern the filling pattern.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPatternRepeating(Pattern, int, int) to fill a repeating pattern using slot indexes.
   * @see #fillPatternRepeating(Pattern, int, int, int, int) to fill a repeating pattern using slot positions contructed
   *   from their rows and columns.
   * @see #fillPatternRepeating(Pattern, SlotPos, SlotPos) to fill a repeating pattern using slot positions.
   */
  @NotNull
  default InventoryContents fillPatternRepeating(@NotNull final Pattern<Icon> pattern) {
    return this.fillPatternRepeating(pattern, 0, 0, -1, -1);
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the first slot index and end at the second slot index.
   * if the pattern is not big enough, it will wrap around to the other side and repeat the pattern.
   * <p>
   * the top-left corner of the specified inventory area is also the top-left corner of the specified pattern.
   * <p>
   * if {@code endIndex} is a negative value it is set to the bottom-right corner.
   *
   * <b>for this to work the pattern needs to be created with {@code wrapAround} enabled.</b>
   *
   * @param pattern the filling pattern.
   * @param startIndex the start slot index where the pattern should begin.
   * @param endIndex the end slot index where the pattern should end.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPatternRepeating(Pattern) to fill a repeating pattern into the whole inventory.
   * @see #fillPatternRepeating(Pattern, int, int, int, int) to fill a repeating pattern using slot positions contructed
   *   from their rows and columns.
   * @see #fillPatternRepeating(Pattern, SlotPos, SlotPos) to fill a repeating pattern using slot positions.
   */
  @NotNull
  default InventoryContents fillPatternRepeating(@NotNull final Pattern<Icon> pattern, final int startIndex,
                                                 final int endIndex) {
    final var columnCount = this.page().column();
    final var maxSize = endIndex < 0;
    if (maxSize) {
      return this.fillPatternRepeating(pattern, startIndex / columnCount, startIndex % columnCount,
        -1, -1);
    }
    return this.fillPatternRepeating(pattern, startIndex / columnCount, startIndex % columnCount,
      endIndex / columnCount, endIndex % columnCount);
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the given slot position and end at the second slot position.
   * if the pattern is not big enough, it will wrap around to the other side and repeat the pattern.
   * <p>
   * the top-left corner of the specified inventory area is also the top-left corner of the specified pattern.
   * <p>
   * if {@code endRow} is a negative value, endRow is automatically set to the max row size,
   * if {@code endColumn} is a negative value, endColumn is automatically set to the max column size.
   *
   * <b>for this to work the pattern needs to be created with {@code wrapAround} enabled.</b>
   *
   * @param pattern the filling pattern.
   * @param startRow the start row of the slot for filling.
   * @param startColumn the start column of the slot for filling.
   * @param endRow the end row of the slot for filling.
   * @param endColumn the end column of the slot for filling.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPatternRepeating(Pattern) to fill a repeating pattern into the whole inventory.
   * @see #fillPatternRepeating(Pattern, int, int) to fill a repeating pattern using slot indexes.
   * @see #fillPatternRepeating(Pattern, SlotPos, SlotPos) to fill a repeating pattern using slot positions.
   */
  @NotNull
  default InventoryContents fillPatternRepeating(@NotNull final Pattern<Icon> pattern, final int startRow,
                                                 final int startColumn, int endRow, int endColumn) {
    Preconditions.checkArgument(pattern.isWrapAround(),
      "To fill in a repeating pattern wrapAround needs to be enabled for the pattern to work!");
    if (endRow < 0) {
      endRow = this.page().row();
    }
    if (endColumn < 0) {
      endColumn = this.page().column();
    }
    Preconditions.checkArgument(startRow < endRow,
      "The start row needs to be lower than the end row");
    Preconditions.checkArgument(startColumn < endColumn,
      "The start column needs to be lower than the end column");
    final var rowDelta = endRow - startRow;
    final var columnDelta = endColumn - startColumn;
    for (var row = 0; row <= rowDelta; row++) {
      for (var column = 0; column <= columnDelta; column++) {
        final var finalRow = startRow + row;
        final var finalColumn = startColumn + column;
        pattern.getObject(row, column).ifPresent(icon ->
          this.set(finalRow, finalColumn, icon));
      }
    }
    return this;
  }

  /**
   * fills the inventory with the given {@link Pattern}.
   * <p>
   * the pattern will start at the given slot position and end at the second slot position.
   * if the pattern is not big enough, it will wrap around to the other side and repeat the pattern.
   * <p>
   * the top-left corner of the specified inventory area is also the top-left corner of the specified pattern.
   * <p>
   * if the row of {@code endPos} is a negative value, endRow is automatically set to the max row size,
   * if the column of {@code endPos} is a negative value, endColumn is automatically set to the max column size.
   *
   * <b>for this to work the pattern needs to be created with {@code wrapAround} enabled.</b>
   *
   * @param pattern the filling pattern.
   * @param startPos the position where the pattern should start.
   * @param endPos the position where the pattern should end.
   *
   * @return {@code this}, for chained calls.
   *
   * @see #fillPatternRepeating(Pattern) to fill a repeating pattern into the whole inventory.
   * @see #fillPatternRepeating(Pattern, int, int) to fill a repeating pattern using slot indexes.
   * @see #fillPatternRepeating(Pattern, int, int, int, int) to fill a repeating pattern using slot positions contructed
   *   from their rows and columns.
   */
  @NotNull
  default InventoryContents fillPatternRepeating(@NotNull final Pattern<Icon> pattern, @NotNull final SlotPos startPos,
                                                 @NotNull final SlotPos endPos) {
    return this.fillPatternRepeating(pattern, startPos.getRow(), startPos.getColumn(), endPos.getRow(),
      endPos.getColumn());
  }

  /**
   * fills a rectangle inside the inventory using the given
   * positions.
   * <p>
   * the created rectangle will have its top-left position at
   * the given <b>from slot index</b> and its bottom-right position at
   * the given <b>to slot index</b>.
   *
   * @param fromIndex the slot index at the top-left position.
   * @param toIndex the slot index at the bottom-right position.
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillRect(final int fromIndex, final int toIndex, @NotNull final Icon item) {
    final var count = this.page().column();
    return this.fillRect(
      fromIndex / count, fromIndex % count,
      toIndex / count, toIndex % count,
      item);
  }

  /**
   * same as {@link InventoryContents#fillRect(int, int, Icon)},
   * but with {@link SlotPos} instead of the indexes.
   *
   * @param fromRow the row of the first corner of the rect.
   * @param fromColumn the column of the first corner of the rect.
   * @param toRow the row of the second corner of the rect.
   * @param toColumn the column of the second corner of the rect.
   * @param item the item to fill the rect with.
   *
   * @return {@code this}, for chained calls.
   *
   * @see InventoryContents#fillRect(int, int, Icon)
   */
  @NotNull
  default InventoryContents fillRect(final int fromRow, final int fromColumn, final int toRow, final int toColumn,
                                     @NotNull final Icon item) {
    return this.applyRect(fromRow, fromColumn, toRow, toColumn, (row, column) -> {
      if (row == fromRow || row == toRow ||
        column == fromColumn || column == toColumn) {
        this.set(row, column, item);
      }
    });
  }

  /**
   * same as {@link InventoryContents#fillRect(int, int, Icon)},
   * but with rows and columns instead of the indexes.
   *
   * @param fromPos the first corner of the rect.
   * @param toPos the second corner of the rect.
   * @param item the item to fill the rect with.
   *
   * @return {@code this}, for chained calls.
   *
   * @see InventoryContents#fillRect(int, int, Icon)
   */
  @NotNull
  default InventoryContents fillRect(@NotNull final SlotPos fromPos, @NotNull final SlotPos toPos,
                                     @NotNull final Icon item) {
    return this.fillRect(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(), item);
  }

  /**
   * fills the given inventory row with the given item.
   *
   * @param row the row to fill.
   * @param item the item to fill.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillRow(final int row, @NotNull final Icon item) {
    final var all = this.all();
    if (row < 0 || row >= all.length) {
      return this;
    }
    IntStream.range(0, all[row].length).forEach(column ->
      this.set(row, column, item));
    return this;
  }

  /**
   * completely fills the provided square with the given {@link Icon}.
   *
   * @param fromIndex the slot index of the upper left corner.
   * @param toIndex the slot index of the lower right corner.
   * @param item the item.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillSquare(final int fromIndex, final int toIndex, @NotNull final Icon item) {
    final var count = this.page().column();
    return this.fillSquare(
      fromIndex / count, fromIndex % count,
      toIndex / count, toIndex % count,
      item);
  }

  /**
   * completely fills the provided square with the given {@link Icon}.
   *
   * @param fromRow the row of the upper left corner.
   * @param fromColumn the column of the upper-left corner.
   * @param toRow the row of the lower right corner.
   * @param toColumn the column of the lower right corner.
   * @param item the item.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillSquare(final int fromRow, final int fromColumn, final int toRow, final int toColumn,
                                       @NotNull final Icon item) {
    Preconditions.checkArgument(fromRow < toRow,
      "The start row needs to be lower than the end row");
    Preconditions.checkArgument(fromColumn < toColumn,
      "The start column needs to be lower than the end column");
    for (int row = fromRow; row <= toRow; row++) {
      for (int column = fromColumn; column <= toColumn; column++) {
        this.set(row, column, item);
      }
    }
    return this;
  }

  /**
   * completely fills the provided square with the given {@link Icon}.
   *
   * @param fromPos the slot position of the upper left corner.
   * @param toPos the slot position of the lower right corner.
   * @param item the item.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents fillSquare(@NotNull final SlotPos fromPos, @NotNull final SlotPos toPos,
                                       @NotNull final Icon item) {
    return this.fillSquare(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(), item);
  }

  /**
   * looks for the given item and compares them using {@link ItemStack#isSimilar(ItemStack)},
   * ignoring the amount.
   * <p>
   * this method searches row for row from left to right.
   *
   * @param item the item to look for.
   *
   * @return an optional containing the position where the item first occurred, or an empty optional.
   */
  @NotNull
  default Optional<SlotPos> findItem(@NotNull final ItemStack item) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[0].length; column++) {
        final var icon = all[row][column];
        if (icon != null && item.isSimilar(icon.calculateItem(this))) {
          return Optional.of(SlotPos.of(row, column));
        }
      }
    }
    return Optional.empty();
  }

  /**
   * looks for the given icon and compares them using {@link ItemStack#isSimilar(ItemStack)},
   * ignoring the amount.
   * <p>
   * this method searches row for row from left to right.
   *
   * @param item the item with the item stack to look for.
   *
   * @return an optional containing the position where the item first occurred, or an empty optional.
   */
  @NotNull
  default Optional<SlotPos> findItem(@NotNull final Icon item) {
    return this.findItem(item.calculateItem(this));
  }

  /**
   * returns the position of the first empty slot
   * in the inventory, or {@code Optional.empty()} if
   * there is no free slot.
   *
   * @return the first empty slot, if there is one.
   */
  @NotNull
  default Optional<SlotPos> firstEmpty() {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[0].length; column++) {
        if (this.get(row, column).isEmpty()) {
          return Optional.of(SlotPos.of(row, column));
        }
      }
    }
    return Optional.empty();
  }

  /**
   * returns the item in the inventory at the given
   * slot index, or {@code Optional.empty()} if
   * the slot is empty or if the index is out of bounds.
   *
   * @param index the slot index.
   *
   * @return the found item, if there is one.
   */
  @NotNull
  default Optional<Icon> get(final int index) {
    final var count = this.page().column();
    return this.get(index / count, index % count);
  }

  /**
   * same as {@link InventoryContents#get(int)},
   * but with a row and a column instead of the index.
   *
   * @param row the row to get.
   * @param column the colum to get.
   *
   * @return the found item, if there is one.
   */
  @NotNull
  default Optional<Icon> get(final int row, final int column) {
    final var all = this.all();
    if (row < 0 || row >= all.length) {
      return Optional.empty();
    }
    if (column < 0 || column >= all[row].length) {
      return Optional.empty();
    }
    return Optional.ofNullable(all[row][column]);
  }

  /**
   * same as {@link InventoryContents#get(int)},
   * but with a {@link SlotPos} instead of the index.
   *
   * @param slotPos the slot position to get.
   *
   * @return the found item, if there is one.
   */
  @NotNull
  default Optional<Icon> get(@NotNull final SlotPos slotPos) {
    return this.get(slotPos.getRow(), slotPos.getColumn());
  }

  /**
   * gets player's bottom of the inventory.
   *
   * @return bottom inventory instance.
   */
  @NotNull
  default Inventory getBottomInventory() {
    return this.player().getOpenInventory().getBottomInventory();
  }

  /**
   * obtains the properties.
   *
   * @return properties.
   */
  @NotNull
  Map<String, Object> getProperties();

  /**
   * gets the value of the property with the given name.
   *
   * @param name the property's name.
   * @param <T> the type of the value.
   *
   * @return the property's value.
   */
  @Nullable
  default <T> T getProperty(@NotNull final String name) {
    if (!this.getProperties().containsKey(name)) {
      return null;
    }
    //noinspection unchecked
    return (T) this.getProperties().get(name);
  }

  /**
   * gets the value of the property with the given name,
   * or a default value if the property isn't set.
   *
   * @param name the property's name.
   * @param def the default value.
   * @param <T> the type of the value.
   *
   * @return the property's value, or the given default value.
   */
  @NotNull
  default <T> T getPropertyOrDefault(@NotNull final String name, @NotNull final T def) {
    //noinspection unchecked
    return (T) this.getProperties().getOrDefault(name, def);
  }

  /**
   * gets player's top of the inventory.
   *
   * @return top inventory instance.
   */
  @NotNull
  default Inventory getTopInventory() {
    return this.player().getOpenInventory().getTopInventory();
  }

  /**
   * returns if a given slot is editable or not.
   *
   * @param slot The slot to check.
   *
   * @return {@code true} if the editable.
   */
  boolean isEditable(@NotNull SlotPos slot);

  /**
   * gets a previously registered iterator named with the given id.
   * <p>
   * if no iterator is found, this will return {@code Optional.empty()}.
   *
   * @param id the id of the iterator.
   *
   * @return the found iterator, if there is one.
   */
  @NotNull
  Optional<SlotIterator> iterator(@NotNull String id);

  /**
   * creates and returns an iterator.
   * <p>
   * this does <b>NOT</b> registers the iterator,
   * thus {@link InventoryContents#iterator(String)} will not be
   * able to return the iterators created with this method.
   *
   * @param type the type of the iterator.
   * @param startRow the starting row of the iterator.
   * @param startColumn the starting column of the iterator.
   *
   * @return the newly created iterator.
   */
  @NotNull
  default SlotIterator newIterator(@NotNull final SlotIterator.Type type, final int startRow, final int startColumn) {
    return new BasicSlotIterator(this, type, startRow, startColumn);
  }

  /**
   * same as {@link InventoryContents#newIterator(String, SlotIterator.Type, int, int)},
   * but using a {@link SlotPos} instead.
   *
   * @param id the id of the iterator.
   * @param type the type of the iterator.
   * @param startPos the starting position of the iterator.
   *
   * @return the newly created iterator.
   */
  @NotNull
  default SlotIterator newIterator(@NotNull final String id, @NotNull final SlotIterator.Type type,
                                   @NotNull final SlotPos startPos) {
    return this.newIterator(id, type, startPos.getRow(), startPos.getColumn());
  }

  /**
   * same as {@link InventoryContents#newIterator(SlotIterator.Type, int, int)},
   * but using a {@link SlotPos} instead.
   *
   * @param type the type of the iterator.
   * @param startPos the starting position of the iterator.
   *
   * @return the newly created iterator.
   */
  @NotNull
  default SlotIterator newIterator(@NotNull final SlotIterator.Type type, @NotNull final SlotPos startPos) {
    return this.newIterator(type, startPos.getRow(), startPos.getColumn());
  }

  /**
   * creates and registers an iterator using a given id.
   * <p>
   * you can retrieve the iterator at any time using
   * the {@link InventoryContents#iterator(String)} method.
   *
   * @param id the id of the iterator.
   * @param type the type of the iterator.
   * @param startRow the starting row of the iterator.
   * @param startColumn the starting column of the iterator.
   *
   * @return the newly created iterator.
   */
  @NotNull
  SlotIterator newIterator(@NotNull String id, @NotNull SlotIterator.Type type, int startRow, int startColumn);

  /**
   * runs {@link Page#notifyUpdate(InventoryContents)} method of {@code this}.
   */
  default void notifyUpdate() {
    this.page().notifyUpdate(this);
  }

  /**
   * runs {@link Page#notifyUpdateForAll()} method of {@code this}.
   */
  default void notifyUpdateForAll() {
    this.page().notifyUpdateForAll();
  }

  /**
   * runs {@link Page#notifyUpdateForAllById()} method of {@code this}.
   */
  default void notifyUpdateForAllById() {
    this.page().notifyUpdateForAllById();
  }

  /**
   * opens the next page with using {@link Pagination}.
   */
  default void openNext() {
    this.page().open(this.player(), this.pagination().next().getPage());
  }

  /**
   * opens the previous page with using {@link Pagination}.
   */
  default void openPrevious() {
    this.page().open(this.player(), this.pagination().previous().getPage());
  }

  /**
   * obtains the page of the {@code this}.
   *
   * @return a page instance.
   */
  @NotNull
  Page page();

  /**
   * gets the pagination system linked to {@code this}.
   *
   * @return the pagination
   */
  @NotNull
  Pagination pagination();

  /**
   * obtains the player of the contents.
   *
   * @return the player instance.
   */
  @NotNull
  Player player();

  /**
   * removes all occurrences of the item from the inventory.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   *
   * @param item the item as an ItemStack that shall be removed from the inventory.
   */
  default void removeAll(@NotNull final ItemStack item) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[row].length; column++) {
        final var icon = all[row][column];
        if (icon != null && item.isSimilar(icon.getItem())) {
          this.set(row, column, null);
        }
      }
    }
  }

  /**
   * removes all occurrences of the item from the inventory.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   * <p>
   * {@link Icon#getItem()} is used to get the item that will be compared against.
   *
   * @param item the item as an {@link Icon} that shall be removed from the inventory.
   */
  default void removeAll(@NotNull final Icon item) {
    this.removeAll(item.getItem());
  }

  /**
   * removes the specified amount of items from the inventory.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   *
   * @param item the item as an ItemStack that shall be removed from the inventory.
   * @param amount the amount that shall be removed.
   */
  default void removeAmount(@NotNull final ItemStack item, int amount) {
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[row].length; column++) {
        final var icon = all[row][column];
        if (icon != null && !item.isSimilar(icon.getItem())) {
          continue;
        }
        if (icon == null) {
          continue;
        }
        final var foundStack = icon.getItem();
        if (foundStack.getAmount() <= amount) {
          amount -= foundStack.getAmount();
          this.set(row, column, null);
          if (amount == 0) {
            return;
          }
        } else if (foundStack.getAmount() > amount) {
          final var clonedStack = foundStack.clone();
          clonedStack.setAmount(clonedStack.getAmount() - amount);
          this.set(row, column, icon.item(clonedStack));
          return;
        }
      }
    }
  }

  /**
   * removes the specified amount of items from the inventory.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   * <p>
   * {@link Icon#getItem()} is used to get the item that will be compared against
   *
   * @param item the item as a {@link Icon} that shall be removed from the inventory.
   * @param amount the amount that shall be removed.
   */
  default void removeAmount(@NotNull final Icon item, final int amount) {
    this.removeAmount(item.getItem(), amount);
  }

  /**
   * clears the first slot where the given item is in.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   * <p>
   * the amount stored in the item is ignored for simplicity.
   *
   * @param item the item as an ItemStack that shall be removed from the inventory.
   */
  default void removeFirst(@NotNull final ItemStack item) {
    this.findItem(item).ifPresent(slotPos -> this.set(slotPos, null));
  }

  /**
   * clears the first slot where the given item is in.
   * <p>
   * the items will be compared using {@link ItemStack#isSimilar(ItemStack)} to check if the are equal.
   * <p>
   * {@link Icon#getItem()} is used to get the item that will be compared against
   * the amount stored in the item is ignored for simplicity.
   *
   * @param item the item as a {@link Icon} that shall be removed from the inventory.
   */
  default void removeFirst(@NotNull final Icon item) {
    this.removeFirst(item.getItem());
  }

  /**
   * re open the current page.
   */
  default void reopen() {
    this.page().open(this.player(), this.getProperties());
  }

  /**
   * sets the item in the inventory at the given
   * slot index.
   *
   * @param index the slot index.
   * @param item the item to set, or {@code null} to clear the slot
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents set(final int index, @Nullable final Icon item) {
    final var columnCount = this.page().column();
    return this.set(index / columnCount, index % columnCount, item);
  }

  /**
   * Same as {@link InventoryContents#set(int, Icon)},
   * but with a {@link SlotPos} instead of the index.
   *
   * @param slotPos the slotPos to set.
   * @param item the item to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default InventoryContents set(@NotNull final SlotPos slotPos, @Nullable final Icon item) {
    return this.set(slotPos.getRow(), slotPos.getColumn(), item);
  }

  /**
   * same as {@link InventoryContents#set(int, Icon)},
   * but with a row and a column instead of the index.
   *
   * @param row the row to set.
   * @param column the column to set.
   * @param item the item to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  InventoryContents set(int row, int column, @Nullable Icon item);

  /**
   * makes a slot editable, which enables the player to
   * put items in and take items out of the inventory in the
   * specified slot.
   *
   * @param slot the slot to set editable.
   *
   * @return {@code this}, for chained calls.
   */
  default InventoryContents setEditable(@NotNull final SlotPos slot) {
    return this.setEditable(slot, true);
  }

  /**
   * makes a slot editable, which enables the player to
   * put items in and take items out of the inventory in the
   * specified slot.
   *
   * @param slot the slot to set editable.
   * @param editable {@code true} to make a slot editable, {@code false}
   *   to make it 'static' again.
   *
   * @return {@code this}, for chained calls.
   */
  InventoryContents setEditable(@NotNull SlotPos slot, boolean editable);

  /**
   * sets the value of the property with the given name.
   * <p>
   * this will replace the existing value for the property,
   * if there is one.
   *
   * @param name the property's name.
   * @param value the new property's value.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  InventoryContents setProperty(@NotNull String name, @NotNull Object value);

  /**
   * returns a list of all the slots in the inventory.
   *
   * @return the inventory slots.
   */
  @NotNull
  default List<SlotPos> slots() {
    final var position = new ArrayList<SlotPos>();
    final var all = this.all();
    for (var row = 0; row < all.length; row++) {
      for (var column = 0; column < all[0].length; column++) {
        position.add(SlotPos.of(row, column));
      }
    }
    return position;
  }

  /**
   * updates the title of the player's page.
   *
   * @param newTitle the new title to update.
   */
  void updateTitle(@NotNull String newTitle);
}
