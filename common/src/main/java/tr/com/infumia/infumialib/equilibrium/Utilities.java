package tr.com.infumia.infumialib.equilibrium;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods.
 */
final class Utilities {

  /**
   * ctor.
   */
  private Utilities() {
  }

  /**
   * checks if the given {@code leftObject} equals {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object equals {@code rightObject}.
   */
  static boolean equals(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return Objects.equals(leftObject, rightObject);
  }

  /**
   * checks if the given {@code leftObject} is instance of {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is instance of {@code rightObject}.
   */
  static boolean instanceOf(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    if (!(leftObject instanceof Class<?>) || !(rightObject instanceof Class<?>)) {
      return false;
    }
    return ((Class<?>) rightObject).isAssignableFrom((Class<?>) leftObject);
  }

  /**
   * checks if the given {@code leftObject} is bigger than {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is bigger than the right object.
   */
  static boolean isBigger(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    if (!(leftObject instanceof Number) || !(rightObject instanceof Number)) {
      return false;
    }
    final Number left = (Number) leftObject;
    final Number right = (Number) rightObject;
    if (left instanceof Double) {
      return left.doubleValue() > right.doubleValue();
    }
    if (left instanceof Integer) {
      return left.intValue() > right.intValue();
    }
    if (left instanceof Long) {
      return left.longValue() > right.longValue();
    }
    if (left instanceof Float) {
      return left.floatValue() > right.floatValue();
    }
    if (left instanceof Short) {
      return (int) left.shortValue() > (int) right.shortValue();
    }
    if (left instanceof Byte) {
      return (int) left.byteValue() > (int) right.byteValue();
    }
    return false;
  }

  /**
   * checks if the given {@code leftObject} is bigger equals than {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is bigger equals than the right object.
   */
  static boolean isBiggerEquals(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    if (!(leftObject instanceof Number) || !(rightObject instanceof Number)) {
      return false;
    }
    final Number left = (Number) leftObject;
    final Number right = (Number) rightObject;
    if (left instanceof Double) {
      return left.doubleValue() >= right.doubleValue();
    }
    if (left instanceof Integer) {
      return left.intValue() >= right.intValue();
    }
    if (left instanceof Long) {
      return left.longValue() >= right.longValue();
    }
    if (left instanceof Float) {
      return left.floatValue() >= right.floatValue();
    }
    if (left instanceof Short) {
      return (int) left.shortValue() >= (int) right.shortValue();
    }
    if (left instanceof Byte) {
      return (int) left.byteValue() >= (int) right.byteValue();
    }
    return false;
  }

  /**
   * checks if the given {@code leftObject} is less than {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is less than the right object.
   */
  static boolean isLess(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return !Utilities.isBiggerEquals(leftObject, rightObject);
  }

  /**
   * checks if the given {@code leftObject} is less equals than {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is less equals than the right object.
   */
  static boolean isLessEquals(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return !Utilities.isBigger(leftObject, rightObject);
  }

  /**
   * checks if the given {@code leftObject} is not instance of {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object is not instance of {@code rightObject}.
   */
  static boolean noInstanceOf(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return !Utilities.instanceOf(leftObject, rightObject);
  }

  /**
   * checks if the given {@code leftObject} not equals {@code rightObject}.
   *
   * @param leftObject the left object to check.
   * @param rightObject the right object to check.
   *
   * @return {@code true} if the left object not equals right object.
   */
  static boolean notEqual(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return !Utilities.equals(leftObject, rightObject);
  }
}
