package tr.com.infumia.infumialib.workload;

/**
 * an interface to determine works.
 */
public interface Workload {

  /**
   * computes a piece of the work.
   */
  void compute();

  /**
   * computes then checks {@link #reschedule()}.
   *
   * @return {@code true} if re-scheduling.
   */
  default boolean computeThenCheckForScheduling() {
    this.compute();
    return !this.reschedule();
  }

  /**
   * checks if the work is reschedulable.
   *
   * @return {@code true} if the work should be re schedule.
   */
  default boolean reschedule() {
    return false;
  }

  /**
   * checks if the work should execute. if it's {@code true} than runs {@link Workload#compute()}.
   *
   * @return {@code true} if the work should execute.
   */
  default boolean shouldExecute() {
    return true;
  }
}
