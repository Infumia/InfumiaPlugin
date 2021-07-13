package tr.com.infumia.infumialib.time;

import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Times {

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
    return TimeUnit.SECONDS.toSeconds(time);
  }

  public long byYear(final long time) {
    return time / 31536000;
  }
}
