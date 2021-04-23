package tr.com.infumia.plugin.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Wrapped;

public final class PlaceholderAPIWrapper implements Wrapped {

  @NotNull
  public String apply(@NotNull final OfflinePlayer player, @NotNull final String string) {
    return PlaceholderAPI.setPlaceholders(player, string);
  }
}
