package tr.com.infumia.infumialib.paper.commands;

import cloud.commandframework.paper.PaperCommandManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract class that helps to create and register commands.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommandHandler {

  /**
   * the manager.
   */
  @NotNull
  protected final PaperCommandManager<CommandSender> manager;

  /**
   * obtains the plugin.
   *
   * @return plugin.
   */
  @NotNull
  public final Plugin getPlugin() {
    return this.manager.getOwningPlugin();
  }

  /**
   * registers the command.
   */
  public abstract void register();
}
