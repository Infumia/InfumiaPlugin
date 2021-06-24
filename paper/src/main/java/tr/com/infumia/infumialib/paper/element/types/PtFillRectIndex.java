package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;

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
  public String getType() {
    return "fill-rect-index";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillRect(this.fromIndex, this.toIndex, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("from-index", this.fromIndex, int.class);
    copy.add("to-index", this.toIndex, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRectIndex::create);
    }
  }
}
