package tr.com.infumia.infumialib.paper.dependencies;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.JsonElement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.platform.Platform;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.plugin.InfumiaPlugin;

@RequiredArgsConstructor
public final class DependencyRegistry {

  private static final ListMultimap<StorageType, Dependency> STORAGE_DEPENDENCIES = ImmutableListMultimap.<StorageType, Dependency>builder()
    .putAll(StorageType.YAML, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_YAML)
    .putAll(StorageType.JSON, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_GSON)
    .putAll(StorageType.HOCON, Dependency.HOCON_CONFIG, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_HOCON)
    .putAll(StorageType.TOML, Dependency.TOML4J, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_TOML)
    .putAll(StorageType.YAML_COMBINED, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_YAML)
    .putAll(StorageType.JSON_COMBINED, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_GSON)
    .putAll(StorageType.HOCON_COMBINED, Dependency.HOCON_CONFIG, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_HOCON)
    .putAll(StorageType.TOML_COMBINED, Dependency.TOML4J, Dependency.CONFIGURATE_CORE, Dependency.CONFIGURATE_TOML)
    .putAll(StorageType.MONGODB, Dependency.MONGODB_DRIVER)
    .putAll(StorageType.MARIADB, Dependency.MARIADB_DRIVER, Dependency.SLF4J_API, Dependency.SLF4J_SIMPLE, Dependency.HIKARI)
    .putAll(StorageType.MYSQL, Dependency.MYSQL_DRIVER, Dependency.SLF4J_API, Dependency.SLF4J_SIMPLE, Dependency.HIKARI)
    .putAll(StorageType.POSTGRESQL, Dependency.POSTGRESQL_DRIVER, Dependency.SLF4J_API, Dependency.SLF4J_SIMPLE, Dependency.HIKARI)
    .putAll(StorageType.SQLITE, Dependency.SQLITE_DRIVER)
    .putAll(StorageType.H2, Dependency.H2_DRIVER)
    .build();

  @NotNull
  private final InfumiaPlugin plugin;

  @SuppressWarnings("ConstantConditions")
  public static boolean isGsonRelocated() {
    return JsonElement.class.getName().startsWith("me.lucko");
  }

  private static boolean classExists(final String className) {
    try {
      Class.forName(className);
      return true;
    } catch (final ClassNotFoundException e) {
      return false;
    }
  }

  private static boolean slf4jPresent() {
    return DependencyRegistry.classExists("org.slf4j.Logger") && DependencyRegistry.classExists("org.slf4j.LoggerFactory");
  }

  public void applyRelocationSettings(final Dependency dependency, final List<Relocation> relocations) {
    final var type = this.plugin.getBootstrap().getType();
    if (!RelocationHandler.DEPENDENCIES.contains(dependency) && DependencyRegistry.isGsonRelocated()) {
      relocations.add(Relocation.of("guava", "com{}google{}common"));
      relocations.add(Relocation.of("gson", "com{}google{}gson"));
    }
    if (dependency == Dependency.CONFIGURATE_YAML && type == Platform.Type.VELOCITY) {
      relocations.add(Relocation.of("yaml", "org{}yaml{}snakeyaml"));
    }
  }

  @NotNull
  public Set<Dependency> resolveStorageDependencies(@NotNull final Set<StorageType> storageTypes) {
    final Set<Dependency> dependencies = new LinkedHashSet<>();
    for (final StorageType storageType : storageTypes) {
      dependencies.addAll(DependencyRegistry.STORAGE_DEPENDENCIES.get(storageType));
    }
    if (this.plugin.getConfiguration().get(ConfigKeys.REDIS_ENABLED)) {
      dependencies.add(Dependency.COMMONS_POOL_2);
      dependencies.add(Dependency.JEDIS);
      dependencies.add(Dependency.SLF4J_API);
      dependencies.add(Dependency.SLF4J_SIMPLE);
    }
    if (this.plugin.getConfiguration().get(ConfigKeys.RABBITMQ_ENABLED)) {
      dependencies.add(Dependency.RABBITMQ);
    }
    if ((dependencies.contains(Dependency.SLF4J_API) || dependencies.contains(Dependency.SLF4J_SIMPLE)) && DependencyRegistry.slf4jPresent()) {
      dependencies.remove(Dependency.SLF4J_API);
      dependencies.remove(Dependency.SLF4J_SIMPLE);
    }
    return dependencies;
  }

  public boolean shouldAutoLoad(final Dependency dependency) {
    switch (dependency) {
      case ASM:
      case ASM_COMMONS:
      case JAR_RELOCATOR:
      case H2_DRIVER:
      case SQLITE_DRIVER:
        return false;
      default:
        return true;
    }
  }
}
