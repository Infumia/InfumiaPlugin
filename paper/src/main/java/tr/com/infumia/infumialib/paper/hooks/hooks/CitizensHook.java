package tr.com.infumia.infumialib.paper.hooks.hooks;

import net.citizensnpcs.Citizens;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;

public final class CitizensHook implements Hook<CitizensWrapper> {

  public static final String CITIZENS_ID = "Citizens";

  @Nullable
  private Citizens citizens;

  @NotNull
  @Override
  public CitizensWrapper create() {
    if (this.citizens == null) {
      throw new IllegalStateException("Citizens not initiated! Use CitizensHook#initiate() method.");
    }
    return new CitizensWrapper(this.citizens);
  }

  @NotNull
  @Override
  public String id() {
    return CitizensHook.CITIZENS_ID;
  }

  @Override
  public boolean initiate() {
    final var citizens = Bukkit.getPluginManager().getPlugin("Citizens");
    if (citizens == null) {
      return false;
    }
    this.citizens = (Citizens) citizens;
    return true;
  }
}
