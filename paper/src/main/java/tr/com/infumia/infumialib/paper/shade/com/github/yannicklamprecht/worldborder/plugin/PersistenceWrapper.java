package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.plugin;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.IWorldBorder;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.PersistentWorldBorderApi;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.Position;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderData;

public class PersistenceWrapper implements PersistentWorldBorderApi {

  private final WorldBorderApi worldBorderApi;

  private final NamespacedKey worldBorderDataKey;

  private final WorldBorderDataTagType worldBorderDataTagType;

  public PersistenceWrapper(final JavaPlugin javaPlugin, final WorldBorderApi worldBorderApi) {
    this.worldBorderApi = worldBorderApi;
    this.worldBorderDataKey = new NamespacedKey(javaPlugin, "world_border_data");
    this.worldBorderDataTagType = new WorldBorderDataTagType(javaPlugin);
  }

  @Override
  public IWorldBorder getWorldBorder(final Player p) {
    final IWorldBorder worldBorder = this.worldBorderApi.getWorldBorder(p);
    final PersistentDataContainer persistentDataContainer = p.getPersistentDataContainer();
    if (persistentDataContainer.has(this.worldBorderDataKey, this.worldBorderDataTagType)) {
      this.applyWorldDataToWorldBorder(worldBorder, persistentDataContainer.get(this.worldBorderDataKey, this.worldBorderDataTagType));
    }
    return worldBorder;
  }

  @Override
  public IWorldBorder getWorldBorder(final World world) {
    return this.worldBorderApi.getWorldBorder(world);
  }

  @Override
  public void resetWorldBorderToGlobal(final Player player) {
    this.worldBorderApi.resetWorldBorderToGlobal(player);
    final PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
    if (persistentDataContainer.has(this.worldBorderDataKey, this.worldBorderDataTagType)) {
      persistentDataContainer.remove(this.worldBorderDataKey);
    }
  }

  @Override
  public void sendRedScreenForSeconds(final Player player, final long timeSeconds, final JavaPlugin javaPlugin) {
    this.worldBorderApi.sendRedScreenForSeconds(player, timeSeconds, javaPlugin);
  }

  @Override
  public void setBorder(final Player player, final double size) {
    this.worldBorderApi.setBorder(player, size);
    this.modifyAndUpdateWorldData(player, worldBorderData -> worldBorderData.setSize(size));
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
    this.worldBorderApi.setBorder(player, size, position);
    this.modifyAndUpdateWorldData(player, worldBorderData -> {
      worldBorderData.setSize(size);
      worldBorderData.setCenter(position.x(), position.z());
    });
  }

  @Override
  public void setBorder(final Player player, final double size, final long milliSeconds) {
    this.worldBorderApi.setBorder(player, size, milliSeconds);
    this.modifyAndUpdateWorldData(player, worldBorderData -> worldBorderData.setSize(size));
  }

  @Override
  public void setBorder(final Player player, final double size, final long time, final TimeUnit timeUnit) {
    this.worldBorderApi.setBorder(player, size, time, timeUnit);
    this.modifyAndUpdateWorldData(player, worldBorderData -> worldBorderData.setSize(size));
  }

  @Override
  public WorldBorderData getWorldBorderData(final Player p) {
    final PersistentDataContainer persistentDataContainer = p.getPersistentDataContainer();
    if (persistentDataContainer.has(this.worldBorderDataKey, this.worldBorderDataTagType)) {
      return persistentDataContainer.get(this.worldBorderDataKey, this.worldBorderDataTagType);
    }
    return null;
  }

  private void applyWorldDataToWorldBorder(final IWorldBorder iWorldBorder, final WorldBorderData worldBorderData) {
    worldBorderData.applyCenter((x, z) -> iWorldBorder.setCenter(new Position(x, z)));
    iWorldBorder.setSize(worldBorderData.getSize());
    iWorldBorder.setDamageBufferInBlocks(worldBorderData.getDamageBuffer());
    iWorldBorder.setDamagePerSecondPerBlock(worldBorderData.getDamageAmount());
    iWorldBorder.setWarningDistanceInBlocks(worldBorderData.getWarningDistance());
    iWorldBorder.setWarningTimeInSeconds(worldBorderData.getWarningTimeSeconds());
  }

  private void modifyAndUpdateWorldData(final Player player, final Consumer<WorldBorderData> worldBorderDataConsumer) {
    final PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
    WorldBorderData worldBorderData = new WorldBorderData();
    if (persistentDataContainer.has(this.worldBorderDataKey, this.worldBorderDataTagType)) {
      worldBorderData = persistentDataContainer.get(this.worldBorderDataKey, this.worldBorderDataTagType);
    }
    worldBorderDataConsumer.accept(worldBorderData);
    persistentDataContainer.set(this.worldBorderDataKey, this.worldBorderDataTagType, worldBorderData);
  }
}
