package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

public final class PtFillPattern implements PlaceType {

  @NotNull
  private final List<String> pattern;

  private final boolean wrapAround;

  public PtFillPattern(final boolean wrapAround, @NotNull final List<String> pattern) {
    this.wrapAround = wrapAround;
    this.pattern = Collections.unmodifiableList(pattern);
  }

  @NotNull
  private static PtFillPattern create(@NotNull final Map<String, Object> objects) {
    return new PtFillPattern(
      PlaceType.getBoolean(objects, "wrap-around", false),
      PlaceType.getStringList(objects, "pattern", List.of("xxx", "yyy", "zzz")));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-pattern";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillPattern(new Pattern<>(this.wrapAround, this.pattern.toArray(String[]::new)));
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    final var copy = transformedData.copy();
    copy.add("wrap-around", this.wrapAround, boolean.class);
    copy.addAsCollection("pattern", this.pattern, String.class);
    transformedData.add("values", copy);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillPattern> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillPattern> deserialize(@NotNull final TransformedData transformedData,
                                               @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillPattern::create);
    }
  }
}
