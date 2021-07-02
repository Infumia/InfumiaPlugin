package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.function.Function;
import java.util.function.Supplier;
import org.bukkit.Location;

/**
 * Created by ysl3000
 */
public abstract class AbstractWorldBorder implements IWorldBorder {

  private final ConsumerSupplierTupel<Position> center;

  private final ConsumerSupplierTupel<Double> damagePerSecondsPerBlock;

  private final ConsumerSupplierTupel<Double> damaheBufferInBlocks;

  private final Function<Location, Boolean> inBoundsSupplier;

  private final FunctionDoubleDoubleLong lerpConsumer;

  private final Supplier<Position> maxSupplier;

  private final Supplier<Position> minSupplier;

  private final ConsumerSupplierTupel<Double> size;

  private final ConsumerSupplierTupel<Integer> warningDistanceInBlocks;

  private final ConsumerSupplierTupel<Integer> warningTimerInSeconds;

  public AbstractWorldBorder(final ConsumerSupplierTupel<Position> center,
                             final Supplier<Position> minSupplier, final Supplier<Position> maxSupplier,
                             final ConsumerSupplierTupel<Double> size,
                             final ConsumerSupplierTupel<Double> damaheBufferInBlocks,
                             final ConsumerSupplierTupel<Double> damagePerSecondsPerBlock,
                             final ConsumerSupplierTupel<Integer> warningTimerInSeconds,
                             final ConsumerSupplierTupel<Integer> warningDistanceInBlocks,
                             final Function<Location, Boolean> inBoundsSupplier,
                             final FunctionDoubleDoubleLong lerpConsumer) {
    this.center = center;
    this.minSupplier = minSupplier;
    this.maxSupplier = maxSupplier;
    this.size = size;
    this.damaheBufferInBlocks = damaheBufferInBlocks;
    this.damagePerSecondsPerBlock = damagePerSecondsPerBlock;
    this.warningTimerInSeconds = warningTimerInSeconds;
    this.warningDistanceInBlocks = warningDistanceInBlocks;
    this.inBoundsSupplier = inBoundsSupplier;
    this.lerpConsumer = lerpConsumer;
  }

  @Override
  public void center(final Position center) {
    this.center.set(center);
  }

  @Override
  public Position center() {
    return this.center.get();
  }

  @Override
  public double damageBufferInBlocks() {
    return this.damaheBufferInBlocks.get();
  }

  @Override
  public void damageBufferInBlocks(final double blocks) {
    this.damaheBufferInBlocks.set(blocks);
  }

  @Override
  public double damagePerSecondPerBlock() {
    return this.damagePerSecondsPerBlock.get();
  }

  @Override
  public void damagePerSecondPerBlock(final double damage) {
    this.damagePerSecondsPerBlock.set(damage);
  }

  @Override
  public boolean isInBounds(final Location location) {
    return this.inBoundsSupplier.apply(location);
  }

  @Override
  public void lerp(final double oldSize, final double newSize, final long time) {
    this.lerpConsumer.lerp(oldSize, newSize, time);
  }

  @Override
  public Position max() {
    return this.maxSupplier.get();
  }

  @Override
  public Position min() {
    return this.minSupplier.get();
  }

  @Override
  public double size() {
    return this.size.get();
  }

  @Override
  public void size(final double radius) {
    this.size.set(radius);
  }

  @Override
  public int warningDistanceInBlocks() {
    return this.warningDistanceInBlocks.get();
  }

  @Override
  public void warningDistanceInBlocks(final int blocks) {
    this.warningDistanceInBlocks.set(blocks);
  }

  @Override
  public void warningTimeInSeconds(final int seconds) {
    this.warningTimerInSeconds.set(seconds);
  }

  @Override
  public int warningTimerInSeconds() {
    return this.warningTimerInSeconds.get();
  }
}
