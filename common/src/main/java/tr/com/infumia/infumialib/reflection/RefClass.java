package tr.com.infumia.infumialib.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine {@link Class}.
 *
 * @param <T> the class's type.
 */
public interface RefClass<T> extends RefAnnotated, RefModifiable {

  /**
   * gets existing constructor by types.
   *
   * @param types the parameter types to get.
   *
   * @return a {@link RefConstructed} object.
   */
  @NotNull
  Optional<RefConstructed<T>> getConstructor(@NotNull Object... types);

  /**
   * gets constructor by number of arguments.
   *
   * @param number the number to get.
   *
   * @return a {@link RefConstructed} object.
   */
  @NotNull
  Optional<RefConstructed<T>> getConstructor(int number);

  /**
   * gets all declared fields.
   *
   * @return all declared fields of the class.
   */
  @NotNull
  List<RefField> getDeclaredFields();

  /**
   * gets a declared field list that has the given annotation.
   *
   * @param annotationClass the annotation class to get.
   * @param <A> the annotation type.
   *
   * @return a field list.
   */
  @NotNull
  default <A extends Annotation> List<RefField> getDeclaredFieldsWithAnnotation(
    @NotNull final Class<A> annotationClass) {
    final var list = new ArrayList<RefField>();
    for (final var refField : this.getDeclaredFields()) {
      if (refField.hasAnnotation(annotationClass)) {
        list.add(refField);
      }
    }
    return list;
  }

  /**
   * gets a declared field list that has the given annotation and runs the consumer for each found field.
   *
   * @param annotationClass the annotation class to get.
   * @param consumer the consumer to run.
   * @param <A> the annotation type.
   */
  default <A extends Annotation> void getDeclaredFieldsWithAnnotation(@NotNull final Class<A> annotationClass,
                                                                      @NotNull final BiConsumer<RefField, A> consumer) {
    this.getDeclaredFields().forEach(refField ->
      refField.getAnnotation(annotationClass, a -> consumer.accept(refField, a)));
  }

  /**
   * gets all declared methods.
   *
   * @return all declared methods of the class.
   */
  @NotNull
  List<RefMethod> getDeclaredMethods();

  /**
   * gets a method field list that has the given annotation.
   *
   * @param annotationClass the annotation class to get.
   * @param <A> the annotation type.
   *
   * @return a method list.
   */
  @NotNull
  default <A extends Annotation> List<RefMethod> getDeclaredMethodsWithAnnotation(
    @NotNull final Class<A> annotationClass) {
    final var list = new ArrayList<RefMethod>();
    for (final var refMethod : this.getDeclaredMethods()) {
      if (refMethod.hasAnnotation(annotationClass)) {
        list.add(refMethod);
      }
    }
    return list;
  }

  /**
   * gets a declared method list that has the given annotation and runs the consumer for each found method.
   *
   * @param annotationClass the annotation class to get.
   * @param consumer the consumer to run.
   * @param <A> the annotation type.
   */
  default <A extends Annotation> void getDeclaredMethodsWithAnnotation(@NotNull final Class<A> annotationClass,
                                                                       @NotNull final BiConsumer<RefMethod, A> consumer) {
    this.getDeclaredMethods().forEach(refField ->
      refField.getAnnotation(annotationClass, a -> consumer.accept(refField, a)));
  }

  /**
   * gets field by name.
   *
   * @param name the name to get.
   *
   * @return a {@link RefField} object.
   */
  @NotNull
  Optional<RefField> getField(@NotNull String name);

  /**
   * gets field by type.
   *
   * @param type the type to get
   * @param <X> the class type of the field.
   *
   * @return a {@link RefField} object.
   */
  @NotNull <X> Optional<RefField> getField(@NotNull RefClass<X> type);

  /**
   * gets field by type.
   *
   * @param type the type to get.
   *
   * @return a {@link RefField} object.
   */
  @NotNull
  Optional<RefField> getField(@NotNull Class<?> type);

  /**
   * gets all fields.
   *
   * @return all fields of the class.
   */
  @NotNull
  List<RefField> getFields();

