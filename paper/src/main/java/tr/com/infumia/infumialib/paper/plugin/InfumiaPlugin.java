package tr.com.infumia.infumialib.paper.plugin;

import java.net.URLClassLoader;
import java.util.EnumSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.dependencies.Dependency;
import tr.com.infumia.infumialib.paper.dependencies.DependencyManager;

public abstract class InfumiaPlugin extends JavaPlugin {

  private final DependencyManager dependencyManager = new DependencyManager(this);

  private final Logger logger = LogManager.getLogger(this.getName());

  @NotNull
  public final Logger getInfumiaLogger() {
    return this.logger;
  }

  @NotNull
  public final URLClassLoader getURLClassLoader() {
    return (URLClassLoader) this.getClassLoader();
  }

  @Override
  public final void onLoad() {
    this.dependencyManager.loadDependencies(EnumSet.allOf(Dependency.class));
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
