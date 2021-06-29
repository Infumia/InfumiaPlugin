package tr.com.infumia.infumialib.paper.hooks.hooks;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class CombatLogXHook implements Hook<CombatLogXWrapper> {

  public static final String COMBAT_LOG_X_ID = "CombatLogX";

  @Nullable
  private ICombatLogX plugin;

  @NotNull
  @Override
  public CombatLogXWrapper create() {
    if (this.plugin == null) {
      throw new IllegalStateException("CombatLogX not initiated! Use CombatLogXHook#initiate() method.");
    }
    return new CombatLogXWrapper(this.plugin);
  }

  @NotNull
  @Override
  public String id() {
    return CombatLogXHook.COMBAT_LOG_X_ID;
  }

  @Override
  public boolean initiate() {
    return (this.plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX")) != null;
  }
}
