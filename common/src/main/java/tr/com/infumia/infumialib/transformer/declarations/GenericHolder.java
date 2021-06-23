package tr.com.infumia.infumialib.transformer.declarations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine generic holders.
 *
 * @param <L> type of the left object class.
 * @param <R> type of the right object class.
 */
public interface GenericHolder<L, R> {

  /**
   * creates a simple instance of {@code this}.
   *
   * @param left the left to create.
   * @param right the right to create.
   * @param <L> type of the left object class.
   * @param <R> type of the right object class.
   *
   * @return a newly created {@code this} instance.
   */
  @NotNull
  static <L, R> GenericHolder<L, R> create(@NotNull final Class<L> left, @NotNull final Class<R> right) {
    return GenericHolder.create(GenericDeclaration.ofReady(left), GenericDeclaration.ofReady(right));
  }

  /**
   * creates a simple instance of {@code this}.
   *
   * @param left the left to create.
   * @param right the right to create.
   * @param <L> type of the left object class.
   * @param <R> type of the right object class.
   *
   * @return a newly created {@code this} instance.
   */
  @NotNull
  static <L, R> GenericHolder<L, R> create(@NotNull final GenericDeclaration left,
                                           @NotNull final GenericDeclaration right) {
    return new Impl<>(left, right);
  }

  /**
   * obtains the left type.
   *
   * @return left type.
   */
  @NotNull
  GenericDeclaration getLeftType();

  /**
   * creates a new generic pair.
   *
   * @return a newly created generic pair.
   */
  @NotNull
  default GenericPair getPair() {
    return GenericPair.of(this.getLeftType(), this.getRightType());
  }

  /**
   * obtains the right type.
   *
   * @return right type.
   */
  @NotNull
  GenericDeclaration getRightType();

  /**
   * a simple implementation of {@link GenericHolder}.
   *
   * @param <L> type of the left object class.
   * @param <R> type of the right object class.
   */
  @Getter
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  final class Impl<L, R> implements GenericHolder<L, R> {

    /**
     * the left.
     */
    @NotNull
    private final GenericDeclaration leftType;

    /**
     * the right.
     */
    @NotNull
    private final GenericDeclaration rightType;
  }
}
