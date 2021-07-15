package tr.com.infumia.infumialib.paper.dependencies;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class RelocationHandler {

  static final Set<Dependency> DEPENDENCIES = EnumSet.of(
    Dependency.ASM,
    Dependency.ASM_COMMONS,
    Dependency.JAR_RELOCATOR);

  private static final String JAR_RELOCATOR_CLASS = "me.lucko.jarrelocator.JarRelocator";

  private static final String JAR_RELOCATOR_RUN_METHOD = "run";

  private final Constructor<?> jarRelocatorConstructor;

  private final Method jarRelocatorRunMethod;

  RelocationHandler(@NotNull final DependencyManager dependencyManager) {
    try {
      dependencyManager.loadDependencies(RelocationHandler.DEPENDENCIES);
      final var jarRelocatorClass = dependencyManager.obtainClassLoaderWith(RelocationHandler.DEPENDENCIES)
        .loadClass(RelocationHandler.JAR_RELOCATOR_CLASS);
      this.jarRelocatorConstructor = jarRelocatorClass.getDeclaredConstructor(File.class, File.class, Map.class);
      this.jarRelocatorConstructor.setAccessible(true);
      this.jarRelocatorRunMethod = jarRelocatorClass.getDeclaredMethod(RelocationHandler.JAR_RELOCATOR_RUN_METHOD);
      this.jarRelocatorRunMethod.setAccessible(true);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  void remap(@NotNull final Path input, @NotNull final Path output,
             @NotNull final Collection<Relocation> relocations) throws Exception {
    final var mappings = new HashMap<String, String>();
    for (final var relocation : relocations) {
      mappings.put(relocation.getPattern(), relocation.getRelocatedPattern());
    }
    this.jarRelocatorRunMethod.invoke(
      this.jarRelocatorConstructor.newInstance(input.toFile(), output.toFile(), mappings));
  }
}
