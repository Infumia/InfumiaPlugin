package tr.com.infumia.infumialib.paper;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.JarInJarClassLoader;
import tr.com.infumia.infumialib.LoaderBootstrap;

public final class BukkitLoaderPlugin extends JavaPlugin {

  private static final String BOOTSTRAP_CLASS = "tr.com.infumia.infumialib.paper.InfumiaPaperBootstrap";

  private static final String JAR_NAME = "infumialibraryplugin.jarinjar";

  @NotNull
  private final LoaderBootstrap plugin;

  public BukkitLoaderPlugin() {
    this.plugin = new JarInJarClassLoader(this.getClass().getClassLoader(), BukkitLoaderPlugin.JAR_NAME)
      .instantiatePlugin(BukkitLoaderPlugin.BOOTSTRAP_CLASS, JavaPlugin.class, this);
  }

  @Override
  public void onLoad() {
    this.plugin.load();
  }

  @Override
  public void onDisable() {
    this.plugin.disable();
  }

  @Override
  public void onEnable() {
    this.plugin.enable();
  }
}
