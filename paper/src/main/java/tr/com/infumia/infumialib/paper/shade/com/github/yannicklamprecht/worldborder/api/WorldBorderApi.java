package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public interface WorldBorderApi {

  IWorldBorder getWorldBorder(Player p);

  IWorldBorder getWorldBorder(World world);

  void resetWorldBorderToGlobal(Player player);

  void sendRedScreenForSeconds(Player player, long timeSeconds, JavaPlugin javaPlugin);

  void setBorder(Player player, double size);

  void setBorder(Player player, double size, Vector vector);

  void setBorder(Player player, double size, Location location);

  void setBorder(Player player, double size, Position position);

  void setBorder(Player player, double size, long milliSeconds);

  void setBorder(Player player, double size, long time, TimeUnit timeUnit);
}
