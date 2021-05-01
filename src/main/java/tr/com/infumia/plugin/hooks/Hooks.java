package tr.com.infumia.plugin.hooks;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.hooks.hooks.ASkyBlockHook;
import tr.com.infumia.plugin.hooks.hooks.ASkyBlockWrapper;
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

@UtilityClass
public class Hooks {

  private final Map<String, Wrapped> WRAPPERS = new HashMap<>();

  public void addHook(@NotNull final Hook hook) {
    if (hook.initiate()) {
      final var id = hook.id();
      Hooks.WRAPPERS.put(id, hook.create());
      Hooks.sendHookNotify("&a" + id);
    }
  }

  @NotNull
  public Optional<ASkyBlockWrapper> getASkyBlock() {
    return Hooks.getWrapper(ASkyBlockHook.A_SKY_BLOCK_ID);
  }

  @NotNull
  public ASkyBlockWrapper getASkyBlockOrThrow() {
    return Hooks.getASkyBlock().orElseThrow();
  }

  @NotNull
  public Optional<BentoBoxWrapper> getBentoBox() {
    return Hooks.getWrapper(BentoBoxHook.BENTO_BOX_ID);
  }

  @NotNull
  public BentoBoxWrapper getBentoBoxOrThrow() {
    return Hooks.getBentoBox().orElseThrow();
  }

  @NotNull
  public Optional<GroupManagerWrapper> getGroupManager() {
    return Hooks.getWrapper(GroupManagerHook.GROUP_MANAGER_ID);
  }

  @NotNull
  public GroupManagerWrapper getGroupManagerOrThrow() {
    return Hooks.getGroupManager().orElseThrow();
  }

  @NotNull
  public Optional<LuckPermsWrapper> getLuckPerms() {
    return Hooks.getWrapper(LuckPermsHook.LUCK_PERMS_ID);
  }

  @NotNull
  public LuckPermsWrapper getLuckPermsOrThrow() {
    return Hooks.getLuckPerms().orElseThrow();
  }

  @NotNull
  public Optional<PermissionsExWrapper> getPermissionsEx() {
    return Hooks.getWrapper(PermissionsExHook.PERMISSIONS_EX_ID);
  }

  @NotNull
  public PermissionsExWrapper getPermissionsExOrThrow() {
    return Hooks.getPermissionsEx().orElseThrow();
  }

  @NotNull
  public Optional<PlaceholderAPIWrapper> getPlaceholderAPI() {
    return Hooks.getWrapper(PlaceholderAPIHook.PLACEHOLDER_API_ID);
  }

  @NotNull
  public PlaceholderAPIWrapper getPlaceholderAPIOrThrow() {
    return Hooks.getPlaceholderAPI().orElseThrow();
  }

  @NotNull
  public Optional<VaultWrapper> getVault() {
    return Hooks.getWrapper(VaultHook.VAULT_ID);
  }

  @NotNull
  public VaultWrapper getVaultOrThrow() {
    return Hooks.getVault().orElseThrow();
  }

  public void loadHooks() {
    Hooks.addHook(new LuckPermsHook());
    Hooks.addHook(new PlaceholderAPIHook());
    Hooks.addHook(new VaultHook());
    Hooks.addHook(new ASkyBlockHook());
    Hooks.addHook(new BentoBoxHook());
    Hooks.addHook(new GroupManagerHook());
    Hooks.addHook(new PermissionsExHook());
  }

  @NotNull
  @SuppressWarnings("unchecked")
  private <T extends Wrapped> Optional<T> getWrapper(@NotNull final String wrappedId) {
    return Optional.ofNullable(Hooks.WRAPPERS.get(wrappedId))
      .map(o -> (T) o);
  }

  private void sendHookNotify(@NotNull final String id) {
    Bukkit.getConsoleSender().sendMessage(ColorUtil.colored(String.format("%s is hooking", id)));
  }
}
