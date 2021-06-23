package tr.com.infumia.infumialib.transformer.transformers;

import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Double}.
 */
public final class TransformerStringToDouble extends Transformer.Base<String, Double> {

  /**
   * ctor.
   */
  public TransformerStringToDouble() {
    super(String.class, Double.class,
      Double::parseDouble);
  }
}
