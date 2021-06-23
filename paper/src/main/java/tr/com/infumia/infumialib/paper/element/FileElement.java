package tr.com.infumia.infumialib.paper.element;

import com.cryptomorin.xseries.XMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.element.Placeholder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.ItemStackBuilder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.ItemStackUtil;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.ClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.SmartEvent;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

@RequiredArgsConstructor
public final class FileElement {

  @NotNull
  private final List<Consumer<ClickEvent>> events;

  @NotNull
  private final ItemStack itemStack;

  @NotNull
  @Getter
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
    return FileElement.fill(builder.getItemStack(), events);
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
    return FileElement.fillBorders(builder.getItemStack(), events);
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
    return FileElement.from(itemStack, PlaceType.FILL_COLUMN, Map.of("column", column), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final ItemStackBuilder builder, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillColumn(builder.getItemStack(), column, events);
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
    return FileElement.fillEmpties(builder.getItemStack(), events);
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
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN,
      Map.of("wrapAround", wrapAround, "pattern", pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(builder.getItemStack(), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final Material material, final boolean wrapAround,
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START,
      Map.of("wrapAround", wrapAround, "pattern", pattern, "startRow", startRow, "startColumn", startColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(builder.getItemStack(), wrapAround, pattern, startRow, startColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final Material material, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(ItemStackBuilder.from(material), wrapAround, pattern, startRow, startColumn,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_PATTERN_START_INDEX,
      Map.of("wrapAround", wrapAround, "pattern", pattern, "startIndex", startIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(builder.getItemStack(), wrapAround, pattern, startIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern, startIndex,
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
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
      Map.of("fromRow", fromRow, "fromColumn", fromColumn, "toRow", toRow, "toColumn", toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final ItemStackBuilder builder, final int fromRow,
                                           final int fromColumn, final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectFromTo(builder.getItemStack(), fromRow, fromColumn, toRow, toColumn, events);
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
      Map.of("fromIndex", fromIndex, "toIndex", toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final ItemStackBuilder builder, final int fromIndex,
                                          final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectIndex(builder.getItemStack(), fromIndex, toIndex, events);
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
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN,
      Map.of("wrapAround", wrapAround, "pattern", pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(builder.getItemStack(), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final Material material, final boolean wrapAround,
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final XMaterial material, final boolean wrapAround,
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(ItemStackBuilder.from(material), wrapAround, pattern, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START,
      Map.of("wrapAround", wrapAround, "pattern", pattern, "startRow", startRow, "startColumn", startColumn, "endRow", endRow, "endColumn", endColumn),
      events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final ItemStackBuilder builder, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(builder.getItemStack(), wrapAround, pattern, startRow, startColumn,
      endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final Material material, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
      startRow, startColumn, endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final XMaterial material, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(material), wrapAround, pattern,
      startRow, startColumn, endRow, endColumn, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStack itemStack,
                                                           final boolean wrapAround, @NotNull final List<String> pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_REPEATING_PATTERN_START_INDEX,
      Map.of("wrapAround", wrapAround, "pattern", pattern, "startIndex", startIndex, "endIndex", endIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStackBuilder builder,
                                                           final boolean wrapAround, @NotNull final List<String> pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(builder.getItemStack(), wrapAround, pattern, startIndex,
      endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final Material material, final boolean wrapAround,
                                                           @NotNull final List<String> pattern, final int startIndex,
                                                           final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
      startIndex, endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final XMaterial material, final boolean wrapAround,
                                                           @NotNull final List<String> pattern, final int startIndex,
                                                           final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(material), wrapAround, pattern,
      startIndex, endIndex, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final ItemStack itemStack, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(itemStack, PlaceType.FILL_ROW, Map.of("row", row), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final ItemStackBuilder builder, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRow(builder.getItemStack(), row, events);
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
      Map.of("fromRow", fromRow, "fromColumn", fromColumn, "toRow", toRow, "toColumn", toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final ItemStackBuilder builder, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareFromTo(builder.getItemStack(), fromRow, fromColumn, toRow, toColumn, events);
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
      Map.of("fromIndex", fromIndex, "toIndex", toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final ItemStackBuilder builder, final int fromIndex,
                                            final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareIndex(builder.getItemStack(), fromIndex, toIndex, events);
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
    return FileElement.from(itemStack, PlaceType.INSERT, Map.of("row", row, "column", column), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final ItemStackBuilder builder, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insert(builder.getItemStack(), row, column, events);
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
    return FileElement.from(itemStack, PlaceType.INSERT_INDEX, Map.of("index", index), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final ItemStackBuilder builder, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insertIndex(builder.getItemStack(), index, events);
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
    return FileElement.none(builder.getItemStack(), events);
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
    return FileElement.from(itemStack, PlaceType.SLOTS, Map.of("slots", slots), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final ItemStackBuilder builder, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.slots(builder.getItemStack(), slots, events);
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

  public FileElement addEvent(@NotNull final Consumer<ClickEvent> event) {
    final var events = new ArrayList<>(this.getEvents());
    events.add(event);
    return this.changeEvent(events);
  }

  @NotNull
  public FileElement addValue(@NotNull final String key, @NotNull final Object object) {
    final var copy = new HashMap<>(this.values);
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
    return this.changeItemStack(ItemStackBuilder.from(this.getItemStack())
      .setLore(lore, colored)
      .getItemStack());
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
    final var clone = this.getItemStack();
    clone.setType(material);
    return this.changeItemStack(clone);
  }

  @NotNull
  public FileElement changeMaterial(@NotNull final XMaterial xmaterial) {
    final var clone = this.getItemStack();
    Optional.ofNullable(xmaterial.parseMaterial()).ifPresent(clone::setType);
    return this.changeItemStack(clone);
  }

  @NotNull
  public FileElement changeName(final boolean colored, @NotNull final String name) {
    return this.changeItemStack(ItemStackBuilder.from(this.getItemStack())
      .setName(name, colored)
      .getItemStack());
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
    final var icon = Icon.from(this.getItemStack());
    this.getEvents().forEach(icon::whenClick);
    return icon;
  }

  @NotNull
  public List<Consumer<ClickEvent>> getEvents() {
    return Collections.unmodifiableList(this.events);
  }

  @NotNull
  public ItemStack getItemStack() {
    return this.itemStack.clone();
  }

  public void place(@NotNull final InventoryContents contents) {
    this.getPlaceType().place(this.clickableItem(), contents, this.values());
  }

  public void place(@NotNull final SmartEvent event) {
    this.place(event.contents());
  }

  @NotNull
  public FileElement removeValue(@NotNull final String key) {
    final var copy = new HashMap<>(this.values);
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
    final var clone = this.getItemStack();
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

  public void set(@NotNull final InventoryContents contents, final int row, final int column) {
    contents.set(row, column, this.clickableItem());
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
    return FileElement.from(itemStack, this.getPlaceType(), this.values(), this.getEvents());
  }

  @NotNull
  private FileElement duplicate(@NotNull final PlaceType type) {
    return FileElement.from(this.getItemStack(), type, this.values(), this.getEvents());
  }

  @NotNull
  private FileElement duplicate(@NotNull final Map<String, Object> values) {
    return FileElement.from(this.getItemStack(), this.getPlaceType(), values, this.getEvents());
  }

  @NotNull
  private FileElement duplicate(@NotNull final List<Consumer<ClickEvent>> events) {
    return FileElement.from(this.getItemStack(), this.getPlaceType(), this.values(), events);
  }

  public static final class Serializer implements ObjectSerializer<FileElement> {

    @NotNull
    @Override
    public Optional<FileElement> deserialize(@NotNull final TransformedData transformedData,
                                             @Nullable final GenericDeclaration declaration) {
      return Optional.empty();
    }

    @NotNull
    @Override
    public Optional<FileElement> deserialize(@NotNull final FileElement fileElement,
                                             @NotNull final TransformedData transformedData,
                                             @Nullable final GenericDeclaration declaration) {
      final var itemMapOptional = transformedData.getAsMap("item", String.class, Object.class);
      if (itemMapOptional.isEmpty()) {
        return Optional.empty();
      }
      final var itemStackOptional = ItemStackUtil.deserialize(KeyUtil.Holder.map(itemMapOptional.get()));
      final var typeOptional = transformedData.get("type", PlaceType.class);
      if (itemStackOptional.isEmpty() || typeOptional.isEmpty()) {
        return Optional.empty();
      }
      final var type = typeOptional.get();
      final var values = transformedData.getAsMap("values", String.class, Object.class)
        .orElse(new HashMap<>());
      if (type.control(values)) {
        return Optional.of(FileElement.from(itemStackOptional.get(), type, values)
          .changeEvent(fileElement.getEvents()));
      }
      return Optional.empty();
    }

    @Override
    public void serialize(@NotNull final FileElement fileElement, @NotNull final TransformedData transformedData) {
      final var map = new HashMap<String, Object>();
      ItemStackUtil.serialize(ItemStackBuilder.from(fileElement.getItemStack()), KeyUtil.Holder.map(map));
      transformedData.add("item", map);
      transformedData.add("type", fileElement.getPlaceType().name(), String.class);
      transformedData.remove("values");
      fileElement.getPlaceType().serialize(fileElement, transformedData);
    }

    @Override
    public boolean supports(@NotNull final Class<?> cls) {
      return cls == FileElement.class;
    }
  }
}
