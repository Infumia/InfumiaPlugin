package tr.com.infumia.infumialib.paper.smartinventory.event;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryContents;
import tr.com.infumia.infumialib.paper.smartinventory.event.abs.PageClickEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents page click events.
 */
@RequiredArgsConstructor
public final class PgClickEvent implements PageClickEvent {

  /**
   * the contents.
   */
  @NotNull
  private final InventoryContents contents;

  /**
   * the event.
   */
  @NotNull
  private final InventoryClickEvent event;

  /**
   * the plugins.
   */
  @NotNull
  private final Plugin plugin;

  @Override
  public void cancel() {
    this.event.setCancelled(true);
  }

  @Override
  public void close() {
    Bukkit.getScheduler().runTask(this.plugin, () ->
      this.contents.page().close(this.contents.player()));
  }

  @NotNull
  @Override
  public InventoryContents contents() {
    return this.contents;
  }

  @NotNull
  @Override
  public InventoryClickEvent getEvent() {
    return this.event;
  }
}
