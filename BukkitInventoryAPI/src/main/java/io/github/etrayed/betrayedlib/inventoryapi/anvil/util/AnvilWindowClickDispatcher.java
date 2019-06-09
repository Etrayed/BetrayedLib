package io.github.etrayed.betrayedlib.inventoryapi.anvil.util;

import io.github.etrayed.betrayedlib.inventoryapi.anvil.AnvilWindow;
import org.bukkit.event.Cancellable;

/**
 * @author Etrayed
 */
@FunctionalInterface
public interface AnvilWindowClickDispatcher {

    void dispatchClick(final AnvilWindow anvilWindow, final AnvilWindow.SlotType slotType, final Cancellable cancellable);
}
