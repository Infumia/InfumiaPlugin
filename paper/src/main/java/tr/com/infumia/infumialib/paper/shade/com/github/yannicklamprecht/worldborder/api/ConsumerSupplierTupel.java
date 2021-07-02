package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public final class ConsumerSupplierTupel<T> {

  private final Consumer<T> consumer;

  private final Supplier<T> supplier;

  public ConsumerSupplierTupel(@NotNull final Consumer<T> consumer, @NotNull final Supplier<T> supplier) {
    this.consumer = consumer;
    this.supplier = supplier;
  }

  public static <T> ConsumerSupplierTupel<T> of(final Consumer<T> consumer, final Supplier<T> supplier) {
    return new ConsumerSupplierTupel<>(consumer, supplier);
  }

  public Consumer<T> consumer() {
    return this.consumer;
  }

  public T get() {
    return this.supplier.get();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.consumer, this.supplier);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    final var that = (ConsumerSupplierTupel) obj;
    return Objects.equals(this.consumer, that.consumer) &&
      Objects.equals(this.supplier, that.supplier);
  }

  @Override
  public String toString() {
    return "ConsumerSupplierTupel[" +
      "consumer=" + this.consumer + ", " +
      "supplier=" + this.supplier + ']';
  }

  public void set(final T value) {
    this.consumer.accept(value);
  }

  public Supplier<T> supplier() {
    return this.supplier;
  }
}
