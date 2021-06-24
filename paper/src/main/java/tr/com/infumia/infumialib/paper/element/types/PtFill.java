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

public final class PtFill implements PlaceType {

  public static final PtFill INSTANCE = new PtFill();

  @NotNull
  private static PtFill create(@NotNull final Map<String, Object> objects) {
    return PtFill.INSTANCE;
  }

  @NotNull
  @Override
  public Serializer getSerializer() {
    return Serializer.INSTANCE;
  }

  @NotNull
  @Override
  public String getType() {
    return "fill";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillEmpties(icon);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    this.getSerializer().serialize(this, transformedData);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFill> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFill> deserialize(@NotNull final TransformedData transformedData,
                                        @Nullable final GenericDeclaration declaration) {
      return Optional.of(PtFill.INSTANCE);
    }

    @Override
    public void serialize(@NotNull final PtFill placeType, @NotNull final TransformedData transformedData) {
      super.serialize(placeType, transformedData);
    }
  }
}
