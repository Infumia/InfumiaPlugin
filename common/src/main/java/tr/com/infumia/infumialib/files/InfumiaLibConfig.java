package tr.com.infumia.infumialib.files;

import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.annotation.Route;
import io.github.portlek.configs.yaml.YamlType;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * a config class that covers Infumia's config.
 */
public final class InfumiaLibConfig implements ConfigHolder {

  /**
   * the check for update.
   */
  @Route("check-for-update")
  public static boolean checkForUpdate = true;

  /**
   * loads the config.
   *
   * @param folder the folder to load.
   */
  public static void load(@NotNull final File folder) {
    InfumiaLibConfig.load(folder, false);
  }

  /**
   * loads the config.
   *
   * @param folder the folder to load.
   * @param async the async to load.
   *
   * @return completed future.
   */
  @NotNull
  @SneakyThrows
  public static CompletableFuture<ConfigLoader> load(@NotNull final File folder, final boolean async) {
    final var path = folder.toPath();
    if (Files.notExists(path)) {
      Files.createDirectories(path);
    }
    return ConfigLoader.builder("config", folder, YamlType.get())
      .setConfigHolder(new InfumiaLibConfig())
      .build()
      .load(true, async);
  }
}
