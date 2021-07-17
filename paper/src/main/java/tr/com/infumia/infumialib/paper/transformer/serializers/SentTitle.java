package tr.com.infumia.infumialib.paper.transformer.serializers;

import com.cryptomorin.xseries.messages.Titles;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.replaceable.RpString;
import tr.com.infumia.infumialib.transformer.ObjectSerializer;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

/**
 * a class that helps developers to send title to players easily.
 */
@Getter
@ToString
@EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY, doNotUseGetters = true)
public final class SentTitle {

  /**
   * the fade in time.
   */
  private final int fadeIn;

  /**
   * the fade out time.
   */
  private final int fadeOut;

  /**
   * the stay time.
   */
  private final int stay;

  /**
   * the sub title.
   */
  @Nullable
  private final RpString subTitle;

  /**
   * the title.
   */
  @Nullable
  private final RpString title;

  /**
   * ctor.
   *
   * @param title the title.
   * @param subTitle the sub title.
   * @param fadeIn the fade in.
   * @param stay the stay.
   * @param fadeOut the fade out.
   */
  public SentTitle(@Nullable final String title, @Nullable final String subTitle, final int fadeIn, final int stay,
                   final int fadeOut) {
    this(title == null ? null : RpString.from(title),
      subTitle == null ? null : RpString.from(subTitle),
      fadeIn, stay, fadeOut);
  }

  /**
   * ctor.
   *
   * @param title the title.
   * @param subTitle the sub title.
   * @param fadeIn the fade in.
   * @param stay the stay.
   * @param fadeOut the fade out.
   */
  public SentTitle(@Nullable final RpString title, @Nullable final RpString subTitle, final int fadeIn, final int stay,
                   final int fadeOut) {
    this.title = title;
    this.subTitle = subTitle;
    this.fadeIn = fadeIn;
    this.stay = stay;
    this.fadeOut = fadeOut;
  }

  /**
   * sends the title to the given player.
   *
   * @param player the player to send.
   * @param entries the entries to send.
   */
  @SafeVarargs
  public final void send(@NotNull final Player player,
                         @NotNull final Map.Entry<String, Supplier<Object>>... entries) {
    Titles.sendTitle(player, this.fadeIn, this.stay, this.fadeOut,
      this.title == null ? null : this.title.build(entries),
      this.subTitle == null ? null : this.subTitle.build(entries));
  }

  /**
   * sends the title to the given player.
   *
   * @param player the player to send.
   * @param title the title function to send.
   * @param subTitle the sub title function to send.
   * @param entries the entries to send.
   */
  @SafeVarargs
  public final void send(@NotNull final Player player, @NotNull final UnaryOperator<String> title,
                         @NotNull final UnaryOperator<String> subTitle,
                         @NotNull final Map.Entry<String, Supplier<Object>>... entries) {
    Titles.sendTitle(player, this.fadeIn, this.stay, this.fadeOut,
      this.title == null ? null : title.apply(this.title.build(entries)),
      this.subTitle == null ? null : subTitle.apply(this.subTitle.build(entries)));
  }

  /**
   * obtains value of the sub title.
   *
   * @return value of the sub title.
   */
  @NotNull
  public String getSubTitleAsValue() {
    return this.subTitle == null ? "" : this.subTitle.getValue();
  }

  /**
   * obtains value of the title.
   *
   * @return value of the title.
   */
  @NotNull
  public String getTitleAsValue() {
    return this.title == null ? "" : this.title.getValue();
  }

  /**
   * changes the sub title's value.
   *
   * @param newSubTitle the new sub title to change.
   *
   * @return a new instance with the new sub title.
   */
  @NotNull
  public SentTitle valueOfSubTitle(@Nullable final String newSubTitle) {
    final var finalNewSubTitle = newSubTitle == null
      ? null
      : this.subTitle == null
      ? RpString.from(newSubTitle)
      : this.subTitle.value(newSubTitle);
    return new SentTitle(
      this.title,
      finalNewSubTitle,
      this.fadeIn,
      this.stay,
      this.fadeOut);
  }

  /**
   * changes the title's value.
   *
   * @param newTitle the new title to change.
   *
   * @return a new instance with the new title.
   */
  @NotNull
  public SentTitle valueOfTitle(@Nullable final String newTitle) {
    final var finalNewTitle = newTitle == null
      ? null
      : this.title == null
      ? RpString.from(newTitle)
      : this.title.value(newTitle);
    return new SentTitle(
      finalNewTitle,
      this.subTitle,
      this.fadeIn,
      this.stay,
      this.fadeOut);
  }

  /**
   * a class that represents serializer of {@link SentTitle}.
   */
  public static final class Serializer implements ObjectSerializer<SentTitle> {

    @NotNull
    @Override
    public Optional<SentTitle> deserialize(@NotNull final TransformedData transformedData,
                                           @Nullable final GenericDeclaration declaration) {
      return Optional.empty();
    }

    @NotNull
    @Override
    public Optional<SentTitle> deserialize(@NotNull final SentTitle field,
                                           @NotNull final TransformedData transformedData,
                                           @Nullable final GenericDeclaration declaration) {
      final var title = transformedData.get("title", RpString.class, field.getTitle());
      final var subTitle = transformedData.get("sub-title", RpString.class, field.getSubTitle());
      if (title.isEmpty() && subTitle.isEmpty()) {
        return Optional.empty();
      }
      final var fadeIn = transformedData.get("fade-in", int.class).orElse(20);
      final var stay = transformedData.get("stay", int.class).orElse(20);
      final var fadeOut = transformedData.get("fade-out", int.class).orElse(20);
      return Optional.of(new SentTitle(title.orElse(null), subTitle.orElse(null), fadeIn, stay, fadeOut));
    }

    @Override
    public void serialize(@NotNull final SentTitle sentTitle, @NotNull final TransformedData transformedData) {
      if (sentTitle.getTitle() != null) {
        transformedData.add("title", sentTitle.getTitle(), RpString.class);
      }
      if (sentTitle.getSubTitle() != null) {
        transformedData.add("sub-title", sentTitle.getSubTitle(), RpString.class);
      }
      transformedData.add("fade-in", sentTitle.fadeIn);
      transformedData.add("stay", sentTitle.stay);
      transformedData.add("fade-out", sentTitle.fadeOut);
    }

    @Override
    public boolean supports(@NotNull final Class<?> cls) {
      return cls == SentTitle.class;
    }
  }
}
