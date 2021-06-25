package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtFillRectFromTo implements PlaceType {

  private final int fromColumn;

  private final int fromRow;

  private final int toColumn;

  private final int toRow;

  public PtFillRectFromTo(final int fromRow, final int fromColumn, final int toRow, final int toColumn) {
    this.fromRow = fromRow;
    this.fromColumn = fromColumn;
    this.toRow = toRow;
    this.toColumn = toColumn;
  }

  @NotNull
  private static PtFillRectFromTo create(@NotNull final Map<String, Object> objects) {
    return new PtFillRectFromTo(
      PlaceType.getInteger(objects, "from-row", 0),
      PlaceType.getInteger(objects, "from-column", 0),
      PlaceType.getInteger(objects, "to-row", 0),
      PlaceType.getInteger(objects, "to-column", 1));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-rect-from-to";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillRect(this.fromRow, this.fromColumn, this.toRow, this.toColumn, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("from-row", this.fromRow, int.class);
    copy.add("from-column", this.fromColumn, int.class);
    copy.add("to-row", this.toRow, int.class);
    copy.add("to-column", this.toColumn, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRectFromTo::create);
    }
  }
}
