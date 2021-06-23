package tr.com.infumia.infumialib.paper.smartinventory.content;

import com.google.common.base.Preconditions;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.SlotIterator;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;

/**
 * an implementation for {@link SlotIterator}.
 */
public final class BasicSlotIterator implements SlotIterator {

  /**
   * the blacklisted.
   */
  private final Set<SlotPos> blacklisted = new HashSet<>();

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  /**
   * the start column.
   */
  private final int startColumn;

  /**
   * the start row.
   */
  private final int startRow;

  /**
   * the type.
   */
  @NotNull
  private final SlotIterator.Type type;

  /**
   * the allow override.
   */
  private boolean allowOverride = true;

  /**
   * the blacklist pattern.
   */
  @Nullable
  private Pattern<Boolean> blacklistPattern;

  /**
   * the blacklist pattern column offset.
   */
  private int blacklistPatternColumnOffset;

  /**
   * the blacklist pattern row offset.
   */
  private int blacklistPatternRowOffset;

  /**
   * the column.
   */
  private int column;

  /**
   * the end column.
   */
  private int endColumn;

  /**
   * the end row.
   */
  private int endRow;

  /**
   * the pattern.
   */
  @Nullable
  private Pattern<Boolean> pattern;

  /**
   * the pattern column offset.
   */
  private int patternColumnOffset;

  /**
   * the pattern row offset.
   */
  private int patternRowOffset;

  /**
   * the row.
   */
  private int row;

  /**
   * the started.
   */
  private boolean started;

  /**
   * ctor.
   *
   * @param contents the contents.
   * @param type the type.
   */
  public BasicSlotIterator(@NotNull final InventoryContents contents, @NotNull final SlotIterator.Type type) {
    this(contents, type, 0, 0);
  }

  /**
   * ctor.
   *
   * @param contents the contents.
   * @param type the type.
   * @param startRow the start row.
   * @param startColumn the start column.
   */
  public BasicSlotIterator(@NotNull final InventoryContents contents, @NotNull final SlotIterator.Type type,
                           final int startRow, final int startColumn) {
    this.contents = contents;
    this.type = type;
    this.endRow = this.contents.page().row() - 1;
    this.endColumn = this.contents.page().column() - 1;
    this.startRow = startRow;
    this.row = startRow;
    this.startColumn = startColumn;
    this.column = startColumn;
  }

  @NotNull
  @Override
  public SlotIterator allowOverride(final boolean override) {
    this.allowOverride = override;
    return this;
  }

  @NotNull
  @Override
  public SlotIterator blacklist(final int index) {
    final int count = this.contents.page().column();
    this.blacklisted.add(SlotPos.of(index / count, index % count));
    return this;
  }

  @NotNull
  @Override
  public SlotIterator blacklist(final int row, final int column) {
    this.blacklisted.add(SlotPos.of(row, column));
    return this;
  }

  @NotNull
  @Override
  public SlotIterator blacklistPattern(@NotNull final Pattern<Boolean> pattern, final int rowOffset,
                                       final int columnOffset) {
    this.blacklistPatternRowOffset = rowOffset;
    this.blacklistPatternColumnOffset = columnOffset;
    if (pattern.getDefaultValue().isEmpty()) {
      pattern.setDefault(false);
    }
    this.blacklistPattern = pattern;
    return this;
  }

  @Override
  public int column() {
    return this.column;
  }

  @NotNull
  @Override
  public SlotIterator column(final int column) {
    this.column = column;
    return this;
  }

  @Override
  public boolean doesAllowOverride() {
    return this.allowOverride;
  }

  @NotNull
  @Override
  public SlotIterator endPosition(final int row, final int column) {
    var tempRow = row;
    var tempColumn = column;
    if (row < 0) {
      tempRow = this.contents.page().row() - 1;
    }
    if (tempColumn < 0) {
      tempColumn = this.contents.page().column() - 1;
    }
    Preconditions.checkArgument(tempRow * tempColumn >= this.startRow * this.startColumn,
      "The end position needs to be after the start of the slot iterator");
    this.endRow = tempRow;
    this.endColumn = tempColumn;
    return this;
  }

