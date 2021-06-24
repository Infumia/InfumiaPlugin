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
public final class PtFillRectIndex implements PlaceType {

  private final int fromIndex;

  private final int toIndex;

  @NotNull
  private static PtFillRectIndex create(@NotNull final Map<String, Object> objects) {
    return new PtFillRectIndex(
      PlaceType.getInteger(objects, "from-index", 0),
      PlaceType.getInteger(objects, "to-index", 1));
  }

  @NotNull
  @Override
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-rect-index";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillRect(this.fromIndex, this.toIndex, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    this.getSerializer().serialize(this, transformedData);
  }

  @NotNull
  public Map<String, Object> toMap() {
    return Map.of(
      "from-index", this.fromIndex,
      "to-index", this.toIndex);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillRectIndex> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillRectIndex> deserialize(@NotNull final TransformedData transformedData,
                                                 @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRectIndex::create);
    }

    @Override
    public void serialize(@NotNull final PtFillRectIndex placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
      transformedData.addAsMap("values", placeType.toMap(), String.class, Object.class);
    }
  }
}
