package tr.com.infumia.plugin.functions;

import org.jetbrains.annotations.NotNull;

public interface TriConsumer<X, Y, Z> {

  void accept(@NotNull X left, @NotNull Y middle, @NotNull Z right);
}
