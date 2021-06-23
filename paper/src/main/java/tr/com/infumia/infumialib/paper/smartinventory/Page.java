package tr.com.infumia.infumialib.paper.smartinventory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.BottomClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.CloseEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.InitEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.OpenEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.OutsideClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.PageClickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.PageEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.TickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.UpdateEvent;
import tr.com.infumia.infumialib.paper.smartinventory.page.BasicPage;

/**
 * an interface to determine {@link Inventory}.
 */
public interface Page {

  /**
   * creates a simple page instance from the given parameters.
   *
   * @param inventory the inventory to create.
   * @param provider the provider to create.
   *
   * @return a simple page instance.
   */
  static Page build(@NotNull final SmartInventory inventory, @NotNull final InventoryProvider provider) {
    return new BasicPage(inventory, provider);
  }

  /**
   * creates a simple page instance from the given inventory.
   *
   * @param inventory the inventory to create.
   *
   * @return a simple page instance.
   */
  static Page build(@NotNull final SmartInventory inventory) {
    return new BasicPage(inventory);
  }

  /**
   * accepts the upcoming event.
   *
   * @param event the event to accept.
   * @param <T> type of the event.
   */
  <T extends PageEvent> void accept(@NotNull T event);

  /**
   * obtains status of the task in terms of async.
   *
   * @return status of the task in terms of async.
   */
  boolean async();

  /**
   * sets the task's async status.
   *
   * @param async the async to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page async(boolean async);

  /**
   * sets the can close to the given boolean.
   *
   * @param canClose the can close to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page canClose(final boolean canClose) {
    return this.canClose(event -> canClose);
  }

  /**
   * checks the can close with the given event.
   *
   * @param event the event to check.
   *
   * @return {@code true} if the can close predicate's test returns {@code true}.
   */
  boolean canClose(@NotNull CloseEvent event);

  /**
   * sets the can close predicate of the page.
   *
   * @param predicate the predicate to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page canClose(@NotNull Predicate<CloseEvent> predicate);

  /**
   * checks the bounds.
   *
   * @param row the row to check.
   * @param column the column to check.
   *
   * @return {@code true} if the given row and column are correct for the page size.
   */
  default boolean checkBounds(final int row, final int column) {
    if (row >= 0) {
      return column >= 0;
    }
    if (row < this.row()) {
      return column < this.column();
    }
    return false;
  }

  /**
   * closes the player's page.
   *
   * @param player the player to close.
   */
  void close(@NotNull Player player);

  /**
   * obtains column of the page.
   *
   * @return column of the page.
   */
  int column();

  /**
   * set the column of the page.
   *
   * @param column the row to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page column(int column);

  /**
   * adds the given consumer.
   *
   * @param clazz the class to determine the type of the event.
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   * @param <T> type of the event.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default <T extends PageEvent> Page handle(@NotNull final Class<T> clazz, @NotNull final Consumer<T> consumer,
                                            @NotNull final List<Predicate<T>> requirements) {
    return this.handle(Handle.from(clazz, consumer, requirements));
  }

  /**
   * adds the given handle.
   *
   * @param handle the handle to add.
   * @param <T> type of the event.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull <T extends PageEvent> Page handle(@NotNull Handle<T> handle);

  /**
   * sets the id of the page.
   *
   * @param id the id to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page id(@NotNull String id);

  /**
   * obtains id of the page.
   *
   * @return id of the page.
   */
  @NotNull
  String id();

  /**
   * obtains the page's inventory manager.
   *
   * @return an inventory manager instance.
   */
  @NotNull
  SmartInventory inventory();

  /**
   * runs the {@link #provider()}'s {@link InventoryProvider#update(InventoryContents)} method with the given contents.
   *
   * @param contents the contents to update.
   */
  void notifyUpdate(@NotNull InventoryContents contents);

  /**
   * runs the {@link InventoryProvider#update(InventoryContents)} methods for all players who opened {@code this}.
   */
  default void notifyUpdateForAll() {
    SmartInventory.notifyUpdateForAll(this.provider().getClass());
  }

