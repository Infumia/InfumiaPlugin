package tr.com.infumia.infumialib.input;

import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine chat platforms.
 *
 * @param <P> type of the sender.
 */
public interface ChatPlatform<P> {

  /**
   * creates an instance for the task.
   *
   * @param runnable the runnable to run.
   * @param time the time to expire.
   *
   * @return an instance for the task.
   */
  @NotNull
  ChatTask createRunTaskLater(@NotNull Runnable runnable, long time);

  /**
   * initiates the platform.
   *
   * @param input the input to initiate.
   */
  void init(@NotNull ChatInput<?, P> input);

  /**
   * un register all listeners.
   */
  void unregisterListeners();
}
