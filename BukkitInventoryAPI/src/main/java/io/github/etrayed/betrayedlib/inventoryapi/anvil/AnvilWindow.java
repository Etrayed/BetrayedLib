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
