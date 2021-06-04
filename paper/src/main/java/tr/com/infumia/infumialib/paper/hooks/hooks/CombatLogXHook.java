package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class CombatLogXHook implements Hook<CombatLogXWrapper> {

  public static final String COMBAT_LOG_X_ID = "CombatLogX";

  @Nullable
  private Plugin plugin;

  @NotNull
  @Override
  public CombatLogXWrapper create() {
    if (this.plugin == null) {
      throw new IllegalStateException("CombatLogX not initiated! Use CombatLogXHook#initiate() method.");
    }
    return new CombatLogXWrapper();
  }

  @NotNull
  @Override
  public String id() {
    return CombatLogXHook.COMBAT_LOG_X_ID;
  }

  @Override
  public boolean initiate() {
    return (this.plugin = Bukkit.getPluginManager().getPlugin("CombatLogX")) != null;
  }
}
