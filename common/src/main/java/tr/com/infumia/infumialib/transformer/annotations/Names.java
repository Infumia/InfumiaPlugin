package tr.com.infumia.infumialib.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.reflection.RefField;

/**
 * an annotation that controls the pathing of field declarations.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Names {

  /**
   * obtains the modifier.
   *
   * @return modifier.
   */
  Modifier modifier() default Modifier.NONE;

  /**
   * obtains the strategy.
   *
   * @return strategy
   */
  Strategy strategy() default Strategy.IDENTITY;

  /**
   * an enum class that contains name modifiers.
   */
  enum Modifier implements UnaryOperator<@NotNull String> {
    /**
     * the none.
     */
    NONE {
      @Override
      public String apply(@NotNull final String s) {
        return s;
      }
    },
    /**
     * the to upper case.
     */
    TO_UPPER_CASE {
      @Override
      public String apply(@NotNull final String s) {
        return s.toUpperCase(Locale.ROOT);
      }
    },
    /**
     * the to lower case.
     */
    TO_LOWER_CASE {
      @Override
      public String apply(@NotNull final String s) {
        return s.toLowerCase(Locale.ROOT);
      }
    }
  }

  /**
   * an enum class that contains name strategies.
   */
  enum Strategy implements UnaryOperator<@NotNull String> {
    /**
     * the identity.
     */
    IDENTITY("", ""),
    /**
     * the snake case.
     */
    SNAKE_CASE("$1_$2", "(\\G(?!^)|\\b(?:[A-Z]{2}|[a-zA-Z][a-z]*))(?=[a-zA-Z]{2,}|\\d)([A-Z](?:[A-Z]|[a-z]*)|\\d+)"),
    /**
     * the hyphen case.
     */
    HYPHEN_CASE("$1-$2", "(\\G(?!^)|\\b(?:[A-Z]{2}|[a-zA-Z][a-z]*))(?=[a-zA-Z]{2,}|\\d)([A-Z](?:[A-Z]|[a-z]*)|\\d+)");

    /**
     * the regex.
     */
    @NotNull
    private final Pattern pattern;

    /**
     * the replacement.
     */
    @NotNull
    private final String replacement;

    /**
     * ctor.
     *
     * @param replacement the replacement.
     * @param regex the regex.
     */
    Strategy(@NotNull final String replacement, @NotNull final String regex) {
      this.replacement = replacement;
      this.pattern = Pattern.compile(regex);
    }

    @Override
    public String apply(@NotNull final String s) {
      return this.pattern.matcher(s).replaceAll(this.replacement);
    }
  }

  /**
   * a class that contains utility methods to calculate path of the class and field.
   */
  final class Calculated {

    /**
     * ctor.
     */
    private Calculated() {
    }

    /**
     * calculates names annotation of the given class.
     *
     * @param cls the cls to calculate.
     *
     * @return calculated names annotations.
     */
    @Nullable
    public static Names calculateNames(@NotNull final Class<?> cls) {
      var tempCls = cls;
      var names = tempCls.getAnnotation(Names.class);
      while (names == null) {
        tempCls = tempCls.getEnclosingClass();
        if (tempCls == null) {
          return null;
        }
        names = tempCls.getAnnotation(Names.class);
      }
      return names;
    }

    /**
     * calculates the path of the given class and filed.
     *
     * @param parent the parent to calculate.
     * @param field the field to calculate.
     *
     * @return calculated path.
     */
    @NotNull
    public static String calculatePath(@Nullable final Names parent, @NotNull final RefField field) {
      final var path = new AtomicReference<String>();
      if (parent != null) {
        path.set(parent.modifier().apply(parent.strategy().apply(field.getName())));
      }
      field.getAnnotation(CustomKey.class, customKey ->
        path.set(customKey.value()));
      path.compareAndSet(null, field.getName());
      return path.get();
    }
  }
}
