package tr.com.infumia.plugin.files;

import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.yaml.YamlType;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a config class that covers InfumiaPlugin's config.
 */
public final class InfumiaConfig implements ConfigHolder {

  /**
   * the check for update.
   */
  public static boolean checkForUpdate = true;

  /**
   * loads the config.
   *
   * @param plugin the plugin to load.
   */
  public static void load(@NotNull final Plugin plugin) {
    InfumiaConfig.load(plugin, false);
  }

  /**
   * loads the config.
   *
   * @param plugin the plugin to load.
   * @param async the async to load.
   *
   * @return completed future.
   */
  @NotNull
  public static CompletableFuture<ConfigLoader> load(@NotNull final Plugin plugin, final boolean async) {
    return ConfigLoader.builder("config.yml", plugin.getDataFolder(), YamlType.get())
      .setConfigHolder(new InfumiaConfig())
      .build()
      .load(true, async);
  }
}
