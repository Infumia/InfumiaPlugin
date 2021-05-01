package tr.com.infumia.plugin.hooks.hooks;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.plugin.hooks.Hook;

public final class LuckPermsHook implements Hook<LuckPermsWrapper> {

  public static final String LUCK_PERMS_ID = "LuckPerms";

  @Nullable
  private LuckPerms luckPerms;

  @Override
  @NotNull
  public LuckPermsWrapper create() {
    if (this.luckPerms == null) {
      throw new IllegalStateException("LuckPerms not initiated! Use LuckPermsHook#initiate method.");
    }
    return new LuckPermsWrapper(this.luckPerms);
  }

  @NotNull
  @Override
  public String id() {
    return LuckPermsHook.LUCK_PERMS_ID;
  }

  @Override
  public boolean initiate() {
    final var check = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;
    if (check) {
      final RegisteredServiceProvider<LuckPerms> provider =
        Bukkit.getServicesManager().getRegistration(LuckPerms.class);
      if (provider != null) {
        this.luckPerms = provider.getProvider();
      }
    }
    return this.luckPerms != null;
  }
}
