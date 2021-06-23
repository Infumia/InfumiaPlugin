package tr.com.infumia.infumialib.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/**
 * an annotation that sets custom path of the field.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomKey {

  /**
   * value of the path.
   *
   * @return path.
   */
  @NotNull
  String value();
}
