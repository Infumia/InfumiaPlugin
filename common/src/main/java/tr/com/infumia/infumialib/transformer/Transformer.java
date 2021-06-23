package tr.com.infumia.infumialib.transformer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericHolder;

/**
 * an interface to determine transformers.
 *
 * @param <R> type of the raw value.
 * @param <F> type of the final value.
 */
public interface Transformer<R, F> extends tr.com.infumia.infumialib.transformer.declarations.GenericHolder<R, F> {

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param transformation the transformation to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> Transformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                         @NotNull final Function<@NotNull R, @Nullable F> transformation) {
    return new Impl<>(rawType, finalType, transformation);
  }

  /**
   * creates a simple transformer.
   *
   * @param rawType the raw type to create.
   * @param finalType the final type to create.
   * @param transformation the transformation to create.
   * @param transformationWithField the transformation with field to create.
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   *
   * @return a newly created transformer.
   */
  @NotNull
  static <R, F> Transformer<R, F> create(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                                         @NotNull final Function<@NotNull R, @Nullable F> transformation,
                                         @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
    return new Impl<>(rawType, finalType, transformation, transformationWithField);
  }

  /**
   * transforms the {@link R} into {@link F}.
   *
   * @param r the r to transform.
   *
   * @return transformed value.
   */
  @NotNull
  Optional<F> transform(@NotNull R r);

  /**
   * transforms the {@link R} into {@link F} with field.
   *
   * @param r the r to transform.
   * @param field the field to transform.
   *
   * @return transformed value.
   */
  @NotNull
  Optional<F> transformWithField(@NotNull R r, @NotNull F field);

  /**
   * an abstract that envelopes to {@link Transformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
  abstract class Base<R, F> implements Transformer<R, F> {

    /**
     * the holder.
     */
    @NotNull
    @Delegate
    private final tr.com.infumia.infumialib.transformer.declarations.GenericHolder<R, F> holder;

    /**
     * the transformation.
     */
    @NotNull
    private final Function<@NotNull R, @Nullable F> transformation;

    /**
     * the transformation with field.
     */
    @NotNull
    private final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField;

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull R, @Nullable F> transformation) {
      this(tr.com.infumia.infumialib.transformer.declarations.GenericHolder.create(rawType, finalType), transformation,
        (r, field) -> transformation.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     * @param transformationWithField the transformation with field.
     */
    protected Base(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                   @NotNull final Function<@NotNull R, @Nullable F> transformation,
                   @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
      this(GenericHolder.create(rawType, finalType), transformation, transformationWithField);
    }

    @NotNull
    @Override
    public final Optional<F> transform(@NotNull final R r) {
      return Optional.ofNullable(this.transformation.apply(r));
    }

    @NotNull
    @Override
    public final Optional<F> transformWithField(@NotNull final R r, @NotNull final F field) {
      return Optional.ofNullable(this.transformationWithField.apply(r, field));
    }
  }

  /**
   * a simple implementation of {@link Transformer}.
   *
   * @param <R> type of the raw value.
   * @param <F> type of the final value.
   */
  final class Impl<R, F> extends Base<R, F> {

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull R, @Nullable F> transformation) {
      super(rawType, finalType, transformation,
        (r, field) -> transformation.apply(r));
    }

    /**
     * ctor.
     *
     * @param rawType the raw type to create.
     * @param finalType the final type to create.
     * @param transformation the transformation.
     * @param transformationWithField the transformation with field.
     */
    private Impl(@NotNull final Class<R> rawType, @NotNull final Class<F> finalType,
                 @NotNull final Function<@NotNull R, @Nullable F> transformation,
                 @NotNull final BiFunction<@NotNull R, @NotNull F, @Nullable F> transformationWithField) {
      super(rawType, finalType, transformation, transformationWithField);
    }
  }
}
