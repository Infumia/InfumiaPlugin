package tr.com.infumia.infumialib.paper.dependencies;

import com.google.common.base.Suppliers;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.plugin.InfumiaPlugin;
import tr.com.infumia.infumialib.reflection.clazz.ClassOf;

@Log4j2
@RequiredArgsConstructor
public final class LibraryLoader {

  private static final Supplier<Method> ADD_URL_METHOD = Suppliers.memoize(() -> {
    try {
      var addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addUrlMethod.setAccessible(true);
      return addUrlMethod;
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  });

  @NotNull
  private final InfumiaPlugin plugin;

  public void load(@NotNull final String groupId, @NotNull final String artifactId, @NotNull final String version,
                   @NotNull final String relocationPattern, @NotNull final String relocatedPattern) {
    this.load(groupId, artifactId, version, "https://repo1.maven.org/maven2", relocationPattern, relocatedPattern);
  }

  public void load(@NotNull final String groupId, @NotNull final String artifactId, @NotNull final String version,
                   @NotNull final String repoUrl,
                   @NotNull final String relocationPattern, @NotNull final String relocatedPattern) {
    this.load(Dependency.builder()
      .artifactId(artifactId)
      .groupId(groupId)
      .version(version)
      .repoUrl(repoUrl)
      .relocationPattern(relocationPattern)
      .relocatedPattern(relocatedPattern)
      .build());
  }

  public void load(@NotNull final Dependency dependency) {
    LibraryLoader.log.debug("Loading dependency {}:{}:{} from {}",
      dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getRepoUrl());
    final var name = String.format("%s-%s",
      dependency.getArtifactId(), dependency.getVersion());
    final var saveLocation = new File(this.getLibFolder(), String.format("%s.jar", name));
    if (!saveLocation.exists()) {
      try {
        LibraryLoader.log.debug("Dependency '{}' is not already in the libraries folder. Attempting to download...", name);
        @Cleanup final var is = dependency.getUrl().openStream();
        Files.copy(is, saveLocation.toPath());
      } catch (final Exception e) {
        e.printStackTrace();
      }
      LibraryLoader.log.debug("Dependency '{}' successfully downloaded.", name);
    }
    if (!saveLocation.exists()) {
      throw new RuntimeException("Unable to download dependency: " + dependency);
    }
    try {
      LibraryLoader.ADD_URL_METHOD.get().invoke(
        this.plugin.getClass().getClassLoader(),
        saveLocation.toURI().toURL());
    } catch (final Exception e) {
      throw new RuntimeException(String.format("Unable to load dependency: %s", saveLocation), e);
    }
    LibraryLoader.log.debug("Loaded dependency '{}' successfully.", name);
  }

  public void loadAll(@NotNull final Object object) {
    this.loadAll(object.getClass());
  }

  public void loadAll(@NotNull final Class<?> clazz) {
    new ClassOf<>(clazz).getAnnotation(MavenLibraries.class, libs -> {
      final var libraries = libs.value();
      final var latch = new CountDownLatch(libraries.length);
      CompletableFuture.runAsync(() -> {
        for (final var lib : libraries) {
          try {
            this.load(
              lib.groupId(), lib.artifactId(), lib.version(),
              lib.repo(),
              lib.relocationPattern(), lib.relocatedPattern());
          } catch (final Throwable e) {
            this.plugin.getInfumiaLogger().fatal(String.format("Unable to load dependency %s.",
              lib.artifactId()), e);
          } finally {
            latch.countDown();
          }
        }
      });
      try {
        latch.await();
      } catch (final InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
  }

  @NotNull
  private File getLibFolder() {
    final var libs = new File(this.plugin.getDataFolder(), "libs");
    libs.mkdirs();
    return libs;
  }
}