package tr.com.infumia.plugin.hooks;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.hooks.BentoBoxHook;
import tr.com.infumia.plugin.hooks.hooks.BentoBoxWrapper;
import tr.com.infumia.plugin.hooks.hooks.GroupManagerHook;
import tr.com.infumia.plugin.hooks.hooks.GroupManagerWrapper;
import tr.com.infumia.plugin.hooks.hooks.LuckPermsHook;
import tr.com.infumia.plugin.hooks.hooks.LuckPermsWrapper;
import tr.com.infumia.plugin.hooks.hooks.PermissionsExHook;
import tr.com.infumia.plugin.hooks.hooks.PermissionsExWrapper;
import tr.com.infumia.plugin.hooks.hooks.PlaceholderAPIHook;
import tr.com.infumia.plugin.hooks.hooks.PlaceholderAPIWrapper;
import tr.com.infumia.plugin.hooks.hooks.VaultHook;
import tr.com.infumia.plugin.hooks.hooks.VaultWrapper;

/**
 * a class that contains utility methods for hooks.
 */
@UtilityClass
public class Hooks {

  /**
   * the wrappers.
   */
  private final Map<String, Wrapped> WRAPPERS = new HashMap<>();

  /**
   * adds the given hook to {@link #WRAPPERS}.
   *
   * @param hook the hook to add.
   */
  public void addHook(@NotNull final Hook<?> hook) {
    if (hook.initiate()) {
      final var id = hook.id();
      Hooks.WRAPPERS.put(id, hook.create());
      Bukkit.getConsoleSender().sendMessage(ColorUtil.colored(String.format("%s is hooking", id)));
    }
  }

  /**
   * obtains the bento box wrapper.
   *
   * @return bento box wrapper.
   */
  @NotNull
  public Optional<BentoBoxWrapper> getBentoBox() {
    return Hooks.getWrapper(BentoBoxHook.BENTO_BOX_ID);
  }

  /**
   * obtains the bento box wrapper.
   *
   * @return bento box wrapper.
   */
  @NotNull
  public BentoBoxWrapper getBentoBoxOrThrow() {
    return Hooks.getBentoBox().orElseThrow();
  }

  /**
   * obtains the group manager wrapper.
   *
   * @return group manager wrapper.
   */
  @NotNull
  public Optional<GroupManagerWrapper> getGroupManager() {
    return Hooks.getWrapper(GroupManagerHook.GROUP_MANAGER_ID);
  }

  /**
   * obtains the group manager wrapper.
   *
   * @return group manager wrapper.
   */
  @NotNull
  public GroupManagerWrapper getGroupManagerOrThrow() {
    return Hooks.getGroupManager().orElseThrow();
  }

  /**
   * obtains the luck perms wrapper.
   *
   * @return luck perms wrapper.
   */
  @NotNull
  public Optional<LuckPermsWrapper> getLuckPerms() {
    return Hooks.getWrapper(LuckPermsHook.LUCK_PERMS_ID);
  }

  /**
   * obtains the luck perms wrapper.
   *
   * @return luck perms wrapper.
   */
  @NotNull
  public LuckPermsWrapper getLuckPermsOrThrow() {
    return Hooks.getLuckPerms().orElseThrow();
  }

  /**
   * obtains the permissions ex wrapper.
   *
   * @return permissions ex wrapper.
   */
  @NotNull
  public Optional<PermissionsExWrapper> getPermissionsEx() {
    return Hooks.getWrapper(PermissionsExHook.PERMISSIONS_EX_ID);
  }

  /**
   * obtains the permissions ex wrapper.
   *
   * @return permissions ex wrapper.
   */
  @NotNull
  public PermissionsExWrapper getPermissionsExOrThrow() {
    return Hooks.getPermissionsEx().orElseThrow();
  }

  /**
   * obtains the placeholder api wrapper.
   *
   * @return placeholder api wrapper.
   */
  @NotNull
  public Optional<PlaceholderAPIWrapper> getPlaceholderAPI() {
    return Hooks.getWrapper(PlaceholderAPIHook.PLACEHOLDER_API_ID);
  }

  /**
   * obtains the placeholder api wrapper.
   *
   * @return placeholder api wrapper.
   */
  @NotNull
  public PlaceholderAPIWrapper getPlaceholderAPIOrThrow() {
    return Hooks.getPlaceholderAPI().orElseThrow();
  }

  /**
   * obtains the vault wrapper.
   *
   * @return vault wrapper.
   */
  @NotNull
  public Optional<VaultWrapper> getVault() {
    return Hooks.getWrapper(VaultHook.VAULT_ID);
  }

  /**
   * obtains the vault wrapper.
   *
   * @return vault wrapper.
   */
  @NotNull
  public VaultWrapper getVaultOrThrow() {
    return Hooks.getVault().orElseThrow();
  }

  /**
   * loads the default hooks.
   */
  public void loadHooks() {
    Hooks.addHook(new LuckPermsHook());
    Hooks.addHook(new PlaceholderAPIHook());
    Hooks.addHook(new VaultHook());
    Hooks.addHook(new BentoBoxHook());
    Hooks.addHook(new GroupManagerHook());
    Hooks.addHook(new PermissionsExHook());
  }

  /**
   * gets the wrapper implementation.
   *
   * @param wrappedId the wrapper id to obtain.
   * @param <T> type of the wrapper.
   *
   * @return wrapper instance.
   */
  @NotNull
  @SuppressWarnings("unchecked")
  private <T extends Wrapped> Optional<T> getWrapper(@NotNull final String wrappedId) {
    return Optional.ofNullable(Hooks.WRAPPERS.get(wrappedId))
      .map(o -> (T) o);
  }
}
