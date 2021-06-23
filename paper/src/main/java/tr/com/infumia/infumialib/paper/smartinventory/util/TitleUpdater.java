package tr.com.infumia.infumialib.paper.smartinventory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an utility class that helps to change title of pages.
 */
@SuppressWarnings("unused")
public final class TitleUpdater {

  /**
   * the chat message class.
   */
  private static final Class<?> CHAT_MESSAGE_CLASS;

  /**
   * the container class.
   */
  private static final Class<?> CONTAINERS_CLASS;

  /**
   * the container class.
   */
  private static final Class<?> CONTAINER_CLASS;

  /**
   * the craft player class.
   */
  private static final Class<?> CRAFT_PLAYER_CLASS;

  /**
   * the entity player class.
   */
  private static final Class<?> ENTITY_PLAYER_CLASS;

  /**
   * the i chat base component class.
   */
  private static final Class<?> I_CHAT_BASE_COMPONENT_CLASS;

  /**
   * the packet play out open windows class.
   */
  private static final Class<?> PACKET_PLAY_OUT_OPEN_WINDOW_CLASS;

  /**
   * the active container field.
   */
  private static Field activeContainerField;

  /**
   * the chat message constructor.
   */
  private static Constructor<?> chatMessageConstructor;

  /**
   * the get bukkit view.
   */
  private static Method getBukkitView;

  /**
   * the get handle.
   */
  private static Method getHandle;

  /**
   * the packet plat out open window constructor.
   */
  private static Constructor<?> packetPlayOutOpenWindowConstructor;

  /**
   * the update inventory.
   */
  private static Method updateInventory;

  /**
   * the window id field.
   */
  private static Field windowIdField;

