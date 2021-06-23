package tr.com.infumia.infumialib.scoreboard;

import tr.com.infumia.infumialib.scoreboard.line.Line;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents boards.
 *
 * @param <O> type of the observers.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Board<O> implements Closeable {

  /**
   * the boards.
   */
  private static final Map<String, Board<?>> BOARDS = new ConcurrentHashMap<>();

  /**
   * the async scheduler.
   */
  @NotNull
  private final ScheduledExecutorService asyncScheduler;

  /**
   * the dynamic observer list.
   */
  @NotNull
  private final Collection<Supplier<Collection<? extends O>>> dynamicObserverList;

  /**
   * the dynamic observers.
   */
  @NotNull
  private final Collection<Supplier<? extends O>> dynamicObservers;

  /**
   * the filters.
   */
  @NotNull
  private final Collection<Predicate<O>> filters;

  /**
   * the id.
   */
  @Nullable
  private final String id;

  /**
   * the lines.
   */
  @NotNull
  private final List<Line<O>> lines;

  /**
   * the mutable board.
   */
  private final ThreadLocal<MutableBoard<O>> mutableBoard = ThreadLocal.withInitial(() ->
    new MutableBoard<>(this));

  /**
   * the observer class.
   */
  @NotNull
  private final Class<O> observerClass;

  /**
   * the remove if.
   */
  @NotNull
  private final Collection<Predicate<O>> removeIf;

  /**
   * the run after.
   */
  @NotNull
  private final Collection<Consumer<O>> runAfter;

  /**
   * the run before.
   */
  @NotNull
  private final Collection<Consumer<O>> runBefore;

  /**
   * the scoreboard sender.
   */
  @NotNull
  private final ScoreboardSender<O> scoreboardSender;

  /**
   * the start delay.
   */
  private final long startDelay;

  /**
   * the static observers.
   */
  @NotNull
  private final Collection<O> staticObservers;

  /**
   * the tick.
   */
  private final long tick;

  /**
   * the title line.
   */
  @NotNull
  private final Line<O> titleLine;

  /**
   * the type.
   */
  @NotNull
  private final BoardType type;

  /**
   * obtains the board by id.
   *
   * @param id the id to obtain.
   *
   * @return board.
   */
  @NotNull
  public static Optional<Board<?>> getBoardById(@NotNull final String id) {
    return Optional.ofNullable(Board.BOARDS.get(id));
  }

  /**
   * obtains the by id and observer class.
   *
   * @param observerClass the observer class to obtain.
   * @param id the id to obtain.
   * @param <O> type of the observers.
   *
   * @return board.
   */
  @NotNull
  public static <O> Optional<Board<O>> getBoardById(@NotNull final Class<O> observerClass, @NotNull final String id) {
    //noinspection unchecked
    return Board.getBoardById(id)
      .filter(board -> observerClass.isAssignableFrom(board.getObserverClass()))
      .map(board -> (Board<O>) board);
  }

  /**
   * creates a new instance of {@link Builder}.
   *
   * @param observerClass the observer class to create.
   * @param <O> type of the observers.
   *
   * @return a newly created instance of {@link Builder}.
   */
  @NotNull
  public static <O> Builder<O> newBuilder(@NotNull final Class<O> observerClass) {
    return new Builder<>(observerClass);
  }

  @Override
  public void close() {
    this.asyncScheduler.shutdown();
    this.scoreboardSender.close();
    this.lines.forEach(Line::close);
  }

  /**
   * resets the {@link #mutableBoard}.
   */
  public void reset() {
    this.mutableBoard.set(new MutableBoard<>(this));
  }

  /**
   * sends the scoreboard for once.
   */
  public void send() {
    this.send(false);
  }

  /**
   * sends the scoreboard for once.
   *
   * @param reset the reset to send.
   */
  public void send(final boolean reset) {
    if (reset) {
      this.reset();
    }
    this.mutableBoard.get().send();
  }

  /**
   * starts the scoreboard sequence.
   */
  public void start() {
    this.reset();
    this.asyncScheduler.scheduleAtFixedRate(this::send, this.startDelay, this.tick, TimeUnit.MILLISECONDS);
  }

  /**
   * a class that represents builders for {@link Board}.
   *
   * @param <O> type of the observers.
   */
  @Getter
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder<O> {

    /**
     * the observer class.
     */
    @NotNull
    private final Class<O> observerClass;

    /**
     * the async scheduler.
     */
    @NotNull
    private ScheduledExecutorService asyncScheduler = Executors.newScheduledThreadPool(2);

    /**
     * the dynamic observer list.
     */
    @NotNull
    private Collection<Supplier<Collection<? extends O>>> dynamicObserverList = new HashSet<>();

    /**
     * the dynamic observers.
     */
    @NotNull
    private Collection<Supplier<? extends O>> dynamicObservers = new HashSet<>();

    /**
     * the filters.
     */
    @NotNull
    private Collection<Predicate<O>> filters = new HashSet<>();

    /**
     * the id.
     */
    @Nullable
    private String id;

    /**
     * the lines.
     */
    @NotNull
    private List<Line<O>> lines = new ArrayList<>();

    /**
     * the remove if.
     */
    @NotNull
    private Collection<Predicate<O>> removeIf = new HashSet<>();

    /**
     * the run after.
     */
    @NotNull
    private Collection<Consumer<O>> runAfter = new HashSet<>();

    /**
     * the run before.
     */
    @NotNull
    private Collection<Consumer<O>> runBefore = new HashSet<>();

    /**
     * the scoreboard sender.
     */
    @NotNull
    private ScoreboardSender<O> scoreboardSender = new ScoreboardSender.Empty<>();

    /**
     * the start delay.
     */
    private long startDelay = 1000L;

    /**
     * the static observers.
     */
    @NotNull
    private Collection<O> staticObservers = new HashSet<>();

    /**
     * the tick.
     */
    private long tick = 1000L;

    /**
     * the title line.
     */
    @NotNull
    private Line<O> titleLine = Line.immutable("");

    /**
     * the type.
     */
    @NotNull
    private BoardType type = BoardType.MODERN;

    /**
     * adds the given dynamic observer list to the {@link #dynamicObserverList}.
     *
     * @param dynamicObserverList the dynamic observer list to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addDynamicObserverList(
      @NotNull final Supplier<Collection<? extends O>>... dynamicObserverList) {
      Collections.addAll(this.dynamicObserverList, dynamicObserverList);
      return this;
    }

    /**
     * adds the given dynamic observers to the {@link #dynamicObservers}.
     *
     * @param dynamicObservers the dynamic observers to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addDynamicObservers(@NotNull final Supplier<O>... dynamicObservers) {
      Collections.addAll(this.dynamicObservers, dynamicObservers);
      return this;
    }

    /**
     * adds the given filters to the {@link #filters}.
     *
     * @param filters the filters to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addFilters(@NotNull final Predicate<O>... filters) {
      Collections.addAll(this.filters, filters);
      return this;
    }

    /**
     * adds the given lines to {@link #lines}.
     *
     * @param lines the lines to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addLines(@NotNull final Line<O>... lines) {
      Collections.addAll(this.lines, lines);
      return this;
    }

    /**
     * adds the given remove if to the {@link #removeIf}.
     *
     * @param removeIf the remove if to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addRemoveIf(@NotNull final Predicate<O>... removeIf) {
      Collections.addAll(this.removeIf, removeIf);
      return this;
    }

    /**
     * adds the given run after to the {@link #runAfter}.
     *
     * @param runAfter the run after to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addRunAfter(@NotNull final Consumer<O>... runAfter) {
      Collections.addAll(this.runAfter, runAfter);
      return this;
    }

    /**
     * adds the given run before to the {@link #runBefore}.
     *
     * @param runBefore the run before to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addRunBefore(@NotNull final Consumer<O>... runBefore) {
      Collections.addAll(this.runBefore, runBefore);
      return this;
    }

    /**
     * adds the given static observers to the {@link #staticObservers}.
     *
     * @param observers the observers to add.
     *
     * @return {@code this} for builder chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> addStaticObservers(@NotNull final O... observers) {
      Collections.addAll(this.staticObservers, observers);
      return this;
    }

    /**
     * sets the dynamic observer list.
     *
     * @param dynamicObserverList the dynamic observer list to set.
     *
     * @return {@code this} for build chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> setDynamicObserverList(
      @NotNull final Supplier<Collection<? extends O>>... dynamicObserverList) {
      return this.setDynamicObserverList(Set.of(dynamicObserverList));
    }

    /**
     * sets the dynamic observers.
     *
     * @param dynamicObservers the dynamic observers to set.
     *
     * @return {@code this} for build chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> setDynamicObservers(@NotNull final Supplier<O>... dynamicObservers) {
      return this.setDynamicObservers(Set.of(dynamicObservers));
    }

    /**
     * sets the static observers.
     *
     * @param staticObservers the static observers to set.
     *
     * @return {@code this} for build chain.
     */
    @SafeVarargs
    @NotNull
    public final Builder<O> setStaticObservers(@NotNull final O... staticObservers) {
      return this.setStaticObservers(Set.of(staticObservers));
    }

    /**
     * adds the given line to the {@link #lines}.
     *
     * @param lineNumber the line number to add.
     * @param line the line to add.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> addLine(final int lineNumber, @NotNull final Line<O> line) {
      this.lines.set(lineNumber, line);
      return this;
    }

    /**
     * adds the given line to the {@link #lines}.
     *
     * @param lineNumber the line number to add.
     * @param line the line to add.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> addLine(final int lineNumber, @NotNull final Function<@NotNull O, @NotNull String> line) {
      return this.addLine(lineNumber, Line.dynamic(line));
    }

    /**
     * builds a new board instance from the builder's value.
     *
     * @return a newly created instance of {@link Board}.
     */
    @NotNull
    public Board<O> build() {
      if (this.id != null && Board.BOARDS.containsKey(this.id)) {
        throw new IllegalArgumentException(String.format("Id called %s is already exist in the boards map.",
          this.id));
      }
      final var board = new Board<>(this.asyncScheduler, this.dynamicObserverList, this.dynamicObservers, this.filters,
        this.id, this.lines, this.observerClass, this.removeIf, this.runAfter, this.runBefore, this.scoreboardSender,
        this.startDelay, this.staticObservers, this.tick, this.titleLine, this.type);
      if (this.id != null) {
        Board.BOARDS.put(this.id, board);
      }
      return board;
    }

    /**
     * sets the async scheduler.
     *
     * @param asyncScheduler the async scheduler to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setAsyncScheduler(@NotNull final ScheduledExecutorService asyncScheduler) {
      this.asyncScheduler = asyncScheduler;
      return this;
    }

    /**
     * sets the dynamic observer list.
     *
     * @param dynamicObserverList the dynamic observer list to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setDynamicObserverList(
      @NotNull final Collection<Supplier<Collection<? extends O>>> dynamicObserverList) {
      this.dynamicObserverList = dynamicObserverList;
      return this;
    }

    /**
     * sets the dynamic observers.
     *
     * @param dynamicObservers the dynamic observers to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setDynamicObservers(@NotNull final Collection<Supplier<? extends O>> dynamicObservers) {
      this.dynamicObservers = dynamicObservers;
      return this;
    }

    /**
     * sets the filters.
     *
     * @param filters the filters to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setFilters(@NotNull final Collection<Predicate<O>> filters) {
      this.filters = filters;
      return this;
    }

    /**
     * sets the id.
     *
     * @param id the id to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setId(final String id) {
      this.id = id;
      return this;
    }

    /**
     * sets the lines.
     *
     * @param lines the lines to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setLines(@NotNull final List<Line<O>> lines) {
      this.lines = lines;
      return this;
    }

    /**
     * sets the remove if.
     *
     * @param removeIf the remove if to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setRemoveIf(@NotNull final Collection<Predicate<O>> removeIf) {
      this.removeIf = removeIf;
      return this;
    }

    /**
     * sets the run after.
     *
     * @param runAfter the run after to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setRunAfter(@NotNull final Collection<Consumer<O>> runAfter) {
      this.runAfter = runAfter;
      return this;
    }

    /**
     * sets the run before.
     *
     * @param runBefore the run before to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setRunBefore(@NotNull final Collection<Consumer<O>> runBefore) {
      this.runBefore = runBefore;
      return this;
    }

    /**
     * sets the scoreboard sender.
     *
     * @param scoreboardSender the scoreboard sender to set.
     *
     * @return {@code this} for build chaining.
     */
    @NotNull
    public Builder<O> setScoreboardSender(@NotNull final ScoreboardSender<O> scoreboardSender) {
      this.scoreboardSender = scoreboardSender;
      return this;
    }

    /**
     * sets the start delay.
     *
     * @param startDelay the start delay to set.
     *
     * @return {@code this} for build chain.
     *
     * @throws IllegalArgumentException if the start delay is lower than 0.
     */
    @NotNull
    public Builder<O> setStartDelay(final long startDelay) {
      if (startDelay < 0L) {
        throw new IllegalArgumentException("Start delay shouldn't less than 0.");
      }
      this.startDelay = startDelay;
      return this;
    }

    /**
     * sets the static observers.
     *
     * @param staticObservers the static observers to set.
     *
     * @return {@code this} for build chain.
     */
    @NotNull
    public Builder<O> setStaticObservers(@NotNull final Collection<O> staticObservers) {
      this.staticObservers = staticObservers;
      return this;
    }

    /**
     * sets the tick.
     *
     * @param tick the tick to set.
     *
     * @return {@code this} for build chain.
     *
     * @throws IllegalArgumentException if the tick is lower than 0.
     */
    @NotNull
    public Builder<O> setTick(final long tick) {
      if (tick <= 0L) {
        throw new IllegalArgumentException("Tick shouldn't equal or less than 0.");
      }
      this.tick = tick;
      return this;
    }

    /**
     * sets the title line.
     *
     * @param titleLine the title line to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setTitleLine(final Line<O> titleLine) {
      this.titleLine = titleLine;
      return this;
    }

    /**
     * sets the type.
     *
     * @param type type to set.
     *
     * @return {@code this} for builder chain.
     */
    @NotNull
    public Builder<O> setType(@NotNull final BoardType type) {
      this.type = type;
      return this;
    }
  }

  /**
   * a class that represents mutable boards.
   *
   * @param <O> type of the observers.
   */
  private static final class MutableBoard<O> {

    /**
     * the board.
     */
    @NotNull
    private final Board<O> board;

    /**
     * the static observers.
     */
    @NotNull
    private final Collection<O> staticObservers;

    /**
     * ctor.
     *
     * @param board the board.
     */
    private MutableBoard(@NotNull final Board<O> board) {
      this.board = board;
      this.staticObservers = new HashSet<>(board.getStaticObservers());
    }

    /**
     * sends the {@link #board} to the all observers.
     */
    public void send() {
      this.staticObserversRemoveIf();
      final var observers = this.getObservers();
      if (observers.isEmpty()) {
        return;
      }
      this.board.getRunBefore().forEach(observers::forEach);
      this.board.getScoreboardSender().send(this.board, observers, this.board.getLines());
      this.board.getRunAfter().forEach(observers::forEach);
    }

    /**
     * obtains the observers.
     *
     * @return observers.
     */
    @NotNull
    @Synchronized("staticObservers")
    private Collection<O> getObservers() {
      final var observers = this.staticObservers.stream()
        .filter(observer ->
          this.board.getFilters().stream()
            .allMatch(predicate -> predicate.test(observer)))
        .collect(Collectors.toSet());
      this.board.getDynamicObservers().stream()
        .map(Supplier::get)
        .filter(observer ->
          this.board.getFilters().stream()
            .allMatch(predicate -> predicate.test(observer)))
        .forEach(observers::add);
      this.board.getDynamicObserverList().stream()
        .map(Supplier::get)
        .flatMap(Collection::stream)
        .filter(observer ->
          this.board.getFilters().stream()
            .allMatch(predicate -> predicate.test(observer)))
        .forEach(observers::add);
      return observers;
    }

    /**
     * removes each {@link #staticObservers} if the observer passes {@link Board#getRemoveIf()}.
     */
    @Synchronized("staticObservers")
    private void staticObserversRemoveIf() {
      this.staticObservers.removeIf(observer ->
        this.board.getRemoveIf().stream()
          .anyMatch(predicate -> predicate.test(observer)));
    }
  }
}
