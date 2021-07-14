package tr.com.infumia.infumialib.dependencies;

import org.jetbrains.annotations.NotNull;

public final class DependencyDownloadException extends Exception {

  public DependencyDownloadException() {
  }

  public DependencyDownloadException(@NotNull final String message) {
    super(message);
  }

  public DependencyDownloadException(@NotNull final String message, @NotNull final Throwable cause) {
    super(message, cause);
  }

  public DependencyDownloadException(@NotNull final Throwable cause) {
    super(cause);
  }
}
