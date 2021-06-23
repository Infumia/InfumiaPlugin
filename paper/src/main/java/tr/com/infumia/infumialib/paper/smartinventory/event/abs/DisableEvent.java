package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine disable.
 */
public interface DisableEvent extends PageEvent {

  /**
   * obtains the event.
   *
   * @return event.
   */
  @NotNull
  PluginDisableEvent getEvent();
}
