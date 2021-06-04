package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class Pl3xMapHook implements Hook<Pl3xMapWrapper> {

  public static final String PL3X_MAP_ID = "Pl3xMap";

  @Nullable
  private Plugin plugin;

  @NotNull
  @Override
  public Pl3xMapWrapper create() {
    if (this.plugin == null) {
      throw new IllegalStateException("Pl3xMap not initiated! Use Pl3xMapHook#initiate() method.");
    }
    return new Pl3xMapWrapper();
  }

  @NotNull
  @Override
  public String id() {
    return Pl3xMapHook.PL3X_MAP_ID;
  }

  @Override
  public boolean initiate() {
    return (this.plugin = Bukkit.getPluginManager().getPlugin("Pl3xMap")) != null;
  }
}
