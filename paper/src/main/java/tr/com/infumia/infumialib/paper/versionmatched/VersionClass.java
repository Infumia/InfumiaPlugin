package tr.com.infumia.infumialib.paper.versionmatched;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.version.BukkitVersion;

/**
 * a class that represents version classes.
 *
 * @param <T> type of the class.
 */
@RequiredArgsConstructor
final class VersionClass<T> {

  /**
   * the numbers.
   */
  private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  /**
   * the raw class name.
   */
  @NotNull
  private final String rawClassName;

  /**
   * the version class.
   */
  @NotNull
  @Getter
  private final Class<? extends T> versionClass;

  /**
   * ctor.
   *
   * @param versionClass the version class.
   */
  VersionClass(@NotNull final Class<? extends T> versionClass) {
    this(versionClass.getSimpleName(), versionClass);
  }

  /**
   * matches the given version.
   *
   * @param version the version to match.
   *
   * @return {@code true} if the version matched.
   */
  boolean match(@NotNull final BukkitVersion version) {
    return this.version().equals(version);
  }

  /**
   * obtains the version.
   *
   * @return version.
   */
  @NotNull
  private BukkitVersion version() {
    final var sub = this.versionSubString();
    Preconditions.checkState(sub != -1, "version() -> Invalid name for \"%s\"", this.rawClassName);
    return new BukkitVersion(this.rawClassName.substring(sub));
  }

  /**
   * obtains the version sub string.
   *
   * @return version sub string.
   */
  private int versionSubString() {
    final var subString = new AtomicInteger();
    finalBreak:
    for (final var name : this.rawClassName.toCharArray()) {
      for (final var number : VersionClass.NUMBERS) {
        if (name == number) {
          break finalBreak;
        }
      }
      subString.incrementAndGet();
    }
    return subString.get();
  }
}
