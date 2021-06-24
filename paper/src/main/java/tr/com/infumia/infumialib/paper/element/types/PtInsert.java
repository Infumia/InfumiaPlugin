package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.SlotPos;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

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
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
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
    this.getSerializer().serialize(this, transformedData);
  }

  public static final class Serializer extends PlaceType.Serializer<PtInsert> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtInsert> deserialize(@NotNull final TransformedData transformedData,
                                          @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtInsert::create);
    }

    @Override
    public void serialize(@NotNull final PtInsert placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
      transformedData.add("row", placeType.row, int.class);
      transformedData.add("column", placeType.column, int.class);
    }
  }
}
