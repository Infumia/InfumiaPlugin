# Infumia Library

## How to Use (Developers)

### Maven

```xml
<repositories>
  <repository>
    <id>jitpack</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>
```

```xml
<dependencies>
  <dependency>
    <groupId>com.github.infumia.infumialib</groupId>
    <artifactId>InfumiaPaper</artifactId>
    <version>${version}</version>
    <scope>provided</scope>
  </dependency>
  <dependency>
    <groupId>com.github.infumia.infumialib</groupId>
    <artifactId>InfumiaVelocity</artifactId>
    <version>${version}</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### Gradle

```groovy
repositories {
  maven {
    url "https://jitpack.io"
  }
}
```

```groovy
dependencies {
    compileOnly("com.github.infumia.infumialib:InfumiaPaper:${version}")
    compileOnly("com.github.infumia.infumialib:InfumiaVelocity:${version}")
}
```

## How to Use (Server Owners)

Download the latest Jar files here https://github.com/Infumia/InfumiaLib/releases/latest

Put the Jar file into your mods/plugins directory.
