package tr.com.infumia.infumialib;

import org.jetbrains.annotations.NotNull;

public final class LoadingException extends RuntimeException {

  public LoadingException(@NotNull final String message) {
    super(message);
  }

  public LoadingException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }
}
