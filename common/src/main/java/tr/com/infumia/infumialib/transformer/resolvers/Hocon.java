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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.postprocessor.PostProcessor;
import tr.com.infumia.infumialib.transformer.postprocessor.SectionSeparator;

/**
 * a class that represents Hocon file configuration.
 */
@RequiredArgsConstructor
public class Hocon extends TransformResolver {

  /**
   * the comment prefix.
   */
  @NotNull
  private final String commentPrefix;

  /**
   * the render options.
   */
  private final ConfigRenderOptions renderOpts = ConfigRenderOptions.defaults()
    .setFormatted(true)
    .setOriginComments(false)
    .setComments(true)
    .setJson(false);

  /**
   * the section separator.
   */
  @NotNull
  private final String sectionSeparator;

  /**
   * the config.
   */
  @NotNull
  private Config config = ConfigFactory.parseMap(new LinkedHashMap<>());

  /**
   * the cached map.
   */
  @NotNull
  private Map<String, Object> map = new LinkedHashMap<>();

  /**
   * ctor.
   *
   * @param sectionSeparator the section separator.
   */
  public Hocon(@NotNull final String sectionSeparator) {
    this("# ", sectionSeparator);
  }

  /**
   * ctor.
   */
  public Hocon() {
    this(SectionSeparator.NONE);
  }

  /**
   * converts config to map.
   *
   * @param config the config to convert.
   * @param declaration the declaration to convert.
   *
   * @return converted map.
   */
  @NotNull
  private static Map<String, Object> hoconToMap(@NotNull final Config config,
                                                @NotNull final TransformedObjectDeclaration declaration) {
    return declaration.getNonMigratedFields().values().stream()
      .map(FieldDeclaration::getPath)
      .filter(config::hasPath)
      .collect(Collectors.toMap(path -> path, path -> config.getValue(path).unwrapped(), (a, b) -> b, LinkedHashMap::new));
  }

  /**
   * creates a new predicate to check lines.
   *
   * @param line the line to check.
   *
   * @return {@code true} if the line starts with the certain keys.
   */
  @NotNull
  private static Predicate<FieldDeclaration> isFieldDeclaredForLine(@NotNull final String line) {
    return field -> {
      final var path = field.getPath();
      return line.startsWith(path + "=")
        || line.startsWith(path + " =")
        || line.startsWith("\"" + path + "\"")
        || line.startsWith(path + "{")
        || line.startsWith(path + " {");
    };
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
    this.config = ConfigFactory.parseString(PostProcessor.of(inputStream).getContext());
    this.map = Hocon.hoconToMap(this.config, declaration);
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
    if (value == null) {
      return null;
    }
    final var genericsDeclaration = GenericDeclaration.of(value);
    if (genericsDeclaration.getType() == char.class || genericsDeclaration.getType() == Character.class) {
      return super.serialize(value, genericType, false);
    }
    return super.serialize(value, genericType, conservative);
  }

  @NotNull
  @Override
  public Map<Object, Object> serializeMap(@NotNull final Map<Object, Object> value,
                                          @Nullable final GenericDeclaration genericType, final boolean conservative)
    throws TransformException {
    final var map = new LinkedHashMap<>();
    final var keyDeclaration = genericType == null ? null : genericType.getSubTypeAt(0).orElse(null);
    final var valueDeclaration = genericType == null ? null : genericType.getSubTypeAt(1).orElse(null);
    value.forEach((key1, value1) ->
      map.put(
        this.serialize(key1, keyDeclaration, false),
        this.serialize(value1, valueDeclaration, conservative)));
    return map;
  }

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
    this.config = ConfigFactory.parseMap(this.map);
    final var builder = new StringBuilder();
    if (!declaration.getNonMigratedFields().isEmpty()) {
      for (final var field : declaration.getNonMigratedFields().values()) {
        final var entryMap = Collections.singletonMap(field.getPath(), this.getValue(field.getPath()));
        final var entryConfig = ConfigFactory.parseMap(entryMap);
        builder.append(entryConfig.root().render(this.renderOpts)).append(this.sectionSeparator);
      }
    } else {
      builder.append(this.config.root().render(this.renderOpts));
    }
    final var processor = PostProcessor.of(builder.toString())
      .removeLines(line -> line.startsWith(this.commentPrefix.trim()))
      .updateLines(line -> declaration.getNonMigratedFields().values().stream()
        .filter(Hocon.isFieldDeclaredForLine(line))
        .findAny()
        .map(FieldDeclaration::getComment)
        .map(comment -> this.sectionSeparator + PostProcessor.createComment(this.commentPrefix, comment.value()) + line)
        .orElse(line));
    final var header = declaration.getHeader();
    if (header != null) {
      processor.prependContextComment(this.commentPrefix, header.value());
    }
    processor.write(outputStream);
  }
}
