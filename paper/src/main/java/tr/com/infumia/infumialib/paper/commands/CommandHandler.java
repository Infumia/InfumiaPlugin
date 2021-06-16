package tr.com.infumia.infumialib.paper.commands;

import dev.jorel.commandapi.CommandAPICommand;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract class that helps to create and register commands.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommandHandler {

  /**
   * the plugin.
   */
  @NotNull
  protected final Plugin plugin;

  /**
   * registers the command.
   */
  public final void register() {
    final var mainCommand = this.getMainCommand();
    mainCommand.setSubcommands(this.getSubCommands());
    mainCommand.register();
  }

  /**
   * obtains the main command.
   *
   * @return main command.
   */
  @NotNull
  protected abstract CommandAPICommand getMainCommand();

  /**
   * obtains the sub commands.
   *
   * @return sub commands.
   */
  @NotNull
  protected abstract List<CommandAPICommand> getSubCommands();
}
