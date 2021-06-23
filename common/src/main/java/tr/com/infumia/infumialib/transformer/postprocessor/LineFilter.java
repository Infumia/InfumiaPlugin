package tr.com.infumia.infumialib.transformer.postprocessor;

import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/**
 * a functional interface that filters lines.
 */
@FunctionalInterface
public interface LineFilter extends Predicate<@NotNull String> {

}
