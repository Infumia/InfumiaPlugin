package tr.com.infumia.plugin.hook.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.plugin.hook.Hook;
import tr.com.infumia.plugin.hook.Wrapped;

public final class VaultHook implements Hook {

  public static final String VAULT_ID = "Vault";

  @Nullable
  private Economy economy;

  @NotNull
  @Override
  public Wrapped create() {
    if (this.economy == null) {
      throw new IllegalStateException("Vault not initiated! Use VaultHook#initiate() method.");
    }
    return new VaultWrapper(this.economy);
  }

  @NotNull
  @Override
  public String id() {
    return VaultHook.VAULT_ID;
  }

  @Override
  public boolean initiate() {
    if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    final var economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      this.economy = economyProvider.getProvider();
    }
    return this.economy != null;
  }
}
