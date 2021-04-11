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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.ChatMetaNode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

@RequiredArgsConstructor
public final class LuckPermsWrapper implements Wrapped {

  @NotNull
  private final LuckPerms luckPerms;

  @NotNull
  public Optional<String> getGroup(final @NotNull String world, @NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(User::getPrimaryGroup);
  }

  @NotNull
  public Optional<List<String>> getUserPrefix(@NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
      .map(nodes -> nodes.stream().map(ChatMetaNode::getMetaValue).collect(Collectors.toList()));
  }

  @NotNull
  public Optional<List<String>> getUserSuffix(@NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
      .map(nodes -> nodes.stream().map(ChatMetaNode::getMetaValue).collect(Collectors.toList()));
  }

  @NotNull
  public Optional<List<String>> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
      .map(nodes -> nodes.stream().map(ChatMetaNode::getMetaValue).collect(Collectors.toList()));
  }

  @NotNull
  public Optional<List<String>> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
      .map(nodes -> nodes.stream().map(ChatMetaNode::getMetaValue).collect(Collectors.toList()));
  }
}
