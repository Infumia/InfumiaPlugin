package tr.com.infumia.infumialib.paper.files;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.annotation.Route;
import io.github.portlek.configs.yaml.YamlType;
import io.github.portlek.replaceable.RpString;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents Infumia Lib's config.
 */
public final class BukkitConfig implements ConfigHolder {

  /**
   * the hook message.
   */
  @Route("hook-message")
  public static RpString hookMessage = RpString.from("%hook% is hooking")
    .regex("%hook%")
    .map(ColorUtil::colored);

  /**
   * ctor.
   */
  private BukkitConfig() {
  }

  /**
   * loads the config.
   *
   * @param plugin the plugin to load.
   */
  public static void load(@NotNull final Plugin plugin) {
    ConfigLoader.builder("bukkit", plugin.getDataFolder(), YamlType.get())
      .setConfigHolder(new BukkitConfig())
      .build()
      .load(true);
  }
}
