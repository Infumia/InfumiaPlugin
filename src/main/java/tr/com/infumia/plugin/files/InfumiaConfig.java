package tr.com.infumia.plugin.files;

import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.annotation.Route;
import io.github.portlek.configs.yaml.YamlType;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a config class that covers InfumiaPlugin's config.
 */
public final class InfumiaConfig implements ConfigHolder {

  /**
   * the check for update.
   */
  @Route("check-for-update")
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
  @SneakyThrows
  public static CompletableFuture<ConfigLoader> load(@NotNull final Plugin plugin, final boolean async) {
    final var path = plugin.getDataFolder().toPath();
    if (Files.notExists(path)) {
      Files.createDirectories(path);
    }
    return ConfigLoader.builder("config", plugin.getDataFolder(), YamlType.get())
      .setConfigHolder(new InfumiaConfig())
      .build()
      .load(true, async);
  }
}
