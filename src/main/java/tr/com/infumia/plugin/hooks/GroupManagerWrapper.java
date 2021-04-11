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

import java.util.Optional;
import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

public final class GroupManagerWrapper implements Wrapped {

  @NotNull
  private final GroupManager groupManager;

  public GroupManagerWrapper(@NotNull final GroupManager groupManager) {
    this.groupManager = groupManager;
  }

  @NotNull
  public Optional<String> getGroup(final @NotNull String world, @NotNull final Player player) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
      .map(handler -> handler.getGroup(player.getName()));
  }

  @NotNull
  public Optional<String> getUserPrefix(@NotNull final Player player) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
      .map(handler -> handler.getUserPrefix(player.getUniqueId().toString()));
  }

  @NotNull
  public Optional<String> getUserSuffix(@NotNull final Player player) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
      .map(handler -> handler.getUserSuffix(player.getUniqueId().toString()));
  }

  @NotNull
  public Optional<String> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(world))
      .map(handler -> handler.getGroupPrefix(group));
  }

  @NotNull
  public Optional<String> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(world))
      .map(handler -> handler.getGroupSuffix(group));
  }
}
