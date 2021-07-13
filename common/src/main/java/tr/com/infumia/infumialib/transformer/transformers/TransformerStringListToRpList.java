package tr.com.infumia.infumialib.transformer.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
      (s, rpList) -> {
        final var list = new ArrayList<String>();
        for (final var o : (List<?>) s) {
          if (o instanceof String) {
            list.add((String) o);
          } else {
            list.add(Objects.toString(o));
          }
        }
        return rpList.value(list);
      });
  }
}
