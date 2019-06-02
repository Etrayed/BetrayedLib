package io.github.etrayed.betrayedlib.inventoryapi.inventory;

import io.github.etrayed.betrayedlib.inventoryapi.inventory.properties.InventoryCloseProperties;

/**
 * @author Etrayed
 */
public interface CloseableInventory {

    void onInventoryClose(final InventoryCloseProperties inventoryCloseProperties);
}
