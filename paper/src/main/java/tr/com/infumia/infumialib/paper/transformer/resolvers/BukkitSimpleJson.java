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

import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.parser.JSONParser;
import tr.com.infumia.infumialib.transformer.resolvers.SimpleJson;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;
import tr.com.infumia.infumialib.transformer.exceptions.TransformException;

/**
 * a class that represents json file configuration.
 */
public final class BukkitSimpleJson extends SimpleJson {

  /**
   * ctor.
   *
   * @param parser the parser.
   */
  public BukkitSimpleJson(final @NotNull JSONParser parser) {
    super(parser);
  }

  /**
   * ctor.
   */
  public BukkitSimpleJson() {
    super();
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

  @Nullable
  @Override
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value instanceof ConfigurationSection) {
      return this.getMapValues((ConfigurationSection) value, false);
    }
    return super.serialize(value, genericType, conservative);
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
