package tr.com.infumia.infumialib.paper.hooks.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tr.com.infumia.infumialib.hooks.Wrapped;
import tr.com.infumia.infumialib.paper.hooks.Groups;

@RequiredArgsConstructor
public final class PermissionsExWrapper implements Wrapped {

  @NotNull
  private final PermissionsEx permissionsEx;

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
    final var calculatedLimit = new AtomicLong(defaultValue);
    final var user = this.permissionsEx.getPermissionsManager().getUser(player.getUniqueId());
    final var permissionMap = user.getAllPermissions();
    final var permissions = new ArrayList<String>();
    for (final var entry : permissionMap.entrySet()) {
      permissions.addAll(entry.getValue());
    }
    if (permissions.isEmpty()) {
      return calculatedLimit.get();
    }
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  @NotNull
  public Optional<String> getGroupPrefix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getGroup(group).getPrefix(world));
  }

  @NotNull
  public Optional<String> getGroupSuffix(@NotNull final String world, @NotNull final String group) {
    return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getGroup(group).getSuffix(world));
  }

  @NotNull
  public Optional<List<String>> getGroups(@NotNull final String world, @NotNull final Player player) {
    return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player))
      .map(permissionUser -> permissionUser.getParentIdentifiers(world));
  }

  @NotNull
  public Optional<String> getUserPrefix(@NotNull final Player player) {
    return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player).getPrefix());
  }

  @NotNull
  public Optional<String> getUserSuffix(@NotNull final Player player) {
    return Optional.ofNullable(this.permissionsEx.getPermissionsManager().getUser(player).getSuffix());
  }
}