  /**
   * runs the {@link InventoryProvider#update(InventoryContents)} methods for all players who opened {@code this}.
   */
  default void notifyUpdateForAllById() {
    SmartInventory.notifyUpdateForAllById(this.id());
  }

  /**
   * opens the page to the given player.
   *
   * @param player the player to open.
   */
  default void open(@NotNull final Player player) {
    this.open(player, 0);
  }

  /**
   * opens the page to the given player.
   *
   * @param player the player to open.
   * @param page the page to open.
   */
  default void open(@NotNull final Player player, final int page) {
    this.open(player, page, Collections.emptyMap());
  }

  /**
   * opens the page to the given player.
   *
   * @param player the player to open.
   * @param properties the properties to open.
   */
  default void open(@NotNull final Player player, @NotNull final Map<String, Object> properties) {
    this.open(player, 0, properties);
  }

  /**
   * opens the page for the player.
   *
   * @param player the player to open.
   * @param page the page to open.
   * @param properties the properties to open with.
   *
   * @return a new {@link Inventory} instance.
   */
  @NotNull
  default Inventory open(@NotNull final Player player, final int page, @NotNull final Map<String, Object> properties) {
    return this.open(player, page, properties, true);
  }

  /**
   * opens the page for the player.
   *
   * @param player the player to open.
   * @param page the page to open.
   * @param properties the properties to open with.
   * @param close the close to open.
   *
   * @return a new {@link Inventory} instance.
   */
  @NotNull
  Inventory open(@NotNull Player player, int page, @NotNull Map<String, Object> properties, boolean close);

  /**
   * obtains tha parent page of {@code this}.
   *
   * @return the parent page of {@code this}.
   */
  @NotNull
  Optional<Page> parent();

  /**
   * sets the parent of the page.
   *
   * @param parent the parent to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page parent(@NotNull Page parent);

  /**
   * obtains the inventory provider.
   *
   * @return an inventory provider instance.
   */
  @NotNull
  InventoryProvider provider();

  /**
   * sets the provider to the given provider.
   *
   * @param provider the provider to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page provider(@NotNull InventoryProvider provider);

  /**
   * obtains row of the page.
   *
   * @return row of the page.
   */
  int row();

  /**
   * set the row of the page.
   *
   * @param row the row to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page row(int row);

  /**
   * obtains the start delay for the task.
   *
   * @return the start delay for the task.
   */
  long startDelay();

  /**
   * sets the start delay of the task.
   *
   * @param startDelay the start delay to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page startDelay(long startDelay);

  /**
   * obtains the tick amount.
   *
   * @return the tick amount.
   */
  long tick();

  /**
   * sets the tick.
   *
   * @param tick the tick to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page tick(long tick);

  /**
   * obtains the task enable.
   *
   * @return {@code true} if the task enable.
   */
  boolean tickEnable();

  /**
   * sets the task's activity.
   *
   * @param tickEnable the tick enable to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page tickEnable(boolean tickEnable);

  /**
   * obtains title of the page.
   *
   * @return title of the page.
   */
  @NotNull
  String title();

