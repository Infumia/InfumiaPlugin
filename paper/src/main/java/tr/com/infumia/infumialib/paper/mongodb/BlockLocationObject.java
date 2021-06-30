package tr.com.infumia.infumialib.paper.mongodb;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@ToString
@EqualsAndHashCode
@Entity("location")
@NoArgsConstructor
@AllArgsConstructor
public final class BlockLocationObject {

  @Id
  private int id;

  private String world;

  private double x;

  private double y;

  private double z;

  @NotNull
  public static BlockLocationObject from(@NotNull final Location location) {
    return new BlockLocationObject(
      0,
      location.getWorld().getName(),
      location.getX(),
      location.getY(),
      location.getZ());
  }

  @NotNull
  public Location getLocation() {
    return new Location(Objects.requireNonNull(Bukkit.getWorld(this.world), "World not found!"), this.x, this.y, this.z);
  }
}
