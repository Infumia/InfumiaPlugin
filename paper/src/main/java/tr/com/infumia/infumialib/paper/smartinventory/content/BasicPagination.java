package tr.com.infumia.infumialib.paper.smartinventory.content;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.Icon;
import tr.com.infumia.infumialib.paper.smartinventory.Pagination;

/**
 * an implementation for {@link Pagination}.
 */
public final class BasicPagination implements Pagination {

  /**
   * the current page.
   */
  private int currentPage;

  /**
   * the icons.
   */
  @NotNull
  private Icon[] icons = new Icon[0];

  /**
   * the icons per page.
   */
  private int iconsPerPage = 5;

  @NotNull
  @Override
  public Pagination first() {
    this.currentPage = 0;
    return this;
  }

  @Override
  public int getPage() {
    return this.currentPage;
  }

  @NotNull
  @Override
  public Icon[] getPageIcons() {
    return Arrays.copyOfRange(this.icons,
      this.currentPage * this.iconsPerPage,
      (this.currentPage + 1) * this.iconsPerPage);
  }

  @Override
  public boolean isFirst() {
    return this.currentPage == 0;
  }

  @Override
  public boolean isLast() {
    return this.currentPage >= (int) Math.ceil((double) this.icons.length / (double) this.iconsPerPage) - 1;
  }

  @NotNull
  @Override
  public Pagination last() {
    return this.page(this.getPageIcons().length / this.iconsPerPage);
  }

  @NotNull
  @Override
  public Pagination next() {
    if (!this.isLast()) {
      this.currentPage++;
    }
    return this;
  }

  @NotNull
  @Override
  public Pagination page(final int page) {
    this.currentPage = page;
    return this;
  }

  @NotNull
  @Override
  public Pagination previous() {
    if (!this.isFirst()) {
      this.currentPage--;
    }
    return this;
  }

  @NotNull
  @Override
  public Pagination setIcons(@NotNull final Icon... icons) {
    this.icons = icons.clone();
    return this;
  }

  @NotNull
  @Override
  public Pagination setIconsPerPage(final int iconsPerPage) {
    this.iconsPerPage = iconsPerPage;
    return this;
  }
}