  @Override
  public boolean ended() {
    return this.row == this.endRow
      && this.column == this.endColumn;
  }

  @NotNull
  @Override
  public Optional<Icon> get() {
    return this.contents.get(this.row, this.column);
  }

  @NotNull
  @Override
  public SlotIterator next() {
    if (this.ended()) {
      this.started = true;
      return this;
    }
    do {
      if (this.started) {
        if (this.type == Type.HORIZONTAL) {
          ++this.column;
          this.column %= this.contents.page().column();
          if (this.column == 0) {
            this.row++;
          }
        } else if (this.type == Type.VERTICAL) {
          ++this.row;
          this.row %= this.contents.page().row();
          if (this.row == 0) {
            this.column++;
          }
        }
      } else {
        this.started = true;
      }
    }
    while (!this.canPlace() && !this.ended());
    return this;
  }

  @NotNull
  @Override
  public SlotIterator previous() {
    if (this.row == 0 && this.column == 0) {
      this.started = true;
      return this;
    }
    do {
      if (this.started) {
        if (this.type == Type.HORIZONTAL) {
          this.column--;
          if (this.column == 0) {
            this.column = this.contents.page().column() - 1;
            this.row--;
          }
        } else if (this.type == Type.VERTICAL) {
          this.row--;
          if (this.row == 0) {
            this.row = this.contents.page().row() - 1;
            this.column--;
          }
        }
      } else {
        this.started = true;
      }
    }
    while (!this.canPlace() && (this.row != 0 || this.column != 0));
    return this;
  }

  @NotNull
  @Override
  public SlotIterator reset() {
    this.started = false;
    return this.row(this.startRow)
      .column(this.startColumn);
  }

  @Override
  public int row() {
    return this.row;
  }

  @NotNull
  @Override
  public SlotIterator row(final int row) {
    this.row = row;
    return this;
  }

  @NotNull
  @Override
  public SlotIterator set(@NotNull final Icon icon) {
    if (this.canPlace()) {
      this.contents.set(this.row, this.column, icon);
    }
    return this;
  }

  @Override
  public boolean started() {
    return this.started;
  }

  @NotNull
  @Override
  public SlotIterator withPattern(@NotNull final Pattern<Boolean> pattern, final int rowOffset,
                                  final int columnOffset) {
    this.patternRowOffset = rowOffset;
    this.patternColumnOffset = columnOffset;
    if (pattern.getDefaultValue().isEmpty()) {
      pattern.setDefault(false);
    }
    this.pattern = pattern;
    return this;
  }

  /**
   * checks if the item can place.
   *
   * @return {@code true} if the item can place the current location.
   */
  private boolean canPlace() {
    final var patternAllows = new AtomicBoolean(true);
    Optional.ofNullable(this.pattern).ifPresent(booleanPattern ->
      patternAllows.set(this.checkPattern(booleanPattern, this.patternRowOffset, this.patternColumnOffset)));
    final var blacklistPatternAllows = new AtomicBoolean(true);
    Optional.ofNullable(this.blacklistPattern).ifPresent(booleanPattern ->
      blacklistPatternAllows.set(!this.checkPattern(booleanPattern, this.blacklistPatternRowOffset, this.blacklistPatternColumnOffset)));
    return !this.blacklisted.contains(SlotPos.of(this.row, this.column)) &&
      (this.allowOverride || this.get().isEmpty()) &&
      patternAllows.get() &&
      blacklistPatternAllows.get();
  }

  /**
   * checks the pattern.
   *
   * @param pattern the pattern to check.
   * @param rowOffset the raw offset to check.
   * @param columnOffset the column offset to check.
   *
   * @return {@code true} if the checking was successful.
   */
  private boolean checkPattern(@NotNull final Pattern<Boolean> pattern, final int rowOffset, final int columnOffset) {
    final var object = pattern.getObject(this.row() - rowOffset, this.column() - columnOffset);
    if (pattern.isWrapAround()) {
      return object.orElse(false);
    }
    return this.row() >= rowOffset && this.column() >= columnOffset &&
      this.row() < pattern.getRowCount() + rowOffset &&
      this.column() < pattern.getColumnCount() + columnOffset &&
      object.orElse(false);
  }
}
