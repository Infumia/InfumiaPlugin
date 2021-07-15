package tr.com.infumia.infumialib.plugin;

import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public interface InfumiaPlugin {

  @NotNull
  Path getDataDirectory();
}
