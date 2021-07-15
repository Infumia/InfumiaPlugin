package tr.com.infumia.infumialib.paper;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
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

  @NotNull
  private final ClassPathAppender classPathAppender;

  @NotNull
  private final ConsoleCommandSender console;

  @NotNull
  @Getter
  private final JavaPlugin loader;

  @NotNull
  private final PluginLogger logger;

  @NotNull
  private final SchedulerAdapter schedulerAdapter;

  public InfumiaPaperBootstrap(@NotNull final JavaPlugin loader) {
    this.loader = loader;
    this.logger = new JavaPluginLogger(loader.getLogger());
    this.schedulerAdapter = new BukkitSchedulerAdapter(this);
    this.classPathAppender = new JarInJarClassPathAppender(this.getClass().getClassLoader());
  }

  @Override
  public void disable() {
  }

  @Override
  public void enable() {
  }

  @Override
  public void load() {
  }

  @NotNull
  @Override
  public ClassPathAppender getClassPathAppender() {
    return null;
  }

  @NotNull
  @Override
  public Path getDataDirectory() {
    return null;
  }

  @NotNull
  @Override
  public CountDownLatch getEnableLatch() {
    return null;
  }

  @NotNull
  @Override
  public CountDownLatch getLoadLatch() {
    return null;
  }

  @NotNull
  @Override
  public Collection<UUID> getOnlinePlayers() {
    return null;
  }

  @NotNull
  @Override
  public Optional<?> getPlayer(final UUID uniqueId) {
    return Optional.empty();
  }

  @Override
  public int getPlayerCount() {
    return 0;
  }

  @NotNull
  @Override
  public Collection<String> getPlayerList() {
    return null;
  }

  @NotNull
  @Override
  public PluginLogger getPluginLogger() {
    return null;
  }

  @NotNull
  @Override
  public SchedulerAdapter getScheduler() {
    return null;
  }

  @NotNull
  @Override
  public String getServerBrand() {
    return null;
  }

  @Override
  public String getServerVersion() {
    return null;
  }

  @NotNull
  @Override
  public Instant getStartupTime() {
    return null;
  }

  @NotNull
  @Override
  public Platform.Type getType() {
    return null;
  }

  @NotNull
  @Override
  public String getVersion() {
    return null;
  }

  @Override
  public boolean isPlayerOnline(final UUID uniqueId) {
    return false;
  }

  @NotNull
  @Override
  public Optional<UUID> lookupUniqueId(final String username) {
    return Optional.empty();
  }

  @NotNull
  @Override
  public Optional<String> lookupUsername(final UUID uniqueId) {
    return Optional.empty();
  }

  public Server getServer() {
    return this.loader.getServer();
  }
}
