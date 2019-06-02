package io.github.etrayed.betrayedlib.inventoryapi.inventory;

import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryClickProperties;

/**
 * @author Etrayed
 */
public interface ClickableInventory {

    void onInventoryClick(final InventoryClickProperties inventoryClickProperties);
}
