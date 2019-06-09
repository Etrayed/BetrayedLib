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
package io.github.etrayed.betrayedlib.inventoryapi.listener;

import io.github.etrayed.betrayedlib.inventoryapi.anvil.AnvilWindow;
import io.github.etrayed.betrayedlib.inventoryapi.anvil.factory.AnvilWindowImpl;
import io.github.etrayed.betrayedlib.inventoryapi.anvil.util.AnvilWindowClickDispatcher;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.ClickableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.CloseableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.InventoryConverter;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryClickProperties;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryCloseProperties;

import net.minecraft.server.v1_8_R3.Container;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class InventoryAPIListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        final Inventory convertedInventory = InventoryConverter.convert(inventoryClickEvent.getClickedInventory());

        if(convertedInventory instanceof ClickableInventory) {
            ((ClickableInventory) convertedInventory)
                    .onInventoryClick(InventoryClickProperties.fromEvent(inventoryClickEvent));
        } else {
            if(inventoryClickEvent.getView() == null) {
                return;
            }

            final Container container = ((CraftInventoryView) inventoryClickEvent.getView()).getHandle();

            if(!(container instanceof AnvilWindowImpl.AlwaysReachableAnvilContainer)) {
                return;
            }

            final AnvilWindow anvilWindow = ((AnvilWindowImpl.AlwaysReachableAnvilContainer) container).getAnvilWindow();
            final AnvilWindowClickDispatcher anvilWindowClickDispatcher
                    = ((AnvilWindowImpl) anvilWindow).onAnvilWindowClick;

            if(anvilWindowClickDispatcher != null) {
                anvilWindowClickDispatcher.dispatchClick(anvilWindow,
                        AnvilWindow.SlotType.getTypeByRawSlot(inventoryClickEvent.getSlot()), inventoryClickEvent);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
        final Inventory convertedInventory = InventoryConverter.convert(inventoryCloseEvent.getInventory());

        if(convertedInventory instanceof CloseableInventory) {
            ((CloseableInventory) convertedInventory)
                    .onInventoryClose(InventoryCloseProperties.fromEvent(inventoryCloseEvent));
        } else {
            if(inventoryCloseEvent.getView() == null) {
                return;
            }

            final Container container = ((CraftInventoryView) inventoryCloseEvent.getView()).getHandle();

            if(!(container instanceof AnvilWindowImpl.AlwaysReachableAnvilContainer)) {
                return;
            }

            final AnvilWindow anvilWindow = ((AnvilWindowImpl.AlwaysReachableAnvilContainer) container).getAnvilWindow();
            final Consumer<AnvilWindow> onAnvilWindowClose = ((AnvilWindowImpl) anvilWindow).onAnvilWindowClose;

            if(onAnvilWindowClose != null) {
                onAnvilWindowClose.accept(anvilWindow);
            }

            inventoryCloseEvent.getInventory().clear();
        }
    }
}
