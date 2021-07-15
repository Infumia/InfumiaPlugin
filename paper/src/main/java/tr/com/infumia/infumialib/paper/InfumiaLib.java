package tr.com.infumia.infumialib.paper;

import tr.com.infumia.infumialib.files.InfumiaLibConfig;
import tr.com.infumia.infumialib.paper.color.CustomColors;
import tr.com.infumia.infumialib.paper.commands.InfumiaPluginCommands;
import tr.com.infumia.infumialib.paper.dependencies.BukkitDependencies;
import tr.com.infumia.infumialib.paper.dependencies.CommonDependencies;
import tr.com.infumia.infumialib.paper.files.PaperConfig;
import tr.com.infumia.infumialib.paper.hooks.Hooks;
import tr.com.infumia.infumialib.paper.plugin.InfumiaPlugin;
import tr.com.infumia.infumialib.paper.utils.GitHubUpdateChecker;
import tr.com.infumia.infumialib.paper.utils.TaskUtilities;

/**
 * a class that represents main class of Infumia Library plugin.
 */
public final class InfumiaLib extends InfumiaPlugin {

  public void loadFiles() {
    InfumiaLibConfig.loadConfig(this.getDataFolder());
    PaperConfig.loadConfig(this.getDataFolder());
  }

  @Override
  protected void disable() {
  }

  @Override
  public void enable() {
    this.libraryLoader.loadAll(CommonDependencies.class);
    this.libraryLoader.loadAll(BukkitDependencies.class);
    InfumiaPlugin.getInventory().init();
    final var commandManager = this.createCommandManager();
    new InfumiaPluginCommands(commandManager, this).register();
    if (InfumiaLibConfig.checkForUpdate) {
      GitHubUpdateChecker.checkForUpdate(this, "Infumia", "InfumiaLib");
    }
    new Metrics(this, 11422);
    Hooks.loadHooks();
  }

  @Override
  public void load() {
    CustomColors.registerAll();
    TaskUtilities.init(this);
    this.loadFiles();
  }
}
