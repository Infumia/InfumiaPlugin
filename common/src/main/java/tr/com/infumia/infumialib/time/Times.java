package tr.com.infumia.infumialib.time;

import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Times {

  private final long ONE_HOUR = Times.ONE_DAY / 24;

  private final long ONE_MINUTE = Times.ONE_HOUR / 60;

  private final long ONE_SECOND = Times.ONE_MINUTE / 60;

  private final long ONE_YEAR = 1000L * 60L * 60L * 24L * 30L * 12L;

  private final long ONE_MONTH = Times.ONE_YEAR / 12;

  private final long ONE_DAY = Times.ONE_MONTH / 30;

  public long byDay(final long time) {
    return TimeUnit.SECONDS.toDays(time);
  }

  public long byHour(final long time) {
    return TimeUnit.SECONDS.toHours(time);
  }

  public long byMinute(final long time) {
    return TimeUnit.SECONDS.toMinutes(time);
  }

  public long byMonth(final long time) {
    return time / 2592000;
  }

  public long bySecond(final long time) {
    return time;
  }

  public long byYear(final long time) {
    return time / 31536000;
  }

  public static long[] getTimeDifference(final long difference) {
    var tempDiff = difference;
    final var result = new long[7];
    final var year = tempDiff / Times.ONE_YEAR;
    tempDiff %= Times.ONE_YEAR;
    final var month = tempDiff / Times.ONE_MONTH;
    tempDiff %= Times.ONE_MONTH;
    final var day = tempDiff / Times.ONE_DAY;
    tempDiff %= Times.ONE_DAY;
    final var hour = tempDiff / Times.ONE_HOUR;
    tempDiff %= Times.ONE_HOUR;
    final var minute = tempDiff / Times.ONE_MINUTE;
    tempDiff %= Times.ONE_MINUTE;
    final var second = tempDiff / Times.ONE_SECOND;
    final var millisecond = tempDiff % Times.ONE_SECOND;
    result[0] = year;
    result[1] = month;
    result[2] = day;
    result[3] = hour;
    result[4] = minute;
    result[5] = second;
    result[6] = millisecond;
    return result;
  }
}
