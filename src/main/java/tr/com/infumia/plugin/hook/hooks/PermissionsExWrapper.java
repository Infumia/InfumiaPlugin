package tr.com.infumia.plugin.hook.hooks;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tr.com.infumia.plugin.hook.Wrapped;

@RequiredArgsConstructor
public final class PermissionsExWrapper implements Wrapped {

  @NotNull
  private final PermissionsEx permissionsEx;

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
