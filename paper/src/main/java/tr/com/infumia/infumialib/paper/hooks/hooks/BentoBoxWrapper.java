package tr.com.infumia.infumialib.paper.hooks.hooks;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.AddonClassLoader;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

@RequiredArgsConstructor
public final class BentoBoxWrapper implements Wrapped {

  private static final Map<Plugin, Boolean> CACHE = new HashMap<>();

  @NotNull
  private final BentoBox bentoBox;

  @NotNull
  private final AddonClassLoader classLoader;

  public static boolean isCache(@NotNull final Plugin plugin) {
    return BentoBoxWrapper.CACHE.getOrDefault(plugin, false);
  }

  public static void setCache(@NotNull final Plugin plugin, final boolean cache) {
    BentoBoxWrapper.CACHE.put(plugin, cache);
  }

  public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, this.getIslandLevel(uuid) + level);
  }

  @NotNull
  public Optional<Island> findFirstIsland(@NotNull final UUID uuid) {
    for (final var island : this.bentoBox.getIslands().getIslands()) {
      if (island.getWorld() != null &&
        island.getOwner() != null &&
        island.getOwner().equals(uuid)) {
        return Optional.of(island);
      }
    }
    return Optional.empty();
  }

  public long getIslandLevel(@NotNull final UUID uuid) {
    final var level = new AtomicLong(0L);
    this.findFirstIsland(uuid).ifPresent(island -> {
      try {
        level.addAndGet((long) this.classLoader
          .findClass("world.bentobox.level.Level", false)
          .getMethod("getIslandLevel", World.class, UUID.class)
          .invoke(this.classLoader.getAddon(), island.getWorld(), uuid)
        );
      } catch (final IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        // ignored
      }
    });
    return level.get();
  }

  public void removeIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, Math.max(0L, this.getIslandLevel(uuid) - level));
  }

  public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.findFirstIsland(uuid).ifPresent(island -> {
      try {
        if (!BentoBoxWrapper.isCache(plugin)) {
          BentoBoxWrapper.setCache(plugin, true);
          this.classLoader
            .findClass("world.bentobox.level.Level", false)
            .getMethod("calculateIslandLevel", World.class, User.class, UUID.class)
            .invoke(this.classLoader.getAddon(), island.getWorld(), User.getInstance(uuid), uuid);
        }
        this.classLoader
          .findClass("world.bentobox.level.Level", false)
          .getMethod("setIslandLevel", World.class, UUID.class, long.class)
          .invoke(this.classLoader.getAddon(), island.getWorld(), uuid, level);
      } catch (final Exception ignored) {
        // ignored
      }
    });
  }
}
