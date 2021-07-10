package tr.com.infumia.infumialib.maps;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MutableMap<K, V> extends HashMap<K, V> {

  private MutableMap(@NotNull final Map<? extends K, ? extends V> m) {
    super(m);
  }

  @NotNull
  public static <K, V> MutableMap<K, V> of() {
    return new MutableMap<>();
  }

  @NotNull
  public static <K, V> MutableMap<K, V> of(@NotNull final Map<? extends K, ? extends V> m) {
    return new MutableMap<>(m);
  }

  @NotNull
  public static <K, V> MutableMap<K, V> of(@NotNull final K key, @NotNull final V value) {
    return MutableMap.<K, V>of().with(key, value);
  }

  public MutableMap<K, V> with(@NotNull final K key, @NotNull final V value) {
    this.put(key, value);
    return this;
  }

  public MutableMap<K, V> without(@NotNull final K key) {
    this.remove(key);
    return this;
  }
}
