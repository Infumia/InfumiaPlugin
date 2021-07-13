package tr.com.infumia.infumialib.replaceable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation of {@link RpBase} for {@link List} {@link String}.
 */
public final class RpList extends RpBase<RpList, List<String>> {

  /**
   * ctor.
   *
   * @param value the value.
   */
  private RpList(@NotNull final List<String> value) {
    super(value);
  }

  /**
   * creates a replaceable list instance.
   *
   * @param texts the texts to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final String... texts) {
    return RpList.from(Arrays.asList(texts));
  }

  /**
   * creates a replaceable list instance.
   *
   * @param list the list to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final List<String> list) {
    return new RpList(list);
  }

  /**
   * creates a replaceable list instance.
   *
   * @param builders the builders to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final StringBuilder... builders) {
    return RpList.from(Arrays.stream(builders)
      .map(StringBuilder::toString)
      .collect(Collectors.toList()));
  }

  /**
   * creates a replaceable list instance.
   *
   * @param list the list to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList fromObjects(@NotNull final List<Object> list) {
    final var result = new ArrayList<String>();
    for (final var o : list) {
      result.add(Objects.toString(o));
    }
    return RpList.from(result);
  }

  /**
   * creates a replaceable list instance.
   *
   * @param objects the objects to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList fromObjects(@NotNull final Object... objects) {
    return RpList.fromObjects(List.of(objects));
  }

  @NotNull
  @Override
  public Supplier<RpList> newSelf(@NotNull final List<String> value) {
    return () -> new RpList(value);
  }

  @NotNull
  @Override
  public List<String> replace(@NotNull final List<String> value, @NotNull final CharSequence regex,
                              @NotNull final CharSequence replace) {
    final var list = new ArrayList<String>();
    for (final var s : value) {
      list.add(s.replace(regex, replace));
    }
    return list;
  }

  @NotNull
  @Override
  public RpList self() {
    return this;
  }
}
