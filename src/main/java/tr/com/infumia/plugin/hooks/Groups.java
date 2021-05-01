package tr.com.infumia.plugin.hooks;

import java.util.ArrayList;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.hooks.GroupManagerWrapper;
import tr.com.infumia.plugin.hooks.hooks.LuckPermsWrapper;
import tr.com.infumia.plugin.hooks.hooks.PermissionsExWrapper;

@UtilityClass
public class Groups {

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
