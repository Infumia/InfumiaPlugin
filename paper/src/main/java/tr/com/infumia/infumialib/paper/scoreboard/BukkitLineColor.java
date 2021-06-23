package tr.com.infumia.infumialib.paper.scoreboard;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.scoreboard.line.LineColor;

/**
 * a class that represents Bukkit's line colors.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitLineColor implements LineColor {

  /**
   * the color.
   */
  @NotNull
  private final ChatColor color;

  /**
   * creates a new line color instance.
   *
   * @return a newly created line color instance.
   */
  @NotNull
  public static LineColor create() {
    return new BukkitLineColor(ChatColor.RESET);
  }

  @NotNull
  @Override
  public String format(@NotNull final String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  @Nullable
  @Override
  public LineColor getByChar(final char charAt) {
    final var newColor = ChatColor.getByChar(charAt);
    return newColor == null ? null : new BukkitLineColor(newColor);
  }

  @Override
  public char getColorChar() {
    return ChatColor.COLOR_CHAR;
  }

  @NotNull
  @Override
  public String toString() {
    return this.color.toString();
  }
}
