package tr.com.infumia.plugin.utils;

import java.io.IOException;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
      try {
        final var version = GitHub.connect()
          .getOrganization(organizationName)
          .getRepository(repositoryName)
          .getLatestRelease()
          .getTagName();
        final var pluginVersion = plugin.getDescription().getVersion();
        if (!version.equals(pluginVersion)) {
          sender.sendMessage(
            GitHubUpdateChecker.createUpdateMessage(plugin, organizationName, repositoryName));
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
   * @param organizationName the organization name to create.
   * @param repositoryName the repository name to create.
   *
   * @return a newly created update message.
   */
  @NotNull
  public static TextComponent createUpdateMessage(@NotNull final Plugin plugin, @NotNull final String organizationName,
                                                  @NotNull final String repositoryName) {
    return Component.text()
      .append(Component.text("[InfumiaPlugin] Update available for ")
        .color(NamedTextColor.YELLOW))
      .append(Component.text(plugin.getName()))
      .append(Component.text(" download the newest version here:")
        .color(NamedTextColor.YELLOW))
      .append(Component.newline())
      .append(Component.text("https://github.com/%organization_name%/%repository_name%/releases/latest")
        .color(NamedTextColor.GOLD)
        .decorate(TextDecoration.UNDERLINED)
        .replaceText(TextReplacementConfig.builder()
          .matchLiteral("%organization_name%")
          .once()
          .replacement(organizationName)
          .build())
        .replaceText(TextReplacementConfig.builder()
          .matchLiteral("%repository_name%")
          .once()
          .replacement(repositoryName)
          .build()))
      .build();
  }
}
