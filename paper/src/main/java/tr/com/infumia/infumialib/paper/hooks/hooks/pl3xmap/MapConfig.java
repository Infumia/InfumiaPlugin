package tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap;

import io.github.portlek.replaceable.RpList;
import java.util.Collection;
import java.util.function.Supplier;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

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
   * <l>
   * <li>%world_unique_id%</li>
   * </l>
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
   * <l>
   * <li>%world_name%</li>
   * <li>%id%</li>
   * </l>
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
