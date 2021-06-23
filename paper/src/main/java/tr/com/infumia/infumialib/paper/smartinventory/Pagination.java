package tr.com.infumia.infumialib.paper.smartinventory;

import org.jetbrains.annotations.NotNull;

/**
 * a class which lets you switch pages;
 * easily get icons in the given page,
 * easily manipulate the pages and
 * check if a page is the first or the last one
 * ({@link Pagination#isFirst()} / {@link Pagination#isLast()}).
 * <p>
 * you must start by setting the {@link Pagination#setIcons(Icon...)} and the {@link Pagination#setIconsPerPage(int)},
 * then you can manipulate the pages by using the
 * {@link Pagination#page(int)} /
 * {@link Pagination#first()} /
 * {@link Pagination#previous()} /
 * {@link Pagination#next()} /
 * {@link Pagination#last()}
 * methods.
 * <p>
 * then, when you need to get all the icons of the current page,
 * either use the {@link Pagination#getPageIcons()} method, or directly
 * add the icons to your inventory with a {@link SlotIterator} and the
 * method {@link Pagination#addToIterator(SlotIterator)}.
 */
public interface Pagination {

  /**
   * adds all the current page icons to the given iterator.
   *
   * @param iterator the iterator.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Pagination addToIterator(@NotNull final SlotIterator iterator) {
    for (final var item : this.getPageIcons()) {
      iterator.next().set(item);
      if (iterator.ended()) {
        break;
      }
    }
    return this;
  }

  /**
   * Sets the current page to the first page.
   * <p>
   * this is equivalent to: {@code page(0)}.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination first();

  /**
   * gets the current page.
   *
   * @return the current page.
   */
  int getPage();

  /**
   * gets the icons of the current page.
   * <p>
   * this returns an array of the size of the icons per page.
   *
   * @return the current page icons.
   */
  @NotNull
  Icon[] getPageIcons();

  /**
   * checks if the current page is the first page.
   * <p>
   * this is equivalent to: {@code page == 0}.
   *
   * @return {@code true} if this page is the first page.
   */
  boolean isFirst();

  /**
   * checks if the current page is the last page.
   * <p>
   * this is equivalent to: {@code page == iconsCount / iconsPerPage}.
   *
   * @return {@code true} if this page is the last page.
   */
  boolean isLast();

  /**
   * sets the current page to the last page.
   * <p>
   * this is equivalent to: {@code page(iconsCount / iconsPerPage)}.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination last();

  /**
   * sets the current page to the next page,
   * if the current page is already the last page, this do nothing.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination next();

  /**
   * sets the current page.
   *
   * @param page the current page.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination page(int page);

  /**
   * sets the current page to the previous page,
   * if the current page is already the first page, this do nothing.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination previous();

  /**
   * sets all the icons for this Pagination.
   *
   * @param icons the icons.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination setIcons(@NotNull Icon... icons);

  /**
   * sets the maximum amount of icons per page.
   *
   * @param iconsPerPage the maximum amount of icons per page.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Pagination setIconsPerPage(int iconsPerPage);
}
