package tr.com.infumia.infumialib.paper;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.LoaderBootstrap;
import tr.com.infumia.infumialib.paper.plugin.BukkitSchedulerAdapter;
import tr.com.infumia.infumialib.paper.plugin.JavaPluginLogger;
import tr.com.infumia.infumialib.plugin.ClassPathAppender;
import tr.com.infumia.infumialib.plugin.InfumiaLibBootstrap;
import tr.com.infumia.infumialib.plugin.JarInJarClassPathAppender;
import tr.com.infumia.infumialib.plugin.Platform;
import tr.com.infumia.infumialib.plugin.PluginLogger;
import tr.com.infumia.infumialib.plugin.SchedulerAdapter;

public final class InfumiaPaperBootstrap implements LoaderBootstrap, InfumiaLibBootstrap {

  @Getter
  @NotNull
  private final ClassPathAppender classPathAppender;

  @Getter
  private final CountDownLatch enableLatch = new CountDownLatch(1);

  @Getter
  private final CountDownLatch loadLatch = new CountDownLatch(1);

  @NotNull
  @Getter
  private final JavaPlugin loader;

  @Getter
  @NotNull
  private final PluginLogger pluginLogger;

  @Getter
  @NotNull
  private final SchedulerAdapter scheduler;

  private boolean incompatibleVersion = false;

  @Nullable
  private Instant startupTime;

  public InfumiaPaperBootstrap(@NotNull final JavaPlugin loader) {
    this.loader = loader;
    this.pluginLogger = new JavaPluginLogger(loader.getLogger());
    this.scheduler = new BukkitSchedulerAdapter(this);
    this.classPathAppender = new JarInJarClassPathAppender(this.getClass().getClassLoader());
  }

  private static boolean checkIncompatibleVersion() {
    try {
      Class.forName("com.google.gson.JsonElement");
      return false;
    } catch (final ClassNotFoundException e) {
      return true;
    }
  }

  @Override
  public void disable() {
    if (this.incompatibleVersion) {
      return;
    }
    this.plugin.disable();
  }

  @Override
  public void enable() {
    if (this.incompatibleVersion) {
      final var logger = this.loader.getLogger();
      logger.severe("----------------------------------------------------------------------");
      logger.severe("Your server version is not compatible with this build of Infumia Library.");
      logger.severe("");
      logger.severe("Download the correct Jar file here:");
      logger.severe("https://github.com/Infumia/InfumiaLib/");
      logger.severe("----------------------------------------------------------------------");
      this.getServer().getPluginManager().disablePlugin(this.loader);
      return;
    }
    this.startupTime = Instant.now();
    try {
      this.plugin.enable();
    } finally {
      this.enableLatch.countDown();
    }
  }

  @Override
  public void load() {
    if (InfumiaPaperBootstrap.checkIncompatibleVersion()) {
      this.incompatibleVersion = true;
      return;
    }
    try {
      this.plugin.load();
    } finally {
      this.loadLatch.countDown();
    }
  }

  @NotNull
  @Override
  public Path getDataDirectory() {
    return this.loader.getDataFolder().toPath().toAbsolutePath();
  }

  @NotNull
  @Override
  public Instant getStartupTime() {
    return Objects.requireNonNull(this.startupTime, "startup time");
  }

  @NotNull
  @Override
  public Platform.Type getType() {
    return Platform.Type.PAPER;
  }

  @NotNull
  @Override
  public String getVersion() {
    return this.loader.getDescription().getVersion();
  }

  public Server getServer() {
    return this.loader.getServer();
  }
}
