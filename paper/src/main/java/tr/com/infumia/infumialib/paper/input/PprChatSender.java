package tr.com.infumia.infumialib.paper.input;

import tr.com.infumia.infumialib.input.ChatSender;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation for {@link ChatSender}.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class PprChatSender implements ChatSender<Player> {

  /**
   * the wrapped.
   */
  @NotNull
  @Getter
  private final Player wrapped;

  @NotNull
  @Override
  public UUID getUniqueId() {
    return this.wrapped.getUniqueId();
  }

  @Override
  public void sendMessage(@NotNull final String message) {
    this.wrapped.sendMessage(message);
  }
}
