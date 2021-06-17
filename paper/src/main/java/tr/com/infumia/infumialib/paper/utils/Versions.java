package tr.com.infumia.infumialib.paper.utils;

import io.github.portlek.bukkitversion.BukkitVersion;

/**
 * a class that represents versions.
 */
public final class Versions {

  /**
   * the server version.
   */
  public static final BukkitVersion SERVER = new BukkitVersion();

  /**
   * the server version as major.
   */
  public static final int SERVER_MAJOR = Versions.SERVER.getMajor();

  /**
   * the server version as micro.
   */
  public static final int SERVER_MICRO = Versions.SERVER.getMicro();

  /**
   * the server version as minor.
   */
  public static final int SERVER_MINOR = Versions.SERVER.getMinor();

  /**
   * ctor.
   */
  private Versions() {
  }
}
