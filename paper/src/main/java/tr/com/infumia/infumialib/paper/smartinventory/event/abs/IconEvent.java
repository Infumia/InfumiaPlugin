package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;

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
