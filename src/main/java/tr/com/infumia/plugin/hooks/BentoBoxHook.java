package tr.com.infumia.plugin.hooks;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Hook;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.addons.AddonClassLoader;

public final class BentoBoxHook implements Hook {

  public static final String BENTOBOX_ID = "BentoBox";

  private BentoBox bentoBox;

  @NotNull
  @Override
  public BentoBoxWrapper create() {
    if (this.bentoBox == null) {
      throw new IllegalStateException("BentoBox not initiated! Use BentoBoxHook#initiate() method.");
    }
    final Optional<Addon> addon = this.bentoBox.getAddonsManager().getAddonByName("Level");
    if (!addon.isPresent()) {
      throw new IllegalStateException("BentoBox not initiated! Use BentoBoxHook#initiate() method.");
    }
    final AddonClassLoader loader = this.bentoBox.getAddonsManager().getLoader(addon.get());
    if (loader == null) {
      throw new IllegalStateException("Couldn't find any AddonClassLoader instance.");
    }
    return new BentoBoxWrapper(this.bentoBox, loader);
  }

  @NotNull
  @Override
  public String id() {
    return BentoBoxHook.BENTOBOX_ID;
  }

  @Override
  public boolean initiate() {
    if (Bukkit.getPluginManager().getPlugin("BentoBox") != null) {
      this.bentoBox = BentoBox.getInstance();
    }
    return this.bentoBox != null && this.bentoBox.getAddonsManager().getAddonByName("Level").isPresent();
  }
}
