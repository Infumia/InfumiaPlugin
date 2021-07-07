package tr.com.infumia.infumialib.paper.mongodb;

import dev.morphia.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.transformer.serializers.SentTitle;
import tr.com.infumia.infumialib.replaceable.RpString;

@ToString
@EqualsAndHashCode
@Entity("title")
@NoArgsConstructor
@AllArgsConstructor
public final class SentTitleObject {

  private int fadeIn;

  private int fadeOut;

  private int stay;

  @Nullable
  private String subTitle;

  @Nullable
  private String title;

  @NotNull
  public static SentTitleObject from(@NotNull final SentTitle sentTitle) {
    final var subTitle = sentTitle.getSubTitle();
    final var title = sentTitle.getTitle();
    return new SentTitleObject(
      sentTitle.getFadeIn(),
      sentTitle.getFadeOut(),
      sentTitle.getStay(),
      subTitle == null ? null : subTitle.getValue(),
      title == null ? null : title.getValue()
    );
  }

  @NotNull
  public SentTitle getSentTitle(@NotNull final SentTitle field) {
    final var title = field.getTitle();
    final var subTitle = field.getSubTitle();
    return new SentTitle(
      this.title == null ? null : title == null ? RpString.from(this.title) : title,
      this.subTitle == null ? null : subTitle == null ? RpString.from(this.subTitle) : subTitle,
      this.fadeIn, this.stay, this.fadeOut);
  }
}
