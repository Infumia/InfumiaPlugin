package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

public final class PtFillRepeatingPatternStart implements PlaceType {

  private final int endColumn;

  private final int endRow;

  @NotNull
  private final List<String> pattern;

  private final int startColumn;

  private final int startRow;

  private final boolean wrapAround;

  public PtFillRepeatingPatternStart(final boolean wrapAround, @NotNull final List<String> pattern, final int startRow,
                                     final int startColumn, final int endRow, final int endColumn) {
    this.wrapAround = wrapAround;
    this.pattern = Collections.unmodifiableList(pattern);
    this.startRow = startRow;
    this.startColumn = startColumn;
    this.endRow = endRow;
    this.endColumn = endColumn;
  }

  @NotNull
  private static PtFillRepeatingPatternStart create(@NotNull final Map<String, Object> objects) {
    return new PtFillRepeatingPatternStart(
      PlaceType.getBoolean(objects, "wrap-around", false),
      PlaceType.getStringList(objects, "pattern", List.of("xxx", "yyy", "zzz")),
      PlaceType.getInteger(objects, "start-row", 0),
      PlaceType.getInteger(objects, "start-column", 0),
      PlaceType.getInteger(objects, "end-row", 0),
      PlaceType.getInteger(objects, "end-column", 1));
  }

  @NotNull
  @Override
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-repeating-pattern-start";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillPatternRepeating(new Pattern<>(this.wrapAround, this.pattern.toArray(String[]::new)),
      this.startRow, this.startColumn, this.endRow, this.endColumn);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    this.getSerializer().serialize(this, transformedData);
  }

  @NotNull
  public Map<String, Object> toMap() {
    return Map.of(
      "wrap-around", this.wrapAround,
      "pattern", this.pattern,
      "start-row", this.startRow,
      "start-column", this.startColumn,
      "end-row", this.endRow,
      "end-column", this.endColumn);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillRepeatingPatternStart> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillRepeatingPatternStart> deserialize(@NotNull final TransformedData transformedData,
                                                             @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRepeatingPatternStart::create);
    }

    @Override
    public void serialize(@NotNull final PtFillRepeatingPatternStart placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
      transformedData.addAsMap("values", placeType.toMap(), String.class, Object.class);
    }
  }
}
