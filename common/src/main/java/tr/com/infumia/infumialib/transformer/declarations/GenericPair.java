package tr.com.infumia.infumialib.transformer.declarations;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents generic pairs.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenericPair {

  /**
   * the left.
   */
  @Nullable
  private final GenericDeclaration left;

  /**
   * the right.
   */
  @Nullable
  private final GenericDeclaration right;

  /**
   * creates a new generic pair.
   *
   * @param left the left to create.
   * @param right the right to creat.
   *
   * @return a newly created generic pair.
   */
  @NotNull
  public static GenericPair of(@Nullable final GenericDeclaration left, @Nullable final GenericDeclaration right) {
    return new GenericPair(left, right);
  }

  /**
   * creates a new generic pair.
   *
   * @param left the left to create.
   * @param right the right to creat.
   *
   * @return a newly created generic pair.
   */
  @NotNull
  public static GenericPair of(@NotNull final Class<?> left, @NotNull final Class<?> right) {
    return GenericPair.of(GenericDeclaration.ofReady(left), GenericDeclaration.ofReady(right));
  }

  /**
   * reverses the pair.
   *
   * @return reversed pair.
   */
  @NotNull
  public GenericPair reverse() {
    return GenericPair.of(this.right, this.left);
  }
}
