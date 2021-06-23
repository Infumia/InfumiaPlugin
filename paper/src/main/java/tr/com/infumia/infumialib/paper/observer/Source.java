package tr.com.infumia.infumialib.paper.observer;

import org.jetbrains.annotations.NotNull;

/**
 * a class that is the observer's source.
 *
 * @param <T> type of the arguments.
 */
public interface Source<T> {

  /**
   * notifies {@link Target#update(Object)} method all of the subscribes.
   *
   * @param argument the argument to notify.
   */
  void notifyTargets(@NotNull T argument);

  /**
   * subscribes the given {@link Target} into the source.
   *
   * @param target the target to subscribe.
   */
  void subscribe(@NotNull Target<T> target);

  /**
   * remove the given {@link Target} from the subscriptions of the source.
   *
   * @param target the target to remove.
   */
  void unsubscribe(@NotNull Target<T> target);
}