  /**
   * gets a field list that has the given annotation.
   *
   * @param annotationClass the annotation class to get.
   * @param <A> the annotation type.
   *
   * @return a field list.
   */
  @NotNull
  default <A extends Annotation> List<RefField> getFieldsWithAnnotation(@NotNull final Class<A> annotationClass) {
    final var list = new ArrayList<RefField>();
    for (final var refField : this.getFields()) {
      if (refField.hasAnnotation(annotationClass)) {
        list.add(refField);
      }
    }
    return list;
  }

  /**
   * gets a field list that has the given annotation and runs the consumer for each found field.
   *
   * @param annotationClass the annotation class to get.
   * @param consumer the consumer to run.
   * @param <A> the annotation type.
   */
  default <A extends Annotation> void getFieldsWithAnnotation(@NotNull final Class<A> annotationClass,
                                                              @NotNull final BiConsumer<RefField, A> consumer) {
    for (final var refField : this.getFields()) {
      refField.getAnnotation(annotationClass, a -> consumer.accept(refField, a));
    }
  }

  /**
   * gets existing method by name and types.
   *
   * @param name the name to get.
   * @param types method parameter types to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getMethod(@NotNull String name, @NotNull Object... types);

  /**
   * gets method by name.
   *
   * @param names the names to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getMethodByName(@NotNull String... names);

  /**
   * gets method by type parameters.
   *
   * @param types the parameter types to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getMethodByParameter(@NotNull Object... types);

  /**
   * gets method by return value.
   *
   * @param type the type to get.
   * @param <X> the class type of the return type.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull <X> Optional<RefMethod> getMethodByReturnType(@NotNull RefClass<X> type);

  /**
   * gets method by return value.
   *
   * @param type the type to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getMethodByReturnType(@NotNull Class<?> type);

  /**
   * gets all methods.
   *
   * @return all methods of the class.
   */
  @NotNull
  List<RefMethod> getMethods();

  /**
   * gets a method list that has the given annotation.
   *
   * @param annotationClass the annotation class to get.
   * @param <A> the annotation type.
   *
   * @return a method list.
   */
  @NotNull
  default <A extends Annotation> List<RefMethod> getMethodsWithAnnotation(@NotNull final Class<A> annotationClass) {
    final var list = new ArrayList<RefMethod>();
    for (final var refField : this.getMethods()) {
      if (refField.hasAnnotation(annotationClass)) {
        list.add(refField);
      }
    }
    return list;
  }

  /**
   * gets a method list that has the given annotation and runs the consumer for each found method.
   *
   * @param annotationClass the annotation class to get.
   * @param consumer the consumer to run.
   * @param <A> the annotation type.
   */
  default <A extends Annotation> void getMethodsWithAnnotation(@NotNull final Class<A> annotationClass,
                                                               @NotNull final BiConsumer<RefMethod, A> consumer) {
    for (final var refField : this.getMethods()) {
      refField.getAnnotation(annotationClass, a -> consumer.accept(refField, a));
    }
  }

  /**
   * gets existing constructor by types.
   *
   * @param types the parameter types to get.
   *
   * @return a {@link RefConstructed} object.
   */
  @NotNull
  Optional<RefConstructed<T>> getPrimitiveConstructor(@NotNull Object... types);

  /**
   * gets existing method by name and types.
   *
   * @param name the name to get.
   * @param types the method parameter types to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getPrimitiveMethod(@NotNull String name, @NotNull Object... types);

  /**
   * gets method by type parameters.
   *
   * @param types the parameter types to get.
   *
   * @return a {@link RefMethod} object.
   */
  @NotNull
  Optional<RefMethod> getPrimitiveMethodByParameter(@NotNull Object... types);

  /**
   * obtains the real class.
   *
   * @return the real class.
   */
  @NotNull
  Class<T> getRealClass();

  /**
   * checks if the given object is instance of {@code this}.
   *
   * @param object the object to check.
   *
   * @return true if object is an instance of this class.
   *
   * @see Class#isInstance(Object)
   */
  boolean isInstance(@NotNull Object object);
}
