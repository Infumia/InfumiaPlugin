/*
 * MIT License
 *
 * Copyright (c) 2021 Infumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.plugin.hooks;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import tr.com.infumia.plugin.Wrapped;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.AddonClassLoader;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

public final class BentoBoxWrapper implements Wrapped {

  private static final Map<Plugin, Boolean> CACHE = new HashMap<>();

  @NotNull
  private final BentoBox bentoBox;

  @NotNull
  private final AddonClassLoader classLoader;

  public BentoBoxWrapper(@NotNull final BentoBox bentoBox, @NotNull final AddonClassLoader classLoader) {
    this.bentoBox = bentoBox;
    this.classLoader = classLoader;
  }

  public static boolean isKekoUtil(@NotNull final Plugin plugin) {
    return BentoBoxWrapper.CACHE.getOrDefault(plugin, false);
  }

  public static void setKekoUtil(@NotNull final Plugin plugin, final boolean kekoUtil) {
    BentoBoxWrapper.CACHE.put(plugin, kekoUtil);
  }

  public long getIslandLevel(@NotNull final UUID uuid) {
    final AtomicLong level = new AtomicLong(0L);
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

  public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, this.getIslandLevel(uuid) + level);
  }

  public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.findFirstIsland(uuid).ifPresent(island -> {
      try {
        if (!BentoBoxWrapper.isKekoUtil(plugin)) {
          BentoBoxWrapper.setKekoUtil(plugin, true);
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

  @NotNull
  public Optional<Island> findFirstIsland(@NotNull final UUID uuid) {
    for (final Island island : this.bentoBox.getIslands().getIslands()) {
      if (island.getWorld() != null &&
        island.getOwner() != null &&
        island.getOwner().equals(uuid)) {
        return Optional.of(island);
      }
    }
    return Optional.empty();
  }
}
