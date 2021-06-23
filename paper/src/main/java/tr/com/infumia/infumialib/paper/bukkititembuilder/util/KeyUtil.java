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

package tr.com.infumia.infumialib.paper.bukkititembuilder.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TransformedData;
import tr.com.infumia.infumialib.transformer.declarations.GenericDeclaration;

/**
 * a class that contains utility methods for getting elements from key of {@link Map}.
 */
public final class KeyUtil {

  /**
   * the amount key.
   */
  public static final String AMOUNT_KEY = "amount";

  /**
   * the author key.
   */
  public static final String AUTHOR_KEY = "author";

  /**
   * the base effect key.
   */
  public static final String BASE_EFFECT_KEY = "base-effect";

  /**
   * the base key.
   */
  public static final String BASE_KEY = "base";

  /**
   * the book key.
   */
  public static final String BOOKS_KEY = "book";

  /**
   * the center key.
   */
  public static final String CENTER_KEY = "center";

  /**
   * the colors key.
   */
  public static final String COLORS_KEY = "colors";

  /**
   * the color key.
   */
  public static final String COLOR_KEY = "color";

  /**
   * the creature key.
   */
  public static final String CREATURE_KEY = "creature";

  /**
   * the custom effects key.
   */
  public static final String CUSTOM_EFFECTS_KEY = "custom-effects";

  /**
   * the damage key.
   */
  public static final String DAMAGE_KEY = "damage";

  /**
   * the data key.
   */
  public static final String DATA_KEY = "data";

  /**
   * the display name key.
   */
  public static final String DISPLAY_NAME_KEY = "name";

  /**
   * the enchantment key.
   */
  public static final String ENCHANTMENT_KEY = "enchants";

  /**
   * the fade key.
   */
  public static final String FADE_KEY = "fade";

  /**
   * the firework key.
   */
  public static final String FIREWORK_KEY = "firework";

  /**
   * the flag key.
   */
  public static final String FLAG_KEY = "flags";

  /**
   * the flicker key.
   */
  public static final String FLICKER_KEY = "flicker";

  /**
   * the generation key.
   */
  public static final String GENERATION_KEY = "generation";

  /**
   * the level key.
   */
  public static final String LEVEL_KEY = "level";

  /**
   * the location key.
   */
  public static final String LOCATION_KEY = "location";

  /**
   * the locked key.
   */
  public static final String LOCKED_KEY = "locked";

  /**
   * the lore key.
   */
  public static final String LORE_KEY = "lore";

  /**
   * the map id key.
   */
  public static final String MAP_ID_KEY = "map-id";

  /**
   * the map key.
   */
  public static final String MAP_KEY = "map";

  /**
   * the material key.
   */
  public static final String MATERIAL_KEY = "material";

  /**
   * the pages key.
   */
  public static final String PAGES_KEY = "pages";

  /**
   * the patterns key.
   */
  public static final String PATTERNS_KEY = "patterns";

  /**
   * the power key.
   */
  public static final String POWER_KEY = "power";

  /**
   * the projectiles ke.
   */
  public static final String PROJECTILES_KEY = "projectiles";

  /**
   * the scale key.
   */
  public static final String SCALE_KEY = "scale";

  /**
   * the scaling key.
   */
  public static final String SCALING_KEY = "scaling";

  /**
   * the skull texture key.
   */
  public static final String SKULL_TEXTURE_KEY = "skull";

  /**
   * the title key.
   */
  public static final String TITLE_KEY = "title";

  /**
   * the tracking position key.
   */
  public static final String TRACKING_POSITION_KEY = "tracking-position";

  /**
   * the trail key.
   */
  public static final String TRAIL_KEY = "trail";

  /**
   * the type key.
   */
  public static final String TYPE_KEY = "type";

  /**
   * the unlimited tracking key.
   */
  public static final String UNLIMITED_TRACKING_KEY = "unlimited-tracking";

  /**
   * the view key.
   */
  public static final String VIEW_KEY = "view";

  /**
   * the world key.
   */
  public static final String WORLD_KEY = "world";

  /**
   * the x key.
   */
  public static final String X_KEY = "x";

  /**
   * the z key.
   */
  public static final String Z_KEY = "z";

  /**
   * ctor.
   */
  private KeyUtil() {
  }

  /**
   * an interface to determine key holders.
   *
   * @param <T> type of the holder.
   */
  public interface Holder<T> {

