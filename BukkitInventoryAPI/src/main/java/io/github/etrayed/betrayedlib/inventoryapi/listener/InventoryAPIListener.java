package io.github.etrayed.betrayedlib.inventoryapi.listener;

import io.github.etrayed.betrayedlib.inventoryapi.inventory.ClickableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.CloseableInventory;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryClickProperties;
import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryCloseProperties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author Etrayed
 */
public class InventoryAPIListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if(inventoryClickEvent.getClickedInventory() instanceof ClickableInventory) {
            ((ClickableInventory) inventoryClickEvent.getClickedInventory())
                    .onInventoryClick(InventoryClickProperties.fromEvent(inventoryClickEvent));
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
        if(inventoryCloseEvent.getInventory() instanceof CloseableInventory) {
            ((CloseableInventory) inventoryCloseEvent.getInventory())
                    .onInventoryClose(InventoryCloseProperties.fromEvent(inventoryCloseEvent));
        }
    }
}
