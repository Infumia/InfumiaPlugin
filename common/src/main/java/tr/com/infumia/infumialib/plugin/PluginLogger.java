package tr.com.infumia.infumialib.plugin;

import org.jetbrains.annotations.NotNull;

public interface PluginLogger {

  void info(@NotNull String s);

  void severe(@NotNull String s);

  void severe(@NotNull String s, @NotNull Throwable t);

  void warn(@NotNull String s);

  void warn(@NotNull String s, @NotNull Throwable t);
}
