package tr.com.infumia.infumialib.paper.smartinventory.handle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.Handle;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.SmartEvent;

/**
 * an implementation for {@link Handle}.
 *
 * @param <T> type of the event.
 */
@RequiredArgsConstructor
public final class BasicHandle<T extends SmartEvent> implements Handle<T> {

  /**
   * the class.
   */
  @NotNull
  private final Class<T> clazz;

  /**
   * the consumer.
   */
  @NotNull
  private final Consumer<T> consumer;

  /**
   * the requirements.
   */
  @NotNull
  private final List<Predicate<T>> requirements;

  @Override
  public void accept(@NotNull final T t) {
    if (this.requirements.stream().allMatch(req -> req.test(t))) {
      this.consumer.accept(t);
    }
  }

  @NotNull
  @Override
  public Class<T> type() {
    return this.clazz;
  }
}
