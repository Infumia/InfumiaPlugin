package tr.com.infumia.plugin.hooks.hooks;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.Wrapped;

@RequiredArgsConstructor
public final class GroupManagerWrapper implements Wrapped {

  @NotNull
  private final GroupManager groupManager;

  @NotNull
  public Optional<String> getGroup(final @NotNull String world, @NotNull final Player player) {
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
