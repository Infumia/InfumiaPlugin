package tr.com.infumia.infumialib.paper.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class LocationUtil {

  @NotNull
  public Location getSafeLocation(@NotNull final Location location, final int range) {
    for (var i = -range; i < range; i++) {
      for (int j = -range; j < range; j++) {
        for (var m = -range; m < range; m++) {
          final var clone = location.clone();
          final var equals = clone.add(i, j, m).getBlock().getType().equals(Material.AIR);
          final var equals2 = clone.add(i, j + 1, m).getBlock().getType().equals(Material.AIR);
          if (equals && equals2) {
            return clone;
          }
        }
      }
    }
    return location.getWorld().getHighestBlockAt(location).getLocation();
  }
}
