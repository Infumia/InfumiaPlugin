package tr.com.infumia.infumialib.reflection.parameterized;

import tr.com.infumia.infumialib.reflection.RefClass;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that converts the given objects into the {@link Class}.
 */
@RequiredArgsConstructor
public final class Converted implements Supplier<Class<?>[]> {

  /**
   * the is primitive.
   */
  private final boolean isPrimitive;

  /**
   * the objects.
   */
  @NotNull
  private final Object[] objects;

  @NotNull
  @Override
  public Class<?>[] get() {
    final var classes = new Class[this.objects.length];
    for (var index = 0; index < this.objects.length; index++) {
      final var object = this.objects[index];
      final Class<?> clazz;
      if (object instanceof RefClass) {
        clazz = ((RefClass<?>) object).getRealClass();
      } else if (object instanceof Class) {
        clazz = (Class<?>) object;
      } else {
        clazz = object.getClass();
      }
      if (this.isPrimitive) {
        classes[index] = new Primitive<>(clazz).get();
      } else {
        classes[index] = clazz;
      }
    }
    return classes;
  }
}
