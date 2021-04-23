package tr.com.infumia.plugin.hooks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Hook;

public final class ASkyBlockHook implements Hook {

  public static final String ASKYBLOCK_ID = "ASkyBlock";

  private ASkyBlockAPI skyBlockAPI;

  @NotNull
  @Override
  public ASkyBlockWrapper create() {
    if (this.skyBlockAPI == null) {
      throw new IllegalStateException("ASkyBlock not initiated! Use ASkyBlockHook#initiate() method.");
    }
    return new ASkyBlockWrapper(this.skyBlockAPI);
  }

  @NotNull
  @Override
  public String id() {
    return ASkyBlockHook.ASKYBLOCK_ID;
  }

  @Override
  public boolean initiate() {
    if (Bukkit.getPluginManager().getPlugin("ASkyBlock") != null) {
      this.skyBlockAPI = ASkyBlockAPI.getInstance();
    }
    return this.skyBlockAPI != null;
  }
}
