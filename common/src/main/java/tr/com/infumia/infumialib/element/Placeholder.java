package tr.com.infumia.infumialib.element;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class Placeholder {

  @NotNull
  private final String regex;

  @NotNull
  private final Object replace;

  @NotNull
  public static Placeholder from(@NotNull final String regex, @NotNull final Object replace) {
    return new Placeholder(regex, replace);
  }

  @NotNull
  public String replace(@NotNull final String text) {
    return text.replace(this.regex, this.replace());
  }

  @NotNull
  public List<String> replace(@NotNull final List<String> list) {
    final var result = new ArrayList<String>();
    for (final var s : list) {
      result.add(s.replace(this.regex, this.replace()));
    }
    return result;
  }

  @NotNull
  private String replace() {
    return String.valueOf(this.replace);
  }
}
