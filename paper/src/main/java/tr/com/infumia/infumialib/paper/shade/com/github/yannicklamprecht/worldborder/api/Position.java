package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by ysl3000
 */
public final class Position {

  private final double x;

  private final double z;

  public Position(final double x, final double z) {
    this.x = x;
    this.z = z;
  }

  public static Position of(final Vector vector) {
    return new Position(vector.getX(), vector.getZ());
  }

  public static Position of(final Location location) {
    return new Position(location.getX(), location.getZ());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.z);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    final var that = (Position) obj;
    return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
      Double.doubleToLongBits(this.z) == Double.doubleToLongBits(that.z);
  }

  @Override
  public String toString() {
    return "Position[" +
      "x=" + this.x + ", " +
      "z=" + this.z + ']';
  }

  public double x() {
    return this.x;
  }

  public double z() {
    return this.z;
  }
}
