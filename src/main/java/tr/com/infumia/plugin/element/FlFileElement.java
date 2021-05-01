package tr.com.infumia.plugin.element;

import io.github.portlek.configs.configuration.ConfigurationSection;
import io.github.portlek.configs.loaders.SectionFieldLoader;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FlFileElement extends SectionFieldLoader<FileElement> {

  public static final Supplier<FlFileElement> INSTANCE = FlFileElement::new;

  private FlFileElement() {
    super(FileElement.class);
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
