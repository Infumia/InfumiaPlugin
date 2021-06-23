package tr.com.infumia.infumialib.paper.bukkititembuilder.color;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.Builder;

/**
 * a class that contains utility methods for colors.
 */
public final class XColor {

  /**
   * the aqua.
   */
  public static final XColor AQUA;

  /**
   * the black.
   */
  public static final XColor BLACK;

  /**
   * the blue.
   */
  public static final XColor BLUE;

  /**
   * the bold.
   */
  public static final XColor BOLD;

  /**
   * the dark aqua.
   */
  public static final XColor DARK_AQUA;

  /**
   * the dark blue.
   */
  public static final XColor DARK_BLUE;

  /**
   * the dark gray.
   */
  public static final XColor DARK_GRAY;

  /**
   * the dark green.
   */
  public static final XColor DARK_GREEN;

  /**
   * the dark purple.
   */
  public static final XColor DARK_PURPLE;

  /**
   * the dark red.
   */
  public static final XColor DARK_RED;

  /**
   * the gold.
   */
  public static final XColor GOLD;

  /**
   * the gray.
   */
  public static final XColor GRAY;

  /**
   * the green.
   */
  public static final XColor GREEN;

  /**
   * the hex.
   */
  public static final XColor HEX;

  /**
   * the italic.
   */
  public static final XColor ITALIC;

  /**
   * the light purple.
   */
  public static final XColor LIGHT_PURPLE;

  /**
   * the obfuscated.
   */
  public static final XColor OBFUSCATED;

  /**
   * the red.
   */
  public static final XColor RED;

  /**
   * the reset.
   */
  public static final XColor RESET;

  /**
   * the strikethrough.
   */
  public static final XColor STRIKETHROUGH;

  /**
   * the underline.
   */
  public static final XColor UNDERLINE;

  /**
   * the white.
   */
  public static final XColor WHITE;

  /**
   * the yellow.
   */
  public static final XColor YELLOW;

  /**
   * the colors by character.
   */
  private static final Map<Character, XColor> BY_CHAR;

  /**
   * the colors by name.
   */
  private static final Map<String, XColor> BY_NAME;

  /**
   * the custom colors by hex.
   */
  private static final Map<String, XColor> CUSTOM_BY_HEX;

  /**
   * the custom colors by name.
   */
  private static final Map<String, XColor> CUSTOM_BY_NAME;

  /**
   * the custom colors by rgb.
   */
  private static final TreeMap<String, XColor> CUSTOM_BY_RGB;

  /**
   * the format pattern.
   */
  private static final Pattern FORMAT = Pattern.compile("(&[klmnorKLMNOR])");

  /**
   * the gradient pattern.
   */
  private static final Pattern GRADIENT = Pattern.compile("(\\{(#[^{]*?)>})(.*?)(\\{(#.*?)<(>?)})");

  /**
   * the hex color pattern.
   */
  private static final Pattern HEX_COLOR_NAME = Pattern.compile("(\\{#)([a-zA-Z_]{3,})(})");

  /**
   * the hex color name last.
   */
  private static final Pattern HEX_COLOR_NAME_LAST = Pattern.compile("(\\{#)([a-zA-Z_]{3,})(})(?!.*\\{#)");

  /**
   * the hex color regex pattern.
   */
  private static final Pattern HEX_COLOR_REGEX = Pattern.compile("(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(})");

  /**
   * the hex color regex last pattern.
   */
  private static final Pattern HEX_COLOR_REGEX_LAST = Pattern.compile("(\\{#)([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})(})(?!.*\\{#)");

  /**
   * the colorize pattern.
   */
  private static final Pattern HEX_DE_COLORIZE_NAME = Pattern.compile("(([&§])x)((([&§])[0-9A-Fa-f]){6})");

  /**
   * the random.
   */
  private static final Random RANDOM = new SecureRandom();

  /**
   * the blue.
   */
  @Getter
  private final int blue;

  /**
   * the char.
   */
  private final char ch;

  /**
   * the color.
   */
  private final boolean color;

