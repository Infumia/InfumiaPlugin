package tr.com.infumia.infumialib.transformer.resolvers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;

/**
 * a class that represents in memory resolver.
 */
public final class InMemoryResolver extends TransformResolver {

  /**
   * the map.
   */
  @NotNull
  private final Map<String, Object> map = new LinkedHashMap<>();

  @NotNull
  @Override
  public List<String> getAllKeys() {
    return List.copyOf(this.map.keySet());
  }

  @NotNull
  @Override
  public Optional<Object> getValue(@NotNull final String path) {
    return Optional.ofNullable(this.map.get(path));
  }

  @Override
  public void load(@NotNull final InputStream inputStream, @NotNull final tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration declaration) {
  }

  @Override
  public boolean pathExists(@NotNull final String path) {
    return this.map.containsKey(path);
  }

  @Override
  public void removeValue(@NotNull final String path, @Nullable final tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration genericType,
                          @Nullable final tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration field) {
    this.map.remove(path);
  }

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, value);
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
  }
}
