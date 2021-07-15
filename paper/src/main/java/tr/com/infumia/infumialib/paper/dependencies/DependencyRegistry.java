package tr.com.infumia.infumialib.paper.dependencies;

import com.google.gson.JsonElement;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
final class DependencyRegistry {

  void applyRelocationSettings(@NotNull final Dependency dependency,
                               @NotNull final List<Relocation> relocations) {
    if (!RelocationHandler.DEPENDENCIES.contains(dependency) && DependencyRegistry.isGsonRelocated()) {
      relocations.add(Relocation.of("guava", "com{}google{}common"));
      relocations.add(Relocation.of("gson", "com{}google{}gson"));
    }
    if (dependency == Dependency.CONFIGURATE_YAML) {
      relocations.add(Relocation.of("yaml", "org{}yaml{}snakeyaml"));
    }
  }

  boolean shouldAutoLoad(@NotNull final Dependency dependency) {
    switch (dependency) {
//      case H2_DRIVER:
//      case SQLITE_DRIVER:
      case ASM:
      case ASM_COMMONS:
      case JAR_RELOCATOR:
        return false;
      default:
        return true;
    }
  }

  private static boolean classExists(final String className) {
    try {
      Class.forName(className);
      return true;
    } catch (final ClassNotFoundException e) {
      return false;
    }
  }

  @SuppressWarnings("ConstantConditions")
  boolean isGsonRelocated() {
    return JsonElement.class.getName().startsWith("tr.com.infumia");
  }

  private boolean slf4jPresent() {
    return DependencyRegistry.classExists("org.slf4j.Logger") &&
      DependencyRegistry.classExists("org.slf4j.LoggerFactory");
  }
}
