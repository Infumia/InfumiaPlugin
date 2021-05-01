package tr.com.infumia.plugin.hooks.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.plugin.hooks.Hook;
import tr.com.infumia.plugin.hooks.Wrapped;

public final class PlaceholderAPIHook implements Hook {

  public static final String PLACEHOLDER_API_ID = "PlaceholderAPI";

  @Nullable
  private PlaceholderAPIPlugin placeholderAPI;

  @Override
  @NotNull
  public Wrapped create() {
    if (this.placeholderAPI == null) {
      throw new IllegalStateException("PlaceholderAPI not initiated! Use PlaceholderAPIHook#initiate method.");
    }
    return new PlaceholderAPIWrapper(this.placeholderAPI);
  }

  @NotNull
  @Override
  public String id() {
    return PlaceholderAPIHook.PLACEHOLDER_API_ID;
  }

  @Override
  public boolean initiate() {
    return (this.placeholderAPI = (PlaceholderAPIPlugin) Bukkit.getPluginManager().getPlugin("PlaceholderAPI")) != null;
  }
}
