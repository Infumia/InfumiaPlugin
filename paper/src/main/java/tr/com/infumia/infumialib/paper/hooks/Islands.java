package tr.com.infumia.infumialib.paper.hooks;

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for island plugins.
 */
@UtilityClass
public class Islands {

  /**
   * adds the level to the first island of the unique id.
   *
   * @param plugin the plugin to add.
   * @param uniqueId the unique id to add.
   * @param level the level to add.
   */
  public void addFirstIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uniqueId, final long level) {
    if (Hooks.supportsBentoBox()) {
      Hooks.getBentoBoxOrThrow().addIslandLevel(plugin, uniqueId, level);
    }
  }

  /**
   * gets the level of the first island of the unique id.
   *
   * @param uniqueId the unique id to get.
   *
   * @return level of the first island.
   */
  public long getFirstIslandLevel(@NotNull final UUID uniqueId) {
    if (Hooks.supportsBentoBox()) {
      return Hooks.getBentoBoxOrThrow().getIslandLevel(uniqueId);
    }
    return 0L;
  }
}
