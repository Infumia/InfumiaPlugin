package tr.com.infumia.infumialib.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.reflection.RefConstructed;
import tr.com.infumia.infumialib.reflection.RefField;
import tr.com.infumia.infumialib.reflection.clazz.ClassOf;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;

/**
 * a class that represents transformer pools.
 */
@Getter
public final class TransformerPool {

  /**
   * ctor.
   */
  private TransformerPool() {
  }

  /**
   * creates a new transformed object.
   *
   * @param transformedObject the transformed object to create.
   * @param <T> type of the transformed object class.
   *
   * @return a newly created transformed object.
   */
  @NotNull
  public static <T extends TransformedObject> T create(@NotNull final T transformedObject) {
    transformedObject.withDeclaration(TransformedObjectDeclaration.of(transformedObject));
    return transformedObject;
  }

  /**
   * creates a new transformed object.
   *
   * @param cls the cls to create.
   * @param <T> type of the transformed object class.
   *
   * @return a newly created transformed object.
   */
  @NotNull
  public static <T extends TransformedObject> T create(@NotNull final Class<T> cls) {
    T transformedObject;
    try {
      transformedObject = new ClassOf<>(cls).getConstructor()
        .flatMap(RefConstructed::create)
        .orElseThrow(() ->
          new tr.com.infumia.infumialib.transformer.exceptions.TransformException(String.format("Something went wrong when creating instance of %s", cls)));
    } catch (final Exception exception) {
      try {
        //noinspection unchecked
        transformedObject = (T) TransformerPool.allocateInstance(cls);
      } catch (final Exception exception1) {
        throw new tr.com.infumia.infumialib.transformer.exceptions.TransformException(String.format("Failed to create %s instance, neither default constructor available, nor unsafe succeeded", cls));
      }
    }
    return TransformerPool.create(transformedObject);
  }

  /**
   * creates a new instance of the given class.
   *
   * @param cls the cls to create.
   *
   * @return a new instance of the class.
   *
   * @throws tr.com.infumia.infumialib.transformer.exceptions.TransformException if something goes wrong when creating
   *   the instance.
   */
  @NotNull
  static Object createInstance(@NotNull final Class<?> cls) throws tr.com.infumia.infumialib.transformer.exceptions.TransformException {
    try {
      if (Collection.class.isAssignableFrom(cls)) {
        if (cls == Set.class) {
          return new HashSet<>();
        }
        if (cls == List.class || cls == Collection.class) {
          return new ArrayList<>();
        }
        return new ClassOf<>(cls).getConstructor()
          .flatMap(RefConstructed::create)
          .orElseThrow();
      }
      if (Map.class.isAssignableFrom(cls)) {
        if (cls == Map.class) {
          return new LinkedHashMap<>();
        }
        return new ClassOf<>(cls).getConstructor()
          .flatMap(RefConstructed::create)
          .orElseThrow();
      }
      throw new tr.com.infumia.infumialib.transformer.exceptions.TransformException(String.format("Cannot create instance of %s", cls));
    } catch (final Exception exception) {
      throw new tr.com.infumia.infumialib.transformer.exceptions.TransformException(String.format("Failed to create instance of %s", cls), exception);
    }
  }

  /**
   * allocates an instance but does not run any constructor.
   * <p>
   * initializes the class if it has not yet been.
   *
   * @param cls the cls to allocate.
   *
   * @return allocated instance.
   */
  @NotNull
  private static Object allocateInstance(@NotNull final Class<?> cls) throws Exception {
    final var unsafeClassOf = new ClassOf<>("sun.misc.Unsafe");
    return unsafeClassOf.getField("theUnsafe")
      .flatMap(RefField::getValue)
      .flatMap(object -> unsafeClassOf.getMethod("allocateInstance", Class.class)
        .map(method -> method.of(object)))
      .flatMap(refMethodExecuted -> refMethodExecuted.call(cls))
      .orElseThrow(() ->
        new TransformException(String.format("Something went wrong when allocating instance of %s", cls)));
  }
}
