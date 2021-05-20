package tr.com.infumia.infumialib.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import org.bstats.velocity.Metrics;

@Plugin(
  id = "infumialib",
  name = "InfumiaLib",
  version = "1.0.0-SNAPSHOT",
  authors = {"infumia"}
)
public final class InfumiaLib {

  private final Metrics.Factory metricsFactory;

  @Inject
  public InfumiaLib(final Metrics.Factory metricsFactory) {
    this.metricsFactory = metricsFactory;
  }

  @Subscribe
  public void onProxyInitialization(final ProxyInitializeEvent event) {
    this.metricsFactory.make(this, 11422);
  }
}
