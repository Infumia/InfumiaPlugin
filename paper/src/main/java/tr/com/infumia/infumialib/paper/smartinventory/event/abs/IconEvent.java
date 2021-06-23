package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine icon events.
 */
public interface IconEvent extends SmartEvent {

  /**
   * obtains the icon.
   *
   * @return icon.
   */
  @NotNull
  Icon icon();
}
