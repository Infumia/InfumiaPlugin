package tr.com.infumia.infumialib.plugin;

import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public interface ClassPathAppender extends AutoCloseable {

  void addJarToClasspath(@NotNull Path file);

  @Override
  default void close() {
  }
}
