package tr.com.infumia.infumialib.reflection.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.reflection.RefMethod;
import tr.com.infumia.infumialib.reflection.RefMethodExecuted;

/**
 * an implementation for {@link RefMethod}.
 */
@Log
@RequiredArgsConstructor
public final class MethodOf implements RefMethod {

  /**
   * the method.
   */
  @NotNull
  private final Method method;

  @Override
  public <A extends Annotation> Optional<A> getAnnotation(@NotNull final Class<A> annotationClass) {
    return Optional.ofNullable(this.method.getDeclaredAnnotation(annotationClass));
  }

  @NotNull
  @Override
  public String getName() {
    return this.method.getName();
  }

  @NotNull
  @Override
  public Class<?>[] getParameterTypes() {
    return this.method.getParameterTypes();
  }

  @NotNull
  @Override
  public Class<?> getReturnType() {
    return this.method.getReturnType();
  }

  @NotNull
  @Override
  public RefMethodExecuted of(@Nullable final Object object) {
    return new MethodExecuted(object);
  }

  @Override
  public boolean hasFinal() {
    return Modifier.isFinal(this.method.getModifiers());
  }

  @Override
  public boolean hasPrivate() {
    return Modifier.isPrivate(this.method.getModifiers());
  }

  @Override
  public boolean hasPublic() {
    return Modifier.isPublic(this.method.getModifiers());
  }

  @Override
  public boolean hasStatic() {
    return Modifier.isStatic(this.method.getModifiers());
  }

  /**
   * an implementation for {@link RefMethodExecuted}.
   */
  @RequiredArgsConstructor
  private final class MethodExecuted implements RefMethodExecuted {

    /**
     * the object.
     */
    @Nullable
    private final Object object;

    @NotNull
    @Override
    public Optional<Object> call(@Nullable final Object... parameters) {
      final var accessible = MethodOf.this.method.isAccessible();
      try {
        MethodOf.this.method.setAccessible(true);
        return Optional.ofNullable(MethodOf.this.method.invoke(this.object, parameters));
      } catch (final IllegalAccessException | InvocationTargetException exception) {
        MethodOf.log.log(Level.SEVERE, "MethodExecuted#call(Object[])", exception);
        return Optional.empty();
      } finally {
        MethodOf.this.method.setAccessible(accessible);
      }
    }
  }
}
