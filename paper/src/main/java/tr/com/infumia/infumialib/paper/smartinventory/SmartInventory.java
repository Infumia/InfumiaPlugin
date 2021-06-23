package tr.com.infumia.infumialib.paper.smartinventory;

import tr.com.infumia.infumialib.paper.smartinventory.event.PgTickEvent;
import tr.com.infumia.infumialib.paper.smartinventory.listener.InventoryClickListener;
import tr.com.infumia.infumialib.paper.smartinventory.listener.InventoryCloseListener;
import tr.com.infumia.infumialib.paper.smartinventory.listener.InventoryDragListener;
import tr.com.infumia.infumialib.paper.smartinventory.listener.InventoryOpenListener;
import tr.com.infumia.infumialib.paper.smartinventory.listener.PlayerQuitListener;
import tr.com.infumia.infumialib.paper.smartinventory.listener.PluginDisableListener;
import tr.com.infumia.infumialib.paper.smartinventory.opener.ChestInventoryOpener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * a class that manages all smart inventories.
 */
public interface SmartInventory {

  /**
   * default inventory openers.
   */
  List<InventoryOpener> DEFAULT_OPENERS = Collections.singletonList(
    new ChestInventoryOpener());

  /**
   * all listener to register.
   */
  Function<Consumer<UUID>, List<Listener>> LISTENERS = function -> Arrays.asList(
    new InventoryClickListener(),
    new InventoryOpenListener(),
    new InventoryCloseListener(function),
    new PlayerQuitListener(function),
    new PluginDisableListener(),
    new InventoryDragListener());

  /**
   * obtains the given {@code uniqueId}'s smart holder.
   *
   * @param uniqueId the unique id to obtain.
   *
   * @return smart holder.
   */
  @NotNull
  static Optional<SmartHolder> getHolder(@NotNull final UUID uniqueId) {
    return Optional.ofNullable(Bukkit.getPlayer(uniqueId))
      .flatMap(SmartInventory::getHolder);
  }

  /**
   * obtains the given {@code player}'s smart holder.
   *
   * @param player the player to obtain.
   *
   * @return smart holder.
   */
  @NotNull
  static Optional<SmartHolder> getHolder(@NotNull final Player player) {
    final InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
    if (!(holder instanceof SmartHolder)) {
      return Optional.empty();
    }
    return Optional.of((SmartHolder) holder)
      .filter(SmartHolder::isActive);
  }

