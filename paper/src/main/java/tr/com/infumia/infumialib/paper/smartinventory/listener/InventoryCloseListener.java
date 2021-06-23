package tr.com.infumia.infumialib.paper.smartinventory.listener;

import tr.com.infumia.infumialib.paper.smartinventory.SmartHolder;
import tr.com.infumia.infumialib.paper.smartinventory.event.PgCloseEvent;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents inventory close listeners.
 */
@RequiredArgsConstructor
public final class InventoryCloseListener implements Listener {

  /**
   * the stop tick function.
   */
  @NotNull
  private final Consumer<UUID> stopTickFunction;

  /**
   * listens inventory close events.
   *
   * @param event the event to listen.
   */
  @EventHandler
  public void onInventoryClose(final InventoryCloseEvent event) {
    final var holder = event.getInventory().getHolder();
    if (!(holder instanceof SmartHolder)) {
      return;
    }
    final var smartHolder = (SmartHolder) holder;
    final var inventory = event.getInventory();
    final var page = smartHolder.getPage();
    final var close = new PgCloseEvent(smartHolder.getContents(), event);
    page.accept(close);
    if (!page.canClose(close)) {
      Bukkit.getScheduler().runTask(smartHolder.getPlugin(), () ->
        event.getPlayer().openInventory(inventory));
      return;
    }
    inventory.clear();
    this.stopTickFunction.accept(event.getPlayer().getUniqueId());
  }
}
