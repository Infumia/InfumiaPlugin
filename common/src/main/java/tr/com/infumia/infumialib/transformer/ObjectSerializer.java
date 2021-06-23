package tr.com.infumia.infumialib.transformer;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

/**
 * an interface to determine object serializers.
 *
 * @param <T> type of the object.
 */
public interface ObjectSerializer<T> {

  /**
   * deserializes the object.
   *
   * @param transformedData the transformed data to deserialize.
   * @param declaration the declaration to deserialize.
   *
   * @return deserialized object.
   */
  @NotNull
  Optional<T> deserialize(@NotNull TransformedData transformedData, @Nullable GenericDeclaration declaration);

  /**
   * deserializes the object.
   *
   * @param field the field to serialize.
   * @param transformedData the transformed data to deserialize.
   * @param declaration the declaration to deserialize.
   *
   * @return deserialized object.
   */
  @NotNull
  Optional<T> deserialize(@NotNull T field, @NotNull TransformedData transformedData,
                          @Nullable GenericDeclaration declaration);

  /**
   * serializes the object.
   *
   * @param t the t to serialize.
   * @param transformedData the transformed data to serialize.
   */
  void serialize(@NotNull T t, @NotNull TransformedData transformedData);

  /**
   * checks if the given class supports the serialization.
   *
   * @param cls the cls to check.
   *
   * @return {@code true} if the class supports the serialization.
   */
  boolean supports(@NotNull Class<?> cls);
}
