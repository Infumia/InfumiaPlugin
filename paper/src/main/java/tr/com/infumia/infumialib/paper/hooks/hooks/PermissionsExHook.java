package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tr.com.infumia.infumialib.paper.hooks.Hook;

public final class PermissionsExHook implements Hook<PermissionsExWrapper> {

  public static final String PERMISSIONS_EX_ID = "PermissionsEx";

  @Nullable
  private PermissionsEx permissionsEx;

  @Override
  @NotNull
  public PermissionsExWrapper create() {
    if (this.permissionsEx == null) {
      throw new IllegalStateException("PermissionsEx not initiated! Use PermissionsExHook#initiate method.");
    }
    return new PermissionsExWrapper(this.permissionsEx);
  }

  @NotNull
  @Override
  public String id() {
    return PermissionsExHook.PERMISSIONS_EX_ID;
  }

  @Override
  public boolean initiate() {
    return (this.permissionsEx = (PermissionsEx) Bukkit.getPluginManager().getPlugin("PermissionsEx")) != null;
  }
}
