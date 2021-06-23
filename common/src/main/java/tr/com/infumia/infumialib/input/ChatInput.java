package tr.com.infumia.infumialib.input;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.input.event.ChatEvent;
import tr.com.infumia.infumialib.input.event.QuitEvent;

/**
 * a class created to get inputs from players.
 *
 * @param <T> the input type.
 * @param <P> the input sender type.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChatInput<T, P> {

  /**
   * the builder.
   */
  @NotNull
  private final ChatInput.Builder<T, P> builder;

  /**
   * the platform.
   */
  @NotNull
  private final ChatPlatform<P> platform;

  /**
   * the started.
   */
  private final AtomicBoolean started = new AtomicBoolean(false);

  /**
   * the expire task.
   */
  @Nullable
  private ChatTask expireTask;

  /**
   * creates a new builder instance.
   *
   * @param platform the platform to create.
   * @param sender the sender to create.
   * @param <T> type of the value.
   * @param <P> type of the sender.
   *
   * @return a newly created builder instance.
   */
  @NotNull
  public static <T, P> Builder<T, P> builder(@NotNull final ChatPlatform<P> platform,
                                             @NotNull final ChatSender<P> sender) {
    return new Builder<>(platform, sender);
  }

  /**
   * stops the chat input sequence.
   *
   * @param sender the sender to end.
   * @param reason the reason to end.
   */
  public void end(@NotNull final P sender, @NotNull final EndReason reason) {
    this.started.set(false);
    this.platform.unregisterListeners();
    Optional.ofNullable(this.expireTask).ifPresent(ChatTask::cancel);
    this.builder.getRunAfter().forEach((r, j) -> {
      if (r == reason) {
        j.forEach(c -> c.accept(sender));
      }
    });
  }

  /**
   * runs when the sender push an input.
   *
   * @param event the event to apply as a send message event.
   */
  public void onChat(@NotNull final ChatEvent<P> event) {
    if (!this.started.get()) {
      return;
    }
    final ChatSender<P> sender = this.builder.getSender();
    if (!sender.getUniqueId().equals(event.getSender().getUniqueId())) {
      return;
    }
    event.cancel();
    final String message = event.getMessage();
    final P wrapped = sender.getWrapped();
    if (message.equalsIgnoreCase(this.builder.getCancel())) {
      this.builder.getOnCancel().accept(wrapped);
      this.end(wrapped, EndReason.PLAYER_CANCELS);
      return;
    }
    if (this.builder.getIsValidInput().test(wrapped, message)) {
      final T value = this.builder.getSetValue().apply(wrapped, message);
      this.builder.getOnFinish().accept(wrapped, value);
      this.end(wrapped, EndReason.FINISH);
    } else {
      if (this.builder.getOnInvalidInput().test(wrapped, message)) {
        Optional.ofNullable(this.builder.getInvalidInputMessage())
          .ifPresent(sender::sendMessage);
        Optional.ofNullable(this.builder.getSendValueMessage())
          .filter(s -> this.builder.isRepeat())
          .ifPresent(sender::sendMessage);
      }
      if (!this.builder.isRepeat()) {
        this.end(wrapped, EndReason.INVALID_INPUT);
      }
    }
  }

  /**
   * runs when the send quits from the game.
   *
   * @param event the event to apply as a quit event.
   */
  public void onQuit(@NotNull final QuitEvent<P> event) {
    if (!this.started.get()) {
      return;
    }
    final ChatSender<P> sender = this.builder.getSender();
    if (event.getSender().getUniqueId().equals(sender.getUniqueId())) {
      this.builder.getOnDisconnect().accept(sender.getWrapped());
      this.end(sender.getWrapped(), EndReason.PLAYER_DISCONNECTS);
    }
  }

  /**
   * starts the chat input sequence.
   */
  public void start() {
    this.platform.init(this);
    final ChatSender<P> sender = this.builder.getSender();
    if (this.builder.getExpire() != -1L) {
      this.expireTask = this.platform.createRunTaskLater(() -> {
        if (!this.started.get()) {
          return;
        }
        Optional.ofNullable(this.expireTask)
          .filter(task -> !task.isCancelled()).map(task -> sender.getWrapped())
          .ifPresent(wrapped -> {
            this.builder.getOnExpire().accept(wrapped);
            this.end(wrapped, EndReason.EXPIRE);
          });
      }, this.builder.getExpire());
    }
    this.started.set(true);
    Optional.ofNullable(this.builder.getSendValueMessage())
      .ifPresent(sender::sendMessage);
  }

  /**
   * a builder class to create {@link ChatInput} instance.
   *
   * @param <T> the value type.
   * @param <P> the input sender type.
   */
  @Getter
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder<T, P> {

    /**
     * the platform.
     */
    @NotNull
    private final ChatPlatform<P> platform;

    /**
     * the run after finish.
     */
    @NotNull
    private final EnumMap<EndReason, Collection<Consumer<P>>> runAfter = new EnumMap<>(EndReason.class);

    /**
     * the sender.
     */
    @NotNull
    private final ChatSender<P> sender;

    /**
     * the cancel.
     */
    @NotNull
    private String cancel = "cancel";

    /**
     * the expire.
     */
    private long expire = -1L;

    /**
     * the expire message.
     */
    @Nullable
    private Function<P, String> expireMessage;

    /**
     * the invalid input message.
     */
    @Nullable
    private String invalidInputMessage = "That is not a valid input!";

    /**
     * the is valid input.
     */
    @NotNull
    private BiPredicate<P, String> isValidInput = (p, mes) -> true;

    /**
     * the on cancel.
     */
    @NotNull
    private Consumer<P> onCancel = p -> {
    };

    /**
     * the on disconnect.
     */
    @NotNull
    private Consumer<P> onDisconnect = sender -> {
    };

    /**
     * the on expire.
     */
    @NotNull
    private Consumer<P> onExpire = p -> {
    };

    /**
     * the on finish.
     */
    @NotNull
    private BiConsumer<P, T> onFinish = (p, val) -> {
    };

    /**
     * the on invalid input.
     */
    @NotNull
    private BiPredicate<P, String> onInvalidInput = (p, mes) -> true;

    /**
     * the repeat.
     */
    private boolean repeat = true;

    /**
     * the send value message.
     */
    @Nullable
    private String sendValueMessage = "Send in the chat the value";

    /**
     * the value.
     */
    @Nullable
    private T value;

    /**
     * the set value.
     */
    @NotNull
    private BiFunction<P, String, T> setValue = (p, mes) -> this.value;

    /**
     * puts the given values into {@link #runAfter}.
     *
     * @param runAfter the run after to put.
     * @param reasons the reasons to put.
     *
     * @return {@code this}.
     */
    @NotNull
    public Builder<T, P> addRunAfter(@NotNull final Consumer<P> runAfter, @NotNull final EndReason... reasons) {
      Arrays.stream(reasons)
        .forEach(reason -> {
          final Collection<Consumer<P>> old = this.runAfter.getOrDefault(reason, new HashSet<>());
          old.add(runAfter);
          this.runAfter.put(reason, old);
        });
      return this;
    }

    /**
     * builds the {@link ChatInput} instance.
     *
     * @return a {@link ChatInput} instance.
     */
    @NotNull
    public ChatInput<T, P> build() {
      return new ChatInput<>(this, this.platform);
    }

    /**
     * puts the given values into {@link #runAfter}.
     * <p>
     * opens the given builder after finishing the chat input.
     *
     * @param builder the run after to put.
     * @param reasons the reasons to put.
     *
     * @return {@code this}.
     *
     * @see #chainAfter(ChatInput, EndReason...)
     */
    @NotNull
    public Builder<T, P> chainAfter(@NotNull final Builder<T, P> builder, @NotNull final EndReason... reasons) {
      return this.chainAfter(builder.build(), reasons);
    }

    /**
     * puts the given values into {@link #runAfter}.
     * <p>
     * opens the given chat input after finishing the chat input.
     *
     * @param input the input to put.
     * @param reasons the reasons to put.
     *
     * @return {@code this}.
     *
     * @see #addRunAfter(Consumer, EndReason...)
     */
    @NotNull
    public Builder<T, P> chainAfter(@NotNull final ChatInput<T, P> input, @NotNull final EndReason... reasons) {
      return this.addRunAfter(sender -> input.start(), Arrays.stream(reasons)
        .filter(reason -> reason != EndReason.PLAYER_DISCONNECTS)
        .toArray(EndReason[]::new));
    }

    /**
     * sets {@link #value} and return {@code this}.
     *
     * @param value the value to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> defaultValue(@Nullable final T value) {
      this.value = value;
      return this;
    }

    /**
     * sets {@link #expire} and return {@code this}.
     *
     * @param expire the expire to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> expire(final long expire) {
      this.expire = expire;
      return this;
    }

    /**
     * sets {@link #invalidInputMessage} and return {@code this}.
     *
     * @param invalidInputMessage the invalid input message to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> invalidInputMessage(@Nullable final String invalidInputMessage) {
      this.invalidInputMessage = invalidInputMessage;
      return this;
    }

    /**
     * sets {@link #isValidInput} and return {@code this}.
     *
     * @param isValidInput the is valid input to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> isValidInput(@NotNull final BiPredicate<P, String> isValidInput) {
      this.isValidInput = isValidInput;
      return this;
    }

    /**
     * sets {@link #onCancel} and return {@code this}.
     *
     * @param onCancel the on cancel to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> onCancel(@NotNull final Consumer<P> onCancel) {
      this.onCancel = onCancel;
      return this;
    }

    /**
     * sets {@link #onDisconnect}.
     *
     * @param onDisconnect the on disconnect to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public Builder<T, P> onDisconnect(@NotNull final Consumer<P> onDisconnect) {
      this.onDisconnect = onDisconnect;
      return this;
    }

    /**
     * sets {@link #onExpire} and return {@code this}.
     *
     * @param onExpire the on expire to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> onExpire(@NotNull final Consumer<P> onExpire) {
      this.onExpire = onExpire;
      return this;
    }

    /**
     * sets {@link #onFinish} and return {@code this}.
     *
     * @param onFinish the on finish to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> onFinish(@NotNull final BiConsumer<P, T> onFinish) {
      this.onFinish = onFinish;
      return this;
    }

    /**
     * sets {@link #onInvalidInput} and return {@code this}.
     *
     * @param onInvalidInput the on invalid input to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> onInvalidInput(@NotNull final BiPredicate<P, String> onInvalidInput) {
      this.onInvalidInput = onInvalidInput;
      return this;
    }

    /**
     * sets {@link #repeat} and return {@code this}.
     *
     * @param repeat the repeat to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> repeat(final boolean repeat) {
      this.repeat = repeat;
      return this;
    }

    /**
     * sets {@link #sendValueMessage} and return {@code this}.
     *
     * @param sendValueMessage the send value message to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> sendValueMessage(@Nullable final String sendValueMessage) {
      this.sendValueMessage = sendValueMessage;
      return this;
    }

    /**
     * sets {@link #expireMessage}.
     *
     * @param expireMessage the expire message to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public Builder<T, P> setExpireMessage(@NotNull final Function<P, String> expireMessage) {
      this.expireMessage = expireMessage;
      return this;
    }

    /**
     * sets {@link #setValue} and return {@code this}.
     *
     * @param setValue the set value to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> setValue(@NotNull final BiFunction<P, String, T> setValue) {
      this.setValue = setValue;
      return this;
    }

    /**
     * sets {@link #cancel} and return {@code this}.
     *
     * @param cancel the cancel to set.
     *
     * @return {@code this}.
     */
    @NotNull
    public ChatInput.Builder<T, P> toCancel(@NotNull final String cancel) {
      this.cancel = cancel;
      return this;
    }
  }
}
