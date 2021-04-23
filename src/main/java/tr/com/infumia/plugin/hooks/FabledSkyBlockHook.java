//package tr.com.infumia.plugin.hooks;
//
//import com.songoda.skyblock.SkyBlock;
//import org.bukkit.Bukkit;
//import org.jetbrains.annotations.NotNull;
//import tr.com.infumia.plugin.Hook;
//
//public final class FabledSkyBlockHook implements Hook {
//
//  public static final String FABLED_SKY_BLOCK_ID = "FabledSkyBlock";
//
//  private SkyBlock skyBlock;
//
//  @NotNull
//  @Override
//  public FabledSkyBlockWrapper create() {
//    if (this.skyBlock == null) {
//      throw new IllegalStateException("FabledSkyBlock not initiated! Use FabledSkyBlockHook#initiate() method.");
//    }
//    return new FabledSkyBlockWrapper(this.skyBlock);
//  }
//
//  @NotNull
//  @Override
//  public String id() {
//    return FabledSkyBlockHook.FABLED_SKY_BLOCK_ID;
//  }
//
//  @Override
//  public boolean initiate() {
//    if (Bukkit.getPluginManager().getPlugin("FabledSkyBlock") != null) {
//      this.skyBlock = SkyBlock.getInstance();
//    }
//    return this.skyBlock != null;
//  }
//}
