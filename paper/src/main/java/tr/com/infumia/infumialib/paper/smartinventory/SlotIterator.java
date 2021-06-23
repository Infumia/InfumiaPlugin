package tr.com.infumia.infumialib.paper.smartinventory;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;

/**
 * a class that allows you to iterate through the slots of
 * an inventory either {@link Type#HORIZONTAL horizontally}
 * or {@link Type#VERTICAL vertically}.
 */
public interface SlotIterator {

  /**
   * sets the value of the allow override option.
   * <p>
   * - if this is {@code true}, the iterator will override any
   * existing icon it founds on its way.
   * <p>
   * - if this is {@code false}, the iterator will skip
   * the slots which are not empty.
   *
   * @param override the value of the allow override option.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator allowOverride(boolean override);

  /**
   * blacklists the given slot position.
   * <p>
   * Blacklisting a slot will make the iterator
   * skip the given slot and directly go to the next
   * un-blacklisted slot.
   *
   * @param slotPos the slot to blacklist.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default SlotIterator blacklist(@NotNull final SlotPos slotPos) {
    return this.blacklist(slotPos.getRow(), slotPos.getColumn());
  }

  /**
   * blacklists the given slot index.
   * <p>
   * blacklisting a slot will make the iterator skip the given slot and directly go to the next
   * un-blacklisted slot.
   *
   * @param index the index to blacklist.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator blacklist(int index);

  /**
   * blacklists the given slot position.
   * <p>
   * blacklisting a slot will make the iterator
   * skip the given slot and directly go to the next
   * un-blacklisted slot.
   *
   * @param row the row of the slot to blacklist.
   * @param column the column of the slot to blacklist.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator blacklist(int row, int column);

  /**
   * this method has the inverse effect of {@link #withPattern(Pattern)}, where the other method would only allow the
   * iterator to go, this method prohibits this slots to iterate over.
   *
   * @param pattern the pattern where the slot iterator cannot iterate.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default SlotIterator blacklistPattern(@NotNull final Pattern<Boolean> pattern) {
    return this.blacklistPattern(pattern, 0, 0);
  }

  /**
   * This method has the inverse effect of {@link #withPattern(Pattern, int, int)},
   * where the other method would only allow the iterator to go,
   * this method prohibits this slots to iterate over.
   *
   * @param pattern the pattern where the slot iterator cannot iterate.
   * @param rowOffset the row offset from the top left corner.
   * @param columnOffset the column offset from the top left corner.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator blacklistPattern(@NotNull Pattern<Boolean> pattern, int rowOffset, int columnOffset);

  /**
   * gets the current column of the iterator.
   *
   * @return the current column.
   */
  int column();

  /**
   * sets the current column of the iterator.
   *
   * @param column the new column.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator column(int column);

  /**
   * gets the value of the allow override option.
   * <p>
   * - if this is {@code true}, the iterator will override any
   * existing icon it founds on its way.
   * <p>
   * - if this is {@code false}, the iterator will skip
   * the slots which are not empty.
   *
   * @return {@code true} if this iterator allows to override.
   */
  boolean doesAllowOverride();

  /**
   * sets the slot where the iterator should end.
   * <p>
   * if the row of the SlotPos is a negative value, it is set to the maximum row count.
   * <p>
   * if the column of the SlotPos is a negative value, it is set to maximum column count.
   *
   * @param endPosition the slot where the iterator should end.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default SlotIterator endPosition(@NotNull final SlotPos endPosition) {
    return this.endPosition(endPosition.getRow(), endPosition.getColumn());
  }

  /**
   * sets the slot where the iterator should end.
   * <p>
   * if the row is a negative value, it is set to the maximum row count.
   * <p>
   * if the column is a negative value, it is set to maximum column count.
   *
   * @param row the row where the iterator should end.
   * @param column the column where the iterator should end.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator endPosition(int row, int column);

  /**
   * checks if this iterator has been ended.
   * <p>
   * an iterator is not ended until it has reached the last slot of the inventory.
   *
   * @return {@code true} if this iterator has been ended.
   */
  boolean ended();

  /**
   * gets the icon at the current position in the inventory.
   *
   * @return the icon at the current position.
   */
  @NotNull
  Optional<Icon> get();

  /**
   * moves the cursor to the next position inside the inventory.
   * <p>
   * this has no effect if the cursor is already at the last position of the inventory.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator next();

  /**
   * moves the cursor to the previous position inside the inventory.
   * <p>
   * this has no effect if the cursor is already  at the first position of the inventory.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator previous();

  /**
   * resets iterator to its original position specified while creation.
   * <p>
   * when the iterator gets reset to its original position, {@code started} gets set back to {@code false}.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator reset();

  /**
   * gets the current row of the iterator.
   *
   * @return the current row.
   */
  int row();

  /**
   * sets the current row of the iterator.
   *
   * @param row the new row.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator row(int row);

  /**
   * replaces the icon at the current position in the inventory by the given icon.
   *
   * @param icon the new icon.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator set(@NotNull Icon icon);

  /**
   * checks if this iterator has been started.
   * <p>
   * an iterator is not started until any
   * of {@link SlotIterator#previous()} or {@link SlotIterator#next()}
   * methods have been called.
   *
   * @return {@code true} if this iterator has been started.
   */
  boolean started();

  /**
   * setting a pattern using this method will use it as a guideline where the slot iterator can set icons or not.
   * if the pattern doesn't fill the whole inventory, the slot iterator is limited to the space the pattern provides.
   * if the pattern has the {@code wrapAround} flag set, then the iterator can iterate over the entire inventory,
   * even if the pattern would not fill it by itself.
   * <p>
   * if the provided pattern has no default value set, this method will set it to {@code false}.
   * <p>
   * if you pass {@code null} into the {@code pattern} parameter, this functionality will be disabled and
   * the iterator will continue to work as normal.
   *
   * @param pattern the pattern to use as a guideline.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default SlotIterator withPattern(@NotNull final Pattern<Boolean> pattern) {
    return this.withPattern(pattern, 0, 0);
  }

  /**
   * setting a pattern using this method will use it as a guideline where the slot iterator can set icons or not.
   * if the pattern doesn't fill the whole inventory, the slot iterator is limited to the space the pattern provides.
   * if the pattern has the {@code wrapAround} flag set, then the iterator can iterate over the entire inventory,
   * even if the pattern would not fill it by itself.
   * <p>
   * the offset defines the top-left corner of the pattern. if the {@code wrapAround} flag is set, then the entire
   * pattern will be just shifted by the given amount.
   * <p>
   * if the provided pattern has no default value set, this method will set it to {@code false}.
   * <br><br>
   * if you pass {@code null} into the {@code pattern} parameter, this functionality will be disabled and
   * the iterator will continue to work as normal.
   *
   * @param pattern the pattern to use as a guideline.
   * @param rowOffset the row offset from the top left corner.
   * @param columnOffset the column offset from the top left corner.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  SlotIterator withPattern(@NotNull Pattern<Boolean> pattern, int rowOffset, int columnOffset);

  /**
   * the iterate type of the inventory.
   */
  enum Type {

    /**
     * iterates horizontally from the left to the right of the inventory, and
     * jump to the next line when the last column is reached.
     */
    HORIZONTAL,
    /**
     * iterates vertically from the up to the down of the inventory, and
     * jump to the next column when the last line is reached.
     */
    VERTICAL
  }
}
