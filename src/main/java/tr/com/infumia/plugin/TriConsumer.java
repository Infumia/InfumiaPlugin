package tr.com.infumia.plugin;

import org.jetbrains.annotations.NotNull;

public interface TriConsumer<X, Y, Z> {

  void accept(@NotNull X left, @NotNull Y middle, @NotNull Z right);
}
