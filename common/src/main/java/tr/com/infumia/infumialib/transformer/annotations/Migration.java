package tr.com.infumia.infumialib.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation to define migrated version of the field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Migration {

  /**
   * obtains the migrated version.
   * <p>
   * does not affect if the value is 0 or lower.
   * <p>
   * removes the path when the value become {@link Version#value()} of the current class.
   *
   * @return migrated version.
   */
  int value() default 0;
}
