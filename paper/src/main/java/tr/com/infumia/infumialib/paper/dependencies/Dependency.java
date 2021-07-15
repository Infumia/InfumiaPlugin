package tr.com.infumia.infumialib.paper.dependencies;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
    "1RsiF3BiVztjlfTA+svDCuoDSGFuSpTZYHvUK8yBx8I="
  ),

  ADVENTURE(
    "me{}lucko",
    "adventure-api",
    "4.7.1",
    "Kp0YN1he11ykhB9vSnUVHRq4/pTuOon+XuklDsSHsQw=",
    Relocation.of("adventure", "net{}kyori{}adventure")
  ),
  ADVENTURE_PLATFORM(
    "me{}lucko",
    "adventure-platform-api",
    "4.7.0",
    "CyYWxQuoN4vHte/5HuFDZEqrBGMi9Vv7uH3toJW1Z5Y=",
    Relocation.of("adventure", "net{}kyori{}adventure")
  ),
  ADVENTURE_PLATFORM_BUKKIT(
    "me{}lucko",
    "adventure-platform-bukkit",
    "4.7.0",
    "9H5MHWbJlZAHZR5zqqjW3QBU0GhJ1l9KgLGE4mKHDe8=",
    Relocation.of("adventure", "net{}kyori{}adventure")
  ),
  ADVENTURE_PLATFORM_BUNGEECORD(
    "me{}lucko",
    "adventure-platform-bungeecord",
    "4.7.0",
    "puM9PtfRzhp1Gq+ZxRAhVZqDblN1P2bb8FUnhLkMVsA=",
    Relocation.of("adventure", "net{}kyori{}adventure")
  ),
  EVENT(
    "net{}kyori",
    "event-api",
    "3.0.0",
    "yjvdTdAyktl3iFEQFLHC3qYwwt7/DbCd7Zc8Q4SlIag=",
    Relocation.of("eventbus", "net{}kyori{}event")
  ),
  CAFFEINE(
    "com{}github{}ben-manes{}caffeine",
    "caffeine",
    "2.9.0",
    "VFMotEO3XLbTHfRKfL3m36GlN72E/dzRFH9B5BJiX2o=",
    Relocation.of("caffeine", "com{}github{}benmanes{}caffeine")
  ),
  OKIO(
    "com{}squareup{}" + RelocationHelper.OKIO_STRING,
    RelocationHelper.OKIO_STRING,
    "1.17.5",
    "Gaf/SNhtPPRJf38lD78pX0MME6Uo3Vt7ID+CGAK4hq0=",
    Relocation.of(RelocationHelper.OKIO_STRING, RelocationHelper.OKIO_STRING)
  ),
  OKHTTP(
    "com{}squareup{}" + RelocationHelper.OKHTTP3_STRING,
    "okhttp",
    "3.14.9",
    "JXD6tVUVy/iB16TO70n8UVSQvAJwV+Zmd2ooMkZa7KA=",
    Relocation.of(RelocationHelper.OKHTTP3_STRING, RelocationHelper.OKHTTP3_STRING),
    Relocation.of(RelocationHelper.OKIO_STRING, RelocationHelper.OKIO_STRING)
  ),
  BYTEBUDDY(
    "net{}bytebuddy",
    "byte-buddy",
    "1.10.22",
    "+TGtxDkxd6+lJExHJXqDlV4n/gR8QJN4xu2gkPsHSoQ=",
    Relocation.of("bytebuddy", "net{}bytebuddy")
  ),
  COMMODORE(
    "me{}lucko",
    "commodore",
    "1.10",
    "DJi8ZLaSwhoU49UBrIFzVBksaVAj8bthtVCnhuBPRz4=",
    Relocation.of("commodore", "me{}lucko{}commodore")
  ),
  COMMODORE_FILE(
    "me{}lucko",
    "commodore-file",
    "1.0",
    "V9++dyp9RbzD4DLO2R9upF8Z8v5SWasyX8ocqYRAMow=",
    Relocation.of("commodore", "me{}lucko{}commodore")
  ),
  MARIADB_DRIVER(
    "org{}mariadb{}jdbc",
    "mariadb-java-client",
    "2.7.2",
    "o/Z3bfCELPZefxWFFQEtUwfalJ9mBCKC4e5EdN0Z9Eg=",
    Relocation.of("mariadb", "org{}mariadb{}jdbc")
  ),
  MYSQL_DRIVER(
    "mysql",
    "mysql-connector-java",
    "8.0.23",
    "/31bQCr9OcEnh0cVBaM6MEEDsjjsG3pE6JNtMynadTU=",
    Relocation.of("mysql", "com{}mysql")
  ),
  POSTGRESQL_DRIVER(
    "org{}postgresql",
    "postgresql",
    "42.2.19",
    "IydH+gkk2Iom36QrgSi2+hFAgC2AQSWJFZboyl8pEyI=",
    Relocation.of("postgresql", "org{}postgresql")
  ),
  H2_DRIVER(
    "com.h2database",
    "h2",
    // seems to be a compat bug in 1.4.200 with older dbs
    // see: https://github.com/h2database/h2database/issues/2078
    "1.4.199",
    "MSWhZ0O8a0z7thq7p4MgPx+2gjCqD9yXiY95b5ml1C4="
    // we don't apply relocations to h2 - it gets loaded via
    // an isolated classloader
  ),
  SQLITE_DRIVER(
    "org.xerial",
    "sqlite-jdbc",
    "3.28.0",
    "k3hOVtv1RiXgbJks+D9w6cG93Vxq0dPwEwjIex2WG2A="
    // we don't apply relocations to sqlite - it gets loaded via
    // an isolated classloader
  ),
  HIKARI(
    "com{}zaxxer",
    "HikariCP",
    "4.0.3",
    "fAJK7/HBBjV210RTUT+d5kR9jmJNF/jifzCi6XaIxsk=",
    Relocation.of("hikari", "com{}zaxxer{}hikari")
  ),
  SLF4J_SIMPLE(
    "org.slf4j",
    "slf4j-simple",
    "1.7.30",
    "i5J5y/9rn4hZTvrjzwIDm2mVAw7sAj7UOSh0jEFnD+4="
  ),
  SLF4J_API(
    "org.slf4j",
    "slf4j-api",
    "1.7.30",
    "zboHlk0btAoHYUhcax6ML4/Z6x0ZxTkorA1/lRAQXFc="
  ),
  MONGODB_DRIVER(
    "org.mongodb",
    "mongo-java-driver",
    "3.12.8",
    "92uqr4qaL3dbw5wrb8sQWQqFxpzr/Y/DhForeyg3taI=",
    Relocation.of("mongodb", "com{}mongodb"),
    Relocation.of("bson", "org{}bson")
  ),
  JEDIS(
    "redis.clients",
    "jedis",
    "3.5.2",
    "jX3340YaYjHFQN2sA+GCo33LB4FuIYKgQUPUv2MK/Xo=",
    Relocation.of("jedis", "redis{}clients{}jedis"),
    Relocation.of("commonspool2", "org{}apache{}commons{}pool2")
  ),
  RABBITMQ(
    "com{}rabbitmq",
    "amqp-client",
    "5.12.0",
    "CxliwVWAnPKi5BwxCu1S1SGzx5fbhTk5JCKdBS27P2c=",
    Relocation.of("rabbitmq", "com{}rabbitmq")
  ),
  COMMONS_POOL_2(
    "org.apache.commons",
    "commons-pool2",
    "2.9.0",
    "vJGbQmv6+zHsxF1mUqnxN0YkZdhJ+zhz142Qw/jTWwE=",
    Relocation.of("commonspool2", "org{}apache{}commons{}pool2")
  ),
  CONFIGURATE_CORE(
    "org{}spongepowered",
    "configurate-core",
    "3.7.2",
    "XF2LzWLkSV0wyQRDt33I+gDlf3t2WzxH1h8JCZZgPp4=",
    Relocation.of("configurate", "ninja{}leaping{}configurate")
  ),
  CONFIGURATE_GSON(
    "org{}spongepowered",
    "configurate-gson",
    "3.7.2",
    "9S/mp3Ig9De7NNd6+2kX+L4R90bHnAosSNVbFjrl7sM=",
    Relocation.of("configurate", "ninja{}leaping{}configurate")
  ),
  CONFIGURATE_YAML(
    "org{}spongepowered",
    "configurate-yaml",
    "3.7.2",
    "OBfYn4nSMGZfVf2DoZhZq+G9TF1mODX/C5OOz/mkPmc=",
    Relocation.of("configurate", "ninja{}leaping{}configurate")
  ),
  SNAKEYAML(
    "org.yaml",
    "snakeyaml",
    "1.28",
    "NURqFCFDXUXkxqwN47U3hSfVzCRGwHGD4kRHcwzh//o=",
    Relocation.of("yaml", "org{}yaml{}snakeyaml")
  ),
  CONFIGURATE_HOCON(
    "org{}spongepowered",
    "configurate-hocon",
    "3.7.2",
    "GOORZlK1FKLzdIm7dKyyXtBdvk7Z89HARAd2H6NiWSY=",
    Relocation.of("configurate", "ninja{}leaping{}configurate"),
    Relocation.of("hocon", "com{}typesafe{}config")
  ),
  HOCON_CONFIG(
    "com{}typesafe",
    "config",
    "1.4.1",
    "TAqn4iPHXIhAxB/Bg9TNMRgUCh7lA+PgjOZu0nlMlI8=",
    Relocation.of("hocon", "com{}typesafe{}config")
  ),
  CONFIGURATE_TOML(
    "me{}lucko{}configurate",
    "configurate-toml",
    "3.7",
    "EmyLOfsiR74QGhkktqhexMN8tC3kg1cM1UhM5MCmxuE=",
    Relocation.of("configurate", "ninja{}leaping{}configurate"),
    Relocation.of("toml4j", "com{}moandjiezana{}toml")
  ),
  TOML4J(
    "com{}moandjiezana{}toml",
    "toml4j",
    "0.7.2",
    "9UdeY+fonl22IiNImux6Vr0wNUN3IHehfCy1TBnKOiA=",
    Relocation.of("toml4j", "com{}moandjiezana{}toml")
  );

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

  @NotNull
  public Collection<Relocation> getRelocations() {
    return Collections.unmodifiableCollection(this.relocations);
  }
}
