package tr.com.infumia.infumialib.paper.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.utils.NumberUtil;

/**
 * a class that contains utility methods for group/permission plugins.
 */
@UtilityClass
public class Groups {

  /**
   * calculates the given permission's limit.
   *
   * @param permission the permission to calculate.
   * @param permissions the permissions to calculate.
   * @param calculatedLimit the calculated limit.
   */
  public void calculatePermissionLimit(@NotNull final String permission, @NotNull final List<String> permissions,
                                       @NotNull final AtomicLong calculatedLimit) {
    for (final var perm : permissions) {
      if (perm.startsWith("-") || !perm.startsWith(permission)) {
        continue;
      }
      final var substring = perm.substring(perm.lastIndexOf(".") + 1);
      if (!NumberUtil.isLong(substring)) {
        continue;
      }
      final var limit = Integer.parseInt(substring);
      if (limit > calculatedLimit.get()) {
        calculatedLimit.set(limit);
      }
    }
  }

  /**
   * checks if the given player has the given group.
   *
   * @param group the group to check.
   * @param player the player to check.
   *
   * @return {@code true} if the player has the group.
   */
  public boolean containsGroup(@NotNull final String group,
                               @NotNull final Player player) {
    if (Hooks.supportsGroupManager()) {
      return Hooks.getGroupManagerOrThrow().getGroup(player.getWorld().getName(), player)
        .orElse("")
        .equalsIgnoreCase(group);
    }
    if (Hooks.supportsLuckPerms()) {
      return Hooks.getLuckPermsOrThrow().getGroup(player.getWorld().getName(), player)
        .orElse("")
        .equalsIgnoreCase(group);
    }
    if (Hooks.supportsPermissionsEx()) {
      return Hooks.getPermissionsExOrThrow().getGroups(player.getWorld().getName(), player)
        .orElse(new ArrayList<>())
        .contains(group);
    }
    return false;
  }

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
  public long getEffectiveLimitedPermission(@NotNull final String permission,
                                            @NotNull final Player player, final long defaultValue) {
    if (Hooks.supportsGroupManager()) {
      return Hooks.getGroupManagerOrThrow().getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    if (Hooks.supportsLuckPerms()) {
      return Hooks.getLuckPermsOrThrow().getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    if (Hooks.supportsPermissionsEx()) {
      return Hooks.getPermissionsExOrThrow().getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    final var calculatedLimit = new AtomicLong(defaultValue);
    final var permissions = new ArrayList<String>();
    for (final var permissionAttachmentInfo : player.getEffectivePermissions()) {
      if (permissionAttachmentInfo.getValue()) {
        permissions.add(permissionAttachmentInfo.getPermission());
      }
    }
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  /**
   * gets the player's first group.
   *
   * @param player the player to get.
   *
   * @return player's first group.
   */
  @NotNull
  public Optional<String> getFirstGroup(@NotNull final Player player) {
    if (Hooks.supportsGroupManager()) {
      return Hooks.getGroupManagerOrThrow().getGroup(player.getWorld().getName(), player);
    }
    if (Hooks.supportsLuckPerms()) {
      return Hooks.getLuckPermsOrThrow().getGroup(player.getWorld().getName(), player);
    }
    if (Hooks.supportsPermissionsEx()) {
      return Hooks.getPermissionsExOrThrow().getGroups(player.getWorld().getName(), player)
        .filter(strings -> !strings.isEmpty())
        .map(strings -> strings.get(0));
    }
    return Optional.empty();
  }
}
