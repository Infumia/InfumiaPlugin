package tr.com.infumia.infumialib.paper.utils;

import tr.com.infumia.infumialib.paper.version.BukkitVersion;

/**
 * a class that represents versions.
 */
public final class Versions {

  /**
   * the server version.
   */
  public static final BukkitVersion FULL = new BukkitVersion();

  /**
   * the server version as major.
   */
  public static final int MAJOR = Versions.FULL.getMajor();

  /**
   * the server version as micro.
   */
  public static final int MICRO = Versions.FULL.getMicro();

  /**
   * the server version as minor.
   */
  public static final int MINOR = Versions.FULL.getMinor();

  /**
   * ctor.
   */
  private Versions() {
  }
}
