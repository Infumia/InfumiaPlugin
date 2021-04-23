//package tr.com.infumia.plugin.hooks;
//
//import com.songoda.skyblock.SkyBlock;
//import com.songoda.skyblock.island.Island;
//import com.songoda.skyblock.island.IslandManager;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import org.bukkit.Bukkit;
//import org.bukkit.plugin.Plugin;
//import org.jetbrains.annotations.NotNull;
//import tr.com.infumia.plugin.Wrapped;
//
//@RequiredArgsConstructor
//public final class FabledSkyBlockWrapper implements Wrapped {
//
//  @NotNull
//  private final SkyBlock skyBlock;
//
//  public void addIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
//    throw new UnsupportedOperationException("FabledSkyBlock does not support to add island level!");
//  }
//
//  public long getIslandLevel(@NotNull final UUID uuid) {
//    final IslandManager manager = this.skyBlock.getIslandManager();
//    final Island island = manager.getIsland(Bukkit.getOfflinePlayer(uuid));
//    if (island == null) {
//      return 0L;
//    }
//    return island.getLevel().getLevel();
//  }
//
//  public void removeIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
//    throw new UnsupportedOperationException("FabledSkyBlock does not support to remove island level!");
//  }
//
//  public void setIslandLevel(@NotNull final Plugin plugin, @NotNull final UUID uuid, final long level) {
//    throw new UnsupportedOperationException("FabledSkyBlock does not support to set island level!");
//  }
//}
