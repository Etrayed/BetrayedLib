package io.github.etrayed.betrayedlib.inventoryapi.inventory.properties;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author Etrayed
 */
public class InventoryCloseProperties implements InventoryProperties {

    private final Player who;

    private InventoryCloseProperties(final Player who) {
        this.who = who;
    }

    @Override
    public Player who() {
        return who;
    }

    public static InventoryCloseProperties fromEvent(final InventoryCloseEvent inventoryCloseEvent) {
        return new InventoryCloseProperties((Player) inventoryCloseEvent.getPlayer());
    }
}
