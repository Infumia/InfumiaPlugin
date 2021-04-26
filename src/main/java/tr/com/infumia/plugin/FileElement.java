package tr.com.infumia.plugin;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.bukkititembuilder.util.ItemStackUtil;
import io.github.portlek.configs.configuration.ConfigurationSection;
import io.github.portlek.configs.loaders.DataSerializer;
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.event.abs.ClickEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class FileElement implements DataSerializer {

  @NotNull
  private final List<Consumer<ClickEvent>> events;

  @NotNull
  private final ItemStack itemStack;

  @NotNull
  private final PlaceType placeType;

  @NotNull
  private final Map<String, Object> values;

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final ItemStackBuilder builder,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fill(builder.itemStack(), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final Material material, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fill(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final XMaterial material, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fill(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillBorders(@NotNull final ItemStack itemStack,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_BORDERS, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillBorders(@NotNull final ItemStackBuilder builder,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillBorders(builder.itemStack(), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillBorders(@NotNull final Material material,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillBorders(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillBorders(@NotNull final XMaterial material,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillBorders(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final ItemStack itemStack, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_COLUMN, PlaceType.parse("column", column), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final ItemStackBuilder builder, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillColumn(builder.itemStack(), column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final Material material, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillColumn(ItemStackBuilder.from(material), column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final XMaterial material, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillColumn(ItemStackBuilder.from(material), column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillEmpties(@NotNull final ItemStack itemStack,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_EMPTIES, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillEmpties(@NotNull final ItemStackBuilder builder,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillEmpties(builder.itemStack(), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillEmpties(@NotNull final Material material,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillEmpties(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillEmpties(@NotNull final XMaterial material,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillEmpties(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                        @NotNull final String[] pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                        @NotNull final String[] pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(builder.itemStack(), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final Material material, final boolean wrapAround,
                                        @NotNull final String[] pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                        @NotNull final String[] pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern, "startRow", startRow, "startColumn", startColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(builder.itemStack(), wrapAround, pattern, startRow, startColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final Material material, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                             @NotNull final String[] pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                  @NotNull final String[] pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START_INDEX,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern, "startIndex", startIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                  @NotNull final String[] pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(builder.itemStack(), wrapAround, pattern, startIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                                  @NotNull final String[] pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern, startIndex,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                                  @NotNull final String[] pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern, startIndex,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final ItemStack itemStack, final int fromRow, final int fromColumn,
                                           final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_RECT_FROM_TO,
      PlaceType.parse("fromRow", fromRow, "fromColumn", fromColumn, "toRow", toRow, "toColumn", toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final ItemStackBuilder builder, final int fromRow,
                                           final int fromColumn, final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectFromTo(builder.itemStack(), fromRow, fromColumn, toRow, toColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final Material material, final int fromRow, final int fromColumn,
                                           final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final XMaterial material, final int fromRow, final int fromColumn,
                                           final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final ItemStack itemStack, final int fromIndex, final int toIndex,
                                          @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_RECT_INDEX,
      PlaceType.parse("fromIndex", fromIndex, "toIndex", toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final ItemStackBuilder builder, final int fromIndex,
                                          final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectIndex(builder.itemStack(), fromIndex, toIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final Material material, final int fromIndex, final int toIndex,
                                          @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final XMaterial material, final int fromIndex, final int toIndex,
                                          @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                 @NotNull final String[] pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                 @NotNull final String[] pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(builder.itemStack(), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final Material material, final boolean wrapAround,
                                                 @NotNull final String[] pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                                 @NotNull final String[] pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern, "startRow", startRow, "startColumn", startColumn, "endRow", endRow, "endColumn", endColumn),
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(builder.itemStack(), wrapAround, pattern, startRow, startColumn,
      endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final Material material, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
      startRow, startColumn, endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                                      @NotNull final String[] pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
      startRow, startColumn, endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStack itemStack,
                                                           final boolean wrapAround, @NotNull final String[] pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START_INDEX,
      PlaceType.parse("wrapAround", wrapAround, "pattern", pattern, "startIndex", startIndex, "endIndex", endIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStackBuilder builder,
                                                           final boolean wrapAround, @NotNull final String[] pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(builder.itemStack(), wrapAround, pattern, startIndex,
      endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                                           @NotNull final String[] pattern, final int startIndex,
                                                           final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
      startIndex, endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                                           @NotNull final String[] pattern, final int startIndex,
                                                           final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
      startIndex, endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final ItemStack itemStack, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_ROW, PlaceType.parse("row", row), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final ItemStackBuilder builder, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRow(builder.itemStack(), row, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final Material material, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRow(ItemStackBuilder.from(material), row, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final XMaterial material, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRow(ItemStackBuilder.from(material), row, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final ItemStack itemStack, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_SQUARE_FROM_TO,
      PlaceType.parse("fromRow", fromRow, "fromColumn", fromColumn, "toRow", toRow, "toColumn", toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final ItemStackBuilder builder, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareFromTo(builder.itemStack(), fromRow, fromColumn, toRow, toColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final Material material, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final XMaterial material, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareFromTo(ItemStackBuilder.from(material), fromRow, fromColumn, toRow, toColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final ItemStack itemStack, final int fromIndex,
                                            final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_SQUARE_INDEX,
      PlaceType.parse("fromIndex", fromIndex, "toIndex", toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final ItemStackBuilder builder, final int fromIndex,
                                            final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareIndex(builder.itemStack(), fromIndex, toIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final Material material, final int fromIndex, final int toIndex,
                                            @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final XMaterial material, final int fromIndex, final int toIndex,
                                            @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareIndex(ItemStackBuilder.from(material), fromIndex, toIndex, events);
  }

  @NotNull
  public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                                 @NotNull final Map<String, Object> values,
                                 @NotNull final List<Consumer<ClickEvent>> events) {
    return new FileElement(events, itemStack, placeType, new HashMap<>(values));
  }

  @SafeVarargs
  @NotNull
  public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                                 @NotNull final Map<String, Object> values,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, placeType, new HashMap<>(values), Arrays.asList(events));
  }

  @NotNull
  public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                                 @NotNull final List<Consumer<ClickEvent>> events) {
    return FileElement.from(itemStack, placeType, new HashMap<>(), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement from(@NotNull final ItemStack itemStack, @NotNull final PlaceType placeType,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, placeType, Arrays.asList(events));
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final ItemStack itemStack, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.INSERT, PlaceType.parse("row", row, "column", column), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final ItemStackBuilder builder, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insert(builder.itemStack(), row, column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final Material material, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insert(ItemStackBuilder.from(material), row, column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final XMaterial material, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insert(ItemStackBuilder.from(material), row, column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final ItemStack itemStack, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.INSERT_INDEX, PlaceType.parse("index", index), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final ItemStackBuilder builder, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insertIndex(builder.itemStack(), index, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final Material material, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insertIndex(ItemStackBuilder.from(material), index, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final XMaterial material, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insertIndex(ItemStackBuilder.from(material), index, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement none(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.NONE, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement none(@NotNull final ItemStackBuilder builder,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.none(builder.itemStack(), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement none(@NotNull final Material material, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.none(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement none(@NotNull final XMaterial material, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.none(ItemStackBuilder.from(material), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final ItemStack itemStack, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.SLOTS, PlaceType.parse("slots", slots), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final ItemStackBuilder builder, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.slots(builder.itemStack(), slots, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final Material material, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.slots(ItemStackBuilder.from(material), slots, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final XMaterial material, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.slots(ItemStackBuilder.from(material), slots, events);
  }

  @NotNull
  static Optional<FileElement> deserialize(@NotNull final ConfigurationSection section) {
    final var itemSection = section.getConfigurationSection("item");
    if (itemSection == null) {
      return Optional.empty();
    }
    final var itemStackOptional = ItemStackUtil.from(itemSection.getMapValues(false));
    final var typeOptional = section.getString("type");
    if (itemStackOptional.isEmpty() || typeOptional == null) {
      return Optional.empty();
    }
    final var type = PlaceType.fromString(typeOptional);
    final var values = Optional.ofNullable(section.getConfigurationSection("values"))
      .map(configurationSection -> configurationSection.getMapValues(false))
      .orElse(new HashMap<>());
    if (type.control(values)) {
      return Optional.of(FileElement.from(itemStackOptional.get(), type, values));
    }
    final var defaults = type.defaultValues();
    if (defaults.isEmpty()) {
      section.set("values", null);
    } else {
      defaults.forEach((s, o) -> section.set("values." + s, o));
    }
    return Optional.empty();
  }

  public FileElement addEvent(@NotNull final Consumer<ClickEvent> event) {
    final List<Consumer<ClickEvent>> events = new ArrayList<>(this.events());
    events.add(event);
    return this.changeEvent(events);
  }

  @NotNull
  public FileElement addValue(@NotNull final String key, @NotNull final Object object) {
    final Map<String, Object> copy = new HashMap<>(this.values);
    copy.put(key, object);
    return this.duplicate(copy);
  }

  @NotNull
  public FileElement changeEvent(@NotNull final List<Consumer<ClickEvent>> events) {
    return this.duplicate(events);
  }

  @NotNull
  public FileElement changeItemStack(@NotNull final ItemStack itemStack) {
    return this.duplicate(itemStack);
  }

  @NotNull
  public FileElement changeLore(final boolean colored, @NotNull final List<String> lore) {
    return this.changeItemStack(ItemStackBuilder.from(this.itemStack())
      .lore(lore, colored)
      .itemStack());
  }

  @NotNull
  public FileElement changeLore(final boolean colored, @NotNull final String... lore) {
    return this.changeLore(colored, Arrays.asList(lore));
  }

  @NotNull
  public FileElement changeLore(@NotNull final List<String> lore) {
    return this.changeLore(true, lore);
  }

  @NotNull
  public FileElement changeLore(@NotNull final String... lore) {
    return this.changeLore(true, lore);
  }

  @NotNull
  public FileElement changeMaterial(@NotNull final Material material) {
    final ItemStack clone = this.itemStack();
    clone.setType(material);
    return this.changeItemStack(clone);
  }

  @NotNull
  public FileElement changeMaterial(@NotNull final XMaterial xmaterial) {
    final ItemStack clone = this.itemStack();
    Optional.ofNullable(xmaterial.parseMaterial()).ifPresent(clone::setType);
    return this.changeItemStack(clone);
  }

  @NotNull
  public FileElement changeName(final boolean colored, @NotNull final String name) {
    return this.changeItemStack(ItemStackBuilder.from(this.itemStack())
      .name(name, colored)
      .itemStack());
  }

  @NotNull
  public FileElement changeName(@NotNull final String name) {
    return this.changeName(true, name);
  }

  @NotNull
  public FileElement changeType(@NotNull final PlaceType type) {
    return this.duplicate(type);
  }

  @NotNull
  public FileElement changeValues(@NotNull final Map<String, Object> values) {
    return this.duplicate(values);
  }

  @NotNull
  public Icon clickableItem() {
    final Icon icon = Icon.from(this.itemStack());
    this.events().forEach(icon::whenClick);
    return icon;
  }

  @NotNull
  public List<Consumer<ClickEvent>> events() {
    return Collections.unmodifiableList(this.events);
  }

  @NotNull
  public ItemStack itemStack() {
    return this.itemStack.clone();
  }

  public void place(@NotNull final InventoryContents contents) {
    this.type().place(this.clickableItem(), contents, this.values());
  }

  @NotNull
  public FileElement removeValue(@NotNull final String key) {
    final Map<String, Object> copy = new HashMap<>(this.values);
    copy.remove(key);
    return this.duplicate(copy);
  }

  @NotNull
  public FileElement replace(@NotNull final String regex, @NotNull final Object replace) {
    return this.replace(true, true, regex, replace);
  }

  @NotNull
  public FileElement replace(final boolean name, final boolean lore, @NotNull final String regex,
                             @NotNull final Object replace) {
    return this.replace(name, lore, Placeholder.from(regex, replace));
  }

  @NotNull
  public FileElement replace(@NotNull final Placeholder... placeholders) {
    return this.replace(true, true, placeholders);
  }

  @NotNull
  public FileElement replace(final boolean name, final boolean lore, @NotNull final Placeholder... placeholders) {
    return this.replace(name, lore, Arrays.asList(placeholders));
  }

  @NotNull
  public FileElement replace(@NotNull final Iterable<Placeholder> placeholders) {
    return this.replace(true, true, placeholders);
  }

  @NotNull
  public FileElement replace(final boolean name, final boolean lore,
                             @NotNull final Iterable<Placeholder> placeholders) {
    final ItemStack clone = this.itemStack();
    Optional.ofNullable(clone.getItemMeta()).ifPresent(itemMeta -> {
      if (name && itemMeta.hasDisplayName()) {
        placeholders.forEach(placeholder ->
          itemMeta.setDisplayName(placeholder.replace(itemMeta.getDisplayName())));
      }
      if (lore && itemMeta.getLore() != null && itemMeta.hasLore()) {
        final List<String> finallore = new ArrayList<>();
        itemMeta.getLore().forEach(s -> {
          final AtomicReference<String> finalstring = new AtomicReference<>(s);
          placeholders.forEach(placeholder ->
            finalstring.set(placeholder.replace(finalstring.get())));
          finallore.add(finalstring.get());
        });
        itemMeta.setLore(finallore);
      }
      clone.setItemMeta(itemMeta);
    });
    return this.changeItemStack(clone);
  }

  @Override
  public void serialize(@NotNull final ConfigurationSection section) {
    section.set("item", ItemStackUtil.to(this.itemStack()));
    section.set("type", this.type().name());
    section.set("values", null);
    this.values().forEach((s, o) ->
      section.set("values." + s, o));
  }

  public void set(@NotNull final InventoryContents contents, final int row, final int column) {
    contents.set(row, column, this.clickableItem());
  }

  @NotNull
  public PlaceType type() {
    return this.placeType;
  }

  @NotNull
  public Optional<Object> value(@NotNull final String key) {
    return Optional.ofNullable(this.values().get(key));
  }

  @NotNull
  public Map<String, Object> values() {
    return Collections.unmodifiableMap(this.values);
  }

  @NotNull
  private FileElement duplicate(@NotNull final ItemStack itemStack) {
    return FileElement.from(itemStack, this.type(), this.values(), this.events());
  }

  @NotNull
  private FileElement duplicate(@NotNull final PlaceType type) {
    return FileElement.from(this.itemStack(), type, this.values(), this.events());
  }

  @NotNull
  private FileElement duplicate(@NotNull final Map<String, Object> values) {
    return FileElement.from(this.itemStack(), this.type(), values, this.events());
  }

  @NotNull
  private FileElement duplicate(@NotNull final List<Consumer<ClickEvent>> events) {
    return FileElement.from(this.itemStack(), this.type(), this.values(), events);
  }
}
