package tr.com.infumia.infumialib.paper.files;

import io.github.portlek.bukkititembuilder.color.XColor;
import io.github.portlek.configs.snakeyaml.bukkit.BukkitSnakeyaml;
import io.github.portlek.replaceable.RpString;
import io.github.portlek.transformer.TransformedObject;
import io.github.portlek.transformer.TransformerPool;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Names;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents Infumia Lib's config.
 */
@Names(strategy = Names.Strategy.HYPHEN_CASE, modifier = Names.Modifier.TO_LOWER_CASE)
public final class PaperConfig extends TransformedObject {

  /**
   * the hook message.
   */
  @Comment("Hooking message for each plugin/library.")
  public static RpString hookMessage = RpString.from("%hook% is hooking.")
    .regex("%hook%")
    .map(XColor::colorize);

  /**
   * ctor.
   */
  private PaperConfig() {
  }

  /**
   * loads the config.
   *
   * @param folder the folder to load.
   */
  public static void loadConfig(@NotNull final File folder) {
    CompletableFuture.runAsync(() ->
      TransformerPool.create(new PaperConfig())
        .withFile(new File(folder, "paper.yml"))
        .withResolver(new BukkitSnakeyaml())
        .initiate());
  }
}
