package tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import net.pl3x.map.api.Key;
import net.pl3x.map.api.Pl3xMap;
import net.pl3x.map.api.SimpleLayerProvider;
import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;

@RequiredArgsConstructor
public final class Pl3xMapWrapper implements Wrapped {

  @NotNull
  private final Pl3xMap provider;

  private final Map<String, Map<UUID, MapTask>> tasks = new ConcurrentHashMap<>();

  public void create(@NotNull final MapConfig config, @NotNull final Predicate<String> worldsPredicate) {
    final var plugin = config.getPlugin();
    final var name = plugin.getName();
    final var map = this.tasks.getOrDefault(name, new HashMap<>());
    this.provider.mapWorlds().stream()
      .filter(mapWorld -> worldsPredicate.test(mapWorld.name()))
      .forEach(world -> {
        final var provider = SimpleLayerProvider
          .builder(config.getControlLabel())
          .showControls(config.isControlShow())
          .defaultHidden(config.isControlHide())
          .build();
        final var key = Key.of(config.getLayerUniqueIdPattern().replace("%world_unique_id%", world.uuid().toString()));
        world.layerRegistry().register(key, provider);
        final var task = new MapTask(config, provider, world);
        task.runTaskTimerAsynchronously(plugin, 0, config.getUpdateInterval());
        map.put(world.uuid(), task);
      });
    this.tasks.put(name, map);
  }

  public void disable(@NotNull final Plugin plugin) {
    Optional.ofNullable(this.tasks.get(plugin.getName())).ifPresent(map -> {
      map.values().forEach(MapTask::cancel);
      map.clear();
    });
  }

  public void newClaim(@NotNull final Plugin plugin, @NotNull final Claim claim) {
    Optional.ofNullable(this.tasks.get(plugin.getName())).ifPresent(map -> {
      final var chunk = claim.getChunk();
      Optional.ofNullable(map.get(chunk.getWorld().getUID())).ifPresent(task ->
        task.handleClaim(claim));
    });
  }

  public void removeClaim(@NotNull final Plugin plugin, @NotNull final Claim claim) {
    Optional.ofNullable(this.tasks.get(plugin.getName())).ifPresent(map -> {
      final Chunk chunk = claim.getChunk();
      Optional.ofNullable(map.get(chunk.getWorld().getUID())).ifPresent(task ->
        task.removeMarker(claim.getId()));
    });
  }
}
