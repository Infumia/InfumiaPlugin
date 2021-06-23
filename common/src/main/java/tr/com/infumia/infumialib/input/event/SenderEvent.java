package tr.com.infumia.infumialib.input.event;

import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.input.ChatSender;

/**
 * an interface to determine events that have the sender in it.
 *
 * @param <P> the sender type.
 */
public interface SenderEvent<P> {

  /**
   * obtains the sender instance.
   *
   * @return the sender instance.
   */
  @NotNull
  ChatSender<P> getSender();
}
