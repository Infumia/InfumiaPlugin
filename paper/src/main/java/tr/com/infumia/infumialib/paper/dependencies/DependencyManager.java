package tr.com.infumia.infumialib.paper.dependencies;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.file.MoreFiles;
import tr.com.infumia.infumialib.paper.plugin.InfumiaPlugin;

public final class DependencyManager {

  private static final Supplier<Method> ADD_URL_METHOD = Suppliers.memoize(() -> {
    try {
      Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addUrlMethod.setAccessible(true);
      return addUrlMethod;
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  });

  @NotNull
  private final Path cacheDirectory;

  private final EnumMap<Dependency, Path> loaded = new EnumMap<>(Dependency.class);

  private final Map<Set<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();

  @NotNull
  private final InfumiaPlugin plugin;

  @MonotonicNonNull
  private RelocationHandler relocationHandler = null;

  public DependencyManager(@NotNull final InfumiaPlugin plugin) {
    this.plugin = plugin;
    this.cacheDirectory = DependencyManager.setupCacheDirectory(plugin);
  }

  @NotNull
  private static Path setupCacheDirectory(@NotNull final InfumiaPlugin plugin) {
    final var cacheDirectory = plugin.getDataFolder().toPath()
      .resolve("libs");
    try {
      MoreFiles.createDirectoriesIfNotExists(cacheDirectory);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to create libs directory", e);
    }
    return cacheDirectory;
  }

  public void loadDependencies(@NotNull final Set<Dependency> dependencies) {
    final var latch = new CountDownLatch(dependencies.size());
    for (final var dependency : dependencies) {
      CompletableFuture.runAsync(() -> {
        try {
          this.loadDependency(dependency);
        } catch (final Throwable e) {
          this.plugin.getInfumiaLogger().fatal(String.format("Unable to load dependency %s.",
            dependency.name()), e);
        } finally {
          latch.countDown();
        }
      });
    }
    try {
      latch.await();
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @NotNull
  public IsolatedClassLoader obtainClassLoaderWith(@NotNull final Set<Dependency> dependencies) {
    final var set = Set.copyOf(dependencies);
    for (final var dependency : dependencies) {
      if (!this.loaded.containsKey(dependency)) {
        throw new IllegalStateException(String.format("Dependency %s is not loaded.",
          dependency));
      }
    }
    synchronized (this.loaders) {
      var classLoader = this.loaders.get(set);
      if (classLoader != null) {
        return classLoader;
      }
      final var urls = set.stream()
        .map(this.loaded::get)
        .map(file -> {
          try {
            return file.toUri().toURL();
          } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
          }
        })
        .toArray(URL[]::new);
      classLoader = new IsolatedClassLoader(urls);
      this.loaders.put(set, classLoader);
      return classLoader;
    }
  }

  @NotNull
  private Path downloadDependency(@NotNull final Dependency dependency) throws DependencyDownloadException {
    final var file = this.cacheDirectory.resolve(dependency.getFileName(null));
    if (Files.exists(file)) {
      return file;
    }
    DependencyDownloadException lastError = null;
    for (final var repo : DependencyRepository.values()) {
      try {
        repo.download(dependency, file);
        return file;
      } catch (final DependencyDownloadException e) {
        lastError = e;
      }
    }
    throw Objects.requireNonNull(lastError, "last error");
  }

  @NotNull
  private synchronized RelocationHandler getRelocationHandler() {
    if (this.relocationHandler == null) {
      this.relocationHandler = new RelocationHandler(this);
    }
    return this.relocationHandler;
  }

  private void loadDependency(@NotNull final Dependency dependency) throws Exception {
    if (this.loaded.containsKey(dependency)) {
      return;
    }
    final var file = this.remapDependency(dependency, this.downloadDependency(dependency));
    this.loaded.put(dependency, file);
    if (DependencyRegistry.shouldAutoLoad(dependency)) {
      try {
        DependencyManager.ADD_URL_METHOD.get().invoke(this.plugin.getURLClassLoader(), file);
      } catch (final Exception e) {
        throw new RuntimeException(String.format("Unable to load dependency: %s", file), e);
      }
    }
  }

  @NotNull
  private Path remapDependency(@NotNull final Dependency dependency, @NotNull final Path normalFile) throws Exception {
    final var rules = new ArrayList<>(dependency.getRelocations());
    DependencyRegistry.applyRelocationSettings(dependency, rules);
    if (rules.isEmpty()) {
      return normalFile;
    }
    final var remappedFile = this.cacheDirectory.resolve(dependency.getFileName(DependencyRegistry.isGsonRelocated()
      ? "remapped-legacy"
      : "remapped"));
    if (Files.exists(remappedFile)) {
      return remappedFile;
    }
    this.getRelocationHandler().remap(normalFile, remappedFile, rules);
    return remappedFile;
  }
}