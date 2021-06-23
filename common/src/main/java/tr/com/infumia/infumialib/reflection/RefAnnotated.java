package tr.com.infumia.infumialib.reflection;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * an interface for the objects which are able to annotate.
 */
public interface RefAnnotated {

  /**
   * gets the annotation from the given annotation class.
   *
   * @param annotationClass the annotation class to get.
   * @param <A> the annotation type.
   *
   * @return the given annotation class's instance if {@code this} have the given
   *   annotation class.
   */
  <A extends Annotation> Optional<A> getAnnotation(@NotNull Class<A> annotationClass);

  /**
   * gets the annotation from the given annotation class and if it's found runs the consumer.
   *
   * @param annotationClass the annotation class to get
   * @param consumer the consumer to run.
   * @param <A> the annotation type.
   */
  default <A extends Annotation> void getAnnotation(@NotNull final Class<A> annotationClass,
                                                    @NotNull final Consumer<A> consumer) {
    this.getAnnotation(annotationClass).ifPresent(consumer);
  }

  /**
   * checks if {@code this} has the given annotation.
   *
   * @param annotationClass the annotation class to check.
   * @param <A> the annotation type.
   *
   * @return {@code true} if {@code this} has the given annotation.
   */
  default <A extends Annotation> boolean hasAnnotation(@NotNull final Class<A> annotationClass) {
    return this.getAnnotation(annotationClass).isPresent();
  }
}
