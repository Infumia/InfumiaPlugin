package tr.com.infumia.infumialib.dependencies;

import java.net.URL;
import java.net.URLClassLoader;
import org.jetbrains.annotations.NotNull;

public final class IsolatedClassLoader extends URLClassLoader {

  static {
    ClassLoader.registerAsParallelCapable();
  }

  public IsolatedClassLoader(@NotNull final URL @NotNull [] urls) {
    super(urls, ClassLoader.getSystemClassLoader().getParent());
  }
}