  static {
    CRAFT_PLAYER_CLASS = ReflectionUtils.getCraftClass("entity.CraftPlayer");
    CHAT_MESSAGE_CLASS = ReflectionUtils.getNMSClass("ChatMessage");
    PACKET_PLAY_OUT_OPEN_WINDOW_CLASS = ReflectionUtils.getNMSClass("PacketPlayOutOpenWindow");
    I_CHAT_BASE_COMPONENT_CLASS = ReflectionUtils.getNMSClass("IChatBaseComponent");
    CONTAINERS_CLASS = TitleUpdater.useContainers() ? ReflectionUtils.getNMSClass("Containers") : null;
    ENTITY_PLAYER_CLASS = ReflectionUtils.getNMSClass("EntityPlayer");
    CONTAINER_CLASS = ReflectionUtils.getNMSClass("Container");
    try {
      TitleUpdater.getHandle = TitleUpdater.CRAFT_PLAYER_CLASS.getMethod("getHandle");
      TitleUpdater.getBukkitView = TitleUpdater.CONTAINER_CLASS.getMethod("getBukkitView");
      TitleUpdater.updateInventory = TitleUpdater.ENTITY_PLAYER_CLASS.getMethod("updateInventory", TitleUpdater.CONTAINER_CLASS);
      TitleUpdater.chatMessageConstructor = TitleUpdater.CHAT_MESSAGE_CLASS.getConstructor(String.class, Object[].class);
      TitleUpdater.packetPlayOutOpenWindowConstructor =
        TitleUpdater.useContainers() ?
          TitleUpdater.PACKET_PLAY_OUT_OPEN_WINDOW_CLASS.getConstructor(int.class, TitleUpdater.CONTAINERS_CLASS, TitleUpdater.I_CHAT_BASE_COMPONENT_CLASS) :
          TitleUpdater.PACKET_PLAY_OUT_OPEN_WINDOW_CLASS.getConstructor(int.class, String.class, TitleUpdater.I_CHAT_BASE_COMPONENT_CLASS, int.class);
      TitleUpdater.activeContainerField = TitleUpdater.ENTITY_PLAYER_CLASS.getField("activeContainer");
      TitleUpdater.windowIdField = TitleUpdater.CONTAINER_CLASS.getField("windowId");
    } catch (final NoSuchMethodException | NoSuchFieldException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * ctor.
   */
  private TitleUpdater() {
  }

  /**
   * Update the player inventory, so you can change the title.
   *
   * @param player whose inventory will be updated.
   * @param newTitle the new title for the inventory.
   */
  public static void updateInventory(@NotNull final Player player, @Nullable final String newTitle) {
    var newTitleTemp = newTitle;
    Objects.requireNonNull(player, "Cannot update inventory to null player");
    try {
      final var craftPlayer = TitleUpdater.CRAFT_PLAYER_CLASS.cast(player);
      final var entityPlayer = TitleUpdater.getHandle.invoke(craftPlayer);
      if (newTitleTemp != null && newTitleTemp.length() > 32) {
        newTitleTemp = newTitleTemp.substring(0, 32);
      }
      final var title = TitleUpdater.chatMessageConstructor.newInstance(newTitleTemp != null ? newTitleTemp : "", new Object[]{});
      final var activeContainer = TitleUpdater.activeContainerField.get(entityPlayer);
      final var windowId = (Integer) TitleUpdater.windowIdField.get(activeContainer);
      final var bukkitView = TitleUpdater.getBukkitView.invoke(activeContainer);
      if (!(bukkitView instanceof InventoryView)) {
        return;
      }
      final var view = (InventoryView) bukkitView;
      final var type = view.getTopInventory().getType();
      if ((type == InventoryType.WORKBENCH || type == InventoryType.ANVIL) && !TitleUpdater.useContainers()) {
        return;
      }
      if (type == InventoryType.CRAFTING || type == InventoryType.CREATIVE || type == InventoryType.PLAYER) {
        return;
      }
      final var size = view.getTopInventory().getSize();
      final var container = Containers.getType(type, size);
      if (container == null) {
        return;
      }
      if (container.getContainerVersion() > TitleUpdater.getVersion() && TitleUpdater.useContainers()) {
        Bukkit.getLogger().warning("This container doesn't work on your current version.");
        return;
      }
      final Object object;
      if (!TitleUpdater.useContainers() && container == Containers.GENERIC_3X3) {
        object = "minecraft:" + type.name().toLowerCase();
      } else {
        object = container.getObject();
      }
      final var packet = TitleUpdater.useContainers()
        ? TitleUpdater.packetPlayOutOpenWindowConstructor.newInstance(windowId, object, title)
        : TitleUpdater.packetPlayOutOpenWindowConstructor.newInstance(windowId, object, title, size);
      ReflectionUtils.sendPacket(player, packet);
      TitleUpdater.updateInventory.invoke(entityPlayer, activeContainer);
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Get the current version of the server.
   *
   * @return version of the server.
   */
  private static int getVersion() {
    return Integer.parseInt(ReflectionUtils.VERSION.split("_")[1]);
  }

  /**
   * Containers were added in 1.14, a String were used in previous versions.
   *
   * @return whether or not to use containers.
   */
  private static boolean useContainers() {
    return TitleUpdater.getVersion() > 13;
  }

  /**
   * An enum class for the necessaries containers.
   */
  public enum Containers {
    /**
     * the generic 9x1.
     */
    GENERIC_9X1(14, "minecraft:chest", "CHEST"),
    /**
     * the generic 9x2.
     */
    GENERIC_9X2(14, "minecraft:chest", "CHEST"),
    /**
     * the generic 9x3.
     */
    GENERIC_9X3(14, "minecraft:chest", "CHEST", "ENDER_CHEST", "BARREL"),
    /**
     * the generic 9x4.
     */
    GENERIC_9X4(14, "minecraft:chest", "CHEST"),
    /**
     * the generic 9x5.
     */
    GENERIC_9X5(14, "minecraft:chest", "CHEST"),
    /**
     * the generic 9x6.
     */
    GENERIC_9X6(14, "minecraft:chest", "CHEST"),
    /**
     * the generic 3x3.
     */
    GENERIC_3X3(14, null, "DISPENSER", "DROPPER"),
    /**
     * the anvil.
     */
    ANVIL(14, "minecraft:anvil", "ANVIL"),
    /**
     * the beacon.
     */
    BEACON(14, "minecraft:beacon", "BEACON"),
    /**
     * the brewing stand.
     */
    BREWING_STAND(14, "minecraft:brewing_stand", "BREWING"),
    /**
     * the enchantment.
     */
    ENCHANTMENT(14, "minecraft:enchanting_table", "ENCHANTING"),
    /**
     * the furnace.
     */
    FURNACE(14, "minecraft:furnace", "FURNACE"),
    /**
     * the hopper.
     */
    HOPPER(14, "minecraft:hopper", "HOPPER"),
    /**
     * the merchant.
     */
    MERCHANT(14, "minecraft:villager", "MERCHANT"),
    /**
     * the shulker box.
     */
    SHULKER_BOX(14, "minecraft:blue_shulker_box", "SHULKER_BOX"),
    /**
     * the blast furnace.
     */
    BLAST_FURNACE(14, null, "BLAST_FURNACE"),
    /**
     * the crafting.
     */
    CRAFTING(14, null, "WORKBENCH"),
    /**
     * the girnd stone.
     */
    GRINDSTONE(14, null, "GRINDSTONE"),
    /**
     * the lectern.
     */
    LECTERN(14, null, "LECTERN"),
    /**
     * the loom.
     */
    LOOM(14, null, "LOOM"),
    /**
     * the smoker.
     */
    SMOKER(14, null, "SMOKER"),
    /**
     * the cartography table.
     */
    CARTOGRAPHY_TABLE(14, null, "CARTOGRAPHY"),
    /**
     * the stone cutter.
     */
    STONE_CUTTER(14, null, "STONECUTTER"),
    /**
     * the smithing.
     */
    SMITHING(16, null, "SMITHING");

    /**
     * the container version.
     */
    @Getter
    private final int containerVersion;

    /**
     * the inventory types names.
     */
    @Getter
    private final String[] inventoryTypesNames;

    /**
     * the minecraft name.
     */
    @Getter
    private final String minecraftName;

    Containers(final int containerVersion, final String minecraftName, final String... inventoryTypesNames) {
      this.containerVersion = containerVersion;
      this.minecraftName = minecraftName;
      this.inventoryTypesNames = inventoryTypesNames;
    }

    /**
     * Get the container based on the current open inventory of the player.
     *
     * @param type type of inventory.
     * @param size size of the inventory.
     *
     * @return the container.
     */
    @Nullable
    public static Containers getType(final InventoryType type, final int size) {
      if (type == InventoryType.CHEST) {
        return Containers.valueOf("GENERIC_9X" + size / 9);
      }
      return Arrays.stream(Containers.values())
        .filter(container -> Arrays.stream(container.getInventoryTypesNames())
          .anyMatch(bukkitName -> bukkitName.equalsIgnoreCase(type.toString())))
        .findFirst()
        .orElse(null);
    }

    /**
     * Get the object from the container enum.
     *
     * @return a Containers object if 1.14, otherwise, a String.
     */
    @Nullable
    public Object getObject() {
      try {
        if (!TitleUpdater.useContainers()) {
          return this.getMinecraftName();
        }
        final var name = TitleUpdater.getVersion() == 14 && this == Containers.CARTOGRAPHY_TABLE ? "CARTOGRAPHY" : this.name();
        final var field = TitleUpdater.CONTAINERS_CLASS.getField(name);
        return field.get(null);
      } catch (final Exception exception) {
        exception.printStackTrace();
      }
      return null;
    }
  }
}
