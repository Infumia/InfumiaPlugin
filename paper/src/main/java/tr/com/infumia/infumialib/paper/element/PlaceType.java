package tr.com.infumia.infumialib.paper.element;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import tr.com.infumia.infumialib.transformer.TransformedData;

@RequiredArgsConstructor
public enum PlaceType {

  SLOTS((icon, contents, objects) ->
    objects.values().stream()
      .map(o -> (Collection<Integer>) o)
      .forEach(slots ->
        slots.forEach(slot ->
          contents.set(slot, icon))),
    Map.of("slots", Collection.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("slots", ((Collection<?>) values.getOrDefault("slots", Collections.emptyList())).stream()
          .map(o -> {
            try {
              return Integer.parseInt(String.valueOf(o));
            } catch (final Exception ignored) {
            }
            return 0;
          })
          .collect(Collectors.toList()));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "slots", map.getOrDefault("slots", List.of(0, 1, 2))
      );
    }
  },
  INSERT_INDEX((icon, contents, objects) ->
    contents.set((int) objects.get("index"), icon),
    Map.of("index", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("index", Integer.parseInt(String.valueOf(values.getOrDefault("index", 0))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "index", map.getOrDefault("index", 0)
      );
    }
  },
  INSERT((icon, contents, objects) ->
    contents.set((int) objects.get("row"), (int) objects.get("column"), icon),
    Map.of(
      "row", int.class,
      "column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("row", Integer.parseInt(String.valueOf(values.getOrDefault("row", 0))));
        map.put("column", Integer.parseInt(String.valueOf(values.getOrDefault("column", 0))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "row", map.getOrDefault("row", 0),
        "column", map.getOrDefault("column", 0)
      );
    }
  },
  FILL((icon, contents, objects) ->
    contents.fill(icon), Collections.emptyMap()) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      return Collections.emptyMap();
    }
  },
  FILL_EMPTIES((icon, contents, objects) ->
    contents.fillEmpties(icon), Collections.emptyMap()) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      return Collections.emptyMap();
    }
  },
  FILL_ROW((icon, contents, objects) ->
    contents.fillRow((int) objects.get("row"), icon),
    Map.of("row", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("row", Integer.parseInt(String.valueOf(values.getOrDefault("row", 0))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "row", map.getOrDefault("row", 0)
      );
    }
  },
  FILL_COLUMN((icon, contents, objects) ->
    contents.fillColumn((int) objects.get("column"), icon),
    Map.of("column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("column", Integer.parseInt(String.valueOf(values.getOrDefault("column", 0))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "column", map.getOrDefault("column", 0)
      );
    }
  },
  FILL_BORDERS((icon, contents, objects) ->
    contents.fillBorders(icon), Collections.emptyMap()) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      return Collections.emptyMap();
    }
  },
  FILL_RECT_INDEX((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    Map.of(
      "from-index", int.class,
      "to-index", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("from-index", Integer.parseInt(String.valueOf(values.getOrDefault("from-index", 0))));
        map.put("to-index", Integer.parseInt(String.valueOf(values.getOrDefault("to-index", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "from-row", map.getOrDefault("from-index", 0),
        "to-index", map.getOrDefault("to-index", 1)
      );
    }
  },
  FILL_RECT_FROM_TO((icon, contents, objects) ->
    contents.fillRect((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    Map.of(
      "from-row", int.class,
      "from-column", int.class,
      "to-row", int.class,
      "to-column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("from-row", Integer.parseInt(String.valueOf(values.getOrDefault("from-row", 0))));
        map.put("from-column", Integer.parseInt(String.valueOf(values.getOrDefault("from-column", 0))));
        map.put("to-row", Integer.parseInt(String.valueOf(values.getOrDefault("to-row", 1))));
        map.put("to-column", Integer.parseInt(String.valueOf(values.getOrDefault("to-column", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "from-row", map.getOrDefault("from-row", 0),
        "from-column", map.getOrDefault("from-column", 0),
        "to-row", map.getOrDefault("to-row", 1),
        "to-column", map.getOrDefault("to-column", 1)
      );
    }
  },
  FILL_SQUARE_INDEX((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-index"), (int) objects.get("to-index"), icon),
    Map.of(
      "from-index", int.class,
      "to-index", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("from-index", Integer.parseInt(String.valueOf(values.getOrDefault("from-index", 0))));
        map.put("to-index", Integer.parseInt(String.valueOf(values.getOrDefault("to-index", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "from-row", map.getOrDefault("from-index", 0),
        "to-index", map.getOrDefault("to-index", 1)
      );
    }
  },
  FILL_SQUARE_FROM_TO((icon, contents, objects) ->
    contents.fillSquare((int) objects.get("from-row"), (int) objects.get("from-column"), (int) objects.get("to-row"), (int) objects.get("to-column"), icon),
    Map.of(
      "from-row", int.class,
      "from-column", int.class,
      "to-row", int.class,
      "to-column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("from-row", Integer.parseInt(String.valueOf(values.getOrDefault("from-row", 0))));
        map.put("from-column", Integer.parseInt(String.valueOf(values.getOrDefault("from-column", 0))));
        map.put("to-row", Integer.parseInt(String.valueOf(values.getOrDefault("to-row", 1))));
        map.put("to-column", Integer.parseInt(String.valueOf(values.getOrDefault("to-column", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "from-row", map.getOrDefault("from-row", 0),
        "from-column", map.getOrDefault("from-column", 0),
        "to-row", map.getOrDefault("to-row", 1),
        "to-column", map.getOrDefault("to-column", 1)
      );
    }
  },
  FILL_PATTERN((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern"))),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz"))
      );
    }
  },
  FILL_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class,
      "start-index", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
        map.put("start-index", Integer.parseInt(String.valueOf(values.getOrDefault("start-index", 0))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz")),
        "start-index", map.getOrDefault("start-index", 1)
      );
    }
  },
  FILL_PATTERN_START((icon, contents, objects) ->
    contents.fillPattern(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class,
      "start-row", int.class,
      "start-column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
        map.put("start-row", Integer.parseInt(String.valueOf(values.getOrDefault("start-row", 0))));
        map.put("end-column", Integer.parseInt(String.valueOf(values.getOrDefault("end-column", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz")),
        "start-row", map.getOrDefault("start-row", 0),
        "end-column", map.getOrDefault("end-column", 1)
      );
    }
  },
  FILL_REPEATING_PATTERN((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), ((Collection<String>) objects.get("pattern")).toArray(String[]::new))),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz"))
      );
    }
  },
  FILL_REPEATING_PATTERN_START_INDEX((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-index"), (int) objects.get("end-index")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class,
      "start-index", int.class,
      "end-index", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
        map.put("start-index", Integer.parseInt(String.valueOf(values.getOrDefault("start-index", 0))));
        map.put("end-index", Integer.parseInt(String.valueOf(values.getOrDefault("end-index", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz")),
        "start-index", map.getOrDefault("start-index", 0),
        "end-index", map.getOrDefault("end-index", 1)
      );
    }
  },
  FILL_REPEATING_PATTERN_START((icon, contents, objects) ->
    contents.fillPatternRepeating(new Pattern<>((boolean) objects.get("wrap-around"), (String[]) objects.get("pattern")), (int) objects.get("start-row"), (int) objects.get("start-column"), (int) objects.get("end-row"), (int) objects.get("end-column")),
    Map.of(
      "wrap-around", boolean.class,
      "pattern", Collection.class,
      "start-row", int.class,
      "start-column", int.class,
      "end-row", int.class,
      "end-column", int.class)) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      final var map = new HashMap<String, Object>();
      final var values = fileElement.values();
      try {
        map.put("wrap-around", Boolean.parseBoolean(String.valueOf(values.getOrDefault("wrap-around", false))));
        map.put("pattern", ((Collection<?>) values.getOrDefault("pattern", Collections.emptyList())).stream()
          .map(String::valueOf)
          .collect(Collectors.toList()));
        map.put("start-row", Integer.parseInt(String.valueOf(values.getOrDefault("start-row", 0))));
        map.put("start-column", Integer.parseInt(String.valueOf(values.getOrDefault("start-column", 0))));
        map.put("end-row", Integer.parseInt(String.valueOf(values.getOrDefault("end-row", 1))));
        map.put("end-column", Integer.parseInt(String.valueOf(values.getOrDefault("end-column", 1))));
      } catch (final Exception ignored) {
      }
      return Map.of(
        "wrap-around", map.getOrDefault("wrap-around", false),
        "pattern", map.getOrDefault("pattern", List.of("xxx", "yyy", "zzz")),
        "start-row", map.getOrDefault("start-row", 0),
        "start-column", map.getOrDefault("start-column", 0),
        "end-row", map.getOrDefault("end-row", 1),
        "end-column", map.getOrDefault("end-column", 1)
      );
    }
  },
  NONE((left, middle, right) -> {
  }, Collections.emptyMap()) {
    @NotNull
    @Override
    public Map<String, Object> serialize(@NotNull final FileElement fileElement) {
      return Collections.emptyMap();
    }
  };

  @NotNull
  private final TriConsumer<Icon, InventoryContents, Map<String, Object>> consumer;

  @NotNull
  private final Map<String, Class<?>> keyAndTypes;

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
        if (cls.equals(entry.getValue())) {
          return true;
        }
        if (entry.getValue().isAssignableFrom(cls)) {
          return true;
        }
        return new ClassOf<>(cls)
          .getField("TYPE")
          .map(refField -> refField.of(o))
          .flatMap(RefFieldExecuted::getValue)
          .map(o1 -> o1.equals(entry.getValue()))
          .orElse(cls.equals(entry.getValue()));
      });
  }

  public void place(@NotNull final Icon icon, @NotNull final InventoryContents contents,
                    @NotNull final Map<String, Object> objects) {
    this.consumer.accept(icon, contents, objects);
  }

  @NotNull
  public abstract Map<String, Object> serialize(@NotNull FileElement fileElement);

  public void serialize(@NotNull final FileElement fileElement, @NotNull final TransformedData transformedData) {
    final var serialize = this.serialize(fileElement);
    if (!serialize.isEmpty()) {
      transformedData.addAsMap("values", serialize, String.class, Object.class);
    }
  }
}
