package tr.com.infumia.infumialib.files;

import io.github.portlek.configs.hjsonjson.HJsonJson;
import io.github.portlek.transformer.TransformedObject;
import io.github.portlek.transformer.TransformerPool;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Names;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

/**
 * a config class that covers Infumia's config.
 */
@Names(strategy = Names.Strategy.HYPHEN_CASE, modifier = Names.Modifier.TO_LOWER_CASE)
public final class InfumiaLibConfig extends TransformedObject {

  /**
   * the check for update.
   */
  @Comment("Checks update for the Infumia library plugin.")
  public static boolean checkForUpdate = true;

  /**
   * ctor.
   */
  private InfumiaLibConfig() {
  }

  /**
   * loads the config.
   *
   * @param folder the folder to load.
   *
   * @return completed future.
   */
  @NotNull
  public static CompletableFuture<Void> loadConfig(@NotNull final File folder) {
    return CompletableFuture.runAsync(() ->
      TransformerPool.create(new InfumiaLibConfig())
        .withFile(new File(folder, "config.hjson"))
        .withResolver(new HJsonJson())
        .initiate());
  }
}
