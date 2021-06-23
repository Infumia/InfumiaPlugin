package tr.com.infumia.infumialib.transformer.transformers;

import java.util.function.Function;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link String}.
 */
public final class TransformerStringToString extends Transformer.Base<String, String> {

  /**
   * ctor.
   */
  public TransformerStringToString() {
    super(String.class, String.class,
      Function.identity());
  }
}
