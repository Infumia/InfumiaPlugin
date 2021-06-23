package tr.com.infumia.infumialib.transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/**
 * an annotation that puts comments to the field's path.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {

  /**
   * obtains the comments.
   *
   * @return comments.
   */
  @NotNull
  String[] value();
}
