package tr.com.infumia.infumialib.paper.hooks.hooks;

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
import tr.com.infumia.infumialib.hooks.Wrapped;

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
}
