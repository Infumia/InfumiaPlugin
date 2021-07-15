package tr.com.infumia.infumialib.paper.dependencies;

import org.jetbrains.annotations.NotNull;

final class DependencyDownloadException extends Exception {

  DependencyDownloadException(@NotNull final String message) {
    super(message);
  }

  DependencyDownloadException(@NotNull final Throwable cause) {
    super(cause);
  }
}
