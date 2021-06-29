package tr.com.infumia.infumialib.paper.hooks.hooks;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;

@Getter
@RequiredArgsConstructor
public final class CombatLogXWrapper implements Wrapped {

  @NotNull
  private final ICombatLogX combatLog;
}