    /**
     * creates a holder from a map.
     *
     * @param map the map to create.
     *
     * @return a newly created holder.
     */
    @NotNull
    static Holder<Map<String, Object>> map(@NotNull final Map<String, Object> map) {
      return new Holder<>() {
        @Override
        public <E> void add(@NotNull final String key, @Nullable final E object, @NotNull final Class<E> cls) {
          map.put(key, object);
        }

        @NotNull
        @Override
        public <T> Optional<T> get(@NotNull final String key, @NotNull final Class<T> cls) {
          final var value = map.get(key);
          if (value == null) {
            return Optional.empty();
          }
          final var valueClass = value.getClass();
          if (cls.isAssignableFrom(valueClass)) {
            //noinspection unchecked
            return Optional.of((T) value);
          }
          if (!GenericDeclaration.isWrapperBoth(cls, valueClass)) {
            return Optional.empty();
          }
          final var clsWrapper = GenericDeclaration.of(cls).toWrapper();
          final var valueWrapper = GenericDeclaration.of(valueClass).toWrapper();
          if (clsWrapper.isPresent() &&
            clsWrapper.get() == (valueWrapper.isPresent() ? valueWrapper.orElseThrow() : valueClass)) {
            //noinspection unchecked
            return Optional.of((T) value);
          }
          if (valueWrapper.isPresent() && valueWrapper.get() == cls) {
            //noinspection unchecked
            return Optional.of((T) value);
          }
          return Optional.empty();
        }

        @NotNull
        @Override
        public Map<String, Object> getHolder() {
          return map;
        }
      };
    }

    /**
     * creates a holder from a transformed data.
     *
     * @param transformedData the transformed data to create.
     *
     * @return a newly created holder.
     */
    @NotNull
    static Holder<TransformedData> transformedData(@NotNull final TransformedData transformedData) {
      return new Holder<>() {
        @Override
        public <E> void add(@NotNull final String key, @Nullable final E object, @NotNull final Class<E> cls) {
          transformedData.add(key, object, cls);
        }

        @Override
        public <E> void addAsCollection(@NotNull final String key, @NotNull final Collection<E> collection,
                                        @NotNull final Class<E> cls) {
          transformedData.addCollection(key, collection, cls);
        }

        @Override
        public <K, V> void addAsMap(@NotNull final String key, @NotNull final Map<K, V> object,
                                    @NotNull final Class<K> keyClass, @NotNull final Class<V> valueClass) {
          transformedData.addAsMap(key, object, keyClass, valueClass);
        }

        @NotNull
        @Override
        public <E> Optional<E> get(@NotNull final String key, @NotNull final Class<E> cls) {
          return transformedData.get(key, cls);
        }

        @NotNull
        @Override
        public <E> Optional<List<E>> getAsList(@NotNull final String key, @NotNull final Class<E> cls) {
          return transformedData.getAsList(key, cls);
        }

        @NotNull
        @Override
        public <K, V> Optional<Map<K, V>> getAsMap(@NotNull final String key, @NotNull final Class<K> keyClass,
                                                   @NotNull final Class<V> valueClass) {
          return transformedData.getAsMap(key, keyClass, valueClass);
        }

        @NotNull
        @Override
        public TransformedData getHolder() {
          return transformedData;
        }
      };
    }

    /**
     * adds the object to the key.
     *
     * @param key the key to add.
     * @param object the object to add.
     * @param cls the cls to add.
     * @param <E> type of the object.
     */
    <E> void add(@NotNull String key, @Nullable E object, @NotNull Class<E> cls);

    /**
     * adds the collection to the key.
     *
     * @param key the key to add.
     * @param collection the collection to add.
     * @param cls the cls to add.
     * @param <E> type of the element.
     */
    default <E> void addAsCollection(@NotNull final String key, @NotNull final Collection<E> collection, @NotNull final Class<E> cls) {
      this.add(key, collection, Collection.class);
    }

    /**
     * adds the object to the key.
     *
     * @param key the key to add.
     * @param object the object to add.
     * @param keyClass the key class to add.
     * @param valueClass the value class to add.
     * @param <K> type of the key.
     * @param <V> type of the value.
     */
    default <K, V> void addAsMap(@NotNull final String key, @NotNull final Map<K, V> object, @NotNull final Class<K> keyClass,
                                 @NotNull final Class<V> valueClass) {
      this.add(key, object, Map.class);
    }

    /**
     * gets the value at key.
     *
     * @param key the key to get.
     * @param cls the cls to get.
     * @param <E> type of the value.
     *
     * @return value at key.
     */
    @NotNull <E> Optional<E> get(@NotNull String key, @NotNull Class<E> cls);

    /**
     * gets the value at key as list.
     *
     * @param key the key to get.
     * @param cls the cls to get.
     * @param <E> type of the element.
     *
     * @return value at key as list.
     */
    @NotNull
    default <E> Optional<List<E>> getAsList(@NotNull final String key, @NotNull final Class<E> cls) {
      return this.get(key, List.class)
        .map(list -> (List<E>) list);
    }

    /**
     * gets the value at key as map.
     *
     * @param key the key to get.
     * @param keyClass the key class to get.
     * @param valueClass the value class to get.
     * @param <K> type of of the key.
     * @param <V> type of the value.
     *
     * @return value at key as map.
     */
    @NotNull
    default <K, V> Optional<Map<K, V>> getAsMap(@NotNull final String key, @NotNull final Class<K> keyClass,
                                                @NotNull final Class<V> valueClass) {
      return this.get(key, Map.class)
        .map(map -> (Map<K, V>) map);
    }

    /**
     * obtains the holder.
     *
     * @return holder
     */
    @NotNull
    T getHolder();
  }
}
