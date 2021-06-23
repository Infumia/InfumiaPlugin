package tr.com.infumia.infumialib.paper.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.input.ChatSender;
import tr.com.infumia.infumialib.input.event.QuitEvent;

/**
 * an implementation for {@link QuitEvent}.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class PprQuitEvent implements QuitEvent<Player> {

  /**
   * the sender.
   */
  @NotNull
  @Getter
  private final ChatSender<Player> sender;
}
