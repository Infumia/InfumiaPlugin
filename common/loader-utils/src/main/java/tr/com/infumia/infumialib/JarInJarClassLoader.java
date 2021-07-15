package tr.com.infumia.infumialib;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.jetbrains.annotations.NotNull;

public final class JarInJarClassLoader extends URLClassLoader {

  static {
    ClassLoader.registerAsParallelCapable();
  }

  public JarInJarClassLoader(@NotNull final ClassLoader loaderClassLoader, @NotNull final String jarResourcePath)
    throws LoadingException {
    super(new URL[]{JarInJarClassLoader.extractJar(loaderClassLoader, jarResourcePath)}, loaderClassLoader);
  }

  @NotNull
  private static URL extractJar(@NotNull final ClassLoader loaderClassLoader, @NotNull final String jarResourcePath)
    throws LoadingException {
    final URL jarInJar = loaderClassLoader.getResource(jarResourcePath);
    if (jarInJar == null) {
      throw new LoadingException("Could not locate jar-in-jar");
    }
    final Path path;
    try {
      path = Files.createTempFile("infumialibraryplugin-jarinjar", ".jar.tmp");
    } catch (final IOException e) {
      throw new LoadingException("Unable to create a temporary file", e);
    }
    path.toFile().deleteOnExit();
    try (final InputStream in = jarInJar.openStream()) {
      Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
    } catch (final IOException e) {
      throw new LoadingException("Unable to copy jar-in-jar to temporary path", e);
    }
    try {
      return path.toUri().toURL();
    } catch (final MalformedURLException e) {
      throw new LoadingException("Unable to get URL from path", e);
    }
  }

  public void addJarToClasspath(@NotNull final URL url) {
    this.addURL(url);
  }

  public void deleteJarResource() {
    final var urls = this.getURLs();
    if (urls.length == 0) {
      return;
    }
    try {
      Files.deleteIfExists(Paths.get(urls[0].toURI()));
    } catch (final Exception ignored) {
    }
  }

  @NotNull
  public <T> LoaderBootstrap instantiatePlugin(@NotNull final String bootstrapClass,
                                               @NotNull final Class<T> loaderPluginType,
                                               @NotNull final T loaderPlugin) throws LoadingException {
    final Class<? extends LoaderBootstrap> plugin;
    try {
      plugin = this.loadClass(bootstrapClass).asSubclass(LoaderBootstrap.class);
    } catch (final ReflectiveOperationException e) {
      throw new LoadingException("Unable to load bootstrap class", e);
    }
    final Constructor<? extends LoaderBootstrap> constructor;
    try {
      constructor = plugin.getConstructor(loaderPluginType);
    } catch (final ReflectiveOperationException e) {
      throw new LoadingException("Unable to get bootstrap constructor", e);
    }
    try {
      return constructor.newInstance(loaderPlugin);
    } catch (final ReflectiveOperationException e) {
      throw new LoadingException("Unable to create bootstrap plugin instance", e);
    }
  }
}