package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class PlaceholderApiHook implements Hook<PlaceholderAPIWrapper> {

  public static final String PLACEHOLDER_API_ID = "PlaceholderAPI";

  @Nullable
  private Plugin plugin;

  @Override
  @NotNull
  public PlaceholderAPIWrapper create() {
    if (this.plugin == null) {
      throw new IllegalStateException("PlaceholderAPI not initiated! Use PlaceholderAPIHook#initiate method.");
    }
    return new PlaceholderAPIWrapper();
  }

  @NotNull
  @Override
  public String id() {
    return PlaceholderApiHook.PLACEHOLDER_API_ID;
  }

  @Override
  public boolean initiate() {
    return (this.plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI")) != null;
  }
}
