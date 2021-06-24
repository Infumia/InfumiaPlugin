package tr.com.infumia.infumialib.paper.bukkititembuilder;

import com.cryptomorin.xseries.XItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.Keys;
import tr.com.infumia.infumialib.transformer.TransformedData;

/**
 * a class that represents leather armor item builders.
 * serialization:
 * <pre>
 * map: (main section)
 *   scaling: boolean (map is scaling or not) (for 8 and newer versions)
 *
 *   location: string (location name) (for 11 and newer versions)
 *
 *   color: 'red, green, blue' (for 11 and newer versions)
 *
 *   map-id: integer (map's id) (for 13 and newer versions)
 *
 *   view: (view section)
 *     scale: string (map's scale) (for 14 and newer versions)
 *
 *     world: string (map's world) (for 14 and newer versions)
 *
 *     locked: boolean (map is locked or not) (for 14 and newer versions)
 *
 *     tracking-position: boolean (is tracking position) (for 14 and newer versions)
 *
 *     unlimited-tracking: boolean (is unlimited tracking) (for 14 and newer versions)
 *
 *     center: (center section)
 *       x: integer (map center's x value) (for 14 and newer versions)
 *
 *       z: integer (map center's z value) (for 14 and newer versions)
 * </pre>
 */
public final class MapItemBuilder extends Builder<MapItemBuilder, MapMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  MapItemBuilder(@NotNull final MapMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new map item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created map item builder instance.
   */
  @NotNull
  public static MapItemBuilder from(@NotNull final MapMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new MapItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates data item builder from serialized data.
   *
   * @param data the data to create.
   *
   * @return a newly created data item builder instance.
   */
  @NotNull
  public static MapItemBuilder from(@NotNull final TransformedData data) {
    return MapItemBuilder.getDeserializer().apply(data).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given data is incorrect!\n%s", data)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return MapItemBuilder.DESERIALIZER;
  }

  @NotNull
  @Override
  public MapItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final TransformedData data) {
    super.serialize(data);
    final var map = new HashMap<String, Object>();
    final var itemMeta = this.getItemMeta();
    map.put(Keys.SCALING_KEY, itemMeta.isScaling());
    if (Builder.VERSION >= 11) {
      if (itemMeta.hasLocationName()) {
        map.put("location", itemMeta.getLocationName());
      }
      final var color = itemMeta.getColor();
      if (color != null) {
        map.put("color", String.format("%d, %d, %d",
          color.getRed(), color.getGreen(), color.getBlue()));
      }
    }
    if (Builder.VERSION >= 13) {
      map.put(Keys.MAP_ID_KEY, itemMeta.getMapId());
    }
    if (Builder.VERSION >= 14) {
      final var mapView = itemMeta.getMapView();
      if (itemMeta.hasMapView() && mapView != null) {
        final var view = new HashMap<>();
        map.put(Keys.VIEW_KEY, view);
        view.put(Keys.SCALE_KEY, mapView.getScale().toString());
        final var world = mapView.getWorld();
        if (world != null) {
          view.put(Keys.WORLD_KEY, world.getName());
        }
        view.put(Keys.LOCKED_KEY, mapView.isLocked());
        view.put(Keys.TRACKING_POSITION_KEY, mapView.isTrackingPosition());
        view.put(Keys.UNLIMITED_TRACKING_KEY, mapView.isUnlimitedTracking());
        final var center = new HashMap<>();
        view.put(Keys.CENTER_KEY, center);
        center.put(Keys.X_KEY, mapView.getCenterX());
        center.put(Keys.Z_KEY, mapView.getCenterZ());
      }
    }
    data.addAsMap(Keys.MAP_KEY, map, String.class, Object.class);
  }

  /**
   * sets color of the map.
   *
   * @param color the color to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setColor(@Nullable final Color color) {
    if (Builder.VERSION >= 11) {
      this.getItemMeta().setColor(color);
    }
    return this.getSelf();
  }

  /**
   * sets location name of the map.
   *
   * @param name the name to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setLocationName(@Nullable final String name) {
    if (Builder.VERSION >= 11) {
      this.getItemMeta().setLocationName(name);
    }
    return this.getSelf();
  }

  /**
   * sets map id of the map.
   *
   * @param id the id to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  @Deprecated
  public MapItemBuilder setMapId(final int id) {
    if (Builder.VERSION >= 13) {
      this.getItemMeta().setMapId(id);
    }
    return this.getSelf();
  }

  /**
   * sets map view of the map.
   *
   * @param mapView the map view to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setMapView(@NotNull final MapView mapView) {
    if (Builder.VERSION >= 14) {
      this.getItemMeta().setMapView(mapView);
    }
    return this.getSelf();
  }

  /**
   * sets scaling of the map.
   *
   * @param scaling the scaling to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public MapItemBuilder setScaling(final boolean scaling) {
    this.getItemMeta().setScaling(scaling);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link MapMeta}.
   */
  public static final class Deserializer implements
    Function<@NotNull TransformedData, @NotNull Optional<MapItemBuilder>> {

    @NotNull
    @Override
    public Optional<MapItemBuilder> apply(@NotNull final TransformedData data) {
      final var itemStack = Builder.getItemStackDeserializer().apply(data);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asMap();
      data.getAsMap(Keys.MAP_KEY, String.class, Object.class)
        .ifPresent(mapSection -> {
          final var scaling = Optional.ofNullable(mapSection.get(Keys.SCALING_KEY))
            .filter(Boolean.class::isInstance)
            .map(Boolean.class::cast)
            .orElse(false);
          builder.setScaling(scaling);
          if (Builder.VERSION >= 11) {
            Optional.ofNullable(mapSection.get(Keys.LOCATION_KEY))
              .filter(String.class::isInstance)
              .map(String.class::cast)
              .ifPresent(builder::setLocationName);
            Optional.ofNullable(mapSection.get(Keys.COLOR_KEY))
              .filter(String.class::isInstance)
              .map(String.class::cast)
              .ifPresent(s -> builder.setColor(XItemStack.parseColor(s)));
          }
          if (Builder.VERSION >= 13) {
            Optional.ofNullable(mapSection.get(Keys.MAP_ID_KEY))
              .filter(Integer.class::isInstance)
              .map(Integer.class::cast)
              .ifPresent(builder::setMapId);
          }
          if (Builder.VERSION >= 14) {
            Optional.ofNullable(mapSection.get(Keys.VIEW_KEY))
              .filter(Map.class::isInstance)
              .map(Map.class::cast)
              .ifPresent(view -> Optional.ofNullable(view.get(Keys.WORLD_KEY))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .flatMap(worldName -> Optional.ofNullable(Bukkit.getWorld(worldName)))
                .ifPresent(world -> {
                  final var scaleOptional = Optional.ofNullable(view.get(Keys.SCALE_KEY))
                    .filter(String.class::isInstance)
                    .map(String.class::cast);
                  final var locked = Optional.ofNullable(view.get(Keys.LOCKED_KEY))
                    .filter(Boolean.class::isInstance)
                    .map(Boolean.class::cast)
                    .orElse(false);
                  final var trackingPosition = Optional.ofNullable(view.get(Keys.TRACKING_POSITION_KEY))
                    .filter(Boolean.class::isInstance)
                    .map(Boolean.class::cast)
                    .orElse(false);
                  final var unlimitedTracking = Optional.ofNullable(view.get(Keys.UNLIMITED_TRACKING_KEY))
                    .filter(Boolean.class::isInstance)
                    .map(Boolean.class::cast)
                    .orElse(false);
                  final var mapView = Bukkit.createMap(world);
                  mapView.setWorld(world);
                  MapView.Scale scale;
                  try {
                    scale = scaleOptional.map(MapView.Scale::valueOf).orElse(MapView.Scale.NORMAL);
                  } catch (final Exception e) {
                    scale = MapView.Scale.NORMAL;
                  }
                  mapView.setScale(scale);
                  mapView.setLocked(locked);
                  mapView.setTrackingPosition(trackingPosition);
                  mapView.setUnlimitedTracking(unlimitedTracking);
                  final var center = Optional.ofNullable(view.get(Keys.CENTER_KEY))
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .orElse(new HashMap<>());
                  final var x = Optional.ofNullable(center.get(Keys.X_KEY))
                    .filter(Integer.class::isInstance)
                    .map(Integer.class::cast)
                    .orElse(0);
                  final var z = Optional.ofNullable(center.get(Keys.Z_KEY))
                    .filter(Integer.class::isInstance)
                    .map(Integer.class::cast)
                    .orElse(0);
                  mapView.setCenterX(x);
                  mapView.setCenterZ(z);
                  builder.setMapView(mapView);
                }));
          }
        });
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(data));
    }
  }
}
