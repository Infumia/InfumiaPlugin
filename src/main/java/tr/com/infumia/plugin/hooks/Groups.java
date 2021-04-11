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

import java.util.ArrayList;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

@UtilityClass
public class Groups {

  public boolean containsGroup(@NotNull final Wrapped wrapper, @NotNull final String group,
                               @NotNull final Player player) {
    if (wrapper instanceof GroupManagerWrapper) {
      return ((GroupManagerWrapper) wrapper).getGroup(player.getWorld().getName(), player)
        .orElse("")
        .equalsIgnoreCase(group);
    }
    if (wrapper instanceof LuckPermsWrapper) {
      return ((LuckPermsWrapper) wrapper).getGroup(player.getWorld().getName(), player)
        .orElse("")
        .equalsIgnoreCase(group);
    }
    if (wrapper instanceof PermissionsExWrapper) {
      return ((PermissionsExWrapper) wrapper).getGroups(player.getWorld().getName(), player)
        .orElse(new ArrayList<>())
        .contains(group);
    }
    return false;
  }

  @NotNull
  public Optional<String> getFirstGroup(@NotNull final Wrapped wrapper, @NotNull final Player player) {
    if (wrapper instanceof GroupManagerWrapper) {
      return ((GroupManagerWrapper) wrapper).getGroup(player.getWorld().getName(), player);
    }
    if (wrapper instanceof LuckPermsWrapper) {
      return ((LuckPermsWrapper) wrapper).getGroup(player.getWorld().getName(), player);
    }
    if (wrapper instanceof PermissionsExWrapper) {
      return ((PermissionsExWrapper) wrapper).getGroups(player.getWorld().getName(), player)
        .filter(strings -> !strings.isEmpty())
        .map(strings -> strings.get(0));
    }
    return Optional.empty();
  }
}
