package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.v1_16;

import static tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.ConsumerSupplierTupel.of;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.AbstractWorldBorder;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.Position;
import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderAction;

public class WorldBorder extends AbstractWorldBorder {

  private final net.minecraft.server.v1_16_R3.WorldBorder handle;

  public WorldBorder(final Player player) {
    this(new net.minecraft.server.v1_16_R3.WorldBorder());
    this.handle.world = ((CraftWorld) player.getWorld()).getHandle();
  }

  public WorldBorder(final World world) {
    this(((CraftWorld) world).getHandle().getWorldBorder());
  }

  public WorldBorder(final net.minecraft.server.v1_16_R3.WorldBorder worldBorder) {
    super(
      of(
        position -> worldBorder.setCenter(position.x(), position.z()),
        () -> new Position(worldBorder.getCenterX(), worldBorder.getCenterZ())
      ),
      () -> new Position(worldBorder.e(), worldBorder.f()),
      () -> new Position(worldBorder.g(), worldBorder.h()),
      of(worldBorder::setSize, worldBorder::getSize),
      of(worldBorder::setDamageBuffer, worldBorder::getDamageBuffer),
      of(worldBorder::setDamageAmount, worldBorder::getDamageAmount),
      of(worldBorder::setWarningTime, worldBorder::getWarningTime),
      of(worldBorder::setWarningDistance, worldBorder::getWarningDistance),
      (Location location) -> worldBorder
        .isInBounds(new ChunkCoordIntPair(location.getBlockX(), location.getBlockZ())),
      worldBorder::transitionSizeBetween
    );
    this.handle = worldBorder;
  }

  @Override
  public void send(final Player player, final WorldBorderAction worldBorderAction) {
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldBorder(this.handle, PacketPlayOutWorldBorder.EnumWorldBorderAction.valueOf(worldBorderAction.name())));
  }
}
