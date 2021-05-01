package tr.com.infumia.plugin.hooks;

import org.jetbrains.annotations.NotNull;

public interface Hook {

  @NotNull
  Wrapped create();

  @NotNull
  String id();

  boolean initiate();
}
