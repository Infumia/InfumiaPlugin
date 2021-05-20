package tr.com.infumia.infumialib.paper.element;

import java.util.List;
import java.util.stream.Collectors;
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
    return list.stream()
      .map(s -> s.replace(this.regex, this.replace()))
      .collect(Collectors.toList());
  }

  @NotNull
  private String replace() {
    return String.valueOf(this.replace);
  }
}
