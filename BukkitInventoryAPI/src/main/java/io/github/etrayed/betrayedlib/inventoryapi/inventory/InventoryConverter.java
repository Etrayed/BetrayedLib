package io.github.etrayed.betrayedlib.inventoryapi.inventory;

import com.google.common.collect.Lists;

import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Etrayed
 */
public class InventoryConverter {

    private static final List<Inventory> REGISTERED_INVENTORIES;

    public static void register(final Inventory inventory) {
        final List<Class<?>> implementations = Arrays.asList(inventory.getClass().getInterfaces());

        if(!implementations.contains(ClickableInventory.class) && !implementations.contains(CloseableInventory.class)) {
            throw new IllegalArgumentException("Inventory needs to implement at least one of \""
                    + ClickableInventory.class.getCanonicalName() + "\" or \""
                    + CloseableInventory.class.getCanonicalName() + "\".");
        }

        REGISTERED_INVENTORIES.add(inventory);
    }

    public static Inventory convert(final Inventory inventory) {
        for (final Inventory registeredInventory : REGISTERED_INVENTORIES) {
            if(registeredInventory.equals(inventory)) {
                return registeredInventory;
            }
        }

        return null;
    }

    static {
        REGISTERED_INVENTORIES = Lists.newArrayList();
    }
}
