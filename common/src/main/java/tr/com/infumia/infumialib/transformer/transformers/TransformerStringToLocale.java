package tr.com.infumia.infumialib.transformer.transformers;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.Transformer;

/**
 * a class that represents transformers between {@link String} and {@link Locale}.
 */
public final class TransformerStringToLocale extends Transformer.Base<String, Locale> {

  /**
   * ctor.
   */
  public TransformerStringToLocale() {
    super(String.class, Locale.class,
      TransformerStringToLocale::toLocale);
  }

  @Nullable
  private static Locale toLocale(@NotNull final String s) {
    final var trim = s.trim();
    if (trim.isEmpty()) {
      return Locale.ROOT;
    }
    final var strings = trim.split("_");
    if (trim.contains("_") && strings.length != 2) {
      return Locale.ROOT;
    }
    if (strings.length != 2) {
      return null;
    }
    return new Locale(strings[0], strings[1]);
  }
}
