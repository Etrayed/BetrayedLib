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
