package tr.com.infumia.infumialib.paper.element;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.functions.TriConsumer;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.util.Pattern;
import tr.com.infumia.infumialib.reflection.RefFieldExecuted;
import tr.com.infumia.infumialib.reflection.clazz.ClassOf;

@RequiredArgsConstructor
public enum PlaceType {

  SLOTS((icon, contents, objects) ->
    objects.values().stream()
      .map(o -> (Collection<Integer>) o)
      .forEach(slots ->
        slots.forEach(slot ->
          contents.set(slot, icon))),
    Map.of("slots", int[].class)),
  INSERT_INDEX((icon, contents, objects) ->
    contents.set((int) objects.get("index"), icon),
    Map.of("index", int.class)),
  INSERT((icon, contents, objects) ->
    contents.set((int) objects.get("row"), (int) objects.get("column"), icon),
    Map.of(
      "row", int.class,
      "column", int.class)),
  FILL((icon, contents, objects) ->
    contents.fill(icon), Collections.emptyMap()),
  FILL_EMPTIES((icon, contents, objects) ->
    contents.fillEmpties(icon), Collections.emptyMap()),
  FILL_ROW((icon, contents, objects) ->
    contents.fillRow((int) objects.get("row"), icon),
    Map.of("row", int.class)),
  FILL_COLUMN((icon, contents, objects) ->
    contents.fillColumn((int) objects.get("column"), icon),
    Map.of("column", int.class)),
  FILL_BORDERS((icon, contents, objects) ->
    contents.fillBorders(icon), Collections.emptyMap()),
  FILL_RECT_INDEX((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    Map.of(
      "from-index", int.class,
      "to-index", int.class)),
  FILL_RECT_FROM_TO((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    Map.of(
      "from-row", int.class,
      "from-column", int.class,
      "to-row", int.class,
      "to-column", int.class)),
  FILL_SQUARE_INDEX((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    Map.of(
      "from-index", int.class,
      "to-index", int.class)),
  FILL_SQUARE_FROM_TO((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    Map.of(
      "from-row", int.class,
      "from-column", int.class,
      "to-row", int.class,
      "to-column", int.class)),
  FILL_PATTERN((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern"))),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class)),
  FILL_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class,
      "start-index", int.class)),
  FILL_PATTERN_START((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class,
      "start-row", int.class,
      "start-column", int.class)),
  FILL_REPEATING_PATTERN((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern"))),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class)),
  FILL_REPEATING_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index"), (int) objects.get("end-index")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class,
      "start-index", int.class,
      "end-index", int.class)),
  FILL_REPEATING_PATTERN_START((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column"), (int) objects.get("end-row"), (int) objects.get("end-column")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", String[].class,
      "start-row", int.class,
      "start-column", int.class,
      "end-row", int.class,
      "end-column", int.class)),
  NONE((left, middle, right) -> {
  }, Collections.emptyMap());

  @NotNull
  private final TriConsumer<Icon, InventoryContents, Map<String, Object>> consumer;

  @NotNull
  private final Map<String, Class<?>> keyAndTypes;

  @NotNull
  private static Object def(@NotNull final Class<?> type) {
    if (type.equals(Integer.class)) {
      return 1;
    }
    if (type.equals(String.class)) {
      return "test";
    }
    if (type.equals(String[].class)) {
      return new String[]{"element-1", "element-2"};
    }
    if (type.equals(int[].class)) {
      return new int[]{0, 1, 2};
    }
    if (type.equals(boolean.class)) {
      return true;
    }
    return "empty";
  }

  public boolean control(@NotNull final Map<String, Object> objects) {
    if (this == PlaceType.SLOTS) {
      return objects.isEmpty() || objects.values().stream()
        .allMatch(o -> Number.class.isAssignableFrom(o.getClass()));
    }
    if (objects.size() != this.keyAndTypes.size()) {
      return false;
    }
    if (objects.isEmpty()) {
      return true;
    }
    return this.keyAndTypes.entrySet().stream()
      .allMatch(entry -> {
        final var o = objects.get(entry.getKey());
        if (o == null) {
          return false;
        }
        final var cls = o.getClass();
        return new ClassOf<>(cls)
          .getField("TYPE")
          .map(refField -> refField.of(o))
          .flatMap(RefFieldExecuted::getValue)
          .map(o1 -> o1.equals(entry.getValue()))
          .orElse(cls.equals(entry.getValue()));
      });
  }

  @NotNull
  public Map<String, Object> defaultValues() {
    return this.keyAndTypes.entrySet().stream()
      .map(entry -> Map.entry(entry.getKey(), PlaceType.def(entry.getValue())))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents,
                    @NotNull final Map<String, Object> objects) {
    this.consumer.accept(icon, contents, objects);
  }
}
