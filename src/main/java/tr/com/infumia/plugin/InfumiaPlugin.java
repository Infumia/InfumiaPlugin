package tr.com.infumia.plugin;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.plugin.commands.InfumiaPluginCommands;
import tr.com.infumia.plugin.files.InfumiaConfig;
import tr.com.infumia.plugin.hooks.Hooks;
import tr.com.infumia.plugin.utils.GitHubUpdateChecker;
import tr.com.infumia.plugin.utils.TaskUtilities;

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
    TaskUtilities.init(this);
    InfumiaConfig.load(this);
    CommandAPI.onLoad(new CommandAPIConfig());
  }

  @Override
  public void onEnable() {
    CommandAPI.onEnable(this);
    new InfumiaPluginCommands(this).register();
    this.inventory.init();
    Hooks.loadHooks();
    if (InfumiaConfig.checkForUpdate) {
      GitHubUpdateChecker.checkForUpdate(this, "Infumia", "InfumiaPlugin");
    }
  }
}
