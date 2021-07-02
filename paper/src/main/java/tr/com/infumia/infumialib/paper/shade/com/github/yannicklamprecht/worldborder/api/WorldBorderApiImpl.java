package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WorldBorderApiImpl implements WorldBorderApi {

  private final Function<World, IWorldBorder> getWorldBorder;

  private final Function<Player, IWorldBorder> getWorldBorderPlayer;

  public WorldBorderApiImpl(final Function<Player, IWorldBorder> getWorldBorderPlayer,
                            final Function<World, IWorldBorder> getWorldBorder) {
    this.getWorldBorderPlayer = getWorldBorderPlayer;
    this.getWorldBorder = getWorldBorder;
  }

  @Override
  public IWorldBorder getWorldBorder(final Player p) {
    return this.getWorldBorderPlayer.apply(p);
  }

  @Override
  public IWorldBorder getWorldBorder(final World world) {
    return this.getWorldBorder.apply(world);
  }

  @Override
  public void resetWorldBorderToGlobal(final Player player) {
    this.getWorldBorder(player.getWorld()).send(player, WorldBorderAction.INITIALIZE);
  }

  @Override
  public void sendRedScreenForSeconds(final Player player, final long timeSeconds, final JavaPlugin javaPlugin) {
    final IWorldBorder border = this.getWorldBorder(player);
    border.setWarningDistanceInBlocks((int) border.getSize());
    border.send(player, WorldBorderAction.SET_WARNING_BLOCKS);
    Bukkit.getScheduler().runTaskLater(javaPlugin,
      () -> {
        border.setWarningDistanceInBlocks(0);
        border.send(player, WorldBorderAction.SET_WARNING_BLOCKS);
      }, timeSeconds * 20L);
  }

  @Override
  public void setBorder(final Player player, final double size) {
    this.setBorder(player, size, player.getWorld().getSpawnLocation());
  }

  @Override
  public void setBorder(final Player player, final double size, final Vector vector) {
    this.setBorder(player, size, Position.of(vector));
  }

  @Override
  public void setBorder(final Player player, final double size, final Location location) {
    this.setBorder(player, size, Position.of(location));
  }

  @Override
  public void setBorder(final Player player, final double size, final Position position) {
    final IWorldBorder border = this.getWorldBorder(player);
    border.setSize(size);
    border.setCenter(position);
    border.send(player, WorldBorderAction.SET_SIZE);
    border.send(player, WorldBorderAction.SET_CENTER);
  }

  @Override
  public void setBorder(final Player player, final double size, final long milliSeconds) {
    final IWorldBorder worldBorder = this.getWorldBorder(player);
    worldBorder.lerp(worldBorder.getSize(), size, milliSeconds);
    worldBorder.send(player, WorldBorderAction.LERP_SIZE);
  }

  @Override
  public void setBorder(final Player player, final double size, final long time, final TimeUnit timeUnit) {
    this.setBorder(player, size, timeUnit.toMillis(time));
  }
}
