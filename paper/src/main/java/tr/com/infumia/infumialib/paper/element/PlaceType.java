package tr.com.infumia.infumialib.paper.element;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.element.types.PtFill;
import tr.com.infumia.infumialib.paper.element.types.PtFillBorders;
import tr.com.infumia.infumialib.paper.element.types.PtFillColumn;
import tr.com.infumia.infumialib.paper.element.types.PtFillEmpties;
import tr.com.infumia.infumialib.paper.element.types.PtFillPattern;
import tr.com.infumia.infumialib.paper.element.types.PtFillPatternStart;
import tr.com.infumia.infumialib.paper.element.types.PtFillPatternStartIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRectFromTo;
import tr.com.infumia.infumialib.paper.element.types.PtFillRectIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPattern;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPatternStart;
import tr.com.infumia.infumialib.paper.element.types.PtFillRepeatingPatternStartIndex;
import tr.com.infumia.infumialib.paper.element.types.PtFillRow;
import tr.com.infumia.infumialib.paper.element.types.PtFillSquareFromTo;
import tr.com.infumia.infumialib.paper.element.types.PtFillSquareIndex;
import tr.com.infumia.infumialib.paper.element.types.PtInsert;
import tr.com.infumia.infumialib.paper.element.types.PtInsertIndex;
import tr.com.infumia.infumialib.paper.element.types.PtNone;
import tr.com.infumia.infumialib.paper.element.types.PtSlots;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

public interface PlaceType {

  static boolean getBoolean(@NotNull final Map<String, Object> map, @NotNull final String key,
                            final boolean defaultValue) {
    var value = defaultValue;
    if (!map.containsKey(key)) {
      return value;
    }
    try {
      value = Boolean.parseBoolean(String.valueOf(map.get(key)));
    } catch (final Exception ignored) {
    }
    return value;
  }

  @NotNull
  static Optional<Serializer<?>> getByType(@NotNull final String type) {
    final var replaced = type.replace("_", "-").toLowerCase(Locale.ROOT).trim();
    if ("fill".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFill.Serializer.INSTANCE);
    } else if ("fill-borders".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillBorders.Serializer.INSTANCE);
    } else if ("fill-column".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillColumn.Serializer.INSTANCE);
    } else if ("fill-empties".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillEmpties.Serializer.INSTANCE);
    } else if ("fill-pattern".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillPattern.Serializer.INSTANCE);
    } else if ("fill-pattern-start".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillPatternStart.Serializer.INSTANCE);
    } else if ("fill-pattern-start-index".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillPatternStartIndex.Serializer.INSTANCE);
    } else if ("fill-rect-from-to".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRectFromTo.Serializer.INSTANCE);
    } else if ("fill-rect-index".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRectIndex.Serializer.INSTANCE);
    } else if ("fill-repeating-pattern".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRepeatingPattern.Serializer.INSTANCE);
    } else if ("fill-repeating-pattern-start".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRepeatingPatternStart.Serializer.INSTANCE);
    } else if ("fill-repeating-pattern-start-index".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRepeatingPatternStartIndex.Serializer.INSTANCE);
    } else if ("fill-row".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillRow.Serializer.INSTANCE);
    } else if ("fill-square-from-to".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillSquareFromTo.Serializer.INSTANCE);
    } else if ("fill-square-index".equalsIgnoreCase(replaced)) {
      return Optional.of(PtFillSquareIndex.Serializer.INSTANCE);
    } else if ("insert".equalsIgnoreCase(replaced)) {
      return Optional.of(PtInsert.Serializer.INSTANCE);
    } else if ("insert-index".equalsIgnoreCase(replaced)) {
      return Optional.of(PtInsertIndex.Serializer.INSTANCE);
    } else if ("none".equalsIgnoreCase(replaced)) {
      return Optional.of(PtNone.Serializer.INSTANCE);
    } else if ("slots".equalsIgnoreCase(replaced)) {
      return Optional.of(PtSlots.Serializer.INSTANCE);
    }
    return Optional.empty();
  }

  static int getInteger(@NotNull final Map<String, Object> map, @NotNull final String key,
                        final int defaultValue) {
    var value = defaultValue;
    if (!map.containsKey(key)) {
      return value;
    }
    try {
      value = Integer.parseInt(String.valueOf(map.get(key)));
    } catch (final Exception ignored) {
    }
    return value;
  }

  static List<Integer> getIntegerList(@NotNull final Map<String, Object> map, @NotNull final String key,
                                      @NotNull final List<Integer> defaultValue) {
    var value = defaultValue;
    if (!map.containsKey(key)) {
      return value;
    }
    try {
      value = ((Collection<?>) map.get(key)).stream()
        .map(String::valueOf)
        .map(s -> {
          try {
            return Integer.parseInt(s);
          } catch (final Exception ignored) {
          }
          return 0;
        })
        .collect(Collectors.toList());
    } catch (final Exception ignored) {
    }
    return value;
  }

  static List<String> getStringList(@NotNull final Map<String, Object> map, @NotNull final String key,
                                    @NotNull final List<String> defaultValue) {
    var value = defaultValue;
    if (!map.containsKey(key)) {
      return value;
    }
    try {
      value = ((Collection<?>) map.get(key)).stream()
        .map(String::valueOf)
        .collect(Collectors.toList());
    } catch (final Exception ignored) {
    }
    return value;
  }

  @NotNull
  Serializer<?> getSerializer();

  @NotNull
  String getType();

  void place(@NotNull Icon icon, @NotNull InventoryContents contents);

  void serialize(@NotNull final TransformedData transformedData);

  abstract class Serializer<P extends PlaceType> implements ObjectSerializer<P> {

    @NotNull
    @Override
    public final Optional<P> deserialize(@NotNull final P field, @NotNull final TransformedData transformedData,
                                         @Nullable final GenericDeclaration declaration) {
      return this.deserialize(transformedData, declaration);
    }

    @Override
    public void serialize(@NotNull final P placeType, @NotNull final TransformedData transformedData) {
      transformedData.add("type", placeType.getType(), String.class);
    }

    @Override
    public final boolean supports(@NotNull final Class<?> cls) {
      return PlaceType.class.isAssignableFrom(cls);
    }
  }
}
