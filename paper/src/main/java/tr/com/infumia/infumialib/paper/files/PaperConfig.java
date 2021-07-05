package tr.com.infumia.infumialib.paper.files;

import java.io.File;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.color.XColor;
import tr.com.infumia.infumialib.paper.transformer.resolvers.BukkitSnakeyaml;
import tr.com.infumia.infumialib.replaceable.RpString;
import tr.com.infumia.infumialib.transformer.TransformedObject;
import tr.com.infumia.infumialib.transformer.TransformerPool;
import tr.com.infumia.infumialib.transformer.annotations.Comment;
import tr.com.infumia.infumialib.transformer.annotations.Names;

/**
 * a class that represents Infumia Lib's config.
 */
@Names(strategy = Names.Strategy.HYPHEN_CASE, modifier = Names.Modifier.TO_LOWER_CASE)
public final class PaperConfig extends TransformedObject {

  /**
   * the hook message.
   */
  @Comment("Hooking message for each plugin/library.")
  public static RpString hookMessage = RpString.from("{#F0E68C}%hook% {#AFEEEE}is hooking.")
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
    TransformerPool.create(new PaperConfig())
      .withFile(new File(folder, "paper.yml"))
      .withResolver(new BukkitSnakeyaml())
      .initiate();
  }
}
