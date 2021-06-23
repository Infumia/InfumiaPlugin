package tr.com.infumia.infumialib.paper.observer.source;

import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.observer.Source;
import tr.com.infumia.infumialib.paper.observer.Target;

/**
 * an implementation for {@link Source}.
 *
 * @param <T> type of the argument.
 */
public final class BasicSource<T> implements Source<T> {

  /**
   * the subscriptions.
   */
  private final Collection<Target<T>> subscriptions = new ArrayList<>();

  @Override
  public void notifyTargets(@NotNull final T argument) {
    this.subscriptions.forEach(target -> target.update(argument));
  }

  @Override
  public void subscribe(@NotNull final Target<T> target) {
    if (!this.subscriptions.contains(target)) {
      this.subscriptions.add(target);
    }
  }

  @Override
  public void unsubscribe(@NotNull final Target<T> target) {
    this.subscriptions.remove(target);
  }
}
