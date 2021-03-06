package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.hooks.Hook;
import world.bentobox.bentobox.BentoBox;

public final class BentoBoxHook implements Hook<BentoBoxWrapper> {

  public static final String BENTO_BOX_ID = "BentoBox";

  @Nullable
  private BentoBox bentoBox;

  @NotNull
  @Override
  public BentoBoxWrapper create() {
    if (this.bentoBox == null) {
      throw new IllegalStateException("BentoBox not initiated! Use BentoBoxHook#initiate() method.");
    }
    final var addonsManager = this.bentoBox.getAddonsManager();
    final var addon = addonsManager.getAddonByName("Level");
    if (addon.isEmpty()) {
      throw new IllegalStateException("BentoBox not initiated! Use BentoBoxHook#initiate() method.");
    }
    final var loader = addonsManager.getLoader(addon.get());
    if (loader == null) {
      throw new IllegalStateException("Couldn't find any AddonClassLoader instance.");
    }
    return new BentoBoxWrapper(this.bentoBox, loader);
  }

  @NotNull
  @Override
  public String id() {
    return BentoBoxHook.BENTO_BOX_ID;
  }

  @Override
  public boolean initiate() {
    if (Bukkit.getPluginManager().getPlugin("BentoBox") != null) {
      this.bentoBox = BentoBox.getInstance();
    }
    return this.bentoBox != null && this.bentoBox.getAddonsManager().getAddonByName("Level").isPresent();
  }
}
