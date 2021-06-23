package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Integer}.
 */
public final class TransformerStringToInteger extends Transformer.Base<String, Integer> {

  /**
   * ctor.
   */
  public TransformerStringToInteger() {
    super(String.class, Integer.class,
      s -> new BigDecimal(s).intValueExact());
  }
}
