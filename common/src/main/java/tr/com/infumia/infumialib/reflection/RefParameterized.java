package tr.com.infumia.infumialib.reflection;

import java.util.Optional;
import java.util.function.Function;

/**
 * an interface to determine primitive class types.
 *
 * @param <T> the primitive class type.
 */
public interface RefParameterized<T> extends Function<Function<Class<?>[], Optional<T>>, Optional<T>> {

}
