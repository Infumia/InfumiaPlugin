package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IWorldBorder {

  void center(Position center);

  Position center();

  double damageBufferInBlocks();

  void damageBufferInBlocks(double blocks);

  double damagePerSecondPerBlock();

  void damagePerSecondPerBlock(double damage);

  default Position getCenter() {
    return this.center();
  }

  default void setCenter(final Position center) {
    this.center(center);
  }

  default double getDamageBufferInBlocks() {
    return this.damageBufferInBlocks();
  }

  default void setDamageBufferInBlocks(final double blocks) {
    this.damageBufferInBlocks(blocks);
  }

  default double getDamagePerSecondPerBlock() {
    return this.damagePerSecondPerBlock();
  }

  default void setDamagePerSecondPerBlock(final double damage) {
    this.damagePerSecondPerBlock(damage);
  }

  default Position getMax() {
    return this.max();
  }

  default Position getMin() {
    return this.min();
  }

  default double getSize() {
    return this.size();
  }

  default void setSize(final double radius) {
    this.size(radius);
  }

  default int getWarningDistanceInBlocks() {
    return this.warningDistanceInBlocks();
  }

  default void setWarningDistanceInBlocks(final int blocks) {
    this.warningDistanceInBlocks(blocks);
  }

  default int getWarningTimerInSeconds() {
    return this.warningTimerInSeconds();
  }

  boolean isInBounds(Location location);

  void lerp(double oldSize, double newSize, long time);

  Position max();

  Position min();

  void send(Player player, WorldBorderAction worldBorderAction);

  default void setWarningTimeInSeconds(final int seconds) {
    this.warningTimeInSeconds(seconds);
  }

  double size();

  void size(double radius);

  int warningDistanceInBlocks();

  void warningDistanceInBlocks(int blocks);

  void warningTimeInSeconds(int seconds);

  int warningTimerInSeconds();
}
