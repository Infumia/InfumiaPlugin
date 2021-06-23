package tr.com.infumia.infumialib.scoreboard.line;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an interface to determine line colors.
 */
public interface LineColor {

  /**
   * the format.
   *
   * @param text the text to format.
   *
   * @return formatted text.
   */
  @NotNull
  String format(@NotNull String text);

  /**
   * gets a new color.
   *
   * @param charAt the char at to get.
   *
   * @return a new color.
   */
  @Nullable
  LineColor getByChar(char charAt);

  /**
   * obtains the color char.
   *
   * @return color char.
   */
  char getColorChar();

  @NotNull
  @Override
  String toString();
}
