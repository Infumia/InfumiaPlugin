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

package tr.com.infumia.infumialib.transformer.resolvers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.postprocessor.PostProcessor;

/**
 * a class that represents Gson file configuration.
 */
@RequiredArgsConstructor
public class GsonJson extends TransformResolver {

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
  public GsonJson() {
    this(new GsonBuilder().setPrettyPrinting().create());
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

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
    this.gson.toJson(this.map, new OutputStreamWriter(outputStream));
  }
}
