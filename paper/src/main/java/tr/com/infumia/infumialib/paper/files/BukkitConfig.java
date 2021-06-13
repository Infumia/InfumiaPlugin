package tr.com.infumia.infumialib.paper.files;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.bukkit.ItemStackSerializer;
import io.github.portlek.configs.bukkit.Position;
import io.github.portlek.configs.bukkit.SentTitle;
import io.github.portlek.configs.snakeyaml.bukkit.BukkitSnakeyaml;
import io.github.portlek.replaceable.RpString;
import io.github.portlek.transformer.TransformedObject;
import io.github.portlek.transformer.TransformerPool;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Names;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.element.FileElement;
import tr.com.infumia.infumialib.paper.utils.TaskUtilities;

/**
 * a class that represents Infumia Lib's config.
 */
@Names(strategy = Names.Strategy.HYPHEN_CASE, modifier = Names.Modifier.TO_LOWER_CASE)
public final class BukkitConfig extends TransformedObject {

  /**
   * the hook message.
   */
  @Comment("Hooking message for each plugin/library.")
  public static RpString hookMessage = RpString.from("%hook% is hooking.")
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
   * @param folder the folder to load.
   */
  public static void loadConfig(@NotNull final File folder) {
    TaskUtilities.async(runnable ->
      TransformerPool.create(new BukkitConfig())
        .withFile(new File(folder, "bukkit.yml"))
        .withResolver(new BukkitSnakeyaml())
        .withTransformPack(registry -> registry
          .withSerializers(
            new FileElement.Serializer(),
            new ItemStackSerializer(),
            new Position.Serializer(),
            new SentTitle.Serializer()))
        .initiate());
  }
}
