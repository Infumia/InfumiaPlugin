package tr.com.infumia.infumialib.paper.smartinventory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.SmartEvent;
import tr.com.infumia.infumialib.paper.smartinventory.handle.BasicHandle;

/**
 * a class that handles and runs the given consumer after checking the requirements.
 *
 * @param <T> type of the event.
 */
public interface Handle<T extends SmartEvent> extends Consumer<T>, Type<T> {

  /**
   * creates a simple handler.
   *
   * @param clazz the class to determine the type of the event.
   * @param consumer the consumer to run.
   * @param requirements the requirements to check.
   * @param <T> type of the {@link SmartEvent}.
   *
   * @return a simple handler instance.
   */
  @NotNull
  static <T extends SmartEvent> Handle<T> from(@NotNull final Class<T> clazz, @NotNull final Consumer<T> consumer,
                                               @NotNull final List<Predicate<T>> requirements) {
    return new BasicHandle<>(clazz, consumer, requirements);
  }

  /**
   * creates a simple handler.
   *
   * @param clazz the class to determine the type of the event.
   * @param consumer the consumer to run.
   * @param requirements the requirements to check.
   * @param <T> type of the {@link SmartEvent}.
   *
   * @return a simple handler instance.
   */
  @SafeVarargs
  @NotNull
  static <T extends SmartEvent> Handle<T> from(@NotNull final Class<T> clazz, @NotNull final Consumer<T> consumer,
                                               @NotNull final Predicate<T>... requirements) {
    return Handle.from(clazz, consumer, Arrays.asList(requirements));
  }
}
