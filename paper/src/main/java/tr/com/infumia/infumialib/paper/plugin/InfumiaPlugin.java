package tr.com.infumia.infumialib.paper.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.dependencies.LibraryLoader;

public abstract class InfumiaPlugin extends JavaPlugin {

  protected final LibraryLoader libraryLoader = new LibraryLoader(this);

  private final Logger logger = LogManager.getLogger(this.getName());

  @NotNull
  public final Logger getInfumiaLogger() {
    return this.logger;
  }

  @Override
  public final void onLoad() {
    this.load();
  }

  @Override
  public final void onDisable() {
    this.disable();
  }

  @Override
  public final void onEnable() {
    this.enable();
  }

  protected abstract void disable();

  protected abstract void enable();

  protected abstract void load();
}
