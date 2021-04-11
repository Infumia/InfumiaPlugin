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

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

public final class ASkyBlockWrapper implements Wrapped {

  private static final Map<Plugin, Boolean> CACHE = new HashMap<>();

  @NotNull
  private final ASkyBlockAPI skyBlockAPI;

  public ASkyBlockWrapper(@NotNull final ASkyBlockAPI skyBlockAPI) {
    this.skyBlockAPI = skyBlockAPI;
  }

  public static boolean isKekoUtil(@NotNull final Plugin plugin) {
    return ASkyBlockWrapper.CACHE.getOrDefault(plugin, false);
  }

  public static void setKekoUtil(@NotNull final Plugin plugin, final boolean kekoUtil) {
    ASkyBlockWrapper.CACHE.put(plugin, kekoUtil);
  }

  public long getIslandLevel(@NotNull final UUID uuid) {
    return this.skyBlockAPI.getLongIslandLevel(uuid);
  }

  public void removeIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, Math.max(0L, this.getIslandLevel(uuid) - level));
  }

  public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    this.setIslandLevel(plugin, uuid, this.getIslandLevel(uuid) + level);
  }

  public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
    if (!ASkyBlockWrapper.isKekoUtil(plugin)) {
      ASkyBlockWrapper.setKekoUtil(plugin, true);
      this.skyBlockAPI.calculateIslandLevel(uuid);
    }
    ASkyBlock.getPlugin().getPlayers().setIslandLevel(uuid, level);
  }
}
