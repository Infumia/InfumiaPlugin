package tr.com.infumia.infumialib.paper.hooks.hooks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.citizensnpcs.Citizens;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;

@RequiredArgsConstructor
public final class CitizensWrapper implements Wrapped {

  @NotNull
  @Getter
  private final Citizens citizens;
}
