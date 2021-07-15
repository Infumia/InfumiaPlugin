package tr.com.infumia.infumialib.paper.plugin;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.plugin.PluginLogger;

@RequiredArgsConstructor
public final class JavaPluginLogger implements PluginLogger {

  @NotNull
  private final Logger logger;

  @Override
  public void info(@NotNull final String s) {
    this.logger.info(s);
  }

  @Override
  public void severe(@NotNull final String s) {
    this.logger.severe(s);
  }

  @Override
  public void severe(@NotNull final String s, @NotNull final Throwable t) {
    this.logger.log(Level.SEVERE, s, t);
  }

  @Override
  public void warn(@NotNull final String s) {
    this.logger.warning(s);
  }

  @Override
  public void warn(@NotNull final String s, @NotNull final Throwable t) {
    this.logger.log(Level.WARNING, s, t);
  }
}
