package tr.com.infumia.infumialib.paper.hooks;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Hook;
import tr.com.infumia.infumialib.hooks.Wrapped;
import tr.com.infumia.infumialib.paper.files.BukkitConfig;
import tr.com.infumia.infumialib.paper.hooks.hooks.BentoBoxHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.BentoBoxWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.CitizensHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.CitizensWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.CombatLogXHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.CombatLogXWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.GroupManagerHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.GroupManagerWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.HolographicDisplaysHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.HolographicDisplaysWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.LuckPermsHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.LuckPermsWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.PermissionsExHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.PermissionsExWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap.Pl3xMapHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.pl3xmap.Pl3xMapWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.PlaceholderAPIWrapper;
import tr.com.infumia.infumialib.paper.hooks.hooks.PlaceholderApiHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.VaultHook;
import tr.com.infumia.infumialib.paper.hooks.hooks.VaultWrapper;

/**
 * a class that contains utility methods for hooks.
 */
@UtilityClass
public class Hooks {

  /**
   * the default hooks.
   */
  private static final Set<Hook<?>> DEFAULT_HOOKS = Set.of(
    new LuckPermsHook(),
    new PlaceholderApiHook(),
    new VaultHook(),
    new BentoBoxHook(),
    new GroupManagerHook(),
    new PermissionsExHook(),
    new CitizensHook(),
    new HolographicDisplaysHook(),
    new CombatLogXHook(),
    new Pl3xMapHook());

  /**
   * the wrappers.
   */
  private final Map<String, Wrapped> WRAPPERS = new ConcurrentHashMap<>();

  /**
   * adds the given hook to {@link #WRAPPERS}.
   *
   * @param hook the hook to add.
   */
  public void addHook(@NotNull final Hook<?> hook) {
    if (!hook.initiate()) {
      return;
    }
    final var id = hook.id();
    Hooks.WRAPPERS.put(id, hook.create());
    Bukkit.getConsoleSender().sendMessage(BukkitConfig.hookMessage
      .build("%hook%", () -> id));
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
   * obtains the citizens wrapper.
   *
   * @return citizens wrapper.
   */
  @NotNull
  public Optional<CitizensWrapper> getCitizens() {
    return Hooks.getWrapper(CitizensHook.CITIZENS_ID);
  }

  /**
   * obtains the citizens wrapper.
   *
   * @return citizens wrapper.
   */
  @NotNull
  public CitizensWrapper getCitizensOrThrow() {
    return Hooks.getCitizens().orElseThrow();
  }

  /**
   * obtains the combat log x wrapper.
   *
   * @return combat log x wrapper.
   */
  @NotNull
  public Optional<CombatLogXWrapper> getCombatLogX() {
    return Hooks.getWrapper(CombatLogXHook.COMBAT_LOG_X_ID);
  }

  /**
   * obtains the combat log x wrapper.
   *
   * @return combat log x wrapper.
   */
  @NotNull
  public CombatLogXWrapper getCombatLogXOrThrow() {
    return Hooks.getCombatLogX().orElseThrow();
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
   * obtains the holographic displays wrapper.
   *
   * @return holographic displays wrapper.
   */
  @NotNull
  public Optional<HolographicDisplaysWrapper> getHolographicDisplays() {
    return Hooks.getWrapper(HolographicDisplaysHook.HOLOGRAPHIC_DISPLAYS_ID);
  }

  /**
   * obtains the holographic displays wrapper.
   *
   * @return holographic displays wrapper.
   */
  @NotNull
  public HolographicDisplaysWrapper getHolographicDisplaysOrThrow() {
    return Hooks.getHolographicDisplays().orElseThrow();
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
   * obtains the pl3x map wrapper.
   *
   * @return pl3x map wrapper.
   */
  @NotNull
  public Optional<Pl3xMapWrapper> getPl3xMap() {
    return Hooks.getWrapper(Pl3xMapHook.PL3X_MAP_ID);
  }

  /**
   * obtains the pl3x map wrapper.
   *
   * @return pl3x map wrapper.
   */
  @NotNull
  public Pl3xMapWrapper getPl3xMapOrThrow() {
    return Hooks.getPl3xMap().orElseThrow();
  }

  /**
   * obtains the placeholder api wrapper.
   *
   * @return placeholder api wrapper.
   */
  @NotNull
  public Optional<PlaceholderAPIWrapper> getPlaceholderApi() {
    return Hooks.getWrapper(PlaceholderApiHook.PLACEHOLDER_API_ID);
  }

  /**
   * obtains the placeholder api wrapper.
   *
   * @return placeholder api wrapper.
   */
  @NotNull
  public PlaceholderAPIWrapper getPlaceholderApiOrThrow() {
    return Hooks.getPlaceholderApi().orElseThrow();
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
    Hooks.DEFAULT_HOOKS.forEach(Hooks::addHook);
  }

  /**
   * checks if the bento box is supported.
   *
   * @return {@code true} if bento box is supported.
   */
  public boolean supportsBentoBox() {
    return Hooks.getBentoBox().isPresent();
  }

  /**
   * checks if the citizens is supported.
   *
   * @return {@code true} if citizens is supported.
   */
  public boolean supportsCitizens() {
    return Hooks.getCitizens().isPresent();
  }

  /**
   * checks if the combat log x is supported.
   *
   * @return {@code true} if combat log x is supported.
   */
  public boolean supportsCombatLogX() {
    return Hooks.getCombatLogX().isPresent();
  }

  /**
   * checks if the group manager is supported.
   *
   * @return {@code true} if group manager is supported.
   */
  public boolean supportsGroupManager() {
    return Hooks.getGroupManager().isPresent();
  }

  /**
   * checks if the holographic displays is supported.
   *
   * @return {@code true} if holographic displays is supported.
   */
  public boolean supportsHolographicDisplays() {
    return Hooks.getHolographicDisplays().isPresent();
  }

  /**
   * checks if the luck perms is supported.
   *
   * @return {@code true} if luck perms is supported.
   */
  public boolean supportsLuckPerms() {
    return Hooks.getLuckPerms().isPresent();
  }

  /**
   * checks if the permissions ex is supported.
   *
   * @return {@code true} if permissions ex is supported.
   */
  public boolean supportsPermissionsEx() {
    return Hooks.getPermissionsEx().isPresent();
  }

  /**
   * checks if the pl3x map is supported.
   *
   * @return {@code true} if pl3x map is supported.
   */
  public boolean supportsPl3xMap() {
    return Hooks.getPl3xMap().isPresent();
  }

  /**
   * checks if the placeholder api is supported.
   *
   * @return {@code true} if placeholder api is supported.
   */
  public boolean supportsPlaceholderApi() {
    return Hooks.getPlaceholderApi().isPresent();
  }

  /**
   * checks if the vault is supported.
   *
   * @return {@code true} if vault is supported.
   */
  public boolean supportsVault() {
    return Hooks.getVault().isPresent();
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
