package tr.com.infumia.infumialib.paper.location;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains utility methods for {@link Random}.
 */
public final class RandomUtil {

  /**
   * a random cache.
   */
  public static final Random RANDOM = new SecureRandom();

  /**
   * ctor.
   */
  private RandomUtil() {
  }

  /**
   * chooses objects from the given list with the given limit.
   *
   * @param list the list to choose.
   * @param limit the limit to choose.
   * @param duplicate the duplicate to check if the object is already in the result list.
   * @param <T> the object type.
   *
   * @return a random chosen list.
   */
  @NotNull
  public static <T> List<T> chooseRandoms(@NotNull final List<T> list, final int limit, final boolean duplicate) {
    if (list.size() <= limit && !duplicate) {
      return Collections.emptyList();
    }
    final var things = new ArrayList<T>();
    var limitClone = limit;
    while (limitClone > 0) {
      final var thing = list.get(RandomUtil.RANDOM.nextInt(list.size()));
      if (things.contains(thing) && !duplicate) {
        continue;
      }
      things.add(thing);
      --limitClone;
    }
    return things;
  }
}