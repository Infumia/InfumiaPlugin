package tr.com.infumia.infumialib.paper.dependencies;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Dependency {
  ;

  private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

  private final byte @NotNull [] checksum;

  @NotNull
  @Getter
  private final String mavenRepoPath;

  @NotNull
  private final Collection<Relocation> relocations;

  @NotNull
  private final String version;

  Dependency(@NotNull final String groupId, @NotNull final String artifactId, @NotNull final String version,
             @NotNull final String checksum, @NotNull final Relocation... relocations) {
    this(
      Base64.getDecoder().decode(checksum),
      String.format(Dependency.MAVEN_FORMAT,
        Dependency.rewriteEscaping(groupId).replace(".", "/"),
        Dependency.rewriteEscaping(artifactId),
        version,
        Dependency.rewriteEscaping(artifactId),
        version),
      List.of(relocations),
      version);
  }

  @NotNull
  private static String rewriteEscaping(@NotNull final String s) {
    return s.replace("{}", ".");
  }

  public boolean checksumMatches(final byte @NotNull [] hash) {
    return Arrays.equals(this.checksum, hash);
  }

  public byte @NotNull [] getChecksum() {
    return this.checksum.clone();
  }

  @NotNull
  public String getFileName(@Nullable final String classifier) {
    return this.name().toLowerCase(Locale.ROOT).replace('_', '-') +
      "-" +
      this.version +
      (classifier == null || classifier.isEmpty() ? "" : "-" + classifier) +
      ".jar";
  }
}
