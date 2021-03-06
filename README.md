# Infumia Library

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/Infumia/InfumiaLib/workflows/build/badge.svg)
[![Release](https://jitpack.io/v/Infumia/InfumiaLib.svg)](https://jitpack.io/#Infumia/InfumiaLib)

## How to Use (Developers)

### Plugin.yml (Paper)

```yml
depend:
  - InfumiaLibraryPlugin
```

### velocity-plugin.json (Velocity)

```json
{
  "dependencies": [
    {
      "id": "infumialibraryplugin",
      "optional": false
    }
  ]
}
```

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
    <groupId>com.github.Infumia.InfumiaLib</groupId>
    <artifactId>InfumiaPaper</artifactId>
    <version>${version}</version>
    <scope>provided</scope>
  </dependency>
  <dependency>
    <groupId>com.github.Infumia.InfumiaLib</groupId>
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
  compileOnly("com.github.Infumia.InfumiaLib:InfumiaPaper:${version}")
  compileOnly("com.github.Infumia.InfumiaLib:InfumiaVelocity:${version}")
}
```

## How to Use (Server Owners)

Download the latest Jar files here https://github.com/Infumia/InfumiaLib/releases/latest

Put the Jar file into your mods/plugins directory.
