package tr.com.infumia.infumialib.paper.files;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.bukkit.ItemStackSerializer;
import io.github.portlek.configs.bukkit.Position;
import io.github.portlek.configs.bukkit.SentTitle;
import io.github.portlek.configs.snakeyaml.bukkit.BukkitSnakeyaml;
import io.github.portlek.replaceable.RpString;
import io.github.portlek.smartinventory.Page;
import io.github.portlek.transformer.TransformedObject;
import io.github.portlek.transformer.TransformerPool;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.Names;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.InfumiaLib;
import tr.com.infumia.infumialib.paper.element.FileElement;

/**
 * a class that represents Infumia Lib's config.
 */
@Names(strategy = Names.Strategy.HYPHEN_CASE, modifier = Names.Modifier.TO_LOWER_CASE)
public final class BukkitConfig extends TransformedObject {

  @Comment("Test file element.")
  public static FileElement fileElement = FileElement.insert(ItemStackBuilder.from(XMaterial.WRITABLE_BOOK), 0, 1)
    .addEvent(clickEvent -> System.out.println(clickEvent.click()));

  public static void openInventory(Player player){
    Page.build(InfumiaLib.getInstance().getInventory())
      .whenInit(initEvent -> {
        fileElement.place(initEvent.contents());
      })
      .open(player);
  }

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
//    CompletableFuture.runAsync(() ->
      TransformerPool.create(new BukkitConfig())
        .withFile(new File(folder, "bukkit.yml"))
        .withResolver(new BukkitSnakeyaml())
        .withTransformPack(registry -> registry
          .withSerializers(
            new FileElement.Serializer(),
            new ItemStackSerializer(),
            new Position.Serializer(),
            new SentTitle.Serializer()))
        .initiate()
//    )
    ;
  }
}
