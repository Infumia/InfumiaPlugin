package tr.com.infumia.infumialib.paper.hooks.hooks;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.paper.hooks.Hook;

public final class GroupManagerHook implements Hook<GroupManagerWrapper> {

  public static final String GROUP_MANAGER_ID = "GroupManager";

  @Nullable
  private GroupManager groupManager;

  @Override
  @NotNull
  public GroupManagerWrapper create() {
    if (this.groupManager == null) {
      throw new IllegalStateException("GroupManager not initiated! Use GroupManagerHook#initiate method.");
    }
    return new GroupManagerWrapper(this.groupManager);
  }

  @NotNull
  @Override
  public String id() {
    return GroupManagerHook.GROUP_MANAGER_ID;
  }

  @Override
  public boolean initiate() {
    return (this.groupManager = (GroupManager) Bukkit.getPluginManager().getPlugin("GroupManager")) != null;
  }
}
