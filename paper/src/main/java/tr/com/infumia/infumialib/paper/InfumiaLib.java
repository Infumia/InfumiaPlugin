package tr.com.infumia.infumialib.paper;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import io.github.portlek.bukkitversion.BukkitVersion;
import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.files.InfumiaLibConfig;
import tr.com.infumia.infumialib.paper.commands.InfumiaPluginCommands;
import tr.com.infumia.infumialib.paper.files.BukkitConfig;
import tr.com.infumia.infumialib.paper.hooks.Hooks;
import tr.com.infumia.infumialib.paper.utils.GitHubUpdateChecker;
import tr.com.infumia.infumialib.paper.utils.TaskUtilities;

public final class InfumiaLib extends JavaPlugin {

  public static final BukkitVersion SERVER_VERSION = new BukkitVersion();

  @Nullable
  private static InfumiaLib instance;

  @Getter
  private final SmartInventory inventory = new BasicSmartInventory(this);

  @NotNull
  public static InfumiaLib getInstance() {
    return Objects.requireNonNull(InfumiaLib.instance, "not initiated");
  }

  @Override
  public void onLoad() {
    InfumiaLib.instance = this;
    TaskUtilities.init(this);
    InfumiaLibConfig.loadConfig(this.getDataFolder());
    BukkitConfig.loadConfig(this.getDataFolder());
    CommandAPI.onLoad(new CommandAPIConfig());
  }

  @Override
  public void onEnable() {
    CommandAPI.onEnable(this);
    new InfumiaPluginCommands(this).register();
    this.inventory.init();
    Hooks.loadHooks();
    if (InfumiaLibConfig.checkForUpdate) {
      GitHubUpdateChecker.checkForUpdate(this, "Infumia", "InfumiaLib");
    }
    new Metrics(this, 11422);
  }
}
