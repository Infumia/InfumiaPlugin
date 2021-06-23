package tr.com.infumia.infumialib.equilibrium;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that allows you to use Java operators.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum Equilibrium {

  /**
   * checks if the object equals.
   */
  EQUALS(Utilities::equals, "Utilities.equals(%s, %s)", "=", "=="),
  /**
   * checks if the object not equals.
   */
  NOT_EQUALS(Utilities::notEqual, "Utilities.notEqual(%s, %s)", "!="),
  /**
   * checks if the number is bigger than the other.
   */
  BIGGER(Utilities::isBigger, "Utilities.isBigger(%s, %s)", ">"),
  /**
   * checks if the number is equal or bigger than the other.
   */
  BIGGER_AND_EQUALS(Utilities::isBiggerEquals, "Utilities.isBiggerEquals(%s, %s)", "=>", ">="),
  /**
   * checks if the number is lower than the other.
   */
  LESS(Utilities::isLess, "Utilities.isLess(%s, %s)", "<"),
  /**
   * checks if the number is equal or lower than the other.
   */
  LESS_OR_EQUALS(Utilities::isLessEquals, "Utilities.isLessEquals(%s, %s)", "<=", "=<"),
  /**
   * checks if the object instance of the other.
   */
  INSTANCE_OF(Utilities::instanceOf, "Utilities.instanceOf(%s, %s)", "is", "instance of"),
  /**
   * checks if the object not instance of the other.
   */
  NOT_INSTANCE_OF(Utilities::noInstanceOf, "Utilities.noInstanceOf(%s, %s)",
    "isnt", "isn't", "isnot", "is not", "not instance of"),
  /**
   * nothing.
   */
  NOTHING((leftObject, rightObject) -> false, "");

  /**
   * the operators.
   */
  @NotNull
  @Getter
  private final List<String> operators;

  /**
   * the function.
   */
  @NotNull
  private final BiPredicate<Object, Object> predicate;

  /**
   * the to string.
   */
  @NotNull
  private final String toString;

  /**
   * ctor.
   *
   * @param predicate the function.
   * @param toString the to string.
   * @param operators the operators.
   */
  Equilibrium(@NotNull final BiPredicate<Object, Object> predicate, @NotNull final String toString,
              @NotNull final String... operators) {
    this(Arrays.asList(operators), predicate, toString);
  }

  /**
   * obtains an {@code this} from the string.
   *
   * @param operator the operator to get.
   *
   * @return an {@code this} object instance.
   */
  @NotNull
  public static Equilibrium fromString(@NotNull final String operator) {
    return Arrays.stream(Equilibrium.values())
      .filter(equilibrium -> equilibrium.getOperators().contains(operator))
      .findFirst()
      .orElse(Equilibrium.NOTHING);
  }

  /**
   * controls the predicate.
   *
   * @param leftObject the left object to control.
   * @param rightObject the right object to control.
   *
   * @return {@code true} if the predicate returns {@code true}.
   */
  public boolean control(@NotNull final Object leftObject, @NotNull final Object rightObject) {
    return this.predicate.test(leftObject, rightObject);
  }

  @NotNull
  @Override
  public String toString() {
    return this.toString;
  }
}
