package tr.com.infumia.infumialib.paper.element.types;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtFill implements PlaceType {

  public static final PtFill INSTANCE = new PtFill();

  @NotNull
  @Override
  public String getType() {
    return "fill";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillEmpties(icon);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return Optional.of(PtFill.INSTANCE);
    }
  }
}
