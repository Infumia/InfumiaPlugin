package tr.com.infumia.infumialib.paper.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.files.InfumiaLibConfig;
import tr.com.infumia.infumialib.paper.files.PaperConfig;
import tr.com.infumia.infumialib.paper.utils.GitHubUpdateChecker;

/**
 * a class that contains Infumia plugin's commands.
 */
public final class InfumiaPluginCommands extends CommandHandler {

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
   * ctor.
   *
   * @param plugin the plugin.
   */
  public InfumiaPluginCommands(@NotNull final Plugin plugin) {
    super(plugin);
  }

  /**
   * obtains the main command.
   *
   * @return main command.
   */
  @NotNull
  @Override
  protected CommandAPICommand getMainCommand() {
    return new CommandAPICommand("infumia")
      .withPermission(InfumiaPluginCommands.MAIN)
      .executes((sender, objects) -> {
        sender.sendMessage(this.getVersionMessage());
      });
  }

  @NotNull
  @Override
  protected List<CommandAPICommand> getSubCommands() {
    return List.of(
      this.getReloadCommand(),
      this.getUpdateCommand());
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
        InfumiaLibConfig.loadConfig(this.plugin.getDataFolder())
          .thenRunAsync(() -> PaperConfig.loadConfig(this.plugin.getDataFolder()))
          .whenComplete((x, y) ->
            sender.sendMessage(this.getReloadCompleteMessage()));
      });
  }

  /**
   * obtains the reload complete message.
   *
   * @return reload complete message.
   */
  @NotNull
  private Component getReloadCompleteMessage() {
    return Component.text()
      .append(Component.text("[InfumiaPlugin]")
        .color(NamedTextColor.YELLOW))
      .append(Component.space())
      .append(Component.text("Reload complete!")
        .color(NamedTextColor.GREEN))
      .build();
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
        GitHubUpdateChecker.checkForUpdate(sender, this.plugin, "Infumia", "InfumiaLib");
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
      .append(Component.text("Infumia Library made by")
        .color(NamedTextColor.YELLOW))
      .append(Component.space())
      .append(Component.text("Infumia")
        .color(NamedTextColor.GOLD)
        .decorate(TextDecoration.UNDERLINED)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/"))
        .hoverEvent(HoverEvent.showText(Component.text("Go to Infumia organization page.")
          .color(NamedTextColor.GRAY))))
      .append(Component.newline())
      .append(Component.text("Current version")
        .color(NamedTextColor.GOLD)
        .decorate(TextDecoration.UNDERLINED)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/tag/" + this.plugin.getDescription().getVersion())))
      .hoverEvent(HoverEvent.showText(Component.text("Go to current version of Infumia Library.")
        .color(NamedTextColor.GRAY)))
      .append(Component.space())
      .append(Component.text("Latest version")
        .color(NamedTextColor.GOLD)
        .decorate(TextDecoration.UNDERLINED)
        .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/latest/"))
        .hoverEvent(HoverEvent.showText(Component.text("Go to latest version of Infumia Library.")
          .color(NamedTextColor.GRAY))))
      .build();
  }
}
