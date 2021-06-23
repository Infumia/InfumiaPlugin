package tr.com.infumia.infumialib.transformer.transformers;

import tr.com.infumia.infumialib.replaceable.RpBase;
import tr.com.infumia.infumialib.replaceable.RpString;
import tr.com.infumia.infumialib.transformer.TwoSideTransformer;

/**
 * a class that represents transformers between {@link String} and {@link RpString}.
 */
public final class TransformerStringToRpString extends TwoSideTransformer.Base<String, RpString> {

  /**
   * ctor.
   */
  public TransformerStringToRpString() {
    super(String.class, RpString.class,
      RpBase::getValue,
      RpString::from,
      (s, rpString) -> rpString.value(s));
  }
}
