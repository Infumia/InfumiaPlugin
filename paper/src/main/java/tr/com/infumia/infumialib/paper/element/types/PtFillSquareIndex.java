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
public final class PtFillSquareIndex implements PlaceType {

  private final int fromIndex;

  private final int toIndex;

  @NotNull
  private static PtFillSquareIndex create(@NotNull final Map<String, Object> objects) {
    return new PtFillSquareIndex(
      PlaceType.getInteger(objects, "from-index", 0),
      PlaceType.getInteger(objects, "to-index", 0));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-square-index";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillSquare(this.fromIndex, this.toIndex, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    transformedData.addAsMap("values", this.toMap(), String.class, Object.class);
  }

  @NotNull
  public Map<String, Object> toMap() {
    return Map.of(
      "from-index", this.fromIndex,
      "to-index", this.toIndex);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillSquareIndex> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillSquareIndex> deserialize(@NotNull final TransformedData transformedData,
                                                   @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillSquareIndex::create);
    }
  }
}
