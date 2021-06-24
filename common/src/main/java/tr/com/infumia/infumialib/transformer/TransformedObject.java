package tr.com.infumia.infumialib.transformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.declarations.TransformedObjectDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;

/**
 * an abstract class that represents transformed objects.
 */
public abstract class TransformedObject {

  /**
   * the declaration.
   */
  @Nullable
  @Getter
  private TransformedObjectDeclaration declaration;

  /**
   * the path.
   */
  @Nullable
  private Path path;

  /**
   * the resolver.
   */
  @Nullable
  @Getter
  private TransformResolver resolver;

  /**
   * ctor.
   */
  protected TransformedObject() {
    this.withDeclaration(TransformedObjectDeclaration.of(this));
  }

  /**
   * get values as map.
   *
   * @param resolver the resolver to get.
   * @param conservative the conservative to get.
   *
   * @return values as map.
   *
   * @throws TransformException if something goes wrong when getting
   *   the value as map.
   * @throws NullPointerException if {@link #declaration} is null.
   */
  @NotNull
  public final Map<String, Object> asMap(@NotNull final TransformResolver resolver, final boolean conservative)
    throws TransformException {
    Objects.requireNonNull(this.declaration, "declaration");
    final var map = new LinkedHashMap<String, Object>();
    this.declaration.getNonMigratedFields().forEach((key, fieldDeclaration) -> map.put(
      fieldDeclaration.getPath(),
      resolver.serialize(fieldDeclaration.getValue(), fieldDeclaration.getGenericDeclaration(), conservative)));
    if (this.resolver == null) {
      return map;
    }
    this.resolver.getAllKeys().stream()
      .filter(keyName -> !map.containsKey(keyName))
      .forEach(keyName ->
        this.resolver.getValue(keyName).ifPresent(value ->
          map.put(keyName, this.resolver.serialize(value, GenericDeclaration.of(value), conservative))));
    return map;
  }

  /**
   * creates the parent directory of {@link #path}.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject createDirectory() throws IOException {
    return this.createDirectory(this.getParent(Objects.requireNonNull(this.path, "path")));
  }

  /**
   * creates the parent directory of the file.
   *
   * @param file the file to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   */
  @NotNull
  public final TransformedObject createDirectory(@NotNull final File file) throws IOException {
    return this.createDirectory(file.toPath());
  }

  /**
   * creates the parent directory of the path.
   *
   * @param path the path to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   */
  @NotNull
  public final TransformedObject createDirectory(@NotNull final Path path) throws IOException {
    Files.createDirectory(path);
    return this;
  }

  /**
   * creates {@link #path}.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject createFile() throws IOException {
    return this.createFile(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * creates the file.
   *
   * @param file the file to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   */
  @NotNull
  public final TransformedObject createFile(@NotNull final File file) throws IOException {
    return this.createFile(file.toPath());
  }

  /**
   * creates the path.
   *
   * @param path the path to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws IOException if something goes wrong with I/O.
   */
  @NotNull
  public final TransformedObject createFile(@NotNull final Path path) throws IOException {
    final var parent = this.getParent(path);
    if (!this.exists(parent)) {
      this.createDirectory(parent);
    }
    if (!this.exists(path)) {
      Files.createFile(path);
    }
    return this;
  }

