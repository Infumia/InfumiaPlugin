package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;

@RequiredArgsConstructor
public final class PtSlots implements PlaceType {

  private final Collection<Integer> slots;

  @NotNull
  private static PtSlots create(@NotNull final Map<String, Object> objects) {
    return new PtSlots(PlaceType.getIntegerList(objects, "slots", List.of(0, 1, 2)));
  }

  @NotNull
  @Override
  public String getType() {
    return "slots";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    this.slots.forEach(slot -> contents.set(slot, icon));
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.addAsCollection("slots", this.slots, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtSlots::create);
    }
  }
}
