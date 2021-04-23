package tr.com.infumia.plugin.hooks;

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

@UtilityClass
public class Islands {

  public void addFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final Plugin plugin,
                                  @NotNull final UUID uniqueId, final long level) {
    if (wrapper instanceof ASkyBlockWrapper) {
      ((ASkyBlockWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    } else if (wrapper instanceof BentoBoxWrapper) {
      ((BentoBoxWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    } else if (wrapper instanceof FabledSkyBlockWrapper) {
      ((FabledSkyBlockWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    }
  }

  public long getFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final UUID uniqueId) {
    if (wrapper instanceof ASkyBlockWrapper) {
      return ((ASkyBlockWrapper) wrapper).getIslandLevel(uniqueId);
    }
    if (wrapper instanceof BentoBoxWrapper) {
      return ((BentoBoxWrapper) wrapper).getIslandLevel(uniqueId);
    }
    if (wrapper instanceof FabledSkyBlockWrapper) {
      return ((FabledSkyBlockWrapper) wrapper).getIslandLevel(uniqueId);
    }
    return 0L;
  }
}
