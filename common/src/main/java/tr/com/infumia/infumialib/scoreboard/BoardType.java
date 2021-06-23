package tr.com.infumia.infumialib.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * an enum class that contains board types.
 */
@Getter
@RequiredArgsConstructor
public enum BoardType {
  /**
   * the kohi.
   */
  KOHI(true, 15),
  /**
   * the viper.
   */
  VIPER(true, -1),
  /**
   * the modern.
   */
  MODERN(false, 1);

  /**
   * the descending.
   */
  private final boolean descending;

  /**
   * the start number.
   */
  private final int startNumber;
}
