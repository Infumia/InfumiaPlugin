package tr.com.infumia.infumialib.paper;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import tr.com.infumia.infumialib.paper.bukkititembuilder.color.CustomColors;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.manager.BasicSmartInventory;
import java.util.Objects;
import java.util.function.Function;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.files.InfumiaLibConfig;
import tr.com.infumia.infumialib.paper.commands.InfumiaPluginCommands;
import tr.com.infumia.infumialib.paper.files.PaperConfig;
import tr.com.infumia.infumialib.paper.hooks.Hooks;
import tr.com.infumia.infumialib.paper.utils.GitHubUpdateChecker;
import tr.com.infumia.infumialib.paper.utils.TaskUtilities;

/**
 * a class that represents main class of Infumia Library plugin.
 */
public final class InfumiaLib extends JavaPlugin {

  /**
   * the instance.
   */
  @Nullable
  private static InfumiaLib instance;

  /**
   * the inventory.
   */
  private final SmartInventory inventory = new BasicSmartInventory(this);

  /**
   * creates a new command manager.
   *
   * @param plugin the plugin to create.
   *
   * @return a newly created command manager.
   */
  @NotNull
  public static PaperCommandManager<CommandSender> createCommandManager(@NotNull final Plugin plugin) {
    final PaperCommandManager<CommandSender> manager;
    try {
      manager = new PaperCommandManager<>(
        plugin, CommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity());
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
    try {
      manager.registerBrigadier();
    } catch (final Exception e) {
      plugin.getLogger().warning("Couldn't add brigadier support.");
    }
    try {
      manager.registerAsynchronousCompletions();
    } catch (final Exception e) {
      plugin.getLogger().warning("Couldn't add async tab completion support.");
    }
    return manager;
  }

  /**
   * obtains the instance.
   *
   * @return instance.
   */
  @NotNull
  public static InfumiaLib getInstance() {
    return Objects.requireNonNull(InfumiaLib.instance, "not initiated");
  }

  /**
   * obtains the inventory.
   *
   * @return inventory.
   */
  @NotNull
  public static SmartInventory getInventory() {
    return InfumiaLib.getInstance().inventory;
  }

  @Override
  public void onLoad() {
    InfumiaLib.instance = this;
    CustomColors.registerAll();
    TaskUtilities.init(this);
    InfumiaLibConfig.loadConfig(this.getDataFolder());
    PaperConfig.loadConfig(this.getDataFolder());
  }

  @Override
  public void onEnable() {
    final var commandManager = InfumiaLib.createCommandManager(this);
    new InfumiaPluginCommands(commandManager).register();
    this.inventory.init();
    Hooks.loadHooks();
    if (InfumiaLibConfig.checkForUpdate) {
      GitHubUpdateChecker.checkForUpdate(this, "Infumia", "InfumiaLib");
    }
    new Metrics(this, 11422);
  }
}
