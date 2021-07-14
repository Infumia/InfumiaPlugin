package tr.com.infumia.infumialib.paper;

import tr.com.infumia.infumialib.paper.dependencies.MavenLibraries;
import tr.com.infumia.infumialib.paper.dependencies.MavenLibrary;
import tr.com.infumia.infumialib.paper.dependencies.Repository;

@MavenLibraries({
  @MavenLibrary(
    groupId = "org.jetbrains.kotlin",
    artifactId = "kotlin-stdlib",
    version = "1.5.21"
  ),
  @MavenLibrary(
    groupId = "io.lettuce",
    artifactId = "lettuce-core",
    version = "6.1.3.RELEASE"
  ),
  @MavenLibrary(
    groupId = "com.zaxxer",
    artifactId = "HikariCP",
    version = "4.0.3"
  ),
  @MavenLibrary(
    groupId = "org.kohsuke",
    artifactId = "github-api",
    version = "1.131"
  ),
  @MavenLibrary(
    groupId = "org.mongodb",
    artifactId = "mongodb-driver-sync",
    version = "4.2.3"
  ),
  @MavenLibrary(
    groupId = "org.mongodb",
    artifactId = "mongo-java-driver",
    version = "3.12.8"
  ),
  @MavenLibrary(
    groupId = "com.flowpowered",
    artifactId = "flow-nbt",
    version = "2.0.0",
    repo = @Repository(
      url = "https://repo.glaremasters.me/repository/concuncan/"
    )
  ),
  @MavenLibrary(
    groupId = "commons-io",
    artifactId = "commons-io",
    version = "2.11.0"
  ),
  @MavenLibrary(
    groupId = "com.github.luben",
    artifactId = "zstd-jni",
    version = "1.5.0-2"
  ),
  @MavenLibrary(
    groupId = "com.google.code.gson",
    artifactId = "gson",
    version = "2.8.7"
  ),
  @MavenLibrary(
    groupId = "org.javassist",
    artifactId = "javassist",
    version = "3.28.0-GA"
  ),
  @MavenLibrary(
    groupId = "org.yaml",
    artifactId = "snakeyaml",
    version = "1.29"
  ),
  @MavenLibrary(
    groupId = "com.github.tomas-langer",
    artifactId = "chalk",
    version = "1.0.2"
  ),
  @MavenLibrary(
    groupId = "com.google.protobuf",
    artifactId = "protobuf-java",
    version = "3.17.3"
  ),
  @MavenLibrary(
    groupId = "org.apache.httpcomponents",
    artifactId = "httpclient",
    version = "4.5.13"
  ),
  @MavenLibrary(
    groupId = "org.apache.httpcomponents",
    artifactId = "httpcore",
    version = "4.4.14"
  ),
  @MavenLibrary(
    groupId = "org.apache.httpcomponents",
    artifactId = "httpclient-cache",
    version = "4.5.13"
  ),
  @MavenLibrary(
    groupId = "org.apache.httpcomponents",
    artifactId = "httpmime",
    version = "4.5.13"
  ),
  @MavenLibrary(
    groupId = "org.cactoos",
    artifactId = "cactoos",
    version = "0.50"
  ),
  @MavenLibrary(
    groupId = "com.github.ben-manes.caffeine",
    artifactId = "guava",
    version = "3.0.3"
  ),
  @MavenLibrary(
    groupId = "com.google.guava",
    artifactId = "guava",
    version = "30.1.1-jre"
  ),
  @MavenLibrary(
    groupId = "com.github.Querz",
    artifactId = "NBT",
    version = "6.1",
    repo = @Repository(
      url = "https://jitpack.io/"
    )
  ),
  @MavenLibrary(
    groupId = "org.slf4j",
    artifactId = "slf4j-log4j12",
    version = "1.7.31"
  ),
  @MavenLibrary(
    groupId = "org.apache.logging.log4j",
    artifactId = "log4j-core",
    version = "2.14.1"
  ),
  @MavenLibrary(
    groupId = "org.apache.logging.log4j",
    artifactId = "log4j-slf4j-impl",
    version = "2.14.1"
  ),
  @MavenLibrary(
    groupId = "eu.okaeri",
    artifactId = "okaeri-hjson",
    version = "4.0.0",
    repo = @Repository(
      url = "https://storehouse.okaeri.eu/repository/maven-public/"
    )
  )
})
final class Libs {

}
