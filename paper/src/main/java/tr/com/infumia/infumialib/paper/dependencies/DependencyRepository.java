package tr.com.infumia.infumialib.paper.dependencies;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum DependencyRepository {
  MAVEN_CENTRAL(false, "https://repo1.maven.org/maven2/"),
  JITPACK("https://jitpack.io/"),
  VELOCITY("https://nexus.velocitypowered.com/repository/maven-public/"),
  PAPER("https://papermc.io/repo/repository/maven-public/"),
  OKAERI("https://storehouse.okaeri.eu/repository/maven-public/"),
  CODEMC("https://repo.codemc.org/repository/maven-public/"),
  CODEMC_NMS("https://repo.codemc.org/repository/nms/"),
  SONATYPE_SNAPSHOT("https://oss.sonatype.org/content/repositories/snapshots/"),
  PLACEHOLDER_API("https://repo.extendedclip.com/content/repositories/placeholderapi/"),
  SK89Q("https://maven.enginehub.org/repo/"),
  INTELLECTUAL_SITES("https://mvn.intellectualsites.com/content/repositories/releases/"),
  DMULLOY2("https://repo.dmulloy2.net/repository/public/"),
  EVERYTHING("https://repo.citizensnpcs.co/"),
  GLAREMASTERS("https://repo.glaremasters.me/repository/concuncan/"),
  PL3X("https://repo.pl3x.net/"),
  MINECRAFT("https://libraries.minecraft.net/"),
  SAVAGELABS("https://nexus.savagelabs.net/repository/maven-releases/");

  private final boolean timeout;

  @NotNull
  private final String url;

  DependencyRepository(@NotNull final String url) {
    this(true, url);
  }

  public void download(@NotNull final Dependency dependency, @NotNull final Path file) throws DependencyDownloadException {
    try {
      Files.write(file, this.download(dependency));
    } catch (final IOException e) {
      throw new DependencyDownloadException(e);
    }
  }

  public byte @NotNull [] download(@NotNull final Dependency dependency) throws DependencyDownloadException {
    final var bytes = this.downloadRaw(dependency);
    final var hash = DigestUtils.sha256(bytes);
    if (!dependency.checksumMatches(hash)) {
      throw new DependencyDownloadException(String.format("Downloaded file had an invalid hash. Expected: %s Actual: %s",
        Base64.getEncoder().encodeToString(dependency.getChecksum()), Base64.getEncoder().encodeToString(hash)));
    }
    return bytes;
  }

  public byte @NotNull [] downloadRaw(@NotNull final Dependency dependency) throws DependencyDownloadException {
    try {
      final var connection = this.openConnection(dependency);
      @Cleanup final var in = connection.getInputStream();
      final var bytes = ByteStreams.toByteArray(in);
      if (bytes.length == 0) {
        throw new DependencyDownloadException("Empty stream");
      }
      return bytes;
    } catch (final Exception e) {
      throw new DependencyDownloadException(e);
    }
  }

  @NotNull
  protected URLConnection openConnection(@NotNull final Dependency dependency) throws IOException {
    final var connection = new URL(this.url + dependency.getMavenRepoPath()).openConnection();
    if (this.timeout) {
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(10000);
    }
    return connection;
  }
}