  /**
   * creates {@link #path}.
   *
   * @return {@code this} for builder chain.
   *
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject createFileUnchecked() {
    return this.createFileUnchecked(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * creates the file.
   *
   * @param file the file to create.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject createFileUnchecked(@NotNull final File file) {
    return this.createFileUnchecked(file.toPath());
  }

  /**
   * creates the path.
   *
   * @param path the path to create.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject createFileUnchecked(@NotNull final Path path) {
    try {
      this.createFile(path);
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return this;
  }

  /**
   * checks if the file exists or not.
   *
   * @return {@code true} if the file exists.
   *
   * @throws NullPointerException if {@link #path} is null.
   */
  public final boolean exists() {
    return this.exists(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * checks if the file exists or not.
   *
   * @param file the file to check.
   *
   * @return {@code true} if the file exists.
   */
  public final boolean exists(@NotNull final File file) {
    return this.exists(file.toPath());
  }

  /**
   * checks if the path exists or not.
   *
   * @param path the path to check.
   *
   * @return {@code true} if the file exists.
   */
  public final boolean exists(@NotNull final Path path) {
    return Files.exists(path);
  }

  /**
   * gets the value at path.
   *
   * @param path the path to get.
   * @param cls the cls to get.
   * @param <T> type of the value.
   *
   * @return value at path.
   *
   * @throws NullPointerException if {@link #resolver} is null.
   * @throws NullPointerException if {@link #declaration} is null.
   */
  @NotNull
  public final <T> Optional<T> get(@NotNull final String path, @NotNull final Class<T> cls) {
    Objects.requireNonNull(this.resolver, "resolver");
    Objects.requireNonNull(this.declaration, "declaration");
    final var field = this.declaration.getNonMigratedFields().get(path);
    if (field == null) {
      return this.resolver.getValue(path, cls, null, null);
    }
    return Optional.ofNullable(this.resolver.deserialize(
      field.getValue(),
      field.getGenericDeclaration(),
      cls,
      GenericDeclaration.ofReady(cls),
      field.getStartingValue()));
  }

  /**
   * gets value at path.
   *
   * @param path the path to get.
   *
   * @return value at path.
   *
   * @throws NullPointerException if {@link #resolver} is null.
   * @throws NullPointerException if {@link #declaration} is null.
   */
  @NotNull
  public final Optional<Object> get(@NotNull final String path) {
    Objects.requireNonNull(this.resolver, "resolver");
    Objects.requireNonNull(this.declaration, "declaration");
    final var field = this.declaration.getNonMigratedFields().get(path);
    if (field != null) {
      return Optional.ofNullable(field.getValue());
    }
    return this.resolver.getValue(path);
  }

  /**
   * obtains all keys.
   *
   * @return all keys.
   */
  @NotNull
  public final List<String> getAllKeys() {
    return new ArrayList<>(Objects.requireNonNull(this.declaration, "declaration").getNonMigratedFields().keySet());
  }

  /**
   * gets parent file of {@link #path}.
   *
   * @return parent path.
   *
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final Path getParent() {
    return this.getParent(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * gets parent path of the path.
   *
   * @param path the parent path.
   *
   * @return parent path.
   */
  @NotNull
  public final Path getParent(@NotNull final Path path) {
    return path.toFile().getParentFile().toPath();
  }

  /**
   * loads the transformed object.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject initiate() throws TransformException {
    return this.initiate(true);
  }

  /**
   * loads the transformed object.
   *
   * @param update the update to initiate.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject initiate(final boolean update) throws TransformException {
    return this.initiate(Objects.requireNonNull(this.path, "path"), update);
  }

  /**
   * initiates the transformed object.
   *
   * @param file the file to initiate.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject initiate(@NotNull final File file) throws TransformException {
    return this.initiate(file, true);
  }

  /**
   * initiates the transformed object.
   *
   * @param file the file to initiate.
   * @param update the update to initiate.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject initiate(@NotNull final File file, final boolean update) throws TransformException {
    return this.initiate(file.toPath(), update);
  }

  /**
   * initiates the transformed object.
   *
   * @param path the path to initiate.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject initiate(@NotNull final Path path) throws TransformException {
    return this.initiate(path, true);
  }

  /**
   * initiates the transformed object.
   *
   * @param path the path to initiate.
   * @param update the update to initiate.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject initiate(@NotNull final Path path, final boolean update) throws TransformException {
    if (!this.exists(path)) {
      this.createFileUnchecked(path);
    }
    return this.load(path, update);
  }

  /**
   * loads the transformed object.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject load() throws TransformException {
    return this.load(true);
  }

  /**
   * loads the transformed object.
   *
   * @param update the update to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject load(final boolean update) throws TransformException {
    return this.load(Objects.requireNonNull(this.path, "path"), update);
  }

  /**
   * loads the transformed object.
   *
   * @param file the file to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject load(@NotNull final File file) throws TransformException {
    return this.load(file, true);
  }

  /**
   * loads the transformed object.
   *
   * @param file the file to load.
   * @param update the update to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject load(@NotNull final File file, final boolean update) throws TransformException {
    return this.load(file.toPath(), update);
  }

  /**
   * loads the transformed object.
   *
   * @param path the path to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject load(@NotNull final Path path) throws TransformException {
    return this.load(path, true);
  }

  /**
   * loads the transformed object.
   *
   * @param path the path to load.
   * @param update the update to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject load(@NotNull final Path path, final boolean update) throws TransformException {
    try {
      this.load(new FileInputStream(path.toFile()));
      if (update) {
        this.save(path);
      }
    } catch (final Exception exception) {
      throw new TransformException(String.format("Failed use #load(%s)", path), exception);
    }
    return this;
  }

  /**
   * loads the transformed object.
   *
   * @param data the data to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   */
  @NotNull
  public final TransformedObject load(@NotNull final String data) {
    return this.load(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * loads the transformed object.
   *
   * @param inputStream the input stream to load.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when loading the objects.
   * @throws NullPointerException if {@link #resolver} is null.
   * @throws NullPointerException if {@link #declaration} is null.
   */
  @NotNull
  public final TransformedObject load(@NotNull final InputStream inputStream) {
    Objects.requireNonNull(this.resolver, "resolver");
    Objects.requireNonNull(this.declaration, "declaration");
    try {
      this.resolver.load(inputStream, this.declaration);
      return this.update();
    } catch (final Exception exception) {
      throw new TransformException(String.format("Failed use #load(%s)", inputStream), exception);
    }
  }

  /**
   * removes the value at path.
   *
   * @param path the path to remove.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject remove(@NotNull final String path) {
    Objects.requireNonNull(this.resolver, "resolver");
    Objects.requireNonNull(this.declaration, "declaration");
    final var field = this.declaration.getNonMigratedFields().get(path);
    if (field != null) {
      field.setValue(null);
    }
    this.resolver.removeValue(path, field == null ? null : field.getGenericDeclaration(), field);
    return this;
  }

  /**
   * saves the objects into the {@link #path}.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when saving objects into the file.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject save() throws TransformException {
    return this.save(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * saves the objects into the file.
   *
   * @param file the file to save.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when saving objects into the file.
   */
  @NotNull
  public final TransformedObject save(@NotNull final File file) throws TransformException {
    return this.save(file.toPath());
  }

  /**
   * saves the objects into the file.
   *
   * @param path the path to save.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when saving objects into the file.
   */
  @NotNull
  public final TransformedObject save(@NotNull final Path path) throws TransformException {
    try {
      this.createFile(path);
      return this.save(new PrintStream(
        new FileOutputStream(path.toFile(), false),
        true,
        StandardCharsets.UTF_8.name()));
    } catch (final Exception exception) {
      throw new TransformException(String.format("Failed use #save(%s)", path), exception);
    }
  }

  /**
   * saves the values into the output stream.
   *
   * @param outputStream the output stream to save.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when saving the objects into the stream.
   */
  @NotNull
  public final TransformedObject save(@NotNull final OutputStream outputStream) throws TransformException {
    Objects.requireNonNull(this.declaration, "declaration");
    Objects.requireNonNull(this.resolver, "resolver");
    this.declaration.getNonMigratedFields().forEach((key, fieldDeclaration) -> {
      final var path = fieldDeclaration.getPath();
      final var fieldValue = fieldDeclaration.getValue();
      if (!this.resolver.isValid(fieldDeclaration, fieldValue)) {
        throw new TransformException(String.format("%s marked %s as invalid without throwing an exception",
          this.resolver.getClass(), path));
      }
      try {
        this.resolver.setValue(path, fieldValue, fieldDeclaration.getGenericDeclaration(), fieldDeclaration);
      } catch (final Exception exception) {
        throw new TransformException(String.format("Failed to use #setValue(%s, %s, %s, %s)",
          path, fieldValue, fieldDeclaration.getGenericDeclaration(), fieldDeclaration), exception);
      }
    });
    try {
      this.resolver.write(outputStream, this.declaration);
    } catch (final Exception exception) {
      throw new TransformException(String.format("Failed use #write(%s)", outputStream), exception);
    }
    return this;
  }

  /**
   * saves default values into the file.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when sawing the defaults.
   * @throws NullPointerException if {@link #path} is null.
   */
  @NotNull
  public final TransformedObject saveDefaults() throws TransformException {
    return this.saveDefaults(Objects.requireNonNull(this.path, "path"));
  }

  /**
   * saves default values into the file.
   *
   * @param file the file to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when sawing the defaults.
   */
  @NotNull
  public final TransformedObject saveDefaults(@NotNull final File file) throws TransformException {
    if (this.exists(file)) {
      this.save(file);
    }
    return this;
  }

  /**
   * saves default values into the file.
   *
   * @param path the path to create.
   *
   * @return {@code this} for builder chain.
   *
   * @throws TransformException if something goes wrong when sawing the defaults.
   */
  @NotNull
  public final TransformedObject saveDefaults(@NotNull final Path path) throws TransformException {
    return this.saveDefaults(path.toFile());
  }

  /**
   * saves values to string.
   *
   * @return saved string.
   *
   * @throws TransformException if something goes wrong when saving the objects.
   */
  @NotNull
  public final String saveToString() throws TransformException {
    final var outputStream = new ByteArrayOutputStream();
    this.save(outputStream);
    return outputStream.toString(StandardCharsets.UTF_8);
  }

  /**
   * sets the value to path.
   *
   * @param path the path to set.
   * @param value the value to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject set(@NotNull final String path, @NotNull final Object value) {
    Objects.requireNonNull(this.resolver, "resolver");
    Objects.requireNonNull(this.declaration, "declaration");
    final var field = this.declaration.getNonMigratedFields().get(path);
    var tempValue = value;
    if (field != null) {
      final var declaration = field.getGenericDeclaration();
      if (declaration.getType() != null) {
        tempValue = this.resolver.deserialize(tempValue, GenericDeclaration.of(tempValue), declaration.getType(), declaration, null);
      }
      field.setValue(tempValue);
    }
    final var fieldGenerics = field == null ? null : field.getGenericDeclaration();
    this.resolver.setValue(path, tempValue, fieldGenerics, field);
    return this;
  }

  /**
   * updates the transformed object.
   *
   * @return {@code this} for builder chain.
   *
   * @throws NullPointerException if {@link #declaration} is null.
   * @throws NullPointerException if {@link #resolver} is null.
   */
  @NotNull
  public final TransformedObject update() {
    Objects.requireNonNull(this.declaration, "declaration");
    Objects.requireNonNull(this.resolver, "resolver");
    Optional.ofNullable(this.resolver.getParentObject())
      .map(TransformedObject::getDeclaration)
      .map(TransformedObjectDeclaration::getVersion)
      .ifPresent(this.declaration::setVersion);
    final int fileVersion;
    if (this.resolver.getAllKeys().contains("file-version")) {
      fileVersion = this.get("file-version", int.class).orElseThrow(() ->
        new TransformException("Something went wrong when getting the version."));
    } else {
      fileVersion = 1;
    }
    if (this.resolver.getParentObject() == null) {
      this.set("file-version", this.declaration.getVersionInteger());
    }
    this.declaration.getAllFields().forEach((s, fieldDeclaration) -> {
      final var version = this.declaration.getVersion();
      final var migration = fieldDeclaration.getMigration();
      if (migration == null ||
        migration.value() <= 0 ||
        version == null ||
        IntStream.rangeClosed(fileVersion, version.value()).noneMatch(value -> value == migration.value())) {
        return;
      }
      this.remove(fieldDeclaration.getPath());
    });
    this.declaration.getNonMigratedFields().forEach((key, fieldDeclaration) -> {
      final var fieldPath = fieldDeclaration.getPath();
      final var genericType = fieldDeclaration.getGenericDeclaration();
      final var type = Objects.requireNonNull(genericType.getType(), "type");
      final var variable = fieldDeclaration.getVariable();
      var updateValue = true;
      if (variable != null) {
        final var variableValue = variable.value();
        final var property = System.getProperty(variableValue, System.getenv(variableValue));
        if (property != null) {
          final Object value;
          try {
            value = this.resolver.deserialize(property, GenericDeclaration.of(property), type, genericType, fieldDeclaration.getStartingValue());
          } catch (final Exception exception) {
            throw new TransformException(String.format("Failed to use #deserialize for @Variable { %s }",
              variableValue), exception);
          }
          if (!this.resolver.isValid(fieldDeclaration, value)) {
            throw new TransformException(String.format("%s marked %s as invalid without throwing an exception",
              this.resolver.getClass(), fieldPath));
          }
          fieldDeclaration.setValue(value);
          fieldDeclaration.setHideVariable(true);
          updateValue = false;
        }
      }
      if (!this.resolver.pathExists(fieldPath)) {
        return;
      }
      final Object value;
      try {
        value = this.resolver.getValue(fieldPath, type, genericType, fieldDeclaration.getStartingValue()).orElse(null);
      } catch (final Exception exception) {
        throw new TransformException(String.format("Failed to use #getValue for %s", fieldPath), exception);
      }
      if (updateValue) {
        if (!this.resolver.isValid(fieldDeclaration, value)) {
          throw new TransformException(String.format("%s marked %s as invalid without throwing an exception",
            this.resolver.getClass(), fieldPath));
        }
        if (value != null) {
          fieldDeclaration.setValue(value);
        }
      }
      fieldDeclaration.setStartingValue(value);
    });
    return this;
  }

  /**
   * sets the declaration.
   *
   * @param declaration the declaration to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withDeclaration(@NotNull final TransformedObjectDeclaration declaration) {
    this.declaration = declaration;
    return this;
  }

  /**
   * sets the {@link #path}.
   *
   * @param file the file to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withFile(@NotNull final File file) {
    return this.withFile(file.toPath());
  }

  /**
   * sets the {@link #path}.
   *
   * @param path the path to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withFile(@NotNull final String path) {
    return this.withFile(Path.of(path));
  }

  /**
   * sets the {@link #path}.
   *
   * @param path the path to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withFile(@NotNull final Path path) {
    this.path = path;
    return this;
  }

  /**
   * sets the resolver.
   *
   * @param resolver the resolver to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withResolver(@NotNull final TransformResolver resolver) {
    this.resolver = resolver.withCurrentObject(this);
    return this;
  }

  /**
   * register the transformer pack.
   *
   * @param consumer the consumer to register.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withTransformPack(@NotNull final Consumer<@NotNull TransformRegistry> consumer) {
    return this.withTransformPack(TransformPack.create(consumer));
  }

  /**
   * register the transform pack.
   *
   * @param pack the pack to register.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final TransformedObject withTransformPack(@NotNull final TransformPack pack) {
    Objects.requireNonNull(this.resolver, "resolver").withTransformerPacks(pack);
    return this;
  }
}
