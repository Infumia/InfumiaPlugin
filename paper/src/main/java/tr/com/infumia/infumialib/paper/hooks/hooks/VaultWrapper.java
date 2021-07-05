package tr.com.infumia.infumialib.paper.hooks.hooks;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.hooks.Wrapped;

@RequiredArgsConstructor
public final class VaultWrapper implements Wrapped {

  @NotNull
  @Delegate
  private final Economy economy;

  public void safeWithdrawPlayer(@NotNull final Player player, final double money) {
    if (this.getBalance(player) >= money) {
      this.economy.withdrawPlayer(player, money);
    }
  }
}
