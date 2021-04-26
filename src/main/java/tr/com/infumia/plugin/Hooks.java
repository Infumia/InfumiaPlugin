package tr.com.infumia.plugin;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.ASkyBlockHook;
import tr.com.infumia.plugin.hooks.ASkyBlockWrapper;
import tr.com.infumia.plugin.hooks.BentoBoxHook;
import tr.com.infumia.plugin.hooks.BentoBoxWrapper;
import tr.com.infumia.plugin.hooks.GroupManagerHook;
import tr.com.infumia.plugin.hooks.GroupManagerWrapper;
import tr.com.infumia.plugin.hooks.LuckPermsHook;
import tr.com.infumia.plugin.hooks.LuckPermsWrapper;
import tr.com.infumia.plugin.hooks.PermissionsExHook;
import tr.com.infumia.plugin.hooks.PermissionsExWrapper;
import tr.com.infumia.plugin.hooks.PlaceholderAPIHook;
import tr.com.infumia.plugin.hooks.PlaceholderAPIWrapper;
import tr.com.infumia.plugin.hooks.VaultHook;
import tr.com.infumia.plugin.hooks.VaultWrapper;

@UtilityClass
public class Hooks {

  private final Map<String, Wrapped> WRAPPERS = new HashMap<>();

  public void addHook(@NotNull final String key, @NotNull final Wrapped wrapper) {
    Hooks.WRAPPERS.put(key, wrapper);
    Hooks.sendHookNotify("&a" + key);
  }

  @NotNull
  public Optional<ASkyBlockWrapper> getASkyBlock() {
    return Hooks.getWrapper(ASkyBlockHook.ASKYBLOCK_ID);
  }

  @NotNull
  public Optional<BentoBoxWrapper> getBentoBox() {
    return Hooks.getWrapper(BentoBoxHook.BENTOBOX_ID);
  }

  @NotNull
  public Optional<GroupManagerWrapper> getGroupManager() {
    return Hooks.getWrapper(GroupManagerHook.GROUP_MANAGER_ID);
  }

  @NotNull
  public Optional<LuckPermsWrapper> getLuckPerms() {
    return Hooks.getWrapper(LuckPermsHook.LUCK_PERMS_ID);
  }

  @NotNull
  public Optional<PermissionsExWrapper> getPermissionsEx() {
    return Hooks.getWrapper(PermissionsExHook.PERMISSONS_EX_ID);
  }

  @NotNull
  public Optional<PlaceholderAPIWrapper> getPlaceholderAPI() {
    return Hooks.getWrapper(PlaceholderAPIHook.PLACEHOLDER_API_ID);
  }

  @NotNull
  public Optional<VaultWrapper> getVault() {
    return Hooks.getWrapper(VaultHook.VAULT_ID);
  }

  public void loadHooks() {
    Stream.of(new LuckPermsHook(), new PlaceholderAPIHook(), new VaultHook(), new ASkyBlockHook(), new BentoBoxHook(),
      new GroupManagerHook(), new PermissionsExHook())
      .filter(Hook::initiate)
      .forEach(hook -> Hooks.addHook(hook.id(), hook.create()));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  private <T extends Wrapped> Optional<T> getWrapper(@NotNull final String wrappedId) {
    return Optional.ofNullable(Hooks.WRAPPERS.get(wrappedId))
      .map(o -> (T) o);
  }

  private void sendHookNotify(@NotNull final String id) {
    Bukkit.getConsoleSender().sendMessage(ColorUtil.colored(id + " is hooking"));
  }
}
