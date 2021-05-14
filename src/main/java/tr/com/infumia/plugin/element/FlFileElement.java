package tr.com.infumia.plugin.element;

import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.configuration.ConfigurationSection;
import io.github.portlek.configs.loaders.SectionFieldLoader;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation of {@link SectionFieldLoader} for {@link FileElement}.
 */
public final class FlFileElement extends SectionFieldLoader<FileElement> {

  /**
   * the instance.
   */
  public static final Func INSTANCE = FlFileElement::new;

  /**
   * ctor.
   *
   * @param holder the holder.
   * @param section the section.
   */
  private FlFileElement(@NotNull final ConfigHolder holder, @NotNull final ConfigurationSection section) {
    super(holder, section, FileElement.class);
  }

  @NotNull
  @Override
  public Optional<FileElement> toFinal(@NotNull final ConfigurationSection section,
                                       @Nullable final FileElement fieldValue) {
    final var deserialize = FileElement.deserialize(section);
    if (fieldValue == null) {
      return deserialize;
    }
    return deserialize.map(fileElement -> fileElement.changeEvent(fieldValue.events()));
  }
}
