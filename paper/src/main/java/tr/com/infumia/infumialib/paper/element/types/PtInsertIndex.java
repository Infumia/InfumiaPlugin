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
public final class PtInsertIndex implements PlaceType {

  private final int index;

  @NotNull
  private static PtInsertIndex create(@NotNull final Map<String, Object> objects) {
    return new PtInsertIndex(PlaceType.getInteger(objects, "index", 0));
  }

  @NotNull
  @Override
  public String getType() {
    return "insert-index";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.set(this.index, icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("index", this.index, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtInsertIndex::create);
    }
  }
}