  /**
   * obtains the smart holders of all the online players.
   *
   * @return smart holders of online players.
   */
  @NotNull
  static List<SmartHolder> getHolders() {
    return Bukkit.getOnlinePlayers().stream()
      .map(SmartInventory::getHolder)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toList());
  }

  /**
   * obtains the players that see the given page.
   *
   * @param page the page to obtain.
   *
   * @return a player list.
   */
  @NotNull
  static List<Player> getOpenedPlayers(@NotNull final Page page) {
    return SmartInventory.getHolders().stream()
      .filter(holder -> page.id().equals(holder.getPage().id()))
      .map(SmartHolder::getPlayer)
      .collect(Collectors.toList());
  }

  /**
   * runs {@link InventoryProvider#update(InventoryContents)} method of the player's page.
   *
   * @param player the player to notify.
   */
  static void notifyUpdate(@NotNull final Player player) {
    SmartInventory.getHolder(player).ifPresent(smartHolder ->
      smartHolder.getContents().notifyUpdate());
  }

  /**
   * runs {@link InventoryProvider#update(InventoryContents)} method of the given provider's class.
   *
   * @param provider the provider to notify.
   * @param <T> type of the class.
   */
  static <T extends InventoryProvider> void notifyUpdateForAll(@NotNull final Class<T> provider) {
    SmartInventory.getHolders().stream()
      .map(SmartHolder::getContents)
      .filter(contents -> provider.equals(contents.page().provider().getClass()))
      .forEach(InventoryContents::notifyUpdate);
  }

  /**
   * runs {@link InventoryProvider#update(InventoryContents)} method of the page called the given id.
   *
   * @param id the id to find and run the update method.
   */
  static void notifyUpdateForAllById(@NotNull final String id) {
    SmartInventory.getHolders().stream()
      .map(SmartHolder::getPage)
      .filter(page -> page.id().equals(id))
      .forEach(Page::notifyUpdateForAll);
  }

  /**
   * finds a {@link InventoryOpener} from the given {@link InventoryType}.
   *
   * @param type the type to find.
   *
   * @return the inventory opener from the given type.
   */
  @NotNull
  default Optional<InventoryOpener> findOpener(@NotNull final InventoryType type) {
    return Stream.of(this.getOpeners(), SmartInventory.DEFAULT_OPENERS)
      .flatMap(Collection::stream)
      .filter(opener -> opener.supports(type))
      .findFirst();
  }

  /**
   * obtains inventory openers.
   *
   * @return inventory openers.
   */
  @NotNull
  Collection<InventoryOpener> getOpeners();

  /**
   * obtains the plugin.
   *
   * @return the plugin.
   */
  @NotNull
  Plugin getPlugin();

  /**
   * obtains the given uniqueId's task.
   *
   * @param uniqueId the uniqueId to obtain.
   *
   * @return a {@link BukkitRunnable} instance.
   */
  @NotNull
  default Optional<BukkitRunnable> getTask(@NotNull final UUID uniqueId) {
    return Optional.ofNullable(this.getTasks().get(uniqueId));
  }

  /**
   * obtains the tasks.
   *
   * @return tasks.
   */
  @NotNull
  Map<UUID, BukkitRunnable> getTasks();

  /**
   * initiates the manager.
   */
  default void init() {
    SmartInventory.LISTENERS.apply(this::stopTick).forEach(listener ->
      Bukkit.getPluginManager().registerEvents(listener, this.getPlugin()));
  }

  /**
   * registers the given inventory openers.
   *
   * @param openers the openers to register.
   */
  default void registerOpeners(@NotNull final InventoryOpener... openers) {
    this.getOpeners().addAll(Arrays.asList(openers));
  }

  /**
   * removes given uniqueId of the ticking task.
   *
   * @param uniqueId the uniqueId to set.
   */
  default void removeTask(@NotNull final UUID uniqueId) {
    this.getTasks().remove(uniqueId);
  }

  /**
   * sets the given player of the ticking task to the given task.
   *
   * @param uniqueId the unique id to set.
   * @param task the task to set.
   */
  default void setTask(@NotNull final UUID uniqueId, @NotNull final BukkitRunnable task) {
    this.getTasks().put(uniqueId, task);
  }

  /**
   * stops the ticking of the given uniqueId.
   *
   * @param uniqueId the uniqueId to stop.
   */
  default void stopTick(@NotNull final UUID uniqueId) {
    this.getTask(uniqueId).ifPresent(runnable -> {
      Bukkit.getScheduler().cancelTask(runnable.getTaskId());
      this.removeTask(uniqueId);
    });
  }

  /**
   * starts the ticking of the given player with the given page.
   *
   * @param uniqueId the unique id to start.
   * @param page the page to start.
   */
  default void tick(@NotNull final UUID uniqueId, @NotNull final Page page) {
    final BukkitRunnable task = new BukkitRunnable() {
      @Override
      public void run() {
        SmartInventory.getHolder(uniqueId)
          .map(SmartHolder::getContents)
          .ifPresent(contents -> {
            page.accept(new PgTickEvent(contents));
            page.provider().tick(contents);
          });
      }
    };
    this.setTask(uniqueId, task);
    if (page.async()) {
      task.runTaskTimerAsynchronously(this.getPlugin(), page.startDelay(), page.tick());
    } else {
      task.runTaskTimer(this.getPlugin(), page.startDelay(), page.tick());
    }
  }

  /**
   * unregisters the given inventory openers.
   *
   * @param openers the openers to unregister.
   */
  default void unregisterOpeners(@NotNull final InventoryOpener... openers) {
    this.getOpeners().removeAll(Arrays.asList(openers));
  }
}
