package tr.com.infumia.infumialib.paper.transformer.resolvers;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for bukkit resolvers.
 */
final class BukkitResolverHelper {

  /**
   * ctor.
   */
  private BukkitResolverHelper() {
  }

  /**
   * gets the section value with primitive objects.
   *
   * @param section the section to get.
   * @param deep the deep to get.
   *
   * @return map values.
   */
  @NotNull
  static Map<String, Object> getMapValues(@NotNull final ConfigurationSection section, final boolean deep) {
    final var map = new HashMap<String, Object>();
    for (final var entry : section.getValues(deep).entrySet()) {
      final var key = entry.getKey();
      final var value = entry.getValue();
      if (value instanceof ConfigurationSection) {
        map.put(key, BukkitResolverHelper.getMapValues((ConfigurationSection) value, deep));
      } else {
        map.put(key, value);
      }
    }
    return map;
  }
}
