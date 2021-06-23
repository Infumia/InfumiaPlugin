/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tr.com.infumia.infumialib.paper.smartinventory.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <b>ReflectionUtils</b> - Reflection handler for NMS and CraftBukkit.<br>
 * Caches the packet related methods and is asynchronous.
 * <p>
 * This class does not handle null checks as most of the requests are from the
 * other utility classes that already handle null checks.
 * <p>
 * <a href="https://wiki.vg/Protocol">Clientbound Packets</a> are considered fake
 * updates to the client without changing the actual data. Since all the data is handled
 * by the server.
 *
 * @author Crypto Morin
 * @version 2.0.0
 */
public final class ReflectionUtils {

  /**
   * We use reflection mainly to avoid writing a new class for version barrier.
   * The version barrier is for NMS that uses the Minecraft version as the main package name.
   * <p>
   * E.g. EntityPlayer in 1.15 is in the class {@code net.minecraft.server.v1_15_R1}
   * but in 1.14 it's in {@code net.minecraft.server.v1_14_R1}
   * In order to maintain cross-version compatibility we cannot import these classes.
   */
  public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

  /**
   * the craft bukkit.
   */
  public static final String CRAFT_BUKKIT = "org.bukkit.craftbukkit." + ReflectionUtils.VERSION + '.';

  /**
   * the nms.
   */
  public static final String NMS = "net.minecraft.server." + ReflectionUtils.VERSION + '.';

  /**
   * the get handle.
   */
  private static final MethodHandle GET_HANDLE;

  /**
   * the player connection.
   */
  private static final MethodHandle PLAYER_CONNECTION;

  /**
   * the send packet.
   */
  private static final MethodHandle SEND_PACKET;

  static {
    final var entityPlayer = ReflectionUtils.getNMSClass("EntityPlayer");
    final var craftPlayer = ReflectionUtils.getCraftClass("entity.CraftPlayer");
    final var playerConnection = ReflectionUtils.getNMSClass("PlayerConnection");
    final var lookup = MethodHandles.lookup();
    MethodHandle sendPacket = null;
    MethodHandle getHandle = null;
    MethodHandle connection = null;
    try {
      connection = lookup.findGetter(entityPlayer, "playerConnection", playerConnection);
      getHandle = lookup.findVirtual(craftPlayer, "getHandle", MethodType.methodType(entityPlayer));
      sendPacket = lookup.findVirtual(playerConnection, "sendPacket", MethodType.methodType(void.class, ReflectionUtils.getNMSClass("Packet")));
    } catch (final NoSuchMethodException | NoSuchFieldException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
    PLAYER_CONNECTION = connection;
    SEND_PACKET = sendPacket;
    GET_HANDLE = getHandle;
  }

  /**
   * ctor.
   */
  private ReflectionUtils() {
  }

  /**
   * Get a CraftBukkit (org.bukkit.craftbukkit) class.
   *
   * @param name the name of the class to load.
   *
   * @return the CraftBukkit class or null if not found.
   *
   * @since 1.0.0
   */
  @Nullable
  public static Class<?> getCraftClass(@NotNull final String name) {
    try {
      return Class.forName(ReflectionUtils.CRAFT_BUKKIT + name);
    } catch (final ClassNotFoundException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Get a NMS (net.minecraft.server) class.
   *
   * @param name the name of the class.
   *
   * @return the NMS class or null if not found.
   *
   * @since 1.0.0
   */
  @Nullable
  public static Class<?> getNMSClass(@NotNull final String name) {
    try {
      return Class.forName(ReflectionUtils.NMS + name);
    } catch (final ClassNotFoundException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Sends a packet to the player asynchronously if they're online.
   * Packets are thread-safe.
   *
   * @param player the player to send the packet to.
   * @param packets the packets to send.
   *
   * @return the async thread handling the packet.
   *
   * @see #sendPacketSync(Player, Object...)
   * @since 1.0.0
   */
  @NotNull
  public static CompletableFuture<Void> sendPacket(@NotNull final Player player, @NotNull final Object... packets) {
    return CompletableFuture.runAsync(() -> ReflectionUtils.sendPacketSync(player, packets))
      .exceptionally(ex -> {
        ex.printStackTrace();
        return null;
      });
  }

  /**
   * Sends a packet to the player synchronously if they're online.
   *
   * @param player the player to send the packet to.
   * @param packets the packets to send.
   *
   * @see #sendPacket(Player, Object...)
   * @since 2.0.0
   */
  public static void sendPacketSync(@NotNull final Player player, @NotNull final Object... packets) {
    try {
      final var handle = ReflectionUtils.GET_HANDLE.invoke(player);
      final var connection = ReflectionUtils.PLAYER_CONNECTION.invoke(handle);
      if (connection != null) {
        for (final var packet : packets) {
          ReflectionUtils.SEND_PACKET.invoke(connection, packet);
        }
      }
    } catch (final Throwable throwable) {
      throwable.printStackTrace();
    }
  }
}
