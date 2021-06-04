package tr.com.infumia.infumialib.paper;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import io.github.portlek.smartinventory.SmartInventory;
import io.github.portlek.smartinventory.manager.BasicSmartInventory;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
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
    final var path = this.getDataFolder().toPath();
    if (Files.notExists(path)) {
      try {
        Files.createDirectories(path);
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
    InfumiaLib.instance = this;
    TaskUtilities.init(this);
    InfumiaLibConfig.load(this.getDataFolder());
    BukkitConfig.load(this);
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
