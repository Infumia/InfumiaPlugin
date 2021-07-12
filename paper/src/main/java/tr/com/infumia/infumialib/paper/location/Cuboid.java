package tr.com.infumia.infumialib.paper.location;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains minimum and maximum location to make a cuboid.
 *
 * @todo #1:15m Make methods, which have many calculation in it, completable future.
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Cuboid {

  /**
   * the maximum x.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double maxX;

  /**
   * the maximum y.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double maxY;

  /**
   * the maximum z.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double maxZ;

  /**
   * the minimum x.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double minX;

  /**
   * the minimum y.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double minY;

  /**
   * the minimum z.
   */
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final double minZ;

  /**
   * the world.
   */
  @NotNull
  private final World world;

  /**
   * the world name.
   */
  @NotNull
  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final String worldName;

  /**
   * the world unique id.
   */
  @NotNull
  private final UUID worldUniqueId;

  /**
   * ctor.
   *
   * @param world the world.
   * @param minimumLocation the minimum location.
   * @param maximumLocation the maximum location.
   * @param block the block.
   *
   * @throws IllegalStateException if worlds of the given locations are not same.
   */
  public Cuboid(@NotNull final World world, @NotNull final Location minimumLocation,
                @NotNull final Location maximumLocation, final boolean block) {
    final var minWorld = Objects.requireNonNull(minimumLocation.getWorld(), "minimum world");
    final var maxWorld = Objects.requireNonNull(maximumLocation.getWorld(), "maximum world");
    Preconditions.checkState(Objects.equals(minWorld, maxWorld), "%s and %s are not equals!", minWorld, maxWorld);
    this.world = world;
    this.worldName = world.getName();
    this.worldUniqueId = world.getUID();
    if (block) {
      this.minX = Math.min(minimumLocation.getBlockX(), maximumLocation.getBlockX());
      this.minY = Math.min(minimumLocation.getBlockY(), maximumLocation.getBlockY());
      this.minZ = Math.min(minimumLocation.getBlockZ(), maximumLocation.getBlockZ());
      this.maxX = Math.max(minimumLocation.getBlockX(), maximumLocation.getBlockX());
      this.maxY = Math.max(minimumLocation.getBlockY(), maximumLocation.getBlockY());
      this.maxZ = Math.max(minimumLocation.getBlockZ(), maximumLocation.getBlockZ());
    } else {
      this.minX = Math.min(minimumLocation.getX(), maximumLocation.getX());
      this.minY = Math.min(minimumLocation.getY(), maximumLocation.getY());
      this.minZ = Math.min(minimumLocation.getZ(), maximumLocation.getZ());
      this.maxX = Math.max(minimumLocation.getX(), maximumLocation.getX());
      this.maxY = Math.max(minimumLocation.getY(), maximumLocation.getY());
      this.maxZ = Math.max(minimumLocation.getZ(), maximumLocation.getZ());
    }
  }

  /**
   * ctor.
   *
   * @param minimumLocation the minimum location.
   * @param maximumLocation the maximum location.
   * @param block the block.
   *
   * @throws IllegalStateException if worlds of the given locations are not same.
   */
  public Cuboid(@NotNull final Location minimumLocation, @NotNull final Location maximumLocation, final boolean block) {
    this(LocationUtil.validWorld(minimumLocation), minimumLocation, maximumLocation, block);
  }

  /**
   * ctor.
   *
   * @param minimumLocation the minimum location.
   * @param maximumLocation the maximum location.
   *
   * @throws IllegalStateException if worlds of the given locations are not same.
   */
  public Cuboid(@NotNull final Location minimumLocation, @NotNull final Location maximumLocation) {
    this(minimumLocation, maximumLocation, false);
  }

  /**
   * ctor.
   *
   * @param world the world.
   * @param minimumLocation the minimum location.
   * @param maximumLocation the maximum location.
   *
   * @throws IllegalStateException if worlds of the given locations are not same.
   */
  public Cuboid(@NotNull final World world, @NotNull final Location minimumLocation,
                @NotNull final Location maximumLocation) {
    this(world, minimumLocation, maximumLocation, false);
  }

  /**
   * obtains blocks inside the cuboid.
   *
   * @return the block list within the cuboid.
   */
  @NotNull
  public List<Block> blocks() {
    this.checkWorldNullability();
    final var result = new ArrayList<Block>();
    for (double x = this.minX; x <= this.maxX; ++x) {
      for (double y = this.minY; y <= this.maxY; ++y) {
        for (double z = this.minZ; z <= this.maxZ; ++z) {
          result.add(this.world.getBlockAt(new Location(this.world, x, y, z)));
        }
      }
    }
    return result;
  }

  /**
   * obtains center of the cuboid.
   *
   * @return center of the cuboid.
   */
  @NotNull
  public Location center() {
    this.checkWorldNullability();
    return new Location(
      this.world,
      this.minX + (this.maxX - this.minX) / 2.0d,
      this.minY + (this.maxY - this.minY) / 2.0d,
      this.minZ + (this.maxZ - this.minZ) / 2.0d);
  }

  /**
   * obtains center bottom of the cuboid.
   *
   * @return center bottom of the cuboid.
   */
  @NotNull
  public Location centerBottom() {
    this.checkWorldNullability();
    return new Location(
      this.world,
      this.minX + (this.maxX - this.minX) / 2.0d,
      this.minY,
      this.minZ + (this.maxZ - this.minZ) / 2.0d);
  }

  /**
   * obtains center bottom of the cuboid.
   *
   * @return center bottom of the cuboid.
   */
  @NotNull
  public Location centerBottomPlus() {
    this.checkWorldNullability();
    final var x = this.minX + (this.maxX - this.minX) / 2.0d + 0.5;
    final var z = this.minZ + (this.maxZ - this.minZ) / 2.0d + 0.5;
    return new Location(this.world, x, this.minY, z);
  }

  /**
   * obtains center bottom of the cuboid.
   *
   * @return center bottom of the cuboid.
   */
  @NotNull
  public Location centerHighestBottom() {
    this.checkWorldNullability();
    final var x = this.minX + (this.maxX - this.minX) / 2.0d;
    final var z = this.minZ + (this.maxZ - this.minZ) / 2.0d;
    return new Location(
      this.world,
      x,
      this.world.getHighestBlockAt((int) x, (int) z).getY(),
      z);
  }

  /**
   * obtains center bottom of the cuboid.
   *
   * @return center bottom of the cuboid.
   */
  @NotNull
  public Location centerHighestBottomUpFloor() {
    this.checkWorldNullability();
    final var x = this.minX + (this.maxX - this.minX) / 2.0d;
    final var z = this.minZ + (this.maxZ - this.minZ) / 2.0d;
    return new Location(
      this.world,
      (int) x,
      this.world.getHighestBlockAt((int) x, (int) z).getY() + 1,
      (int) z);
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInX(@NotNull final Location location) {
    return location.getX() >= this.minX && location.getX() <= this.maxX;
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInXY(@NotNull final Location location) {
    return this.isInX(location) && this.isInY(location);
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInXYZ(@NotNull final Location location) {
    return this.isInX(location) && this.isInY(location) && this.isInZ(location);
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInXZ(@NotNull final Location location) {
    return this.isInX(location) && this.isInZ(location);
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInY(@NotNull final Location location) {
    return this.minY <= location.getY() && location.getY() <= this.maxY;
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInYZ(@NotNull final Location location) {
    return this.isInY(location) && this.isInZ(location);
  }

  /**
   * checks if the given location is in the cuboid.
   *
   * @param location the location to check.
   *
   * @return {@code true} if the given location is in the cuboid.
   */
  public boolean isInZ(@NotNull final Location location) {
    return this.minZ <= location.getZ() && location.getZ() <= this.maxZ;
  }

  /**
   * obtains locations inside the cuboid.
   *
   * @return the location list within the cuboid.
   */
  @NotNull
  public List<Location> locations() {
    this.checkWorldNullability();
    final var result = new ArrayList<Location>();
    for (double x = this.minX; x <= this.maxX; ++x) {
      for (double y = this.minY; y <= this.maxY; ++y) {
        for (double z = this.minZ; z <= this.maxZ; ++z) {
          result.add(new Location(this.world, x, y, z));
        }
      }
    }
    return result;
  }

  /**
   * obtains a random block list from the given limit.
   *
   * @param limit the limit to choose.
   * @param duplicate the duplicate to check if the block is already in the result list.
   *
   * @return a random block list.
   */
  @NotNull
  public List<Block> randomBlocks(final int limit, final boolean duplicate) {
    return RandomUtil.chooseRandoms(this.blocks(), limit, duplicate);
  }

  /**
   * obtains a random location list from the given limit.
   *
   * @param limit the limit to choose.
   * @param duplicate the duplicate to check if the location is already in the result list.
   *
   * @return a random location list.
   */
  @NotNull
  public List<Location> randomLocations(final int limit, final boolean duplicate) {
    return RandomUtil.chooseRandoms(this.locations(), limit, duplicate);
  }

  /**
   * removes all blocks where are in the cuboid.
   */
  public void removeAll() {
    this.blocks().forEach(block -> block.setType(Material.AIR));
  }

  /**
   * sets all blocks inside the cuboid.
   *
   * @param material the material to set.
   */
  public void set(@NotNull final Material material) {
    this.blocks().forEach(block -> block.setType(material));
  }

  /**
   * checks if the world is null or not.
   *
   * @throws NullPointerException if the world is currently null.
   */
  private void checkWorldNullability() {
    Objects.requireNonNull(Bukkit.getWorld(this.worldUniqueId), "world");
  }
}