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

public final class PtFillRepeatingPattern implements PlaceType {

  @NotNull
  private final List<String> pattern;

  private final boolean wrapAround;

  public PtFillRepeatingPattern(final boolean wrapAround, @NotNull final List<String> pattern) {
    this.wrapAround = wrapAround;
    this.pattern = Collections.unmodifiableList(pattern);
  }

  @NotNull
  private static PtFillRepeatingPattern create(@NotNull final Map<String, Object> objects) {
    return new PtFillRepeatingPattern(
      PlaceType.getBoolean(objects, "wrap-around", false),
      PlaceType.getStringList(objects, "pattern", List.of("xxx", "yyy", "zzz")));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-repeating-pattern";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillPatternRepeating(new Pattern<>(this.wrapAround, this.pattern.toArray(String[]::new)));
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    transformedData.addAsMap("values", this.toMap(), String.class, Object.class);
  }

  @NotNull
  public Map<String, Object> toMap() {
    return Map.of(
      "wrap-around", this.wrapAround,
      "pattern", this.pattern);
  }

  public static final class Serializer extends PlaceType.Serializer<PtFillRepeatingPattern> {

    public static final Serializer INSTANCE = new Serializer();

    @NotNull
    @Override
    public Optional<PtFillRepeatingPattern> deserialize(@NotNull final TransformedData transformedData,
                                                        @Nullable final GenericDeclaration declaration) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRepeatingPattern::create);
    }
  }
}
