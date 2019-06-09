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
