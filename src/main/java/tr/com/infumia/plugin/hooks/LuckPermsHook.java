/*
 * MIT License
 *
 * Copyright (c) 2021 Infumia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package tr.com.infumia.plugin.hooks;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.plugin.Hook;
import tr.com.infumia.plugin.Wrapped;

public final class LuckPermsHook implements Hook {

  public static final String LUCK_PERMS_ID = "LuckPerms";

  private LuckPerms luckPerms;

  @NotNull
  @Override
  public String id() {
    return LuckPermsHook.LUCK_PERMS_ID;
  }

  @Override
  public boolean initiate() {
    final boolean check = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;
    if (check) {
      final RegisteredServiceProvider<LuckPerms> provider =
        Bukkit.getServicesManager().getRegistration(LuckPerms.class);
      if (provider != null) {
        this.luckPerms = provider.getProvider();
      }
    }
    return this.luckPerms != null;
  }

  @Override
  @NotNull
  public Wrapped create() {
    if (this.luckPerms == null) {
      throw new IllegalStateException("LuckPerms not initiated! Use LuckPermsHook#initiate method.");
    }
    return new LuckPermsWrapper(this.luckPerms);
  }
}