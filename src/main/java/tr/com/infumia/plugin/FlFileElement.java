package tr.com.infumia.plugin;

import io.github.portlek.configs.Loader;
import io.github.portlek.configs.annotation.Route;
import io.github.portlek.configs.loaders.BaseFieldLoader;
import io.github.portlek.reflection.RefField;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public final class FlFileElement extends BaseFieldLoader {

  public static final Supplier<FlFileElement> INSTANCE = FlFileElement::new;

  @Override
  public boolean canLoad(@NotNull final Loader loader, @NotNull final RefField field) {
    return FileElement.class == field.getType();
  }

  @Override
  public void onLoad(@NotNull final Loader loader, @NotNull final RefField field) {
    final var path = field.getAnnotation(Route.class)
      .map(Route::value)
      .orElse(field.getName());
    final var fieldValue = field.getValue();
    final var currentSection = this.getSection(loader);
    var section = currentSection.getConfigurationSection(path);
    if (section == null) {
      section = currentSection.createSection(path);
    }
    final var valueAtPath = FileElement.from(section);
    if (fieldValue.isPresent()) {
      final var fileElement = (FileElement) fieldValue.get();
      if (valueAtPath.isPresent()) {
        field.setValue(valueAtPath.get()
          .changeEvent(fileElement.events()));
      } else {
        FileElement.to(section, fileElement);
      }
    } else {
      valueAtPath.ifPresent(field::setValue);
    }
  }
}
