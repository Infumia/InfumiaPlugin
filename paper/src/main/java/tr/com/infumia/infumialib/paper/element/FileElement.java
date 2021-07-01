package tr.com.infumia.infumialib.paper.element;

import com.cryptomorin.xseries.XMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import tr.com.infumia.infumialib.paper.bukkititembuilder.Builder;
import tr.com.infumia.infumialib.paper.bukkititembuilder.ItemStackBuilder;
import tr.com.infumia.infumialib.paper.element.types.PtFill;
import tr.com.infumia.infumialib.paper.element.types.PtFillBorders;
import tr.com.infumia.infumialib.paper.element.types.PtFillColumn;
import tr.com.infumia.infumialib.paper.element.types.PtFillEmpties;
import tr.com.infumia.infumialib.paper.element.types.PtFillPattern;
import tr.com.infumia.infumialib.paper.element.types.PtFillPatternStart;
import tr.com.infumia.infumialib.paper.element.types.PtFillPatternStartIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRectFromTo;
import tr.com.infumia.infumialib.paper.element.types.PtFillRectIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPattern;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPatternStart;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPatternStartIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRow;
import tr.com.infumia.infumialib.paper.element.types.PtFillSquareFromTo;
import tr.com.infumia.infumialib.paper.element.types.PtFillSquareIndex;
import tr.com.infumia.infumialib.paper.element.types.PtInsert;
import tr.com.infumia.infumialib.paper.element.types.PtInsertIndex;
import tr.com.infumia.infumialib.paper.element.types.PtNone;
import tr.com.infumia.infumialib.paper.element.types.PtSlots;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.ClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.SmartEvent;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

@SuppressWarnings("all")
@RequiredArgsConstructor
public final class FileElement {

  @NotNull
  private final List<Consumer<ClickEvent>> events;

  @NotNull
  private final Builder<?, ?> itemStack;

