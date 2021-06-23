package tr.com.infumia.infumialib.scoreboard.line.lines;

import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents lines which have highlight animation.
 *
 * @param <O> type of the observers.
 */
public abstract class HighlightedLine<O> extends FramedLine<O> {

  /**
   * the context.
   */
  @NotNull
  private final String context;

  /**
   * the highlight format.
   */
  @NotNull
  private final String highlightFormat;

  /**
   * the normal format.
   */
  @NotNull
  private final String normalFormat;

  /**
   * the prefix.
   */
  @NotNull
  private final String prefix;

  /**
   * the suffix.
   */
  @NotNull
  private final String suffix;

  /**
   * ctor.
   *
   * @param context the context.
   * @param highlightFormat the highlight format.
   * @param normalFormat the normal format.
   * @param prefix the prefix.
   * @param suffix the suffix.
   */
  protected HighlightedLine(@NotNull final String context, @NotNull final String highlightFormat,
                            @NotNull final String normalFormat, @NotNull final String prefix,
                            @NotNull final String suffix) {
    super(new ArrayList<>());
    this.context = context;
    this.normalFormat = normalFormat;
    this.highlightFormat = highlightFormat;
    this.prefix = prefix;
    this.suffix = suffix;
  }

  /**
   * generates the highlighted frames.
   */
  protected final void generate() {
    var index = 0;
    while (index < this.context.length()) {
      if (this.context.charAt(index) == ' ') {
        this.addFrame(this.prefix + this.normalFormat + this.context + this.suffix);
      } else {
        final var highlighted = this.normalFormat + this.context.substring(0, index) +
          this.highlightFormat + this.context.charAt(index) +
          this.normalFormat + this.context.substring(index + 1);
        this.addFrame(this.prefix + highlighted + this.suffix);
      }
      index++;
    }
  }
}
