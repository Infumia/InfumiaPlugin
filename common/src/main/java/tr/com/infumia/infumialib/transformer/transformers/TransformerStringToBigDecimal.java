package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link BigDecimal}.
 */
public final class TransformerStringToBigDecimal extends Transformer.Base<String, BigDecimal> {

  /**
   * ctor.
   */
  public TransformerStringToBigDecimal() {
    super(String.class, BigDecimal.class,
      BigDecimal::new);
  }
}
