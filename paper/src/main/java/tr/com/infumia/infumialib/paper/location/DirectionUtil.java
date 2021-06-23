package tr.com.infumia.infumialib.paper.location;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for {@link Directions}.
 */
public final class DirectionUtil {

  /**
   * ctor.
   */
  private DirectionUtil() {
  }

  /**
   * obtains a {@link Directions} from the given player.
   *
   * @param player the player to obtain.
   *
   * @return direction of the given player.
   */
  @NotNull
  public static Directions directionOf(@NotNull final Player player) {
    return DirectionUtil.directionOf(player.getLocation());
  }

  /**
   * obtains a {@link Directions} from the given location.
   *
   * @param location the location to obtain.
   *
   * @return direction of the given location.
   */
  @NotNull
  public static Directions directionOf(@NotNull final Location location) {
    return DirectionUtil.directionOf(location.getYaw());
  }

  /**
   * obtains a {@link Directions} from the given yaw.
   *
   * @param yaw the yaw to obtain.
   *
   * @return direction of the given yaw.
   */
  @NotNull
  public static Directions directionOf(final float yaw) {
    final var directions = List.of(
      Directions.SOUTH,
      Directions.WEST,
      Directions.NORTH,
      Directions.EAST);
    return DirectionUtil.directionOf(directions, yaw);
  }

  /**
   * obtains a double {@link Directions} from the given player.
   *
   * @param player the player to obtain.
   *
   * @return direction of the given player.
   */
  @NotNull
  public static Directions doubleDirectionOf(@NotNull final Player player) {
    return DirectionUtil.doubleDirectionOf(player.getLocation());
  }

  /**
   * obtains a double {@link Directions} from the given location.
   *
   * @param location the location to obtain.
   *
   * @return direction of the given location.
   */
  @NotNull
  public static Directions doubleDirectionOf(@NotNull final Location location) {
    return DirectionUtil.doubleDirectionOf(location.getYaw());
  }

  /**
   * obtains a double {@link Directions} from the given yaw.
   *
   * @param yaw the yaw to obtain.
   *
   * @return direction of the given yaw.
   */
  @NotNull
  public static Directions doubleDirectionOf(final float yaw) {
    final var directions = List.of(
      Directions.SOUTH,
      Directions.SOUTHWEST,
      Directions.WEST,
      Directions.NORTHWEST,
      Directions.NORTH,
      Directions.NORTHEAST,
      Directions.EAST,
      Directions.SOUTHEAST);
    return DirectionUtil.doubleDirectionOf(directions, yaw);
  }

  /**
   * obtains a {@link Directions} from the given direction list with the given yaw.
   *
   * @param directions the directions to obtain.
   * @param yaw the yaw to obtain.
   *
   * @return direction of the yaw from the direction list.
   */
  @NotNull
  private static Directions directionOf(@NotNull final List<Directions> directions, final float yaw) {
    return directions.get((int) (yaw + 45.0f) % 360 / 90);
  }

  /**
   * obtains a double {@link Directions} from the given direction list with the given yaw.
   *
   * @param directions the directions to obtain.
   * @param yaw the yaw to obtain.
   *
   * @return direction of the yaw from the direction list.
   */
  @NotNull
  private static Directions doubleDirectionOf(@NotNull final List<Directions> directions, final float yaw) {
    return directions.get((int) (yaw + 22.5f) % 360 / 45);
  }
}