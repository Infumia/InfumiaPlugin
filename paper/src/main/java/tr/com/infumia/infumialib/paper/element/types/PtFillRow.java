package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

@RequiredArgsConstructor
public final class PtFillRow implements PlaceType {

  private final int row;

  @NotNull
  private static PtFillRow create(@NotNull final Map<String, Object> objects) {
    return new PtFillRow(PlaceType.getInteger(objects, "row", 0));
  }

  @NotNull
  @Override
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-row";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillRow(this.row, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    this.getSerializer().serialize(this, transformedData);
  }

  @NotNull
  public Map<String, Object> toMap() {
    return Map.of("row", this.row);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillRow> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillRow> deserialize(@NotNull final TransformedData transformedData,
                                           @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRow::create);
    }

    @Override
    public void serialize(@NotNull final PtFillRow placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
      transformedData.addAsMap("values", placeType.toMap(), String.class, Object.class);
    }
  }
}
