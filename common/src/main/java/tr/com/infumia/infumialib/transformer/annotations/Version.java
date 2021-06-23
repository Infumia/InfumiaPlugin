package tr.com.infumia.infumialib.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation to define version of the transformed object.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {

  /**
   * obtains the version.
   *
   * @return version.
   */
  int value() default 1;
}
