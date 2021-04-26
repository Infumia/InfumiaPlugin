package tr.com.infumia.plugin;

import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * main class of the Infumia plugin.
 */
public final class InfumiaPlugin extends JavaPlugin {

  @Nullable
  private static InfumiaPlugin instance;

  @Getter
  private final SmartInventory inventory = new BasicSmartInventory(this);

  @NotNull
  public static InfumiaPlugin getInstance() {
    return Objects.requireNonNull(InfumiaPlugin.instance, "not initiated");
  }

  @Override
  public void onLoad() {
    InfumiaPlugin.instance = this;
  }

  @Override
  public void onEnable() {
    TaskUtilities.init(this);
    this.inventory.init();
    Hooks.loadHooks();
  }
}
