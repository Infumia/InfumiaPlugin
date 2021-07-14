package tr.com.infumia.infumialib.dependencies;

import com.google.common.collect.ImmutableList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum Dependency {

  ASM(
    "org.ow2.asm",
    "asm",
    "9.1",
    "zaTeRV+rSP8Ly3xItGOUR9TehZp6/DCglKmG8JNr66I="
  ),
  ASM_COMMONS(
    "org.ow2.asm",
    "asm-commons",
    "9.1",
    "r8sm3B/BLAxKma2mcJCN2C4Y38SIyvXuklRplrRwwAw="
  ),
  JAR_RELOCATOR(
    "me.lucko",
    "jar-relocator",
    "1.4",
    "1RsiF3BiVztjlfTA+svDCuoDSGFuSpTZYHvUK8yBx8I=");

  private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

  @Getter
  private final byte[] checksum;

  @Getter
  @NotNull
  private final String mavenRepoPath;

  @Getter
  @NotNull
  private final List<Relocation> relocations;

  @NotNull
  private final String version;

  Dependency(@NotNull final String groupId, @NotNull final String artifactId, @NotNull final String version,
             @NotNull final String checksum) {
    this(groupId, artifactId, version, checksum, new Relocation[0]);
  }

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
      ImmutableList.copyOf(relocations),
      version);
  }

  @NotNull
  public static MessageDigest createDigest() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (final NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private static String rewriteEscaping(@NotNull final String text) {
    return text.replace("{}", ".");
  }

  public boolean checksumMatches(final byte @NotNull [] hash) {
    return Arrays.equals(this.checksum, hash);
  }

  @NotNull
  public String getFileName(@NotNull final String classifier) {
    final var name = this.name().toLowerCase().replace('_', '-');
    final var extra = classifier.isEmpty()
      ? ""
      : "-" + classifier;
    return name + "-" + this.version + extra + ".jar";
  }
}
