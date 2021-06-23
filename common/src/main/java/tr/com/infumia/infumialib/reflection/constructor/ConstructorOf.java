package tr.com.infumia.infumialib.reflection.constructor;

import tr.com.infumia.infumialib.reflection.RefConstructed;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation for {@link RefConstructed}.
 *
 * @param <T> the result instance's type.
 */
@Log
@RequiredArgsConstructor
public final class ConstructorOf<T> implements RefConstructed<T> {

  /**
   * the constructor.
   */
  @NotNull
  private final Constructor<T> constructor;

  @NotNull
  @Override
  public Optional<T> create(@Nullable final Object... parameters) {
    final var accessible = this.constructor.isAccessible();
    try {
      this.constructor.setAccessible(true);
      return Optional.of(this.constructor.newInstance(parameters));
    } catch (final IllegalAccessException | InstantiationException | InvocationTargetException exception) {
      ConstructorOf.log.log(Level.SEVERE, "ConstructorOf#create(Object[])", exception);
      return Optional.empty();
    } finally {
      this.constructor.setAccessible(accessible);
    }
  }

  @Override
  public <A extends Annotation> Optional<A> getAnnotation(@NotNull final Class<A> annotationClass) {
    return Optional.ofNullable(this.constructor.getDeclaredAnnotation(annotationClass));
  }

  @Override
  public boolean hasFinal() {
    return Modifier.isFinal(this.constructor.getModifiers());
  }

  @Override
  public boolean hasPrivate() {
    return Modifier.isPrivate(this.constructor.getModifiers());
  }

  @Override
  public boolean hasPublic() {
    return Modifier.isPublic(this.constructor.getModifiers());
  }

  @Override
  public boolean hasStatic() {
    return Modifier.isStatic(this.constructor.getModifiers());
  }
}
