package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigInteger;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link BigInteger}.
 */
public final class TransformerStringToBigInteger extends Transformer.Base<String, BigInteger> {

  /**
   * ctor.
   */
  public TransformerStringToBigInteger() {
    super(String.class, BigInteger.class,
      BigInteger::new);
  }
}
