package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class HolographicDisplaysHook implements Hook<HolographicDisplaysWrapper> {

  public static final String HOLOGRAPHIC_DISPLAYS_ID = "HolographicDisplays";

  @Nullable
  private Plugin plugin;

  @NotNull
  @Override
  public HolographicDisplaysWrapper create() {
    if (this.plugin == null) {
      throw new IllegalStateException("HolographicDisplays not initiated! Use HolographicDisplaysHook#initiate() method.");
    }
    return new HolographicDisplaysWrapper();
  }

  @NotNull
  @Override
  public String id() {
    return HolographicDisplaysHook.HOLOGRAPHIC_DISPLAYS_ID;
  }

  @Override
  public boolean initiate() {
    return (this.plugin = Bukkit.getPluginManager().getPlugin("HolographicDisplays")) != null;
  }
}
