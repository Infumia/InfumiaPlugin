package tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap;

import java.util.Collection;
import java.util.function.Supplier;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.replaceable.RpList;

public interface MapConfig {

  @NotNull
  Supplier<Collection<Claim>> getClaimSupplier();

  @NotNull
  RpList getClickTooltip();

  @NotNull
  String getControlLabel();

  @NotNull
  String getDisabledText();

  @NotNull
  String getEnabledText();

  @NotNull
  String getFillColor();

  double getFillOpacity();

  /**
   * obtains layer unique id pattern.
   * <p>
   * regex:
   * %world_unique_id%
   *
   * @return layer unique id pattern.
   */
  @NotNull
  String getLayerUniqueIdPattern();

  @NotNull
  Plugin getPlugin();

  /**
   * obtains region id pattern.
   * <p>
   * regex:
   * %world_name%
   * %id%
   *
   * @return region id pattern.
   */
  @NotNull
  String getRegionIdPattern();

  @NotNull
  String getStrokeColor();

  double getStrokeOpacity();

  int getStrokeWeight();

  /**
   * update interval as milliseconds.
   *
   * @return updated interval.
   */
  int getUpdateInterval();

  boolean isControlHide();

  boolean isControlShow();
}
