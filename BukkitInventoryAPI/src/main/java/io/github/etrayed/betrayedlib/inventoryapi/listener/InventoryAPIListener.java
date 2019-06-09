package io.github.etrayed.betrayedlib.inventoryapi.listener;

import io.github.etrayed.betrayedlib.inventoryapi.inventory.ClickableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.CloseableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.InventoryConverter;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryClickProperties;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryCloseProperties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

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
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
        final Inventory convertedInventory = InventoryConverter.convert(inventoryCloseEvent.getInventory());

        if(convertedInventory instanceof CloseableInventory) {
            ((CloseableInventory) convertedInventory)
                    .onInventoryClose(InventoryCloseProperties.fromEvent(inventoryCloseEvent));
        }
    }
}
