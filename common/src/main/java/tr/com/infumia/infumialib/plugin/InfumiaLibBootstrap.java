package tr.com.infumia.infumialib.plugin;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfumiaLibBootstrap {

  @NotNull
  ClassPathAppender getClassPathAppender();

  default Path getConfigDirectory() {
    return this.getDataDirectory();
  }

  @NotNull
  Path getDataDirectory();

  @NotNull
  CountDownLatch getEnableLatch();

  @NotNull
  CountDownLatch getLoadLatch();

  @NotNull
  Collection<UUID> getOnlinePlayers();

  @NotNull
  Optional<?> getPlayer(UUID uniqueId);

  int getPlayerCount();

  @NotNull
  Collection<String> getPlayerList();

  @NotNull
  PluginLogger getPluginLogger();

  @Nullable
  default InputStream getResourceStream(@NotNull final String path) {
    return this.getClass().getClassLoader().getResourceAsStream(path);
  }

  @NotNull
  SchedulerAdapter getScheduler();

  @NotNull
  String getServerBrand();

  @Nullable
  default String getServerName() {
    return null;
  }

  @NotNull
  String getServerVersion();

  @NotNull
  Instant getStartupTime();

  @NotNull
  Platform.Type getType();

  @NotNull
  String getVersion();

  @Nullable
  default String identifyClassLoader(final ClassLoader classLoader) throws Exception {
    return null;
  }

  boolean isPlayerOnline(UUID uniqueId);

  @NotNull
  Optional<UUID> lookupUniqueId(String username);

  @NotNull
  Optional<String> lookupUsername(UUID uniqueId);
}
