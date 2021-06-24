package tr.com.infumia.infumialib.paper.element.types;

import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

public final class PtFillBorders implements PlaceType {

  public static final PtFillBorders INSTANCE = new PtFillBorders();

  @NotNull
  private static PtFillBorders create(@NotNull final Map<String, Object> objects) {
    return PtFillBorders.INSTANCE;
  }

  @NotNull
  @Override
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-borders";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillEmpties(icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    this.getSerializer().serialize(this, transformedData);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillBorders> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillBorders> deserialize(@NotNull final TransformedData transformedData,
                                               @Nullable final GenericDeclaration declaration) {
      return Optional.of(PtFillBorders.INSTANCE);
    }

    @Override
    public void serialize(@NotNull final PtFillBorders placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
    }
  }
}
