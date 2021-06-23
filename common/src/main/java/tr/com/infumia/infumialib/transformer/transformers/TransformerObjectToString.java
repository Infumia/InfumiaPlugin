package tr.com.infumia.infumialib.transformer.transformers;

import java.util.Objects;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link Object} and {@link String}.
 */
public final class TransformerObjectToString extends Transformer.Base<Object, String> {

  /**
   * ctor.
   */
  public TransformerObjectToString() {
    super(Object.class, String.class,
      Objects::toString);
  }
}
