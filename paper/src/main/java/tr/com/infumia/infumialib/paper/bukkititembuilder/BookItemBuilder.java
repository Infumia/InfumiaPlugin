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

package tr.com.infumia.infumialib.paper.bukkititembuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.bukkititembuilder.util.KeyUtil;

/**
 * a class that represents book item builders.
 * <p>
 * serialization:
 * <pre>
 * book: (main section)
 *   title: string (book's title) (for 8 and newer versions)
 *
 *   author: string (book's author) (for 8 and newer versions)
 *
 *   generation: string (book's generation) (for 10 and newer versions)
 *
 *   pages: (string list) (for 8 and newer versions)
 *     - 'page 1'
 * </pre>
 */
public final class BookItemBuilder extends Builder<BookItemBuilder, BookMeta> {

  /**
   * the deserializer.
   */
  private static final Deserializer DESERIALIZER = new Deserializer();

  /**
   * ctor.
   *
   * @param itemMeta the item meta.
   * @param itemStack the item stack.
   */
  BookItemBuilder(@NotNull final BookMeta itemMeta, @NotNull final ItemStack itemStack) {
    super(itemMeta, itemStack);
  }

  /**
   * creates a new book item builder instance.
   *
   * @param itemMeta the item meta to create.
   * @param itemStack the item stack to create.
   *
   * @return a newly created book item builder instance.
   */
  @NotNull
  public static BookItemBuilder from(@NotNull final BookMeta itemMeta, @NotNull final ItemStack itemStack) {
    return new BookItemBuilder(itemMeta, itemStack);
  }

  /**
   * creates book item builder from serialized holder.
   *
   * @param holder the holder to create.
   *
   * @return a newly created book item builder instance.
   */
  @NotNull
  public static BookItemBuilder from(@NotNull final KeyUtil.Holder<?> holder) {
    return BookItemBuilder.getDeserializer().apply(holder).orElseThrow(() ->
      new IllegalArgumentException(String.format("The given holder is incorrect!\n%s", holder)));
  }

  /**
   * obtains the deserializer.
   *
   * @return deserializer.
   */
  @NotNull
  public static Deserializer getDeserializer() {
    return BookItemBuilder.DESERIALIZER;
  }

  /**
   * adds page to the book.
   *
   * @param list the list to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder addPages(@NotNull final String... list) {
    this.getItemMeta().addPage(list);
    return this.getSelf();
  }

  @NotNull
  @Override
  public BookItemBuilder getSelf() {
    return this;
  }

  @Override
  public void serialize(@NotNull final KeyUtil.Holder<?> holder) {
    super.serialize(holder);
    final var book = new HashMap<String, Object>();
    final var itemMeta = this.getItemMeta();
    if (itemMeta.hasAuthor()) {
      book.put(KeyUtil.TITLE_KEY, itemMeta.getTitle());
    }
    if (itemMeta.hasAuthor()) {
      book.put(KeyUtil.AUTHOR_KEY, itemMeta.getAuthor());
    }
    if (Builder.VERSION >= 10) {
      final var generation = itemMeta.getGeneration();
      if (generation != null) {
        book.put(KeyUtil.GENERATION_KEY, generation.toString());
      }
    }
    book.put(KeyUtil.PAGES_KEY, itemMeta.getPages());
    holder.addAsMap(KeyUtil.BOOKS_KEY, book, String.class, Object.class);
  }

  /**
   * sets author of the book.
   *
   * @param author the author to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setAuthor(@Nullable final String author) {
    this.getItemMeta().setAuthor(author);
    return this.getSelf();
  }

  /**
   * sets generation of the book.
   *
   * @param generation the generation to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setGeneration(@Nullable final BookMeta.Generation generation) {
    if (Builder.VERSION >= 10) {
      this.getItemMeta().setGeneration(generation);
    }
    return this.getSelf();
  }

  /**
   * sets page of the book.
   *
   * @param page the page to set.
   * @param text the text to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPage(final int page, @NotNull final String text) {
    this.getItemMeta().setPage(page, text);
    return this.getSelf();
  }

  /**
   * sets pages of the book.
   *
   * @param list the list to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPages(@NotNull final String... list) {
    return this.setPages(Arrays.asList(list));
  }

  /**
   * sets pages of the book.
   *
   * @param list the list to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setPages(@NotNull final List<String> list) {
    this.getItemMeta().setPages(list);
    return this.getSelf();
  }

  /**
   * sets title of the book.
   *
   * @param title the book to set.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public BookItemBuilder setTitle(@Nullable final String title) {
    this.getItemMeta().setTitle(title);
    return this.getSelf();
  }

  /**
   * a class that represents deserializer of {@link BookMeta}.
   */
  public static final class Deserializer implements
    Function<KeyUtil.@NotNull Holder<?>, @NotNull Optional<BookItemBuilder>> {

    @NotNull
    @Override
    public Optional<BookItemBuilder> apply(@NotNull final KeyUtil.Holder<?> holder) {
      final var itemStack = Builder.getItemStackDeserializer().apply(holder);
      if (itemStack.isEmpty()) {
        return Optional.empty();
      }
      final var builder = ItemStackBuilder.from(itemStack.get()).asBook();
      holder.getAsMap(KeyUtil.BOOKS_KEY, String.class, Object.class)
        .ifPresent(book -> {
          final var title = Optional.ofNullable(book.get(KeyUtil.TITLE_KEY))
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .orElse(null);
          final var author = Optional.ofNullable(book.get(KeyUtil.AUTHOR_KEY))
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .orElse(null);
          final var pages = Optional.ofNullable(book.get(KeyUtil.PAGES_KEY))
            .filter(List.class::isInstance)
            .map(object -> (List<String>) object)
            .orElse(Collections.emptyList());
          builder.setTitle(title);
          builder.setAuthor(author);
          builder.setPages(pages);
          if (Builder.VERSION >= 10) {
            Optional.ofNullable(book.get(KeyUtil.GENERATION_KEY))
              .filter(String.class::isInstance)
              .map(String.class::cast)
              .ifPresent(generationString -> {
                BookMeta.Generation generation;
                try {
                  generation = BookMeta.Generation.valueOf(generationString);
                } catch (final Exception e) {
                  generation = null;
                }
                builder.setGeneration(generation);
              });
          }
        });
      return Optional.of(Builder.getItemMetaDeserializer(builder).apply(holder));
    }
  }
}
