package tr.com.infumia.infumialib.transformer.transformers;

import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Float}.
 */
public final class TransformerStringToFloat extends Transformer.Base<String, Float> {

  /**
   * ctor.
   */
  public TransformerStringToFloat() {
    super(String.class, Float.class,
      Float::parseFloat);
  }
}