  @NotNull
  @Getter
  private final PlaceType placeType;

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final Builder<?, ?> builder, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, PtFill.INSTANCE, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fill(@NotNull final ItemStack itemStack, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fill(ItemStackBuilder.from(itemStack), events);
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
  public static FileElement fillBorders(@NotNull final Builder<?, ?> builder,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, PtFillBorders.INSTANCE, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillBorders(@NotNull final ItemStack itemStack,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillBorders(ItemStackBuilder.from(itemStack), events);
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
    return FileElement.fillColumn(ItemStackBuilder.from(itemStack), column, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillColumn(@NotNull final Builder<?, ?> builder, final int column,
                                       @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillColumn(column), events);
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
  public static FileElement fillEmpties(@NotNull final Builder<?, ?> builder,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, PtFillEmpties.INSTANCE, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillEmpties(@NotNull final ItemStack itemStack,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillEmpties(ItemStackBuilder.from(itemStack), events);
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
  public static FileElement fillPattern(@NotNull final Builder<?, ?> builder, final boolean wrapAround,
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillPattern(wrapAround, pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                        @NotNull final List<String> pattern,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPattern(ItemStackBuilder.from(itemStack), wrapAround, pattern, events);
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
  public static FileElement fillPatternStart(@NotNull final Builder<?, ?> builder, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillPatternStart(wrapAround, pattern, startRow, startColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                             @NotNull final List<String> pattern, final int startRow,
                                             final int startColumn, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStart(ItemStackBuilder.from(itemStack), wrapAround, pattern, startRow, startColumn,
      events);
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
  public static FileElement fillPatternStartIndex(@NotNull final Builder<?, ?> builder, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillPatternStartIndex(wrapAround, pattern, startIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillPatternStartIndex(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                  @NotNull final List<String> pattern, final int startIndex,
                                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillPatternStartIndex(ItemStackBuilder.from(itemStack), wrapAround, pattern, startIndex, events);
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
  public static FileElement fillRectFromTo(@NotNull final Builder<?, ?> builder, final int fromRow, final int fromColumn,
                                           final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillRectFromTo(fromRow, fromColumn, toRow, toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectFromTo(@NotNull final ItemStack itemStack, final int fromRow,
                                           final int fromColumn, final int toRow, final int toColumn,
                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectFromTo(ItemStackBuilder.from(itemStack), fromRow, fromColumn, toRow, toColumn, events);
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
  public static FileElement fillRectIndex(@NotNull final Builder<?, ?> builder, final int fromIndex, final int toIndex,
                                          @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillRectIndex(fromIndex, toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRectIndex(@NotNull final ItemStack itemStack, final int fromIndex,
                                          final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRectIndex(ItemStackBuilder.from(itemStack), fromIndex, toIndex, events);
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
  public static FileElement fillRepeatingPattern(@NotNull final Builder<?, ?> builder, final boolean wrapAround,
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillRepeatingPattern(wrapAround, pattern), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPattern(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                 @NotNull final List<String> pattern,
                                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPattern(ItemStackBuilder.from(itemStack), wrapAround, pattern, events);
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
  public static FileElement fillRepeatingPatternStart(@NotNull final Builder<?, ?> builder, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder,
      new PtFillRepeatingPatternStart(wrapAround, pattern, startRow, startColumn, endRow, endColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStart(@NotNull final ItemStack itemStack, final boolean wrapAround,
                                                      @NotNull final List<String> pattern, final int startRow,
                                                      final int startColumn, final int endRow, final int endColumn,
                                                      @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStart(ItemStackBuilder.from(itemStack), wrapAround, pattern, startRow,
      startColumn, endRow, endColumn, events);
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
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final Builder<?, ?> builder,
                                                           final boolean wrapAround, @NotNull final List<String> pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillRepeatingPatternStartIndex(wrapAround, pattern, startIndex, endIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRepeatingPatternStartIndex(@NotNull final ItemStack itemStack,
                                                           final boolean wrapAround, @NotNull final List<String> pattern,
                                                           final int startIndex, final int endIndex,
                                                           @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRepeatingPatternStartIndex(ItemStackBuilder.from(itemStack), wrapAround, pattern, startIndex,
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
  public static FileElement fillRow(@NotNull final Builder<?, ?> builder, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillRow(row), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillRow(@NotNull final ItemStack itemStack, final int row,
                                    @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillRow(ItemStackBuilder.from(itemStack), row, events);
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
  public static FileElement fillSquareFromTo(@NotNull final Builder<?, ?> builder, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillSquareFromTo(fromRow, fromColumn, toRow, toColumn), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareFromTo(@NotNull final ItemStack itemStack, final int fromRow,
                                             final int fromColumn, final int toRow, final int toColumn,
                                             @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareFromTo(ItemStackBuilder.from(itemStack), fromRow, fromColumn, toRow, toColumn, events);
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
  public static FileElement fillSquareIndex(@NotNull final Builder<?, ?> builder, final int fromIndex,
                                            final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtFillSquareIndex(fromIndex, toIndex), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement fillSquareIndex(@NotNull final ItemStack itemStack, final int fromIndex,
                                            final int toIndex, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.fillSquareIndex(ItemStackBuilder.from(itemStack), fromIndex, toIndex, events);
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
  public static FileElement from(@NotNull final Builder<?, ?> builder, @NotNull final PlaceType placeType,
                                 @NotNull final List<Consumer<ClickEvent>> events) {
    return new FileElement(events, builder, placeType);
  }

  @SafeVarargs
  @NotNull
  public static FileElement from(@NotNull final Builder<?, ?> builder, @NotNull final PlaceType placeType,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, placeType, Arrays.asList(events));
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final Builder<?, ?> builder, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtInsert(row, column), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insert(@NotNull final ItemStack itemStack, final int row, final int column,
                                   @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insert(ItemStackBuilder.from(itemStack), row, column, events);
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
  public static FileElement insertIndex(@NotNull final Builder<?, ?> builder, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtInsertIndex(index), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement insertIndex(@NotNull final ItemStack itemStack, final int index,
                                        @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.insertIndex(ItemStackBuilder.from(itemStack), index, events);
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
  public static FileElement none(@NotNull final Builder<?, ?> builder, @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, PtNone.INSTANCE, events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement none(@NotNull final ItemStack itemStack,
                                 @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.none(ItemStackBuilder.from(itemStack), events);
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
  public static FileElement slots(@NotNull final Builder<?, ?> builder, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(builder, new PtSlots(slots), events);
  }

  @SafeVarargs
  @NotNull
  public static FileElement slots(@NotNull final ItemStack itemStack, @NotNull final List<Integer> slots,
                                  @NotNull final Consumer<ClickEvent>... events) {
    return FileElement.from(ItemStackBuilder.from(itemStack), new PtSlots(slots), events);
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
    return this.itemStack.getItemStack().clone();
  }

  public void place(@NotNull final InventoryContents contents) {
    this.placeType.place(this.clickableItem(), contents);
  }

  public void place(@NotNull final SmartEvent event) {
    this.place(event.contents());
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
  private FileElement duplicate(@NotNull final ItemStack itemStack) {
    return FileElement.from(ItemStackBuilder.from(itemStack), this.placeType, this.getEvents());
  }

  @NotNull
  private FileElement duplicate(@NotNull final PlaceType type) {
    return FileElement.from(ItemStackBuilder.from(this.getItemStack()), type, this.getEvents());
  }

  @NotNull
  private FileElement duplicate(@NotNull final List<Consumer<ClickEvent>> events) {
    return FileElement.from(ItemStackBuilder.from(this.getItemStack()), this.placeType, events);
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
      final var itemStack = transformedData.get("item", ItemStack.class);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var type = transformedData.get("type", String.class);
      if (type.isEmpty()) {
        return Optional.empty();
      }
      final var deserializer = PlaceType.getByType(type.get());
      if (deserializer.isEmpty()) {
        return Optional.empty();
      }
      final var placeType = deserializer.get().deserialize(transformedData);
      if (placeType.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(FileElement.from(ItemStackBuilder.from(itemStack.get()), placeType.get())
        .changeEvent(fileElement.getEvents()));
    }

    @Override
    public void serialize(@NotNull final FileElement fileElement, @NotNull final TransformedData transformedData) {
      transformedData.add("item", fileElement.getItemStack(), ItemStack.class);
      fileElement.getPlaceType().serialize(transformedData);
    }

    @Override
    public boolean supports(@NotNull final Class<?> cls) {
      return cls == FileElement.class;
    }
  }
}
