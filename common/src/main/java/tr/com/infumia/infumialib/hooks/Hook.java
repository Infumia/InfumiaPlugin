package tr.com.infumia.infumialib.hooks;

import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine hooks.
 *
 * @param <W> type of the wrapper object.
 */
public interface Hook<W extends Wrapped> {

  /**
   * creates a new wrapper.
   *
   * @return wrapper.
   */
  @NotNull
  W create();

  /**
   * obtains the id.
   *
   * @return id.
   */
  @NotNull
  String id();

  /**
   * initiates the hook.
   *
   * @return {@code true} if the hook initiated successfully.
   */
  boolean initiate();
}
