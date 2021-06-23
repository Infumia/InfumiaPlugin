package tr.com.infumia.infumialib.paper.smartinventory.event.abs;

import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine quit events.
 */
public interface QuitEvent extends PageEvent {

  /**
   * obtains the event.
   *
   * @return event.
   */
  @NotNull
  PlayerQuitEvent getEvent();
}
