package tr.com.infumia.infumialib.paper.element.types;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtNone implements PlaceType {

  public static final PtNone INSTANCE = new PtNone();

  @NotNull
  @Override
  public String getType() {
    return "none";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return Optional.of(PtNone.INSTANCE);
    }
  }
}
