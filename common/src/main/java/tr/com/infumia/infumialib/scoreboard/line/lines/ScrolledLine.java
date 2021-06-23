package tr.com.infumia.infumialib.scoreboard.line.lines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.scoreboard.line.LineColor;

/**
 * a class that represents scrolled lines.
 *
 * @param <O> type of the observers.
 */
public abstract class ScrolledLine<O> extends FramedLine<O> {

  /**
   * the list.
   */
  @NotNull
  private final List<String> list = new ArrayList<>();

  /**
   * the message.
   */
  @NotNull
  private final String message;

  /**
   * the position.
   */
  private final AtomicInteger position = new AtomicInteger();

  /**
   * the space between.
   */
  private final int spaceBetween;

  /**
   * the width.
   */
  private final int width;

  /**
   * the line color.
   */
  @NotNull
  private LineColor color;

  /**
   * ctor.
   *
   * @param message the message.
   * @param spaceBetween the space between.
   * @param width the width.
   * @param color the color.
   */
  protected ScrolledLine(@NotNull final String message, final int spaceBetween, final int width,
                         @NotNull final LineColor color) {
    super(new ArrayList<>());
    this.message = color.format(message);
    this.spaceBetween = spaceBetween;
    this.width = width;
    this.color = color;
  }

  @NotNull
  @Override
  public final String getNext(@NotNull final O observer) {
    var sb = this.generateNextBuilder();
    if (sb.charAt(sb.length() - 1) == this.color.getColorChar()) {
      sb.setCharAt(sb.length() - 1, ' ');
    }
    if (sb.charAt(0) != this.color.getColorChar()) {
      return this.color.toString() + sb;
    }
    final var charAt = this.color.getByChar(sb.charAt(1));
    if (charAt == null) {
      return this.color.toString() + sb;
    }
    this.color = charAt;
    sb = this.generateNextBuilder();
    if (sb.charAt(0) != ' ') {
      sb.setCharAt(0, ' ');
    }
    return this.color.toString() + sb;
  }

  /**
   * generates the frame list.
   */
  protected final void generate() {
    var tempWidth = this.width;
    var tempSpaceBetween = this.spaceBetween;
    var tempMessage = this.message;
    if (tempMessage.length() < tempWidth) {
      final var sb = new StringBuilder(tempMessage);
      while (sb.length() < tempWidth) {
        sb.append(" ");
      }
      tempMessage = sb.toString();
    }
    tempWidth -= 2;
    if (tempWidth < 1) {
      tempWidth = 1;
    }
    if (tempSpaceBetween < 0) {
      tempSpaceBetween = 0;
    }
    for (var i = 0; i < tempMessage.length() - tempWidth; i++) {
      this.list.add(tempMessage.substring(i, i + tempWidth));
    }
    final var space = new StringBuilder();
    for (var i = 0; i < tempSpaceBetween; ++i) {
      this.list.add(tempMessage.substring(tempMessage.length() - tempWidth + Math.min(i, tempWidth)) + space);
      if (space.length() < tempWidth) {
        space.append(" ");
      }
    }
    for (var i = 0; i < tempWidth - tempSpaceBetween; ++i) {
      this.list.add(tempMessage.substring(tempMessage.length() - tempWidth + tempSpaceBetween + i) + space + tempMessage.substring(0, i));
    }
    for (var i = 0; i < tempSpaceBetween; i++) {
      if (i > space.length()) {
        break;
      }
      this.list.add(space.substring(0, space.length() - i) + tempMessage.substring(0, tempWidth - Math.min(tempSpaceBetween, tempWidth) + i));
    }
  }

  /**
   * generated the next string builder.
   *
   * @return next string builder.
   */
  @NotNull
  private StringBuilder generateNextBuilder() {
    return new StringBuilder(this.list.get(this.position.getAndIncrement() % this.list.size()));
  }
}
