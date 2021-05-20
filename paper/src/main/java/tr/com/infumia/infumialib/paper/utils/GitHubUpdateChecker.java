package tr.com.infumia.infumialib.paper.utils;

import java.io.IOException;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.github.GitHub;

/**
 * a class that contains update checker for GitHub.
 */
@UtilityClass
public class GitHubUpdateChecker {

  /**
   * checks for the update.
   *
   * @param plugin the plugin to check.
   * @param organizationName the organization name to check.
   * @param repositoryName the repository name to check.
   */
  public static void checkForUpdate(@NotNull final Plugin plugin, @NotNull final String organizationName,
                                    @NotNull final String repositoryName) {
    GitHubUpdateChecker.checkForUpdate(Bukkit.getConsoleSender(), plugin, organizationName, repositoryName);
  }

  /**
   * checks for the update.
   *
   * @param sender the sender to check.
   * @param plugin the plugin to check.
   * @param organizationName the organization name to check.
   * @param repositoryName the repository name to check.
   */
  public static void checkForUpdate(@NotNull final CommandSender sender, @NotNull final Plugin plugin,
                                    @NotNull final String organizationName, @NotNull final String repositoryName) {
    TaskUtilities.async(() -> {
      try {
        final var version = GitHub.connect()
          .getOrganization(organizationName)
          .getRepository(repositoryName)
          .getLatestRelease()
          .getTagName();
        final var pluginVersion = plugin.getDescription().getVersion();
        if (!version.equals(pluginVersion)) {
          sender.sendMessage(GitHubUpdateChecker.createUpdateMessage0(plugin));
          sender.sendMessage(GitHubUpdateChecker.createUpdateMessage1(organizationName, repositoryName));
        }
      } catch (final IOException e) {
        plugin.getSLF4JLogger().warn("Something went wrong when connecting to GitHub.", e);
      }
    });
  }

  /**
   * creates a new update message.
   *
   * @param plugin the plugin to create.
   *
   * @return a newly created update message.
   */
  @NotNull
  private static TextComponent createUpdateMessage0(@NotNull final Plugin plugin) {
    return Component.text()
      .append(Component.text("[InfumiaPlugin] Update available for ")
        .color(NamedTextColor.YELLOW))
      .append(Component.text(plugin.getName()))
      .append(Component.text(" download the newest version here:")
        .color(NamedTextColor.YELLOW))
      .build();
  }

  /**
   * creates a new update message.
   *
   * @param organizationName the organization name to create.
   * @param repositoryName the repository name to create.
   *
   * @return a newly created update message.
   */
  @NotNull
  private static TextComponent createUpdateMessage1(@NotNull final String organizationName,
                                                    @NotNull final String repositoryName) {
    return Component.text()
      .append(Component.text("https://github.com/")
        .color(NamedTextColor.GOLD))
      .append(Component.text(organizationName)
        .color(NamedTextColor.GOLD))
      .append(Component.text("/")
        .color(NamedTextColor.GOLD))
      .append(Component.text(repositoryName)
        .color(NamedTextColor.GOLD))
      .append(Component.text("/releases/latest/")
        .color(NamedTextColor.GOLD))
      .append(Component.text())
      .build();
  }
}
