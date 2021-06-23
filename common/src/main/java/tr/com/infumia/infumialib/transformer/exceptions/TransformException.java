package tr.com.infumia.infumialib.transformer.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * an exception that thrown when something goes for transformer.
 */
public final class TransformException extends RuntimeException {

  /**
   * ctor.
   *
   * @param message the message.
   */
  public TransformException(@NotNull final String message) {
    super(message);
  }

  /**
   * ctor.
   *
   * @param message the message.
   * @param cause the cause.
   */
  public TransformException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }
}
