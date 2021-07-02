package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.plugin;

import java.util.Optional;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderData;

public class WorldBorderDataTagType implements PersistentDataType<PersistentDataContainer, WorldBorderData> {

  private final NamespacedKey damageAmountKey;

  private final NamespacedKey damageBufferInBlocksKey;

  private final NamespacedKey sizeKey;

  private final NamespacedKey warningDistanceKey;

  private final NamespacedKey warningTimeSecondsKey;

  private final NamespacedKey xKey;

  private final NamespacedKey zKey;

  public WorldBorderDataTagType(final JavaPlugin javaPlugin) {
    this.sizeKey = new NamespacedKey(javaPlugin, "size");
    this.xKey = new NamespacedKey(javaPlugin, "center_x");
    this.zKey = new NamespacedKey(javaPlugin, "center_z");
    this.damageBufferInBlocksKey = new NamespacedKey(javaPlugin, "damage_buffer_in_blocks");
    this.damageAmountKey = new NamespacedKey(javaPlugin, "damage_amount");
    this.warningTimeSecondsKey = new NamespacedKey(javaPlugin, "warning_time_seconds");
    this.warningDistanceKey = new NamespacedKey(javaPlugin, "warning_distance");
  }

  @Override
  public Class<PersistentDataContainer> getPrimitiveType() {
    return PersistentDataContainer.class;
  }

  @Override
  public Class<WorldBorderData> getComplexType() {
    return WorldBorderData.class;
  }

  @Override
  public PersistentDataContainer toPrimitive(final WorldBorderData complex, final PersistentDataAdapterContext context) {
    final PersistentDataContainer persistentDataContainer = context.newPersistentDataContainer();
    persistentDataContainer.set(this.sizeKey, PersistentDataType.DOUBLE, complex.getSize());
    complex.applyCenter((x, z) -> {
      persistentDataContainer.set(this.xKey, PersistentDataType.DOUBLE, x);
      persistentDataContainer.set(this.zKey, PersistentDataType.DOUBLE, z);
    });
    persistentDataContainer.set(this.damageBufferInBlocksKey, PersistentDataType.DOUBLE, complex.getDamageBuffer());
    persistentDataContainer.set(this.damageAmountKey, PersistentDataType.DOUBLE, complex.getDamageAmount());
    persistentDataContainer.set(this.warningTimeSecondsKey, PersistentDataType.INTEGER, complex.getWarningTimeSeconds());
    persistentDataContainer.set(this.warningDistanceKey, PersistentDataType.INTEGER, complex.getWarningDistance());
    return persistentDataContainer;
  }

  @Override
  public WorldBorderData fromPrimitive(final PersistentDataContainer primitive, final PersistentDataAdapterContext context) {
    final WorldBorderData worldBorderData = new WorldBorderData();
    this.get(primitive, this.sizeKey, PersistentDataType.DOUBLE).ifPresent(worldBorderData::setSize);
    final Optional<Double> centerX = this.get(primitive, this.xKey, PersistentDataType.DOUBLE);
    final Optional<Double> centerZ = this.get(primitive, this.zKey, PersistentDataType.DOUBLE);
    if (centerX.isPresent() && centerZ.isPresent()) {
      worldBorderData.setCenter(centerX.get(), centerZ.get());
    }
    this.get(primitive, this.damageBufferInBlocksKey, PersistentDataType.DOUBLE).ifPresent(worldBorderData::setDamageBuffer);
    this.get(primitive, this.damageAmountKey, PersistentDataType.DOUBLE).ifPresent(worldBorderData::setDamageAmount);
    this.get(primitive, this.warningTimeSecondsKey, PersistentDataType.INTEGER).ifPresent(worldBorderData::setWarningTimeSeconds);
    this.get(primitive, this.warningDistanceKey, PersistentDataType.INTEGER).ifPresent(worldBorderData::setWarningDistance);
    return worldBorderData;
  }

  private <T, Z> Optional<Z> get(final PersistentDataContainer persistentDataContainer, final NamespacedKey namespacedKey, final PersistentDataType<T, Z> type) {
    if (persistentDataContainer.has(namespacedKey, type)) {
      return Optional.ofNullable(persistentDataContainer.get(namespacedKey, type));
    }
    return Optional.empty();
  }
}
