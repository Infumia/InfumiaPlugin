package tr.com.infumia.infumialib.transformer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericHolder;

/**
 * an interface to determine transformers.
 *
 * @param <R> type of the raw value.
 * @param <F> type of the final value.
 */
public interface TwoSideTransformer<R, F> extends Transformer<R, F> {

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param toRaw the to raw to create.
   * @param toFinal the to final to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> TwoSideTransformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                                @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                                                @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
    return new Impl<>(rawType, finalType, toRaw, toFinal);
  }

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param toRaw the to raw to create.
   * @param toFinal the to final to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> TwoSideTransformer<R, F> create(@NotNull final GenericDeclaration rawType,
                                                @NotNull final GenericDeclaration finalType,
                                                @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                                                @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
    return new Impl<>(rawType, finalType, toRaw, toFinal);
  }

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param toRaw the to raw to create.
   * @param toFinal the to final to create.
   * @param toFinalWithField the to final with field to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> TwoSideTransformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                                @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                                                @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                                                @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
    return new Impl<>(rawType, finalType, toRaw, toFinal, toFinalWithField);
  }

  /**
   * reverses the two side transformer.
   *
   * @return reversed two side transformer.
   */
  @NotNull
  default TwoSideTransformer<F, R> reverse() {
    return TwoSideTransformer.create(this.getRightType(), this.getLeftType(), this::toFinalOrNull, this::toRawOrNull);
  }

  /**
   * converts the {@link R} into {@link F}.
   *
   * @param r the {@link R} to convert.
   *
   * @return {@link F} value.
   */
  @NotNull
  Optional<F> toFinal(@NotNull R r);

  /**
   * converts the {@link R} into {@link F}.
   *
   * @param r the {@link R} to convert.
   *
   * @return {@link F} value.
   */
  @Nullable
  default F toFinalOrNull(@NotNull final R r) {
    return this.toFinal(r).orElse(null);
  }

  /**
   * converts the {@link R} into {@link F}.
   *
   * @param r the {@link R} to convert.
   * @param field the field to convert.
   *
   * @return {@link F} value.
   */
  @NotNull
  Optional<F> toFinalWithField(@NotNull R r, @NotNull F field);

  /**
   * converts the {@link R} into {@link F}.
   *
   * @param r the {@link R} to convert.
   * @param field the field to convert.
   *
   * @return {@link F} value.
   */
  @Nullable
  default F toFinalWithFieldOrNull(@NotNull final R r, @NotNull final F field) {
    return this.toFinalWithField(r, field).orElse(null);
  }

  /**
   * converts the {@link F} into {@link R}.
   *
   * @param f the {@link F} to convert.
   *
   * @return {@link R} value.
   */
  @NotNull
  Optional<R> toRaw(@NotNull F f);

  /**
   * converts the {@link F} into {@link R}.
   *
   * @param f the {@link F} to convert.
   *
   * @return {@link R} value.
   */
  @Nullable
  default R toRawOrNull(@NotNull final F f) {
    return this.toRaw(f).orElse(null);
  }

  @Override
  @NotNull
  default Optional<F> transform(@NotNull final R r) {
    return this.toFinal(r);
  }

  @Override
  @NotNull
  default Optional<F> transformWithField(@NotNull final R r, @NotNull final F field) {
    return this.toFinalWithField(r, field);
  }

  /**
   * a simple implementation of {@link TwoSideTransformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  abstract class Base<R, F> implements TwoSideTransformer<R, F> {

    /**
     * the holder.
     */
    @NotNull
    @Delegate
    private final GenericHolder<R, F> holder;

    /**
     * the to final.
     */
    @NotNull
    private final Function<@NotNull R, @Nullable F> toFinal;

    /**
     * the to final with field.
     */
    @NotNull
    private final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField;

    /**
     * the to raw.
     */
    @NotNull
    private final Function<@NotNull F, @Nullable R> toRaw;

    /**
     * ctor.
     *
     * @param holder the holder.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     * @param toFinalWithField the to final with field.
     */
    protected Base(@NotNull final GenericHolder<R, F> holder, @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                   @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                   @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
      this.holder = holder;
      this.toRaw = toRaw;
      this.toFinal = toFinal;
      this.toFinalWithField = toFinalWithField;
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     * @param toFinalWithField the to final with field.
     */
    protected Base(@NotNull final GenericDeclaration rawType, @NotNull final GenericDeclaration finalType,
                   @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                   @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                   @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
      this(GenericHolder.create(rawType, finalType), toRaw, toFinal, toFinalWithField);
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     */
    protected Base(@NotNull final GenericDeclaration rawType, @NotNull final GenericDeclaration finalType,
                   @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                   @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
      this(GenericHolder.create(rawType, finalType), toRaw, toFinal,
        (r, field) -> toFinal.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                   @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
      this(GenericDeclaration.ofReady(rawType), GenericDeclaration.ofReady(finalType), toRaw, toFinal);
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     * @param toFinalWithField the to final with field.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                   @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                   @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
      this(GenericHolder.create(rawType, finalType), toRaw, toFinal, toFinalWithField);
    }

    @NotNull
    @Override
    public final Optional<F> toFinal(@NotNull final R r) {
      return Optional.ofNullable(this.toFinal.apply(r));
    }

    @NotNull
    @Override
    public final Optional<F> toFinalWithField(@NotNull final R r, @NotNull final F field) {
      return Optional.ofNullable(this.toFinalWithField.apply(r, field));
    }

    @NotNull
    @Override
    public final Optional<R> toRaw(@NotNull final F f) {
      return Optional.ofNullable(this.toRaw.apply(f));
    }
  }

  /**
   * a simple implementation of {@link TwoSideTransformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  final class Impl<R, F> extends Base<R, F> {

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     * @param toFinalWithField the to final with field.
     */
    private Impl(@NotNull final GenericDeclaration rawType, @NotNull final GenericDeclaration finalType,
                 @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                 @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                 @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
      super(rawType, finalType, toRaw, toFinal, toFinalWithField);
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     * @param toFinalWithField the to final with field.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                 @NotNull final Function<@NotNull R, @Nullable F> toFinal,
                 @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> toFinalWithField) {
      this(GenericDeclaration.ofReady(rawType), GenericDeclaration.ofReady(finalType), toRaw, toFinal, toFinalWithField);
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     */
    private Impl(@NotNull final GenericDeclaration rawType, @NotNull final GenericDeclaration finalType,
                 @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                 @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
      this(rawType, finalType, toRaw, toFinal,
        (r, field) -> toFinal.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type.
     * @param finalType the final type.
     * @param toRaw the to raw.
     * @param toFinal the to final.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull F, @Nullable R> toRaw,
                 @NotNull final Function<@NotNull R, @Nullable F> toFinal) {
      this(GenericDeclaration.ofReady(rawType), GenericDeclaration.ofReady(finalType), toRaw, toFinal);
    }
  }
}
