package tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.pl3x.map.api.Key;
import net.pl3x.map.api.MapWorld;
import net.pl3x.map.api.Point;
import net.pl3x.map.api.SimpleLayerProvider;
import net.pl3x.map.api.marker.Marker;
import net.pl3x.map.api.marker.MarkerOptions;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.utils.Colors;

@RequiredArgsConstructor
public final class MapTask extends BukkitRunnable {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  @NotNull
  private final MapConfig config;

  @NotNull
  private final SimpleLayerProvider provider;

  private final AtomicBoolean stop = new AtomicBoolean(false);

  @NotNull
  private final MapWorld world;

  @Override
  public synchronized void cancel() throws IllegalStateException {
    this.stop.set(true);
    this.provider.clearMarkers();
    super.cancel();
  }

  @Override
  public void run() {
    if (!this.stop.get()) {
      this.updateClaims();
    }
  }

  public void handleClaim(@NotNull final Claim claim) {
    final var rect = Marker.rectangle(
      Point.of(claim.getMinX(), claim.getMinZ()),
      Point.of(claim.getMaxX(), claim.getMaxZ()));
    final var worldName = claim.getChunk().getWorld().getName();
    final var build = this.config.getClickTooltip().build(
      Map.entry("<b>", () -> "<span style=\"font-weight:bold;\">"),
      Map.entry("</b>", () -> "</span>"),
      Map.entry("%owner%", claim::getOwner),
      Map.entry("%members%", () -> claim.getMembers().stream()
        .map(Claim.Member::getName)
        .collect(Collectors.joining(","))),
      Map.entry("%last_data%", () -> MapTask.DATE_FORMAT.format(new Date(claim.getLastDate()))),
      Map.entry("%claim_count%", () -> String.valueOf(claim.getChildren().size())),
      Map.entry("%pvp%", () -> claim.isPvp()
        ? ChatColor.stripColor(this.config.getEnabledText())
        : ChatColor.stripColor(this.config.getDisabledText()))
    );
    final var stroke = Colors.valueOf(this.config.getStrokeColor());
    final var fill = Colors.valueOf(this.config.getFillColor());
    final Color strokeColor = new Color(
      (float) stroke.getRed(), (float) stroke.getGreen(), (float) stroke.getBlue()
    );
    final Color fillColor = new Color(
      (float) fill.getRed(), (float) fill.getGreen(), (float) fill.getBlue()
    );
    final MarkerOptions.Builder options = MarkerOptions.builder()
      .strokeColor(strokeColor)
      .strokeWeight(this.config.getStrokeWeight())
      .strokeOpacity(this.config.getStrokeOpacity())
      .fillColor(fillColor)
      .fillOpacity(this.config.getFillOpacity())
      .clickTooltip(String.join("<br/>", build));
    rect.markerOptions(options.build());
    final String markerId = this.config.getRegionIdPattern()
      .replace("%world_name%", worldName)
      .replace("%id%", Long.toHexString(claim.getId()));
    this.provider.addMarker(Key.of(markerId), rect);
  }

  public void removeMarker(final int id) {
    final var markerId = this.config.getLayerUniqueIdPattern()
      .replace("%world_name%", this.world.name())
      .replace("%id%", Long.toHexString(id));
    this.provider.removeMarker(Key.key(markerId));
  }

  private void updateClaims() {
    this.provider.clearMarkers();
    final var claims = this.config.getClaimSupplier().get();
    claims.stream()
      .filter(claim -> claim.getChunk().getWorld().getUID().equals(this.world.uuid()))
      .filter(claim -> claim.getParentId() == -1)
      .forEach(this::handleClaim);
  }
}
