/*
MIT License

Copyright (c) 2019 Miklas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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
