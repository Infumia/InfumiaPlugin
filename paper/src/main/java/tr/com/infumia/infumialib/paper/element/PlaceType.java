package tr.com.infumia.infumialib.paper.element;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    if ("fill-repeating-pattern-start-index".equalsIgnoreCase(replaced)) {
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
