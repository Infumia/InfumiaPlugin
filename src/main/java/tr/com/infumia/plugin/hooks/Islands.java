package tr.com.infumia.plugin.hooks;

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.hooks.ASkyBlockWrapper;
import tr.com.infumia.plugin.hooks.hooks.BentoBoxWrapper;

@UtilityClass
public class Islands {

  /**
   * adds the level to the first island of the unique id.
   *
   * @param plugin the plugin to add.
   * @param uniqueId the unique id to add.
   * @param level the level to add.
   */
  public void addFirstIslandLevel(@NotNull final Plugin plugin,
                                  @NotNull final UUID uniqueId, final long level) {
    Hooks.getASkyBlock().ifPresentOrElse(wrapper ->
      Islands.addFirstIslandLevel(wrapper, plugin, uniqueId, level), () ->
      Hooks.getBentoBox().ifPresent(wrapper ->
        Islands.addFirstIslandLevel(wrapper, plugin, uniqueId, level)));
  }

  /**
   * adds the level to the first island of the unique id.
   *
   * @param wrapper the wrapper to add.
   * @param plugin the plugin to add.
   * @param uniqueId the unique id to add.
   * @param level the level to add.
   */
  public void addFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final Plugin plugin,
                                  @NotNull final UUID uniqueId, final long level) {
    if (wrapper instanceof ASkyBlockWrapper) {
      ((ASkyBlockWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    } else if (wrapper instanceof BentoBoxWrapper) {
      ((BentoBoxWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
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
    return Hooks.getASkyBlock()
      .map(wrapper ->
        Islands.getFirstIslandLevel(wrapper, uniqueId))
      .orElse(Hooks.getBentoBox()
        .map(wrapper ->
          Islands.getFirstIslandLevel(wrapper, uniqueId))
        .orElse(0L));
  }

  /**
   * gets the level of the first island of the unique id.
   *
   * @param wrapper the wrapper to get.
   * @param uniqueId the unique id to get.
   *
   * @return level of the first island.
   */
  public long getFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final UUID uniqueId) {
    if (wrapper instanceof ASkyBlockWrapper) {
      return ((ASkyBlockWrapper) wrapper).getIslandLevel(uniqueId);
    }
    if (wrapper instanceof BentoBoxWrapper) {
      return ((BentoBoxWrapper) wrapper).getIslandLevel(uniqueId);
    }
    return 0L;
  }
}
