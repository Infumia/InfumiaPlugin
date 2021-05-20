package tr.com.infumia.infumialib.paper.hooks;

import java.util.ArrayList;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.hooks.hooks.GroupManagerWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.LuckPermsWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.PermissionsExWrapper;

/**
 * a class that contains utility methods for group/permission plugins.
 */
@UtilityClass
public class Groups {

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
}
