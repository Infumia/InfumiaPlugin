package tr.com.infumia.infumialib.input;

/**
 * an interface to determine tasks.
 */
public interface ChatTask {

  /**
   * cancels the task.
   */
  void cancel();

  /**
   * checks if the task is cancelled.
   *
   * @return {@code true} if the test is cancelled.
   */
  boolean isCancelled();
}
