package tr.com.infumia.infumialib.transformer.postprocessor;

import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

/**
 * a functional interface that manipulates inputs.
 */
@FunctionalInterface
public interface Manipulator extends UnaryOperator<@NotNull String> {

}
