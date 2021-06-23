package tr.com.infumia.infumialib.paper.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.paper.PaperCommandManager;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.files.InfumiaLibConfig;
import tr.com.infumia.infumialib.paper.files.PaperConfig;
import tr.com.infumia.infumialib.paper.utils.GitHubUpdateChecker;

/**
 * a class that contains Infumia plugin's commands.
 */
@RequiredArgsConstructor
public final class InfumiaPluginCommands implements Command {

  /**
   * the manager.
   */
  @NotNull
  protected final PaperCommandManager<CommandSender> manager;

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  @Override
  public void register() {
    final var builder = this.manager.commandBuilder("infumia", ArgumentDescription.of("Main command of Infumia Library plugin."));
    final var mainCommand = builder.permission("infumiaplugin.command.main")
      .handler(context -> context.getSender().sendMessage(this.getVersionMessage()));
    final var reloadCommand = builder.literal("reload", ArgumentDescription.of("Reloads Infumia Library plugin's configuration files."))
      .permission("infumiaplugin.command.reload")
      .handler(context ->
        InfumiaLibConfig.loadConfig(this.plugin.getDataFolder())
          .thenRunAsync(() -> PaperConfig.loadConfig(this.plugin.getDataFolder()))
          .whenComplete((x, y) ->
            context.getSender().sendMessage(this.getReloadCompleteMessage())));
    final var updateCommand = builder.literal("update", ArgumentDescription.of("Checks for update ıf Infumia Library plugin."))
      .permission("infumiaplugin.command.update")
      .handler(context ->
        GitHubUpdateChecker.checkForUpdate(context.getSender(), this.plugin, "Infumia", "InfumiaLib"));
    this.manager.command(mainCommand)
      .command(reloadCommand)
      .command(updateCommand);
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
