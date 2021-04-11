/*
 * MIT License
 *
 * Copyright (c) 2021 Infumia
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