  /**
   * the green.
   */
  @Getter
  private final int green;

  /**
   * the hex code.
   */
  @Nullable
  @Getter
  private final String hexCode;

  /**
   * the name.
   */
  @Nullable
  @Getter
  private final String name;

  /**
   * the pattern.
   */
  @Nullable
  private final Pattern pattern;

  /**
   * the red.
   */
  @Getter
  private final int red;

  /**
   * the reset.
   */
  private final boolean reset;

  static {
    BY_CHAR = new HashMap<>();
    BY_NAME = new HashMap<>();
    CUSTOM_BY_HEX = new LinkedHashMap<>();
    CUSTOM_BY_NAME = new HashMap<>();
    CUSTOM_BY_RGB = new TreeMap<>();
    AQUA = new XColor("Aqua", 'b', 85, 255, 255);
    BLACK = new XColor("Black", '0', 0, 0, 0);
    BLUE = new XColor("Blue", '9', 85, 85, 255);
    BOLD = new XColor("Bold", 'l', false);
    DARK_AQUA = new XColor("Dark_Aqua", '3', 0, 170, 170);
    DARK_BLUE = new XColor("Dark_Blue", '1', 0, 0, 170);
    DARK_GRAY = new XColor("Dark_Gray", '8', 85, 85, 85);
    DARK_GREEN = new XColor("Dark_Green", '2', 0, 170, 0);
    DARK_PURPLE = new XColor("Dark_Purple", '5', 170, 0, 170);
    DARK_RED = new XColor("Dark_Red", '4', 170, 0, 0);
    GOLD = new XColor("Gold", '6', 255, 170, 0);
    GRAY = new XColor("Gray", '7', 170, 170, 170);
    GREEN = new XColor("Green", 'a', 85, 255, 85);
    HEX = new XColor("Hex", 'x', false, false);
    ITALIC = new XColor("Italic", 'o', false);
    LIGHT_PURPLE = new XColor("Light_Purple", 'd', 255, 85, 255);
    OBFUSCATED = new XColor("Obfuscated", 'k', false);
    RED = new XColor("Red", 'c', 255, 85, 85);
    RESET = new XColor("Reset", 'r', false, true);
    STRIKETHROUGH = new XColor("Strikethrough", 'm', false);
    UNDERLINE = new XColor("Underline", 'n', false);
    WHITE = new XColor("White", 'f', 255, 255, 255);
    YELLOW = new XColor("Yellow", 'e', 255, 255, 85);
    for (var x = 0.0f; x <= 1.0f; x += (float) 0.1) {
      for (var z = 0.1f; z <= 1.0f; z += (float) 0.1) {
        for (var y = 0.0f; y <= 1.0f; y += (float) 0.03) {
          final var color = Color.getHSBColor(y, x, z);
          final var builder = new StringBuilder()
            .append(Integer.toHexString((color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue() & 0xFFFFFF));
          while (builder.length() < 6) {
            builder.append("0").append(builder);
          }
          XColor.getClosest(builder.toString());
        }
      }
    }
  }

  /**
   * ctor.
   *
   * @param name the name.
   * @param hexCode the hex code.
   */
  public XColor(@Nullable final String name, @NotNull final String hexCode) {
    this.color = true;
    this.reset = false;
    this.name = name;
    this.pattern = null;
    this.ch = '\u0000';
    var tmpHexCode = hexCode.startsWith("#") ? hexCode.substring(1) : hexCode;
    int tempRed = -1;
    int tempGreen = -1;
    int tempBlue = -1;
    try {
      tempRed = Integer.valueOf(tmpHexCode.substring(0, 2), 16);
      tempGreen = Integer.valueOf(tmpHexCode.substring(2, 4), 16);
      tempBlue = Integer.parseInt(tmpHexCode.substring(4, 6), 16);
    } catch (final Throwable e) {
      tmpHexCode = null;
    }
    this.hexCode = tmpHexCode;
    this.red = tempRed;
    this.green = tempGreen;
    this.blue = tempBlue;
  }

  /**
   * ctor.
   *
   * @param name the name.
   * @param ch the character.
   * @param color the color.
   * @param reset the reset.
   * @param red the red.
   * @param green the green.
   * @param blue the blue.
   */
  public XColor(@NotNull final String name, final char ch, final boolean color, final boolean reset,
                final int red, final int green, final int blue) {
    this.name = name;
    this.ch = ch;
    this.color = color;
    this.reset = reset;
    this.pattern = Pattern.compile("(?i)(&[" + ch + "])");
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.hexCode = null;
    if (Builder.VERSION >= 16 || !name.equalsIgnoreCase("Hex")) {
      XColor.BY_CHAR.put(ch, this);
      XColor.BY_NAME.put(this.name.toLowerCase(Locale.ROOT).replace("_", ""), this);
    }
  }

  /**
   * ctor.
   *
   * @param hexCode the hex code.
   */
  public XColor(@NotNull final String hexCode) {
    this(null, hexCode);
  }

  /**
   * ctor.
   *
   * @param name the name.
   * @param ch the character.
   * @param red the red.
   * @param green the green.
   * @param blue the blue.
   */
  public XColor(@NotNull final String name, final char ch, final int red, final int green, final int blue) {
    this(name, ch, true, false, red, green, blue);
  }

  /**
   * ctor.
   *
   * @param name the name.
   * @param ch the character.
   * @param color the color.
   */
  public XColor(@NotNull final String name, final char ch, final boolean color) {
    this(name, ch, color, false);
  }

  /**
   * ctor.
   *
   * @param name the name.
   * @param ch the character.
   * @param color the color.
   * @param reset the reset.
   */
  public XColor(@NotNull final String name, final char ch, final boolean color, final boolean reset) {
    this(name, ch, color, reset, -1, -1, -1);
  }

  /**
   * adds the custom color to {@link #CUSTOM_BY_HEX} and {@link #CUSTOM_BY_NAME}.
   *
   * @param name the name to add.
   * @param hexCode the hex code to add.
   * @param color the color to add.
   */
  public static void addCustomColor(@NotNull final String name, @NotNull final String hexCode,
                                    @NotNull final XColor color) {
    XColor.CUSTOM_BY_HEX.put(hexCode, color);
    XColor.CUSTOM_BY_NAME.put(name, color);
  }

  /**
   * colorizes the given list.
   *
   * @param list the list to colorize.
   *
   * @return colored list.
   */
  @NotNull
  public static List<String> colorize(@NotNull final Collection<String> list) {
    return list.stream()
      .map(XColor::colorize)
      .collect(Collectors.toList());
  }

  /**
   * colorize the given text.
   *
   * @param text the text to colorize.
   *
   * @return colored text.
   */
  @NotNull
  public static String colorize(@NotNull final String text) {
    var replaced = XColor.gradient(text);
    if (!replaced.contains("{#")) {
      return ChatColor.translateAlternateColorCodes('&', replaced);
    }
    var match = XColor.HEX_COLOR_REGEX.matcher(replaced);
    while (match.find()) {
      final var string = match.group();
      final var builder = new StringBuilder("§x");
      final var charArray = string.substring(2, string.length() - 1).toCharArray();
      for (final var ch : charArray) {
        builder.append('§').append(ch);
        if (string.substring(2, string.length() - 1).length() == 3) {
          builder.append('§').append(ch);
        }
      }
      replaced = replaced.replace(string, builder.toString());
    }
    match = XColor.HEX_COLOR_NAME.matcher(replaced);
    while (match.find()) {
      final var string2 = match.group(2);
      final var color = XColor.getByCustomName(string2.toLowerCase().replace("_", ""));
      if (color.isEmpty()) {
        continue;
      }
      final var hexCode = color.get().getHexCode();
      if (hexCode == null) {
        continue;
      }
      final var builder = new StringBuilder("§x");
      final var charArray = hexCode.toCharArray();
      for (final var ch : charArray) {
        builder.append('§').append(ch);
      }
      replaced = replaced.replace(match.group(), builder.toString());
    }
    return ChatColor.translateAlternateColorCodes('&', replaced);
  }

  /**
   * de-colorizes the list.
   *
   * @param list the list to de-colorize.
   *
   * @return un-colorized text.
   */
  @NotNull
  public static List<String> deColorize(@NotNull final Collection<String> list) {
    return list.stream()
      .map(XColor::deColorize)
      .collect(Collectors.toList());
  }

  /**
   * de-colorizes the text.
   *
   * @param text the text to de-colorize.
   *
   * @return un-colorized text.
   */
  @NotNull
  public static String deColorize(@NotNull final String text) {
    return XColor.deColorize(text, true);
  }

  /**
   * de-colorizes the text.
   *
   * @param text the text to de-colorize.
   * @param colorizeBefore the colorize before to de-colorize.
   *
   * @return de-colorized text.
   */
  @NotNull
  public static String deColorize(@NotNull final String text, final boolean colorizeBefore) {
    var replaced = text;
    if (colorizeBefore) {
      replaced = XColor.colorize(replaced);
    }
    replaced = replaced.replace("§", "&");
    if (!replaced.contains("&x")) {
      return replaced;
    }
    final var match = XColor.HEX_DE_COLORIZE_NAME.matcher(replaced);
    while (match.find()) {
      final var reg = match.group(3).replace("&", "");
      final var custom = XColor.CUSTOM_BY_HEX.get(reg.toLowerCase());
      if (custom != null && custom.getName() != null) {
        replaced = replaced.replace(match.group(), "{#" + custom.getName()
          .toLowerCase(Locale.ROOT)
          .replace("_", "") + "}");
      } else {
        replaced = replaced.replace(match.group(), "{#" + reg + "}");
      }
    }
    return replaced;
  }

  /**
   * flats the text.
   *
   * @param text the text to flat.
   *
   * @return flatten text.
   */
  @NotNull
  public static String flatten(@NotNull final String text) {
    return XColor.deColorize(text, true).replace("&", "＆").replace("{#", "{＆#");
  }

  /**
   * gets custom color by name.
   *
   * @param name the name to get.
   *
   * @return custom color.
   */
  @NotNull
  public static Optional<XColor> getByCustomName(@NotNull final String name) {
    if (!name.equalsIgnoreCase("random")) {
      return Optional.ofNullable(XColor.CUSTOM_BY_NAME.get(name.toLowerCase().replace("_", "")));
    }
    final var valuesList = new ArrayList<>(XColor.CUSTOM_BY_NAME.values());
    final var randomIndex = XColor.RANDOM.nextInt(valuesList.size());
    return Optional.ofNullable(valuesList.get(randomIndex));
  }

  /**
   * gets color from hex code.
   *
   * @param hexCode the hex code to get.
   *
   * @return color by hex code.
   */
  @NotNull
  public static Optional<XColor> getByHex(@NotNull final String hexCode) {
    var replaced = hexCode;
    if (replaced.startsWith("{#")) {
      replaced = replaced.substring("{#".length());
    }
    if (replaced.endsWith("}")) {
      replaced = replaced.substring(0, replaced.length() - "}".length());
    }
    return Optional.ofNullable(XColor.CUSTOM_BY_HEX.get(replaced.toLowerCase().replace("_", "")));
  }

  /**
   * gets closes color of the hex code.
   *
   * @param hexCode the hex code to get.
   *
   * @return closes color.
   */
  @Nullable
  public static XColor getClosest(@NotNull final String hexCode) {
    var replaced = hexCode;
    if (replaced.startsWith("#")) {
      replaced = replaced.substring(1);
    }
    var closest = XColor.CUSTOM_BY_RGB.get(replaced);
    if (closest != null) {
      return closest;
    }
    final Color color1;
    try {
      color1 = new Color(Integer.valueOf(
        replaced.substring(0, 2), 16),
        Integer.valueOf(replaced.substring(2, 4), 16),
        Integer.valueOf(replaced.substring(4, 6), 16));
    } catch (final Throwable e) {
      return null;
    }
    var distance = Double.MAX_VALUE;
    for (final var entry : XColor.CUSTOM_BY_HEX.entrySet()) {
      final var value = entry.getValue();
      final var colorHexCode = value.getHexCode();
      if (colorHexCode == null) {
        continue;
      }
      final var color2 = new Color(
        Integer.valueOf(colorHexCode.substring(0, 2), 16),
        Integer.valueOf(colorHexCode.substring(2, 4), 16),
        Integer.valueOf(colorHexCode.substring(4, 6), 16));
      final var red2 = color2.getRed();
      final var red1 = color1.getRed();
      final var remaining = red2 + red1 >> 1;
      final var red = red2 - red1;
      final var green = color2.getGreen() - color1.getGreen();
      final var blue = color2.getBlue() - color1.getBlue();
      final var dist = Math.sqrt(((512 + remaining) * red * red >> 8) + 4 * green * green + ((767 - remaining) * blue * blue >> 8));
      if (dist < distance) {
        closest = value;
        distance = dist;
      }
    }
    if (closest != null) {
      XColor.CUSTOM_BY_RGB.put(replaced, closest);
      return closest;
    }
    XColor.CUSTOM_BY_RGB.put(replaced, null);
    return null;
  }

  /**
   * gets colors.
   *
   * @param text the text to get.
   *
   * @return color.
   */
  @NotNull
  public static Optional<XColor> getColor(@NotNull final String text) {
    final var deColorized = XColor.deColorize(text);
    if (deColorized.contains("{#")) {
      var match = XColor.HEX_COLOR_REGEX_LAST.matcher(deColorized);
      if (match.find()) {
        return Optional.of(new XColor(match.group(2)));
      }
      match = XColor.HEX_COLOR_NAME_LAST.matcher(deColorized);
      if (match.find()) {
        return Optional.of(new XColor(match.group(2)));
      }
    }
    var replaced = XColor.deColorize(text).replace("&", "");
    final var color = XColor.getXColor(replaced);
    if (color.isPresent()) {
      return color;
    }
    final var length = deColorized.length();
    if (length <= 1 || !String.valueOf(deColorized.charAt(length - 2)).equalsIgnoreCase("&")) {
      return Optional.empty();
    }
    replaced = replaced.substring(replaced.length() - 1);
    for (final var one : XColor.BY_CHAR.entrySet()) {
      if (String.valueOf(one.getKey()).equalsIgnoreCase(replaced)) {
        return Optional.ofNullable(one.getValue());
      }
    }
    return Optional.empty();
  }

  /**
   * gets formats of the text.
   *
   * @param text the text to get.
   *
   * @return formats of the text.
   */
  @NotNull
  public static Optional<XColor> getFormat(@NotNull final String text) {
    var replaced = text;
    final var deColorized = XColor.deColorize(replaced);
    replaced = replaced.replace("§", "&");
    final var color = XColor.getXColor(replaced);
    if (color.isEmpty()) {
      return color;
    }
    if (deColorized.length() <= 1 ||
      !String.valueOf(deColorized.charAt(deColorized.length() - 2)).equalsIgnoreCase("&")) {
      return Optional.empty();
    }
    replaced = replaced.substring(replaced.length() - 1);
    for (final var entry : XColor.BY_CHAR.entrySet()) {
      if (!String.valueOf(entry.getKey()).equalsIgnoreCase(replaced)) {
        continue;
      }
      return entry.getValue().isFormat()
        ? Optional.of(entry.getValue())
        : Optional.empty();
    }
    return Optional.empty();
  }

  /**
   * gets formats from the text.
   *
   * @param text the text to get.
   *
   * @return formats.
   */
  @NotNull
  public static Collection<XColor> getFormats(@NotNull final String text) {
    final var replaced = text.replace("§", "&");
    final var formats = new HashSet<XColor>();
    final var match = XColor.FORMAT.matcher(replaced);
    while (match.find()) {
      XColor.getFormat(match.group())
        .filter(XColor::isFormat)
        .ifPresent(formats::add);
    }
    return formats;
  }

  /**
   * gets last colors of the text.
   *
   * @param text the text to get.
   *
   * @return last colors of the text.
   */
  @NotNull
  public static String getLastColors(@NotNull final String text) {
    final var replaced = XColor.deColorize(text);
    var match = XColor.HEX_COLOR_REGEX_LAST.matcher(replaced);
    if (match.find()) {
      final var colorByHex = match.group(0);
      if (replaced.endsWith(colorByHex)) {
        return colorByHex;
      }
      final var split = replaced.split(XColor.escape(colorByHex), 2);
      final var last = XColor.getLastColors(split[1]);
      return last.isEmpty() ? colorByHex : last;
    }
    match = XColor.HEX_COLOR_NAME_LAST.matcher(replaced);
    if (match.find()) {
      final var colorByName = match.group();
      if (replaced.endsWith(colorByName)) {
        return colorByName;
      }
      final var split = replaced.split(XColor.escape(colorByName), 2);
      final var last = XColor.getLastColors(split[1]);
      return last.isEmpty() ? colorByName : last;
    }
    return ChatColor.getLastColors(XColor.colorize(replaced));
  }

  /**
   * obtains random color.
   *
   * @return random color.
   */
  @NotNull
  public static XColor getRandomColor() {
    final var colors = XColor.BY_NAME.values().stream()
      .filter(XColor::isColor)
      .collect(Collectors.toCollection(ArrayList::new));
    Collections.shuffle(colors);
    return colors.get(0);
  }

  /**
   * gradients the text.
   *
   * @param text the text to gradient.
   *
   * @return gradient text.
   */
  @NotNull
  public static String gradient(@NotNull final String text) {
    var replaced = text;
    final var matcher = XColor.GRADIENT.matcher(replaced);
    while (matcher.find()) {
      final var match = matcher.group();
      final var color1Optional = XColor.getColor("{#" + matcher.group(2).replace("#", "") + "}");
      final var color2Optional = XColor.getColor("{#" + matcher.group(5).replace("#", "") + "}");
      if (color1Optional.isEmpty()) {
        continue;
      }
      if (color2Optional.isEmpty()) {
        continue;
      }
      final var color1 = color1Optional.get();
      final var color2 = color2Optional.get();
      var gradientText = matcher.group(3);
      final var continuous = !matcher.group(6).isEmpty();
      final var builder = new StringBuilder();
      final var formats = XColor.getFormats(gradientText);
      gradientText = XColor.stripColor(gradientText);
      for (var i = 0; i < gradientText.length(); ++i) {
        final var ch = gradientText.charAt(i);
        var length = gradientText.length();
        length = Math.max(length, 2);
        final var percent = i * 100.0 / (length - 1);
        final var mixedColor = color1.mixColors(color2, percent);
        builder.append("{#").append(mixedColor.getHexCode()).append("}");
        if (!formats.isEmpty()) {
          for (final var format : formats) {
            builder.append("&").append(format.getChar());
          }
        }
        builder.append(ch);
      }
      if (continuous) {
        builder.append("{#").append(matcher.group(5).replace("#", "")).append(">").append("}");
      }
      replaced = replaced.replace(match, builder.toString());
      if (!continuous) {
        continue;
      }
      replaced = XColor.gradient(replaced);
    }
    return replaced;
  }

  /**
   * mixes the colors.
   *
   * @param color1 the color 1 to mix.
   * @param color2 the color 2 to mix.
   * @param percent the percent to mix.
   *
   * @return mixed colors.
   */
  @NotNull
  public static XColor mixColors(@NotNull final XColor color1, @NotNull final XColor color2, final double percent) {
    final var finalPercent = percent / 100.0;
    final var inversePercent = 1.0 - finalPercent;
    final var redPart = (int) (color2.getRed() * finalPercent + color1.getRed() * inversePercent);
    final var greenPart = (int) (color2.getGreen() * finalPercent + color1.getGreen() * inversePercent);
    final var bluePart = (int) (color2.getBlue() * finalPercent + color1.getBlue() * inversePercent);
    return new XColor(String.format("#%02x%02x%02x", redPart, greenPart, bluePart));
  }

  /**
   * strips colors.
   *
   * @param text the text to strip.
   *
   * @return strip text.
   */
  @NotNull
  public static String stripColor(@NotNull final String text) {
    return ChatColor.stripColor(XColor.colorize(text));
  }

  /**
   * adds escapes to the text.
   *
   * @param text the text to add.
   *
   * @return escape text.
   */
  @NotNull
  private static String escape(@NotNull final String text) {
    return text.replace("#", "\\#").replace("{", "\\{").replace("}", "\\}");
  }

  /**
   * gets color from {@link #BY_NAME} or {@link #CUSTOM_BY_NAME}.
   *
   * @param text the text to get.
   *
   * @return color instance.
   */
  @NotNull
  private static Optional<XColor> getXColor(@NotNull final String text) {
    if (text.length() <= 1) {
      return Optional.empty();
    }
    final var formatted = text.toLowerCase(Locale.ROOT).replace("_", "");
    var color = XColor.BY_NAME.get(formatted);
    if (color != null) {
      return Optional.of(color);
    }
    color = XColor.CUSTOM_BY_NAME.get(formatted);
    if (color != null) {
      return Optional.of(color);
    }
    return Optional.empty();
  }

  /**
   * gets the bukkit color code.
   *
   * @return bukkit color code.
   */
  @NotNull
  public String getBukkitColorCode() {
    if (this.hexCode != null) {
      return XColor.colorize("{#" + this.hexCode + "}");
    }
    return "§" + this.getChar();
  }

  /**
   * obtains the char.
   *
   * @return char.
   */
  public char getChar() {
    return this.ch;
  }

  /**
   * obtains the clean name.
   *
   * @return clean name.
   */
  @Nullable
  public String getCleanName() {
    if (this.name == null) {
      return null;
    }
    return this.name.replace("_", "");
  }

  /**
   * obtains the bukkit color.
   *
   * @return bukkit color.
   */
  @Nullable
  public ChatColor getColor() {
    return ChatColor.getByChar(this.getChar());
  }

  /**
   * obtains the color code.
   *
   * @return color code.
   */
  @NotNull
  public String getColorCode() {
    if (this.hexCode != null) {
      return "{#" + this.hexCode + "}";
    }
    return "&" + this.ch;
  }

  /**
   * obtains the formatted hex.
   *
   * @return formatted hex.
   */
  @NotNull
  public String getFormattedHex() {
    return this.getFormattedHex("");
  }

  /**
   * obtains the formatted hex.
   *
   * @param suffix the suffix t oget.
   *
   * @return formatted hex.
   */
  @NotNull
  public String getFormattedHex(@NotNull final String suffix) {
    return "{#" + this.hexCode + suffix + "}";
  }

  /**
   * get rgb color.
   *
   * @return color from rgb.
   */
  @Nullable
  public org.bukkit.Color getRGBColor() {
    if (this.blue < 0) {
      return null;
    }
    return org.bukkit.Color.fromRGB(this.getRed(), this.getGreen(), this.getBlue());
  }

  /**
   * obtains the color.
   *
   * @return color.
   */
  public boolean isColor() {
    return this.color;
  }

  /**
   * checks if the color is a format.
   *
   * @return {@code true} if the color is a format.
   */
  public boolean isFormat() {
    return !this.color && !this.reset;
  }

  /**
   * mixes the colors.
   *
   * @param color the color to mix.
   * @param percent the percent to mix.
   *
   * @return mixed colors.
   */
  @NotNull
  public XColor mixColors(@NotNull final XColor color, final double percent) {
    return XColor.mixColors(this, color, percent);
  }

  @NotNull
  @Override
  public String toString() {
    return this.getBukkitColorCode();
  }
}
