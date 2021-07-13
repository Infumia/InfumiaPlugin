package tr.com.infumia.infumialib.reflection.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.reflection.RefClass;
import tr.com.infumia.infumialib.reflection.RefConstructed;
import tr.com.infumia.infumialib.reflection.RefField;
import tr.com.infumia.infumialib.reflection.RefMethod;
import tr.com.infumia.infumialib.reflection.constructor.ConstructorOf;
import tr.com.infumia.infumialib.reflection.field.FieldOf;
import tr.com.infumia.infumialib.reflection.method.MethodOf;
import tr.com.infumia.infumialib.reflection.parameterized.ParameterizedOf;

/**
 * an implementation for {@link RefClass}.
 *
 * @param <T> the class's type.
 */
@Log
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public final class ClassOf<T> implements RefClass<T> {

  /**
   * the class.
   */
  @NotNull
  private final Class<T> clazz;

  /**
   * ctor.
   *
   * @param object the object to get its class.
   *
   * @see Object#getClass()
   */
  public ClassOf(@NotNull final T object) {
    this((Class<T>) object.getClass());
  }

  /**
   * ctor.
   *
   * @param className the class name to get its class.
   *
   * @throws ClassNotFoundException if the given {@code className} not found.
   * @see Class#forName(String)
   */
  public ClassOf(@NotNull final String className) throws ClassNotFoundException {
    this((Class<T>) Class.forName(className));
  }

  @Override
  public <A extends Annotation> Optional<A> getAnnotation(@NotNull final Class<A> annotationClass) {
    return Optional.ofNullable(this.clazz.getDeclaredAnnotation(annotationClass));
  }

  @NotNull
  @Override
  public Optional<RefConstructed<T>> getConstructor(@NotNull final Object... types) {
    return this.getConstructor0(false, types);
  }

  @NotNull
  @Override
  public Optional<RefConstructed<T>> getConstructor(final int number) {
    final var constructors = new ArrayList<>(Arrays.asList(this.clazz.getConstructors()));
    constructors.addAll(Arrays.asList(this.clazz.getDeclaredConstructors()));
    for (final var constructor : constructors) {
      if (constructor != null) {
        if (constructor.getParameterTypes().length == number) {
          return Optional.of(new ConstructorOf<>((Constructor<T>) constructor));
        }
      }
    }
    return Optional.empty();
  }

  @NotNull
  @Override
  public List<RefField> getDeclaredFields() {
    final var list = new ArrayList<RefField>();
    for (final var field : this.clazz.getDeclaredFields()) {
      list.add(new FieldOf(field));
    }
    return list;
  }

  @NotNull
  @Override
  public List<RefMethod> getDeclaredMethods() {
    final var list = new ArrayList<RefMethod>();
    for (final var method : this.clazz.getDeclaredMethods()) {
      list.add(new MethodOf(method));
    }
    return list;
  }

  @NotNull
  @Override
  public Optional<RefField> getField(@NotNull final String name) {
    try {
      return Optional.of(new FieldOf(this.clazz.getField(name)));
    } catch (final NoSuchFieldException ignored) {
      try {
        return Optional.of(new FieldOf(this.clazz.getDeclaredField(name)));
      } catch (final NoSuchFieldException exception) {
        ClassOf.log.log(Level.SEVERE, "ClassOf#getField(String)", exception);
        return Optional.empty();
      }
    }
  }

  @NotNull
  @Override
  public <X> Optional<RefField> getField(@NotNull final RefClass<X> type) {
    return this.getField(type.getRealClass());
  }

  @NotNull
  @Override
  public Optional<RefField> getField(@NotNull final Class<?> type) {
    final var fields = this.getFields();
    fields.addAll(this.getDeclaredFields());
    for (final var field : fields) {
      if (field != null) {
        if (type.equals(field.getType())) {
          return Optional.of(field);
        }
      }
    }
    return Optional.empty();
  }

  @NotNull
  @Override
  public List<RefField> getFields() {
    final var list = new ArrayList<RefField>();
    for (final var field : this.clazz.getFields()) {
      list.add(new FieldOf(field));
    }
    return list;
  }

  @NotNull
  @Override
  public Optional<RefMethod> getMethod(@NotNull final String name, @NotNull final Object... types) {
    return this.getMethod0(name, false, types);
  }

  @NotNull
  @Override
  public Optional<RefMethod> getMethodByName(@NotNull final String... names) {
    final var methods = this.getMethods();
    methods.addAll(this.getDeclaredMethods());
    for (final var method : methods) {
      if (method != null) {
        String found = null;
        for (final var s : names) {
          found = s;
          break;
        }
        if (found != null && method.getName().equals(found)) {
          return Optional.of(method);
        }
      }
    }
    return Optional.empty();
  }

  @Override
  @NotNull
  public Optional<RefMethod> getMethodByParameter(@NotNull final Object... types) {
    return this.findMethod0(false, types);
  }

  @NotNull
  @Override
  public <X> Optional<RefMethod> getMethodByReturnType(@NotNull final RefClass<X> type) {
    return this.getMethodByReturnType(type.getRealClass());
  }

  @NotNull
  @Override
  public Optional<RefMethod> getMethodByReturnType(@NotNull final Class<?> type) {
    final var methods = this.getMethods();
    methods.addAll(this.getDeclaredMethods());
    for (final var method : methods) {
      if (method != null) {
        if (type.equals(method.getReturnType())) {
          return Optional.of(method);
        }
      }
    }
    return Optional.empty();
  }

  @NotNull
  @Override
  public List<RefMethod> getMethods() {
    return Arrays.stream(this.clazz.getMethods())
      .map(MethodOf::new)
      .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<RefConstructed<T>> getPrimitiveConstructor(@NotNull final Object... types) {
    return this.getConstructor0(true, types);
  }

  @NotNull
  @Override
  public Optional<RefMethod> getPrimitiveMethod(@NotNull final String name, @NotNull final Object... types) {
    return this.getMethod0(name, true, types);
  }

  @Override
  @NotNull
  public Optional<RefMethod> getPrimitiveMethodByParameter(@NotNull final Object... types) {
    return this.findMethod0(true, types);
  }

  @NotNull
  @Override
  public Class<T> getRealClass() {
    return this.clazz;
  }

  @Override
  public boolean isInstance(@NotNull final Object object) {
    return this.clazz.isInstance(object);
  }

  @Override
  public boolean hasFinal() {
    return Modifier.isFinal(this.clazz.getModifiers());
  }

  @Override
  public boolean hasPrivate() {
    return Modifier.isPrivate(this.clazz.getModifiers());
  }

  @Override
  public boolean hasPublic() {
    return Modifier.isPublic(this.clazz.getModifiers());
  }

  @Override
  public boolean hasStatic() {
    return Modifier.isStatic(this.clazz.getModifiers());
  }

  /**
   * finds method by parameter type.
   *
   * @param primitive the primitive to get.
   * @param types the types to get.
   *
   * @return method.
   */
  @NotNull
  private Optional<RefMethod> findMethod0(final boolean primitive, @NotNull final Object... types) {
    final var parameter = new ParameterizedOf<>(primitive, types);
    final var methods = this.getMethods();
    methods.addAll(this.getDeclaredMethods());
    final var classList = new ArrayList<Class<?>>();
    parameter.apply(classes -> {
      classList.addAll(Arrays.asList(classes));
      return Optional.empty();
    });
    findMethod:
    for (final var method : methods) {
      final var methodTypes = method.getParameterTypes();
      if (methodTypes.length != classList.size()) {
        continue;
      }
      for (int index = 0; index < classList.size(); index++) {
        if (!Arrays.equals(classList.toArray(new Class<?>[0]), methodTypes)) {
          continue findMethod;
        }
      }
      return Optional.of(method);
    }
    return Optional.empty();
  }

  /**
   * gets constructor by parameter type.
   *
   * @param primitive the primitive to get.
   * @param types the types to get.
   *
   * @return constructor
   */
  @NotNull
  private Optional<RefConstructed<T>> getConstructor0(final boolean primitive, @NotNull final Object... types) {
    final var parameter = new ParameterizedOf<RefConstructed<T>>(primitive, types);
    return parameter.apply(classes -> {
      try {
        return Optional.of(new ConstructorOf<>(this.clazz.getConstructor(classes)));
      } catch (final NoSuchMethodException exception) {
        return parameter.apply(declaredClasses -> {
          try {
            return Optional.of(new ConstructorOf<>(this.clazz.getDeclaredConstructor(declaredClasses)));
          } catch (final NoSuchMethodException noSuchMethodException) {
            ClassOf.log.log(Level.SEVERE, "ClassOf#getConstructor0(boolean, Object[])", exception);
            return Optional.empty();
          }
        });
      }
    });
  }

  /**
   * gets method by name.
   *
   * @param name the name to get.
   * @param primitive the primitive to get.
   * @param types the types to get.
   *
   * @return method.
   */
  @NotNull
  private Optional<RefMethod> getMethod0(@NotNull final String name, final boolean primitive,
                                         @NotNull final Object... types) {
    final var parameter = new ParameterizedOf<RefMethod>(primitive, types);
    return parameter.apply(classes -> {
      try {
        return Optional.of(new MethodOf(this.clazz.getMethod(name, classes)));
      } catch (final NoSuchMethodException exception) {
        return parameter.apply(declaredClasses -> {
          try {
            return Optional.of(new MethodOf(this.clazz.getDeclaredMethod(name, declaredClasses)));
          } catch (final NoSuchMethodException noSuchMethodException) {
            ClassOf.log.log(Level.SEVERE, "ClassOf#getMethod0(String, boolean, Object[])", exception);
            return Optional.empty();
          }
        });
      }
    });
  }
}
