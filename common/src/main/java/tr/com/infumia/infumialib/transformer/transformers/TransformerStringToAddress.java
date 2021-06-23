package tr.com.infumia.infumialib.transformer.transformers;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.com.infumia.infumialib.transformer.TwoSideTransformer;

/**
 * a class that represents transformers between {@link String} and {@link InetSocketAddress}.
 */
public final class TransformerStringToAddress extends TwoSideTransformer.Base<String, InetSocketAddress> {

  /**
   * ctor.
   */
  public TransformerStringToAddress() {
    super(String.class, InetSocketAddress.class,
      TransformerStringToAddress::toAddress,
      TransformerStringToAddress::toAddress);
  }

  @NotNull
  private static String toAddress(@NotNull final InetSocketAddress address) {
    return address.getHostName() + ":" + address.getPort();
  }

  /**
   * converts the given string into {@link InetSocketAddress}.
   *
   * @param address the address to convert.
   *
   * @return converted {@link InetSocketAddress} instance.
   */
  @Nullable
  private static InetSocketAddress toAddress(@NotNull final String address) {
    final var trim = address.trim();
    final var strings = trim.split(":");
    if (strings.length != 2) {
      return null;
    }
    return new InetSocketAddress(strings[0], new BigDecimal(strings[1]).intValueExact());
  }
}
