package tr.com.infumia.infumialib.paper.mongodb;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.location.Cuboid;

@ToString
@EqualsAndHashCode
@Entity("cuboid")
@NoArgsConstructor
@AllArgsConstructor
public final class CuboidObject {

  @Id
  private int id;

  @Property("max-x")
  private double maxX;

  @Property("max-y")
  private double maxY;

  @Property("max-z")
  private double maxZ;

  @Property("min-x")
  private double minX;

  @Property("min-y")
  private double minY;

  @Property("min-z")
  private double minZ;

  private String world;

  @NotNull
  public static CuboidObject from(@NotNull final Cuboid cuboid) {
    return new CuboidObject(
      0,
      cuboid.getMaxX(),
      cuboid.getMaxY(),
      cuboid.getMaxZ(),
      cuboid.getMinX(),
      cuboid.getMinY(),
      cuboid.getMinZ(),
      cuboid.getWorld().toString());
  }

  @NotNull
  public World getBukkitWorld() {
    return Objects.requireNonNull(Bukkit.getWorld(this.world), "World not found!");
  }

  @NotNull
  public Cuboid getCuboid() {
    return new Cuboid(this.getMinimumLocation(), this.getMaximumLocation());
  }

  @NotNull
  public Location getMaximumLocation() {
    return new Location(this.getBukkitWorld(), this.maxX, this.maxY, this.maxZ);
  }

  @NotNull
  public Location getMinimumLocation() {
    return new Location(this.getBukkitWorld(), this.minX, this.minY, this.minZ);
  }
}
