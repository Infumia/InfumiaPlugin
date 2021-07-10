package tr.com.infumia.infumialib.paper.smartinventory.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.observer.Source;
import tr.com.infumia.infumialib.paper.observer.source.BasicSource;
import tr.com.infumia.infumialib.paper.smartinventory.Handle;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.InventoryProvider;
import tr.com.infumia.infumialib.paper.smartinventory.Page;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import tr.com.infumia.infumialib.paper.smartinventory.content.BasicInventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgCloseEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgInitEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgUpdateEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.CloseEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.PageEvent;

/**
 * an implementation for {@link Page}.
 */
@RequiredArgsConstructor
public final class BasicPage implements Page {

  /**
   * the handles.
   */
  private final Collection<Handle<? extends PageEvent>> handles = new ArrayList<>();

  /**
   * the inventory manager.
   */
  @NotNull
  private final SmartInventory inventory;

  /**
   * the observer's source.
   */
  private final Source<InventoryContents> source = new BasicSource<>();

  /**
   * the inventory type.
   *
   * @todo #1:5m Add a method to change the type of the inventory.
   */
  @NotNull
  private final InventoryType type = InventoryType.CHEST;

  /**
   * the async.
   */
  private boolean async = false;

  /**
   * the can close.
   */
  @NotNull
  private Predicate<CloseEvent> canClose = event -> true;

  /**
   * the column.
   */
  private int column = 9;

  /**
   * the id.
   */
  @NotNull
  private String id = "none";

  /**
   * the parent.
   */
  @Nullable
  private Page parent;

  /**
   * the provider.
   */
  @NotNull
  private InventoryProvider provider;

  /**
   * the row.
   */
  private int row = 1;

  /**
   * the start delay time.
   */
  private long startDelay = 1L;

  /**
   * the tick time.
   */
  private long tick = 1L;

  /**
   * the tick enable.
   */
  private boolean tickEnable = true;

  /**
   * the title.
   */
  @NotNull
  private String title = "Smart Inventory";

  /**
   * ctor.
   *
   * @param inventory the inventory.
   */
  public BasicPage(@NotNull final SmartInventory inventory) {
    this(inventory, InventoryProvider.EMPTY);
  }

  @Override
  public <T extends PageEvent> void accept(@NotNull final T event) {
    this.handles.stream()
      .filter(handle -> handle.type().isAssignableFrom(event.getClass()))
      .map(handle -> (Handle<T>) handle)
      .forEach(handle -> handle.accept(event));
  }

  @Override
  public boolean async() {
    return this.async;
  }

  @NotNull
  @Override
  public Page async(final boolean async) {
    this.async = async;
    return this;
  }

  @Override
  public boolean canClose(@NotNull final CloseEvent event) {
    return this.canClose.test(event);
  }

  @NotNull
  @Override
  public Page canClose(@NotNull final Predicate<CloseEvent> predicate) {
    this.canClose = predicate;
    return this;
  }

  @Override
  public void close(@NotNull final Player player) {
    SmartInventory.getHolder(player).ifPresent(holder -> {
      this.accept(new PgCloseEvent(holder.getContents(), new InventoryCloseEvent(player.getOpenInventory())));
      this.inventory().stopTick(player.getUniqueId());
      this.source.unsubscribe(this.provider());
      holder.setActive(false);
      player.closeInventory();
    });
  }

  @Override
  public int column() {
    return this.column;
  }

  @NotNull
  @Override
  public Page column(final int column) {
    this.column = column;
    return this;
  }

  @NotNull
  @Override
  public <T extends PageEvent> Page handle(@NotNull final Handle<T> handle) {
    this.handles.add(handle);
    return this;
  }

  @NotNull
  @Override
  public Page id(@NotNull final String id) {
    this.id = id;
    return this;
  }

  @NotNull
  @Override
  public String id() {
    return this.id;
  }

  @NotNull
  @Override
  public SmartInventory inventory() {
    return this.inventory;
  }

  @Override
  public void notifyUpdate(@NotNull final InventoryContents contents) {
    this.accept(new PgUpdateEvent(contents));
    this.source.notifyTargets(contents);
  }

  @NotNull
  @Override
  public Inventory open(@NotNull final Player player, final int page, @NotNull final Map<String, Object> properties,
                        final boolean close) {
    if (close) {
      this.close(player);
    } else {
      SmartInventory.getHolder(player).ifPresent(smartHolder -> {
        final var oldPage = smartHolder.getPage();
        if (this.row != oldPage.row() || this.column != oldPage.column()) {
          this.close(player);
        }
      });
    }
    final var opener = this.inventory().findOpener(this.type).orElseThrow(() ->
      new IllegalStateException("No opener found for the inventory type " + this.type.name()));
    this.source.subscribe(this.provider());
    final var contents = new BasicInventoryContents(this, player);
    contents.pagination().page(page);
    properties.forEach(contents::setProperty);
    this.accept(new PgInitEvent(contents));
    this.provider().init(contents);
    final var opened = opener.open(contents);
    if (this.tickEnable()) {
      this.inventory().tick(player.getUniqueId(), this);
    }
    return opened;
  }

  @NotNull
  @Override
  public Optional<Page> parent() {
    return Optional.ofNullable(this.parent);
  }

  @NotNull
  @Override
  public Page parent(@NotNull final Page parent) {
    this.parent = parent;
    return this;
  }

  @NotNull
  @Override
  public InventoryProvider provider() {
    return this.provider;
  }

  @NotNull
  @Override
  public Page provider(@NotNull final InventoryProvider provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public int row() {
    return this.row;
  }

  @NotNull
  @Override
  public Page row(final int row) {
    this.row = row;
    return this;
  }

  @Override
  public long startDelay() {
    return this.startDelay;
  }

  @NotNull
  @Override
  public Page startDelay(final long startDelay) {
    this.startDelay = startDelay;
    return this;
  }

  @Override
  public long tick() {
    return this.tick;
  }

  @NotNull
  @Override
  public Page tick(final long tick) {
    this.tick = tick;
    return this;
  }

  @Override
  public boolean tickEnable() {
    return this.tickEnable;
  }

  @NotNull
  @Override
  public Page tickEnable(final boolean tickEnable) {
    this.tickEnable = tickEnable;
    return this;
  }

  @NotNull
  @Override
  public String title() {
    return this.title;
  }

  @NotNull
  @Override
  public Page title(@NotNull final String title) {
    this.title = title;
    return this;
  }
}
