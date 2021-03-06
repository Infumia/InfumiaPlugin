package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtFillPatternStart implements PlaceType {

  @NotNull
  private final List<String> pattern;

  private final int startColumn;

  private final int startRow;

  private final boolean wrapAround;

  public PtFillPatternStart(final boolean wrapAround, @NotNull final List<String> pattern, final int startRow,
                            final int startColumn) {
    this.wrapAround = wrapAround;
    this.pattern = Collections.unmodifiableList(pattern);
    this.startRow = startRow;
    this.startColumn = startColumn;
  }

  @NotNull
  private static PtFillPatternStart create(@NotNull final Map<String, Object> objects) {
    return new PtFillPatternStart(
      PlaceType.getBoolean(objects, "wrap-around", false),
      PlaceType.getStringList(objects, "pattern", List.of("xxx", "yyy", "zzz")),
      PlaceType.getInteger(objects, "start-row", 0),
      PlaceType.getInteger(objects, "start-column", 0));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-pattern-start";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillPattern(new Pattern<>(this.wrapAround, this.pattern.toArray(String[]::new)),
      this.startRow, this.startColumn);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("wrap-around", this.wrapAround, boolean.class);
    copy.addAsCollection("pattern", this.pattern, String.class);
    copy.add("start-row", this.startRow, int.class);
    copy.add("start-column", this.startColumn, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillPatternStart::create);
    }
  }
}
