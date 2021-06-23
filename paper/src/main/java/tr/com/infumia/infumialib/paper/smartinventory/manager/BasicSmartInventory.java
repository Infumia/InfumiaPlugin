package tr.com.infumia.infumialib.paper.smartinventory.manager;

import tr.com.infumia.infumialib.paper.smartinventory.InventoryOpener;
import tr.com.infumia.infumialib.paper.smartinventory.SmartInventory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation for {@link SmartInventory}.
 */
@Getter
@RequiredArgsConstructor
public final class BasicSmartInventory implements SmartInventory {

  /**
   * the openers.
   */
  private final Collection<InventoryOpener> openers = new ArrayList<>();

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  /**
   * the tasks.
   */
  private final Map<UUID, BukkitRunnable> tasks = new ConcurrentHashMap<>();

  static {
    try {
      Class.forName("tr.com.infumia.infumialib.paper.smartinventory.event.PlgnDisableEvent");
    } catch (final ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
