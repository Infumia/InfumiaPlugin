package tr.com.infumia.infumialib.dependencies;

import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.file.MoreFiles;
import tr.com.infumia.infumialib.plugin.InfumiaPlugin;

public final class DependencyManager {

  @NotNull
  private final Path cacheDirectory;

  private final EnumMap<Dependency, Path> loaded = new EnumMap<>(Dependency.class);

  private final Map<ImmutableSet<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();

  @NotNull
  private final InfumiaPlugin plugin;

  @NotNull
  private final DependencyRegistry registry;

  @MonotonicNonNull
  private RelocationHandler relocationHandler = null;

  public DependencyManager(@NotNull final InfumiaPlugin plugin) {
    this.plugin = plugin;
    this.registry = new DependencyRegistry(plugin);
    this.cacheDirectory = DependencyManager.setupCacheDirectory(plugin);
  }

  @NotNull
  private static Path setupCacheDirectory(@NotNull final InfumiaPlugin plugin) {
    final var cacheDirectory = plugin.getDataDirectory().resolve("libs");
    try {
      MoreFiles.createDirectoriesIfNotExists(cacheDirectory);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to create libs directory", e);
    }
    return cacheDirectory;
  }

  public void loadDependencies(@NotNull final Collection<Dependency> dependencies) {
    final var latch = new CountDownLatch(dependencies.size());
    for (final var dependency : dependencies) {
      this.plugin.getScheduler().async().execute(() -> {
        try {
          this.loadDependency(dependency);
        } catch (final Throwable e) {
          this.plugin.getLogger().severe("Unable to load dependency " + dependency.name() + ".", e);
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

  public void loadStorageDependencies(final Set<StorageType> storageTypes) {
    this.loadDependencies(this.registry.resolveStorageDependencies(storageTypes));
  }

  public IsolatedClassLoader obtainClassLoaderWith(final Set<Dependency> dependencies) {
    final ImmutableSet<Dependency> set = ImmutableSet.copyOf(dependencies);
    for (final Dependency dependency : dependencies) {
      if (!this.loaded.containsKey(dependency)) {
        throw new IllegalStateException("Dependency " + dependency + " is not loaded.");
      }
    }
    synchronized (this.loaders) {
      IsolatedClassLoader classLoader = this.loaders.get(set);
      if (classLoader != null) {
        return classLoader;
      }
      final URL[] urls = set.stream()
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

  private Path downloadDependency(final Dependency dependency) throws DependencyDownloadException {
    final Path file = this.cacheDirectory.resolve(dependency.getFileName(null));
    // if the file already exists, don't attempt to re-download it.
    if (Files.exists(file)) {
      return file;
    }
    DependencyDownloadException lastError = null;
    // attempt to download the dependency from each repo in order.
    for (final DependencyRepository repo : DependencyRepository.values()) {
      try {
        repo.download(dependency, file);
        return file;
      } catch (final DependencyDownloadException e) {
        lastError = e;
      }
    }
    throw Objects.requireNonNull(lastError);
  }

  private synchronized RelocationHandler getRelocationHandler() {
    if (this.relocationHandler == null) {
      this.relocationHandler = new RelocationHandler(this);
    }
    return this.relocationHandler;
  }

  private void loadDependency(final Dependency dependency) throws Exception {
    if (this.loaded.containsKey(dependency)) {
      return;
    }
    final Path file = this.remapDependency(dependency, this.downloadDependency(dependency));
    this.loaded.put(dependency, file);
    if (this.registry.shouldAutoLoad(dependency)) {
      this.plugin.getBootstrap().getClassPathAppender().addJarToClasspath(file);
    }
  }

  private Path remapDependency(final Dependency dependency, final Path normalFile) throws Exception {
    final List<Relocation> rules = new ArrayList<>(dependency.getRelocations());
    this.registry.applyRelocationSettings(dependency, rules);
    if (rules.isEmpty()) {
      return normalFile;
    }
    final Path remappedFile = this.cacheDirectory.resolve(dependency.getFileName(DependencyRegistry.isGsonRelocated() ? "remapped-legacy" : "remapped"));
    // if the remapped source exists already, just use that.
    if (Files.exists(remappedFile)) {
      return remappedFile;
    }
    this.getRelocationHandler().remap(normalFile, remappedFile, rules);
    return remappedFile;
  }
}
