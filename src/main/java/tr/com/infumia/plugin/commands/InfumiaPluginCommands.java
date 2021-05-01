package tr.com.infumia.plugin.commands;

import dev.jorel.commandapi.CommandAPICommand;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.files.InfumiaConfig;
import tr.com.infumia.plugin.utils.GitHubUpdateChecker;

@UtilityClass
public class InfumiaPluginCommands {

  public void register(@NotNull final Plugin plugin) {
    new CommandAPICommand("infumia")
      .withPermission("infumiaplugin.command.main")
      .executes((sender, objects) -> {
        sender.sendMessage(Component.text()
          .append(Component.text("Infumia Plugin made by ")
            .color(NamedTextColor.YELLOW))
          .append(Component.text("Infumia")
            .color(NamedTextColor.GOLD)
            .decorate(TextDecoration.UNDERLINED)
            .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/")))
          .append(Component.newline())
          .append(Component.text("Current version")
            .color(NamedTextColor.GOLD)
            .decorate(TextDecoration.UNDERLINED)
            .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/tag/" + plugin.getDescription().getVersion())))
          .append(Component.text("Latest version")
            .color(NamedTextColor.GOLD)
            .decorate(TextDecoration.UNDERLINED)
            .clickEvent(ClickEvent.openUrl("https://github.com/Infumia/InfumiaPlugin/releases/latest/")))

        );
      })
      .withSubcommand(new CommandAPICommand("reload")
        .withPermission("infumiaplugin.command.reload")
        .executes((sender, objects) -> {
          InfumiaConfig.load(plugin, true).whenComplete((configLoader, throwable) -> {
            sender.sendMessage(Component.text()
              .append(Component.text("[InfumiaPlugin] ")
                .color(NamedTextColor.YELLOW))
              .append(Component.text("Reload complete!")
                .color(NamedTextColor.GREEN)));
          });
        }))
      .withSubcommand(new CommandAPICommand("update")
        .withPermission("infumiaplugin.command.update")
        .executes((sender, objects) -> {
          GitHubUpdateChecker.checkForUpdate(sender, plugin, "Infumia", "InfumiaPlugin");
        }))
      .register();
  }
}
