package tr.com.infumia.infumialib.transformer.transformers;

import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Boolean}.
 */
public final class TransformerStringToBoolean extends Transformer.Base<String, Boolean> {

  /**
   * ctor.
   */
  public TransformerStringToBoolean() {
    super(String.class, Boolean.class,
      Boolean::parseBoolean);
  }
}
