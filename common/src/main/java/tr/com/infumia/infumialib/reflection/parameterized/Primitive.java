package tr.com.infumia.infumialib.reflection.parameterized;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that converts the given class into the its primitive.
 *
 * @param <T> the class type.
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public final class Primitive<T> implements Supplier<Class<T>> {

  /**
   * the class.
   */
  @NotNull
  private final Class<T> clazz;

  @NotNull
  @Override
  public Class<T> get() {
    switch (this.clazz.getName()) {
      case "java.lang.Integer":
        return (Class<T>) Integer.TYPE;
      case "java.lang.Float":
        return (Class<T>) Float.TYPE;
      case "java.lang.Short":
        return (Class<T>) Short.TYPE;
      case "java.lang.Character":
        return (Class<T>) Character.TYPE;
      case "java.lang.Boolean":
        return (Class<T>) Boolean.TYPE;
      case "java.lang.Byte":
        return (Class<T>) Byte.TYPE;
      case "java.lang.Long":
        return (Class<T>) Long.TYPE;
      case "java.lang.Void":
        return (Class<T>) Void.TYPE;
      case "java.lang.Double":
        return (Class<T>) Double.TYPE;
      default:
        return this.clazz;
    }
  }
}
