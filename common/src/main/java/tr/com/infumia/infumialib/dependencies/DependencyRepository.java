package tr.com.infumia.infumialib.dependencies;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import lombok.Cleanup;
import org.jetbrains.annotations.NotNull;

public enum DependencyRepository {
  LUCK_MIRROR("https://nexus.lucko.me/repository/maven-central/") {
    @Override
    protected URLConnection openConnection(final Dependency dependency) throws IOException {
      final URLConnection connection = super.openConnection(dependency);
      connection.setRequestProperty("User-Agent", "luckperms");
      connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5));
      connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
      return connection;
    }
  },

  MAVEN_CENTRAL("https://repo1.maven.org/maven2/");

  private final String url;

  DependencyRepository(final String url) {
    this.url = url;
  }

  public byte[] download(@NotNull final Dependency dependency) throws DependencyDownloadException {
    final var bytes = this.downloadRaw(dependency);
    final var hash = Dependency.createDigest().digest(bytes);
    if (!dependency.checksumMatches(hash)) {
      throw new DependencyDownloadException("Downloaded file had an invalid hash. " +
        "Expected: " + Base64.getEncoder().encodeToString(dependency.getChecksum()) + " " +
        "Actual: " + Base64.getEncoder().encodeToString(hash));
    }
    return bytes;
  }

  public void download(@NotNull final Dependency dependency, @NotNull final Path file) throws DependencyDownloadException {
    try {
      Files.write(file, this.download(dependency));
    } catch (final IOException e) {
      throw new DependencyDownloadException(e);
    }
  }

  public byte[] downloadRaw(final Dependency dependency) throws DependencyDownloadException {
    try {
      final var connection = this.openConnection(dependency);
      @Cleanup final var in = connection.getInputStream();
      final byte[] bytes = ByteStreams.toByteArray(in);
      if (bytes.length == 0) {
        throw new DependencyDownloadException("Empty stream");
      }
      return bytes;
    } catch (final Exception e) {
      throw new DependencyDownloadException(e);
    }
  }

  protected URLConnection openConnection(final Dependency dependency) throws IOException {
    return new URL(this.url + dependency.getMavenRepoPath())
      .openConnection();
  }
}
