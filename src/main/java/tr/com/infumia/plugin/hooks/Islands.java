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

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

@UtilityClass
public class Islands {

  public void addFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final Plugin plugin,
                                  @NotNull final UUID uniqueId, final long level) {
    if (wrapper instanceof ASkyBlockWrapper) {
      ((ASkyBlockWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    } else if (wrapper instanceof BentoBoxWrapper) {
      ((BentoBoxWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    } else if (wrapper instanceof FabledSkyBlockWrapper) {
      ((FabledSkyBlockWrapper) wrapper).addIslandLevel(plugin, uniqueId, level);
    }
  }

  public long getFirstIslandLevel(@NotNull final Wrapped wrapper, @NotNull final UUID uniqueId) {
    if (wrapper instanceof ASkyBlockWrapper) {
      return ((ASkyBlockWrapper) wrapper).getIslandLevel(uniqueId);
    }
    if (wrapper instanceof BentoBoxWrapper) {
      return ((BentoBoxWrapper) wrapper).getIslandLevel(uniqueId);
    }
    if (wrapper instanceof FabledSkyBlockWrapper) {
      return ((FabledSkyBlockWrapper) wrapper).getIslandLevel(uniqueId);
    }
    return 0L;
  }
}
