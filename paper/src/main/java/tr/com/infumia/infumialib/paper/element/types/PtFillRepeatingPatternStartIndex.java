package tr.com.infumia.infumialib.paper.element.types;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.PlaceType;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.transformer.TransformedData;

public final class PtFillRepeatingPatternStartIndex implements PlaceType {

  private final int endIndex;

  @NotNull
  private final List<String> pattern;

  private final int startIndex;

  private final boolean wrapAround;

  public PtFillRepeatingPatternStartIndex(final boolean wrapAround, @NotNull final List<String> pattern,
                                          final int startIndex, final int endIndex) {
    this.wrapAround = wrapAround;
    this.pattern = Collections.unmodifiableList(pattern);
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  @NotNull
  private static PtFillRepeatingPatternStartIndex create(@NotNull final Map<String, Object> objects) {
    return new PtFillRepeatingPatternStartIndex(
      PlaceType.getBoolean(objects, "wrap-around", false),
      PlaceType.getStringList(objects, "pattern", List.of("xxx", "yyy", "zzz")),
      PlaceType.getInteger(objects, "start-index", 0),
      PlaceType.getInteger(objects, "end-index", 1));
  }

  @NotNull
  @Override
  public String getType() {
    return "fill-repeating-pattern-start-index";
  }

  @Override
  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents) {
    contents.fillPatternRepeating(new Pattern<>(this.wrapAround, this.pattern.toArray(String[]::new)),
      this.startIndex, this.endIndex);
  }

  @Override
  public void serialize(@NotNull final TransformedData transformedData) {
    PlaceType.super.serialize(transformedData);
    final var copy = transformedData.copy();
    copy.add("wrap-around", this.wrapAround, boolean.class);
    copy.addAsCollection("pattern", this.pattern, String.class);
    copy.add("start-index", this.startIndex, int.class);
    copy.add("end-index", this.endIndex, int.class);
    transformedData.add("values", copy);
  }

  public static final class Deserializer implements PlaceType.Deserializer {

    public static final Deserializer INSTANCE = new Deserializer();

    @NotNull
    @Override
    public Optional<PlaceType> deserialize(@NotNull final TransformedData transformedData) {
      return transformedData.getAsMap("values", String.class, Object.class)
        .map(PtFillRepeatingPatternStartIndex::create);
    }
  }
}
