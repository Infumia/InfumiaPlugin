package tr.com.infumia.infumialib.scoreboard.line;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine a line of scoreboards.
 *
 * @param <O> type of the observer.
 */
public interface Line<O> extends Function<@NotNull O, @NotNull String>, Closeable {

  /**
   * creates a simple dynamic line instance.
   *
   * @param line the line to create.
   * @param <O> type of the observers.
   *
   * @return a newly created dynamic line instance.
   */
  @NotNull
  static <O> Line<O> dynamic(@NotNull final Function<@NotNull O, @NotNull String> line) {
    return new Impl<>(line, false);
  }

  /**
   * creates a simple line instance.
   *
   * @param line the line to create.
   * @param <O> type of the observers.
   *
   * @return a newly created line instance.
   */
  @NotNull
  static <O> Line<O> immutable(@NotNull final String line) {
    return new Impl<>(observer -> line, false);
  }

  /**
   * creates a merged line.
   *
   * @param lines the lines to create.
   * @param <O> type of the observers.
   *
   * @return a newly created merged line.
   */
  @NotNull
  static <O> Merged<O> merged(@NotNull final List<Line<O>> lines) {
    return new Merged<>(lines);
  }

  /**
   * creates a merged line.
   *
   * @param lines the lines to create.
   * @param <O> type of the observers.
   *
   * @return a newly created merged line.
   */
  @SafeVarargs
  @NotNull
  static <O> Merged<O> merged(@NotNull final Line<O>... lines) {
    return Line.merged(List.of(lines));
  }

  @Override
  default void close() {
  }

  /**
   * checks if the line should update every sent.
   *
   * @return {@code true} if the line should update every sent.
   */
  boolean isUpdate();

  /**
   * an envelope implementation of {@link Line}.
   *
   * @param <O> type of the observer.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  abstract class Envelope<O> implements Line<O> {

    /**
     * the delegate.
     */
    @NotNull
    @Delegate
    private final Line<O> delegate;
  }

  /**
   * a simple implementation of {@link Line}.
   *
   * @param <O> type of the observer.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  final class Impl<O> implements Line<O> {

    /**
     * the function.
     */
    @NotNull
    @Delegate
    private final Function<@NotNull O, @NotNull String> function;

    /**
     * the update.
     */
    @Getter
    private final boolean update;
  }

  /**
   * a class that represents merged lines.
   *
   * @param <O> type of the observer.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  final class Merged<O> implements Line<O> {

    /**
     * the lines.
     */
    @NotNull
    private final List<Line<O>> lines;

    @NotNull
    @Override
    public String apply(@NotNull final O o) {
      return this.lines.stream()
        .map(line -> line.apply(o))
        .collect(Collectors.joining(""));
    }

    @Override
    public boolean isUpdate() {
      return this.lines.stream().anyMatch(Line::isUpdate);
    }
  }
}
