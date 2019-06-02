package io.github.etrayed.betrayedlib.inventoryapi.inventory.properties;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Etrayed
 */
public class InventoryClickProperties implements InventoryProperties {

    private final Player who;

    private final ItemStack usedItem;

    private final InventoryAction inventoryAction;

    private final ClickType clickType;

    private final int slot;

    private final InventoryType.SlotType slotType;

    private final Cancellable cancellable;

    private InventoryClickProperties(final Player who, final ItemStack usedItem, final InventoryAction inventoryAction,
                                     final ClickType clickType, final int slot, final InventoryType.SlotType slotType,
                                     final Cancellable cancellable) {
        this.who = who;
        this.usedItem = usedItem;
        this.inventoryAction = inventoryAction;
        this.clickType = clickType;
        this.slot = slot;
        this.slotType = slotType;
        this.cancellable = cancellable;
    }

    @Override
    public Player who() {
        return who;
    }

    public ItemStack getUsedItem() {
        return usedItem;
    }

    public InventoryAction getInventoryAction() {
        return inventoryAction;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public int getSlot() {
        return slot;
    }

    public InventoryType.SlotType getSlotType() {
        return slotType;
    }

    public Cancellable getCancellable() {
        return cancellable;
    }

    public static InventoryClickProperties fromEvent(final InventoryClickEvent inventoryClickEvent) {
        return new InventoryClickProperties((Player) inventoryClickEvent.getWhoClicked(),
                inventoryClickEvent.getCurrentItem(), inventoryClickEvent.getAction(),
                inventoryClickEvent.getClick(), inventoryClickEvent.getSlot(), inventoryClickEvent.getSlotType(),
                inventoryClickEvent);
    }
}
