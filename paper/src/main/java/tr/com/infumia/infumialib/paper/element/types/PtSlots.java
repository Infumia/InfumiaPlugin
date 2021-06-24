package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collection;
import java.util.List;
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
    final var copy = transformedData.copy();
    copy.addAsCollection("slots", this.slots, int.class);
    transformedData.add("values", copy);
  }

  public static final class Serializer extends PlaceType.Serializer<PtSlots> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtSlots> deserialize(@NotNull final TransformedData transformedData,
                                         @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtSlots::create);
    }
  }
}
