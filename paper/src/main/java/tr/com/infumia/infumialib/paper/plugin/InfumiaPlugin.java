package tr.com.infumia.infumialib.paper.plugin;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.dependencies.LibraryLoader;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.plugin.PersistenceWrapper;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.manager.BasicSmartInventory;
import tr.com.infumia.infumialib.paper.utils.Versions;

public abstract class InfumiaPlugin extends JavaPlugin {

  /**
   * the inventory.
   */
  @Nullable
  private static SmartInventory inventory;

  /**
   * teh world border api.
   */
  @Nullable
  private static WorldBorderApi worldBorderApi;

  /**
   * the library loader.
   */
  protected final LibraryLoader libraryLoader = new LibraryLoader(this);

  /**
   * the logger.
   */
  private final Logger logger = LogManager.getLogger(this.getName());

  /**
   * obtains the inventory.
   *
   * @return inventory.
   */
  @NotNull
  public static SmartInventory getInventory() {
    return Objects.requireNonNull(InfumiaPlugin.inventory, "inventory");
  }

  /**
   * obtains the world border api.
   *
   * @return world border api.
   */
  @NotNull
  public static Optional<WorldBorderApi> getWorldBorderApi() {
    return Optional.ofNullable(InfumiaPlugin.worldBorderApi);
  }

  /**
   * obtains the world border api.
   *
   * @return world border api.
   */
  @NotNull
  public static WorldBorderApi getWorldBorderApiOrThrow() {
    return InfumiaPlugin.getWorldBorderApi().orElseThrow();
  }

  /**
   * obtains the logger.
   *
   * @return logger.
   */
  @NotNull
  public final Logger getInfumiaLogger() {
    return this.logger;
  }

  @Override
  public final void onLoad() {
    if (InfumiaPlugin.inventory == null) {
      InfumiaPlugin.inventory = new BasicSmartInventory(this);
    }
    this.load();
  }

  @Override
  public final void onDisable() {
    this.disable();
  }

  @Override
  public final void onEnable() {
    this.initiateWorldBorder();
    this.enable();
  }

  /**
   * creates a new command manager.
   *
   * @return a newly created command manager.
   */
  @NotNull
  protected PaperCommandManager<CommandSender> createCommandManager() {
    final PaperCommandManager<CommandSender> manager;
    try {
      manager = new PaperCommandManager<>(this,
        CommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity());
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
    try {
      manager.registerBrigadier();
    } catch (final Exception e) {
      this.getInfumiaLogger().error("Couldn't add brigadier support.");
    }
    try {
      manager.registerAsynchronousCompletions();
    } catch (final Exception e) {
      this.getInfumiaLogger().error("Couldn't add async tab completion support.");
    }
    return manager;
  }

  /**
   * disables the plugin.
   */
  protected abstract void disable();

  /**
   * enables the plugin.
   */
  protected abstract void enable();

  /**
   * loads the plugin.
   */
  protected abstract void load();

  /**
   * initiates the world border.
   */
  private void initiateWorldBorder() {
    if (Versions.MINOR >= 16 && Versions.MICRO == 3) {
      InfumiaPlugin.worldBorderApi = new tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.v1_16.Border();
      InfumiaPlugin.worldBorderApi = new PersistenceWrapper(this, InfumiaPlugin.worldBorderApi);
    }
  }
}
