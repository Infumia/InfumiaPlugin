package tr.com.infumia.infumialib.transformer.transformers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import tr.com.infumia.infumialib.replaceable.RpBase;
import tr.com.infumia.infumialib.replaceable.RpList;
import tr.com.infumia.infumialib.transformer.TwoSideTransformer;

/**
 * a class that represents transformers between {@link String} {@link List} and {@link RpList}.
 */
@SuppressWarnings("rawtypes")
public final class TransformerStringListToRpList extends TwoSideTransformer.Base<List, RpList> {

  /**
   * ctor.
   */
  public TransformerStringListToRpList() {
    super(List.class, RpList.class,
      RpBase::getValue,
      RpList::fromObjects,
      (s, rpList) -> rpList.value(((List<?>) s).stream().map(Objects::toString).collect(Collectors.toList())));
  }
}
