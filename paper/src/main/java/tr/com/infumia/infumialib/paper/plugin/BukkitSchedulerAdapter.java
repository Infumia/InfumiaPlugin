package tr.com.infumia.infumialib.paper.plugin;

import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.InfumiaPaperBootstrap;
import tr.com.infumia.infumialib.plugin.AbstractJavaScheduler;
import tr.com.infumia.infumialib.plugin.SchedulerAdapter;

public final class BukkitSchedulerAdapter extends AbstractJavaScheduler implements SchedulerAdapter {

  @NotNull
  private final Executor sync;

  public BukkitSchedulerAdapter(@NotNull final InfumiaPaperBootstrap bootstrap) {
    this.sync = r -> bootstrap.getServer().getScheduler().scheduleSyncDelayedTask(bootstrap.getLoader(), r);
  }

  @NotNull
  @Override
  public Executor sync() {
    return this.sync;
  }
}
