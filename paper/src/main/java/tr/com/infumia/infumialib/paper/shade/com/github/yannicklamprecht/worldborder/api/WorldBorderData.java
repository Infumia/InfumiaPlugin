package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import java.util.function.BiConsumer;

public class WorldBorderData {

  private double damageAmount;

  private double damageBufferInBlocks;

  private double size;

  private int warningDistance;

  private int warningTimeSeconds;

  private double x;

  private double z;

  public void applyAll(final IWorldBorder worldBorder) {
    worldBorder.setSize(this.size);
    worldBorder.setCenter(new Position(this.x, this.z));
    worldBorder.setDamageBufferInBlocks(this.damageBufferInBlocks);
    worldBorder.setDamagePerSecondPerBlock(this.damageAmount);
    worldBorder.setWarningTimeInSeconds(this.warningTimeSeconds);
    worldBorder.setWarningDistanceInBlocks(this.warningDistance);
  }

  public void applyCenter(final BiConsumer<Double, Double> doubleBiConsumer) {
    doubleBiConsumer.accept(this.x, this.z);
  }

  public double getDamageAmount() {
    return this.damageAmount;
  }

  public void setDamageAmount(final double damage) {
    this.damageAmount = damage;
  }

  public double getDamageBuffer() {
    return this.damageBufferInBlocks;
  }

  public void setDamageBuffer(final double blocks) {
    this.damageBufferInBlocks = blocks;
  }

  public double getSize() {
    return this.size;
  }

  public void setSize(final double newSize) {
    this.size = newSize;
  }

  public int getWarningDistance() {
    return this.warningDistance;
  }

  public void setWarningDistance(final int distance) {
    this.warningDistance = distance;
  }

  public int getWarningTimeSeconds() {
    return this.warningTimeSeconds;
  }

  public void setWarningTimeSeconds(final int seconds) {
    this.warningTimeSeconds = seconds;
  }

  public void setCenter(final double x, final double z) {
    this.x = x;
    this.z = z;
  }
}
