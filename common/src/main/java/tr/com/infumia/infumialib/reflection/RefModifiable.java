package tr.com.infumia.infumialib.reflection;

/**
 * an interface to determine modifiable objects.
 */
public interface RefModifiable {

  /**
   * checks if the field has {@code final} modifier.
   *
   * @return {@code true} if the field has {@code final} modifier.
   */
  boolean hasFinal();

  /**
   * checks if the field has {@code private} modifier.
   *
   * @return {@code true} if the field has {@code private} modifier.
   */
  boolean hasPrivate();

  /**
   * checks if the field has {@code public} modifier.
   *
   * @return {@code true} if the field has {@code public} modifier.
   */
  boolean hasPublic();

  /**
   * checks if the field has {@code static} modifier.
   *
   * @return {@code true} if the field has {@code static} modifier.
   */
  boolean hasStatic();
}
