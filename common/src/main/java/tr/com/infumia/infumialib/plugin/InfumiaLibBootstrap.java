package tr.com.infumia.infumialib.plugin;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
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
  PluginLogger getPluginLogger();

  @Nullable
  default InputStream getResourceStream(@NotNull final String path) {
    return this.getClass().getClassLoader().getResourceAsStream(path);
  }

  @NotNull
  SchedulerAdapter getScheduler();

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
}
