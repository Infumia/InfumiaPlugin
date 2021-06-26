package tr.com.infumia.infumialib.paper.mongodb;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import tr.com.infumia.infumialib.paper.location.Cuboid;

@ToString
@EqualsAndHashCode
@Entity("min-max")
public final class MinMaxBlockLocation {

  @Id
  private ObjectId id;

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
