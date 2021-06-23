/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.infumialib.paper.transformer.resolvers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.postprocessor.PostProcessor;

/**
 * a class that represents json file configuration.
 */
@RequiredArgsConstructor
public final class BukkitGsonJson extends TransformResolver {

  /**
   * the gson.
   */
  @NotNull
  private final Gson gson;

  /**
   * the cache map.
   */
  private Map<String, Object> map = new LinkedHashMap<>();

  /**
   * ctor.
   */
  public BukkitGsonJson() {
    this(new GsonBuilder().setPrettyPrinting().create());
  }

  @Nullable
  @Override
  public <T> T deserialize(@Nullable final Object object, @Nullable final GenericDeclaration genericSource,
                           @NotNull final Class<T> targetClass, @Nullable final GenericDeclaration genericTarget,
                           @Nullable final Object defaultValue) throws TransformException {
    if (object instanceof ConfigurationSection) {
      final var values = this.getMapValues((ConfigurationSection) object, false);
      return super.deserialize(values, GenericDeclaration.of(values), targetClass, genericTarget, defaultValue);
    }
    return super.deserialize(object, genericSource, targetClass, genericTarget, defaultValue);
  }

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
  public void load(@NotNull final InputStream inputStream, @NotNull final TransformedObjectDeclaration declaration) {
    //noinspection unchecked
    this.map = this.gson.fromJson(PostProcessor.of(inputStream).getContext(), Map.class);
    if (this.map == null) {
      this.map = new LinkedHashMap<>();
    }
  }

  @Override
  public boolean pathExists(@NotNull final String path) {
    return this.map.containsKey(path);
  }

  @Override
  public void removeValue(@NotNull final String path, @Nullable final GenericDeclaration genericType,
                          @Nullable final FieldDeclaration field) {
    this.map.remove(path);
  }

  @Nullable
  @Override
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value instanceof ConfigurationSection) {
      return this.getMapValues((ConfigurationSection) value, false);
    }
    return super.serialize(value, genericType, conservative);
  }

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
    this.gson.toJson(this.map, new OutputStreamWriter(outputStream));
  }

  /**
   * gets the section value with primitive objects.
   *
   * @param section the section to get.
   * @param deep the deep to get.
   *
   * @return map values.
   */
  @NotNull
  private Map<String, Object> getMapValues(@NotNull final ConfigurationSection section, final boolean deep) {
    return section.getValues(deep).entrySet().stream()
      .map(entry -> {
        final var key = entry.getKey();
        final var value = entry.getValue();
        if (value instanceof ConfigurationSection) {
          return Map.entry(key, this.getMapValues((ConfigurationSection) value, deep));
        }
        return Map.entry(key, value);
      })
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
