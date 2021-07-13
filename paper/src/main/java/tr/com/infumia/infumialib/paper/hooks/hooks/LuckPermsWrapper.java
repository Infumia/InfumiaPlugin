package tr.com.infumia.infumialib.paper.hooks.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;
import tr.com.infumia.infumialib.paper.hooks.Groups;

@RequiredArgsConstructor
public final class LuckPermsWrapper implements Wrapped {

  @NotNull
  private final LuckPerms luckPerms;

  /**
   * gets player's limit in the given permission.
   * <p>
   * permission pattern should be like 'xxx.yyy.zzz.'
   *
   * @param permission the permission to get.
   * @param player the player to get.
   * @param defaultValue the default value to get.
   *
   * @return player's limit in the permission.
   */
  public long getEffectiveLimitedPermission(@NotNull final String permission, @NotNull final Player player,
                                            final long defaultValue) {
    return this.getEffectiveLimitedPermission(permission, player.getUniqueId(), defaultValue);
  }

  /**
   * gets player's limit in the given permission.
   * <p>
   * permission pattern should be like 'xxx.yyy.zzz.'
   *
   * @param permission the permission to get.
   * @param uniqueId the unique id of player to get.
   * @param defaultValue the default value to get.
   *
   * @return player's limit in the permission.
   */
  public long getEffectiveLimitedPermission(@NotNull final String permission, @NotNull final UUID uniqueId,
                                            final long defaultValue) {
    final var calculatedLimit = new AtomicLong(defaultValue);
    final var user = this.luckPerms.getUserManager().getUser(uniqueId);
    if (user == null) {
      return calculatedLimit.get();
    }
    final var permissionMap = user.getCachedData().getPermissionData().getPermissionMap();
    final var permissions = new ArrayList<String>();
    for (final var entry : permissionMap.entrySet()) {
      if (entry.getValue()) {
        permissions.add(entry.getKey());
      }
    }
    if (permissions.isEmpty()) {
      return calculatedLimit.get();
    }
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  @NotNull
  public Optional<String> getGroup(@NotNull final String world, @NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(User::getPrimaryGroup);
  }

  @NotNull
  public Optional<List<String>> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
      .map(nodes -> {
        final var list = new ArrayList<String>();
        for (final var node : nodes) {
          list.add(node.getMetaValue());
        }
        return list;
      });
  }

  @NotNull
  public Optional<List<String>> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.luckPerms.getGroupManager().getGroup(group))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
      .map(nodes -> {
        final var list = new ArrayList<String>();
        for (final var node : nodes) {
          list.add(node.getMetaValue());
        }
        return list;
      });
  }

  @NotNull
  public Optional<List<String>> getUserPrefix(@NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.PREFIX)))
      .map(nodes -> {
        final var list = new ArrayList<String>();
        for (final var node : nodes) {
          list.add(node.getMetaValue());
        }
        return list;
      });
  }

  @NotNull
  public Optional<List<String>> getUserSuffix(@NotNull final Player player) {
    return Optional.ofNullable(this.luckPerms.getUserManager().getUser(player.getUniqueId()))
      .map(gr -> new ArrayList<>(gr.getNodes(NodeType.SUFFIX)))
      .map(nodes -> {
        final var list = new ArrayList<String>();
        for (final var node : nodes) {
          list.add(node.getMetaValue());
        }
        return list;
      });
  }
}
