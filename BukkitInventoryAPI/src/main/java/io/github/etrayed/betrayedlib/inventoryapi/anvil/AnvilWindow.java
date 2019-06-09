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
package io.github.etrayed.betrayedlib.inventoryapi.anvil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author Etrayed
 */
public interface AnvilWindow extends AutoCloseable {

    Player getOwner();

    ItemStack getItem(final SlotType slotType);

    ItemStack[] getItems();

    void setItem(final SlotType slotType, final ItemStack itemStack);

    void setItems(final Map<SlotType, ItemStack> slotTypeItemStackMap);

    void openWindow();

    boolean isOpen();

    String getRenameText();

    void setRenameText(final String renameText);

    @Override
    void close();

    enum SlotType {

        INPUT_LEFT,
        INPUT_RIGHT,
        OUTPUT;

        public int getRawSlot() {
            return ordinal();
        }

        public static SlotType getTypeByRawSlot(final int rawSlot) {
            return values()[rawSlot];
        }
    }
}
