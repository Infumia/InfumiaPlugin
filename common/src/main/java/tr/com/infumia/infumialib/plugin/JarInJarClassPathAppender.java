package tr.com.infumia.infumialib.plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.JarInJarClassLoader;

public final class JarInJarClassPathAppender implements ClassPathAppender {

  @NotNull
  private final JarInJarClassLoader classLoader;

  public JarInJarClassPathAppender(@NotNull final ClassLoader classLoader) {
    if (!(classLoader instanceof JarInJarClassLoader)) {
      throw new IllegalArgumentException("Loader is not a JarInJarClassLoader: " + classLoader.getClass().getName());
    }
    this.classLoader = (JarInJarClassLoader) classLoader;
  }

  @Override
  public void addJarToClasspath(@NotNull final Path file) {
    try {
      this.classLoader.addJarToClasspath(file.toUri().toURL());
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    this.classLoader.deleteJarResource();
    try {
      this.classLoader.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
