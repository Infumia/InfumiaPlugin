package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtInsert implements PlaceType {

  private final int column;

  private final int row;

  public PtInsert(final int row, final int column) {
    this.row = row;
    this.column = column;
  }

  @NotNull
  private static PtInsert create(@NotNull final Map<String, Object> objects) {
    return new PtInsert(
      PlaceType.getInteger(objects, "row", 0),
      PlaceType.getInteger(objects, "column", 0));
  }

  @NotNull
  @Override
  public String getType() {
    return "insert";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.set(SlotPos.of(this.row, this.column), icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("row", this.row, int.class);
    copy.add("column", this.column, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtInsert::create);
    }
  }
}
