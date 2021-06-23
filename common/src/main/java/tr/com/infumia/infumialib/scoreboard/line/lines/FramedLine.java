package tr.com.infumia.infumialib.scoreboard.line.lines;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.scoreboard.line.AnimatedLine;

/**
 * a class that represents framed lines.
 *
 * @param <O> type of the observers.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FramedLine<O> implements AnimatedLine<O> {

  /**
   * the frames.
   */
  @NotNull
  protected final List<String> frames;

  /**
   * the current frame.
   */
  @Getter
  @Setter
  protected int currentFrame = -1;

  /**
   * adds the frame.
   *
   * @param string the string to add.
   */
  public final void addFrame(@NotNull final String string) {
    this.frames.add(string);
  }

  /**
   * gets the frame.
   *
   * @param index the index to get.
   *
   * @return frame.
   */
  @NotNull
  public final String getFrame(final int index) {
    return this.frames.get(index);
  }

  /**
   * obtains the frame size.
   *
   * @return frame size.
   */
  public final int getTotalLength() {
    return this.frames.size();
  }

  /**
   * removes the frame.
   *
   * @param frame the frame to remove.
   */
  public final void removeFrame(@NotNull final String frame) {
    this.frames.remove(frame);
  }

  /**
   * sets the frame.
   *
   * @param index the index to set.
   * @param frame the frame to set.
   */
  public final void setFrame(final int index, @NotNull final String frame) {
    this.frames.set(index, frame);
  }

  @Nullable
  @Override
  public String getCurrent(@NotNull final O observer) {
    if (this.currentFrame <= -1) {
      return null;
    }
    return this.frames.get(this.currentFrame);
  }

  @NotNull
  @Override
  public String getNext(@NotNull final O observer) {
    this.currentFrame++;
    if (this.currentFrame >= this.frames.size()) {
      this.currentFrame = 0;
    }
    return this.frames.get(this.currentFrame);
  }

  @NotNull
  @Override
  public String getPrevious(@NotNull final O observer) {
    this.currentFrame--;
    if (this.currentFrame <= -1) {
      this.currentFrame = this.frames.size() - 1;
    }
    return this.frames.get(this.currentFrame);
  }
}
