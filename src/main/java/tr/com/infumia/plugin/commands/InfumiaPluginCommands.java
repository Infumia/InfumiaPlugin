package tr.com.infumia.plugin.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.files.InfumiaConfig;
import tr.com.infumia.plugin.utils.GitHubUpdateChecker;

/**
 * a class that contains Infumia plugin's commands.
 */
@RequiredArgsConstructor
public final class InfumiaPluginCommands {

  /**
   * the main.
   */
  private static final CommandPermission MAIN = CommandPermission.fromString("infumiaplugin.command.main");

  /**
   * the reload.
   */
  private static final CommandPermission RELOAD = CommandPermission.fromString("infumiaplugin.command.reload");

  /**
   * the update.
   */
  private static final CommandPermission UPDATE = CommandPermission.fromString("infumiaplugin.command.update");

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  /**
   * obtains the reload complete message.
   *
   * @return reload complete message.
   */
  @NotNull
  private static Component getReloadCompleteMessage() {
    return Component.text()
      .append(Component.text("[InfumiaPlugin] ")
        .color(NamedTextColor.YELLOW))
      .append(Component.text("Reload complete!")
        .color(NamedTextColor.GREEN))
      .build();
  }

  /**
   * registers the InfumiaPlugin's command.
   */
  public void register() {
    this.getCommandTree().register();
  }

  /**
   * obtains the main command.
   *
   * @return main command.
   */
  @NotNull
  private CommandAPICommand getCommandTree() {
    return this.getMainCommand()
      .withSubcommand(this.getReloadCommand())
      .withSubcommand(this.getUpdateCommand());
  }

  /**
   * obtains the main command.
   *
   * @return main command.
   */
  @NotNull
  private CommandAPICommand getMainCommand() {
    return new CommandAPICommand("infumia")
      .withPermission(InfumiaPluginCommands.MAIN)
      .executes((sender, objects) -> {
        sender.sendMessage(this.getVersionMessage());
      });
  }

  /**
   * obtains the reload command.
   *
   * @return reload command.
   */
  @NotNull
  private CommandAPICommand getReloadCommand() {
    return new CommandAPICommand("reload")
      .withPermission(InfumiaPluginCommands.RELOAD)
      .executes((sender, objects) -> {
        InfumiaConfig.load(this.plugin, true).whenComplete((configLoader, throwable) -> {
          sender.sendMessage(InfumiaPluginCommands.getReloadCompleteMessage());
        });
      });
  }

  /**
   * obtains the update command.
   *
   * @return update command.
   */
  @NotNull
  private CommandAPICommand getUpdateCommand() {
    return new CommandAPICommand("update")
      .withPermission(InfumiaPluginCommands.UPDATE)
      .executes((sender, objects) -> {
        GitHubUpdateChecker.checkForUpdate(sender, this.plugin, "Infumia", "InfumiaPlugin");
      });
  }

  /**
   * obtains the version command's message.
   *
   * @return a newly created version message.
   */
  @NotNull
  private Component getVersionMessage() {
    return Component.text()
      .append(Component.text("Infumia Plugin made by ")
        .color(NamedTextColor.YELLOW))
      .append(Component.text("Infumia")
        .color(NamedTextColor.GOLD)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/")))
      .append(Component.newline())
      .append(Component.text("Current version")
        .color(NamedTextColor.GOLD)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/tag/" + this.plugin.getDescription().getVersion())))
      .append(Component.text("Latest version")
        .color(NamedTextColor.GOLD)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/latest/")))
      .build();
  }
}
