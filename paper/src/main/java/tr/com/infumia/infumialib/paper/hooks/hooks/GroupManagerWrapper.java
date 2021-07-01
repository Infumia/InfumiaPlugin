package tr.com.infumia.infumialib.paper.hooks.hooks;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;
import tr.com.infumia.infumialib.paper.hooks.Groups;

@RequiredArgsConstructor
public final class GroupManagerWrapper implements Wrapped {

  @NotNull
  private final GroupManager groupManager;

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
    final var effectivePermissions = this.groupManager.getWorldsHolder().getWorldPermissions(player);
    final var permissions = effectivePermissions.getAllPlayersPermissions(player.getName());
    if (permissions.isEmpty()) {
      return calculatedLimit.get();
    }
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  /**
   * gets player's limit in the given permission.
   * <p>
   * permission pattern should be like 'xxx.yyy.zzz.'
   *
   * @param permission the permission to get.
   * @param name the player name to get.
   * @param defaultValue the default value to get.
   *
   * @return player's limit in the permission.
   */
  public long getEffectiveLimitedPermission(@NotNull final String permission, @NotNull final String name,
                                            final long defaultValue) {
    final var calculatedLimit = new AtomicLong(defaultValue);
    final var effectivePermissions = this.groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(name);
    final var permissions = effectivePermissions.getAllPlayersPermissions(name);
    if (permissions.isEmpty()) {
      return calculatedLimit.get();
    }
    Groups.calculatePermissionLimit(permission, permissions, calculatedLimit);
    return calculatedLimit.get();
  }

  @NotNull
  public Optional<String> getGroup(@NotNull final String world, @NotNull final Player player) {
    return Optional.ofNullable(this.groupManager.getWorldsHolder().getWorldPermissions(player))
      .map(handler -> handler.getGroup(player.getName()));
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
}
