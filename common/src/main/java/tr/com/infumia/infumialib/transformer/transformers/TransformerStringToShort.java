package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Short}.
 */
public final class TransformerStringToShort extends Transformer.Base<String, Short> {

  /**
   * ctor.
   */
  public TransformerStringToShort() {
    super(String.class, Short.class,
      s -> new BigDecimal(s).shortValueExact());
  }
}
