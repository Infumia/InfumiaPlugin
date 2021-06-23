package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Long}.
 */
public final class TransformerStringToLong extends Transformer.Base<String, Long> {

  /**
   * ctor.
   */
  public TransformerStringToLong() {
    super(String.class, Long.class,
      s -> new BigDecimal(s).longValueExact());
  }
}
