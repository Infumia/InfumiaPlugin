package tr.com.infumia.infumialib.input.event;

import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine chat events.
 *
 * @param <P> the sender type.
 */
public interface ChatEvent<P> extends SenderEvent<P> {

  /**
   * cancels the event.
   */
  void cancel();

  /**
   * obtains the sent message.
   *
   * @return the sent message.
   */
  @NotNull
  String getMessage();
}