  /**
   * sets the title of the page.
   *
   * @param title the title to set.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  Page title(@NotNull String title);

  /**
   * adds the given consumer as a bottom inventory click event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenBottomClick(@NotNull final Consumer<BottomClickEvent> consumer) {
    return this.whenBottomClick(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a bottom inventory click event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenBottomClick(@NotNull final Consumer<BottomClickEvent> consumer,
                               @NotNull final Predicate<BottomClickEvent> requirement) {
    return this.whenBottomClick(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a bottom inventory click event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenBottomClick(@NotNull final Consumer<BottomClickEvent> consumer,
                               @NotNull final List<Predicate<BottomClickEvent>> requirements) {
    return this.handle(BottomClickEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a close event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenClose(@NotNull final Consumer<CloseEvent> consumer) {
    return this.whenClose(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a close event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenClose(@NotNull final Consumer<CloseEvent> consumer,
                         @NotNull final Predicate<CloseEvent> requirement) {
    return this.whenClose(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a close event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenClose(@NotNull final Consumer<CloseEvent> consumer,
                         @NotNull final List<Predicate<CloseEvent>> requirements) {
    return this.handle(CloseEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a empty slot click event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenEmptyClick(@NotNull final Consumer<PageClickEvent> consumer) {
    return this.whenEmptyClick(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a empty slot click event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenEmptyClick(@NotNull final Consumer<PageClickEvent> consumer,
                              @NotNull final Predicate<PageClickEvent> requirement) {
    return this.whenEmptyClick(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a empty slot click event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenEmptyClick(@NotNull final Consumer<PageClickEvent> consumer,
                              @NotNull final List<Predicate<PageClickEvent>> requirements) {
    return this.handle(PageClickEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a init event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenInit(@NotNull final Consumer<InitEvent> consumer) {
    return this.whenInit(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a init event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenInit(@NotNull final Consumer<InitEvent> consumer, @NotNull final Predicate<InitEvent> requirement) {
    return this.whenInit(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a init event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenInit(@NotNull final Consumer<InitEvent> consumer,
                        @NotNull final List<Predicate<InitEvent>> requirements) {
    return this.handle(InitEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a open event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOpen(@NotNull final Consumer<OpenEvent> consumer) {
    return this.whenOpen(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a open event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOpen(@NotNull final Consumer<OpenEvent> consumer,
                        @NotNull final Predicate<OpenEvent> requirement) {
    return this.whenOpen(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a open event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOpen(@NotNull final Consumer<OpenEvent> consumer,
                        @NotNull final List<Predicate<OpenEvent>> requirements) {
    return this.handle(OpenEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a outside inventory click event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOutsideClick(@NotNull final Consumer<OutsideClickEvent> consumer) {
    return this.whenOutsideClick(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a outside inventory click event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOutsideClick(@NotNull final Consumer<OutsideClickEvent> consumer,
                                @NotNull final Predicate<OutsideClickEvent> requirement) {
    return this.whenOutsideClick(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a outside inventory click event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenOutsideClick(@NotNull final Consumer<OutsideClickEvent> consumer,
                                @NotNull final List<Predicate<OutsideClickEvent>> requirements) {
    return this.handle(OutsideClickEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a tick event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenTick(@NotNull final Consumer<TickEvent> consumer) {
    return this.whenTick(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a tick event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenTick(@NotNull final Consumer<TickEvent> consumer, @NotNull final Predicate<TickEvent> requirement) {
    return this.whenTick(consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a tick event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenTick(@NotNull final Consumer<TickEvent> consumer,
                        @NotNull final List<Predicate<TickEvent>> requirements) {
    return this.handle(TickEvent.class, consumer, requirements);
  }

  /**
   * adds the given consumer as a update event.
   *
   * @param consumer the consumer to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenUpdate(@NotNull final Consumer<UpdateEvent> consumer) {
    return this.whenUpdate(consumer, Collections.emptyList());
  }

  /**
   * adds the given consumer as a update event.
   *
   * @param consumer the consumer to add.
   * @param requirement the requirement to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenUpdate(@NotNull final Consumer<UpdateEvent> consumer,
                          @NotNull final Predicate<UpdateEvent> requirement) {
    return this.handle(UpdateEvent.class, consumer, Collections.singletonList(requirement));
  }

  /**
   * adds the given consumer as a update event.
   *
   * @param consumer the consumer to add.
   * @param requirements the requirements to add.
   *
   * @return {@code this}, for chained calls.
   */
  @NotNull
  default Page whenUpdate(@NotNull final Consumer<UpdateEvent> consumer,
                          @NotNull final List<Predicate<UpdateEvent>> requirements) {
    return this.handle(UpdateEvent.class, consumer, requirements);
  }
}
