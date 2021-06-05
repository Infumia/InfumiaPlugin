package tr.com.infumia.infumialib.paper.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;
import tr.com.infumia.infumialib.paper.hooks.hooks.GroupManagerWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.LuckPermsWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.PermissionsExWrapper;
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
    permissions.stream()
      .filter(perm -> !perm.startsWith("-"))
      .filter(perm -> perm.startsWith(permission))
      .map(perm -> perm.substring(perm.lastIndexOf(".") + 1))
      .filter(NumberUtil::isLong)
      .mapToLong(Integer::parseInt)
      .filter(limit -> limit > calculatedLimit.get())
      .forEach(calculatedLimit::set);
  }

  /**
   * checks if the given player has the given group.
   *
   * @param group the group to check.
   * @param player the player to check.
   *
   * @return {@code true} if the player has the group.
   */
  public boolean containsGroup(@NotNull final String group, @NotNull final Player player) {
    return Hooks.getGroupManager()
      .map(wrapper ->
        Groups.containsGroup(wrapper, group, player))
      .orElse(Hooks.getLuckPerms()
        .map(wrapper ->
          Groups.containsGroup(wrapper, group, player))
        .orElse(Hooks.getPermissionsEx()
          .map(wrapper ->
            Groups.containsGroup(wrapper, group, player))
          .orElse(false)));
  }

  /**
   * checks if the given player has the given group.
   *
   * @param wrapper the wrapper to check.
   * @param group the group to check.
   * @param player the player to check.
   *
   * @return {@code true} if the player has the group.
   */
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
    return Hooks.getGroupManager()
      .map(wrapper ->
        Groups.getEffectiveLimitedPermission(wrapper, permission, player, defaultValue))
      .orElse(Hooks.getLuckPerms()
        .map(wrapper ->
          Groups.getEffectiveLimitedPermission(wrapper, permission, player, defaultValue))
        .orElse(Hooks.getPermissionsEx()
          .map(wrapper ->
            Groups.getEffectiveLimitedPermission(wrapper, permission, player, defaultValue))
          .orElse(defaultValue)));
  }

  /**
   * gets player's limit in the given permission.
   * <p>
   * permission pattern should be like 'xxx.yyy.zzz.'
   *
   * @param wrapper the wrapper to get.
   * @param permission the permission to get.
   * @param player the player to get.
   * @param defaultValue the default value to get.
   *
   * @return player's limit in the permission.
   */
  public long getEffectiveLimitedPermission(@NotNull final Wrapped wrapper, @NotNull final String permission,
                                            @NotNull final Player player, final long defaultValue) {
    if (wrapper instanceof GroupManagerWrapper) {
      return ((GroupManagerWrapper) wrapper).getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    if (wrapper instanceof LuckPermsWrapper) {
      return ((LuckPermsWrapper) wrapper).getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    if (wrapper instanceof PermissionsExWrapper) {
      return ((PermissionsExWrapper) wrapper).getEffectiveLimitedPermission(permission, player, defaultValue);
    }
    final var calculatedLimit = new AtomicLong(defaultValue);
    final var permissions = player.getEffectivePermissions().stream()
      .filter(PermissionAttachmentInfo::getValue)
      .map(PermissionAttachmentInfo::getPermission)
      .collect(Collectors.toList());
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  /**
   * gets the player's first group.
   *
   * @param wrapper the wrapper to get.
   * @param player the player to get.
   *
   * @return player's first group.
   */
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

  /**
   * gets the player's first group.
   *
   * @param player the player to get.
   *
   * @return player's first group.
   */
  @NotNull
  public Optional<String> getFirstGroup(@NotNull final Player player) {
    return Optional.ofNullable(Hooks.getGroupManager()
      .flatMap(wrapper ->
        Groups.getFirstGroup(wrapper, player))
      .orElse(Hooks.getLuckPerms()
        .flatMap(wrapper ->
          Groups.getFirstGroup(wrapper, player))
        .orElse(Hooks.getPermissionsEx()
          .flatMap(wrapper ->
            Groups.getFirstGroup(wrapper, player))
          .orElse(null))));
  }
}
