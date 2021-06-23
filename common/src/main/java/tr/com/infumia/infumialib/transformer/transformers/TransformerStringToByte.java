package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Byte}.
 */
public final class TransformerStringToByte extends Transformer.Base<String, Byte> {

  /**
   * ctor.
   */
  public TransformerStringToByte() {
    super(String.class, Byte.class,
      s -> new BigDecimal(s).byteValueExact());
  }
}
