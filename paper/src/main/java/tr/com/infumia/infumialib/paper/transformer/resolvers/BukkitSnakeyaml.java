package tr.com.infumia.infumialib.paper.transformer.resolvers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformResolver;
import tr.com.infumia.infumialib.transformer.TransformedObject;
import tr.com.infumia.infumialib.transformer.declarations.FieldDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;
import tr.com.infumia.infumialib.transformer.postprocessor.LineInfo;
import tr.com.infumia.infumialib.transformer.postprocessor.PostProcessor;
import tr.com.infumia.infumialib.transformer.postprocessor.SectionSeparator;
import tr.com.infumia.infumialib.transformer.postprocessor.walkers.YamlSectionWalker;

/**
 * a class that represents yaml file configuration.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitSnakeyaml extends TransformResolver {

  /**
   * the comment prefix.
   */
  @NotNull
  private final String commentPrefix;

  /**
   * the config.
   */
  @NotNull
  private final YamlConfiguration config;

  /**
   * the section separator.
   */
  @NotNull
  private final String sectionSeparator;

  /**
   * ctor.
   *
   * @param config the config.
   * @param commentPrefix the comment prefix.
   * @param sectionSeparator the section separator.
   */
  public BukkitSnakeyaml(@NotNull final YamlConfiguration config, @NotNull final String commentPrefix,
                         @NotNull final String sectionSeparator) {
    this(commentPrefix, config, sectionSeparator);
  }

  /**
   * ctor.
   *
   * @param commentPrefix the comment prefix.
   * @param sectionSeparator the section separator.
   */
  public BukkitSnakeyaml(@NotNull final String commentPrefix, @NotNull final String sectionSeparator) {
    this(commentPrefix, new YamlConfiguration(), sectionSeparator);
  }

  /**
   * ctor.
   *
   * @param config the config.
   */
  public BukkitSnakeyaml(@NotNull final YamlConfiguration config) {
    this("# ", config, SectionSeparator.NONE);
  }

  /**
   * ctor.
   *
   * @param sectionSeparator the section separator.
   */
  public BukkitSnakeyaml(@NotNull final String sectionSeparator) {
    this("# ", sectionSeparator);
  }

  /**
   * ctor.
   */
  public BukkitSnakeyaml() {
    this(new YamlConfiguration());
  }

  @Nullable
  @Override
  public <T> T deserialize(@Nullable final Object object, @Nullable final GenericDeclaration genericSource,
                           @NotNull final Class<T> targetClass, @Nullable final GenericDeclaration genericTarget,
                           @Nullable final Object defaultValue)
    throws TransformException {
    if (object instanceof ConfigurationSection) {
      final var values = this.getMapValues((ConfigurationSection) object, false);
      return super.deserialize(values, GenericDeclaration.of(values), targetClass, genericTarget, defaultValue);
    }
    return super.deserialize(object, genericSource, targetClass, genericTarget, defaultValue);
  }

  @NotNull
  @Override
  public List<String> getAllKeys() {
    return List.copyOf(this.config.getKeys(false));
  }

  @NotNull
  @Override
  public Optional<Object> getValue(@NotNull final String path) {
    return Optional.ofNullable(this.config.get(path));
  }

  @Override
  public void load(@NotNull final InputStream inputStream, @NotNull final TransformedObjectDeclaration declaration)
    throws Exception {
    this.config.loadFromString(PostProcessor.of(inputStream).getContext());
  }

  @Override
  public boolean pathExists(@NotNull final String path) {
    return this.config.getKeys(false).contains(path);
  }

  @Override
  public void removeValue(@NotNull final String path, @Nullable final GenericDeclaration genericType,
                          @Nullable final FieldDeclaration field) {
    this.config.set(path, null);
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
    this.config.set(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
    final var contents = this.config.saveToString();
    final var processor = PostProcessor.of(contents)
      .removeLines(line -> line.startsWith(this.commentPrefix.trim()))
      .updateLinesPaths(new YamlSectionWalker() {
        @NotNull
        @Override
        public String update(@NotNull final String line, @NotNull final LineInfo lineInfo,
                             @NotNull final List<LineInfo> path) {
          var currentDeclaration = declaration;
          for (int i = 0; i < path.size() - 1; i++) {
            final var pathElement = path.get(i);
            final var field = currentDeclaration.getNonMigratedFields().get(pathElement.getName());
            if (field == null) {
              return line;
            }
            final var fieldType = field.getGenericDeclaration();
            final var type = fieldType.getType();
            if (type == null) {
              continue;
            }
            if (!TransformedObject.class.isAssignableFrom(type)) {
              return line;
            }
            currentDeclaration = TransformedObjectDeclaration.of(type);
          }
          final var lineDeclaration = currentDeclaration.getNonMigratedFields().get(lineInfo.getName());
          if (lineDeclaration == null) {
            return line;
          }
          final var fieldComment = lineDeclaration.getComment();
          if (fieldComment == null) {
            return line;
          }
          return PostProcessor.addIndent(
            PostProcessor.createComment(BukkitSnakeyaml.this.commentPrefix, fieldComment.value()),
            lineInfo.getIndent()) + line;
        }
      });
    final var header = declaration.getHeader();
    if (header != null) {
      processor.prependContextComment(this.commentPrefix, this.sectionSeparator, header.value());
    }
    processor.write(outputStream);
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
