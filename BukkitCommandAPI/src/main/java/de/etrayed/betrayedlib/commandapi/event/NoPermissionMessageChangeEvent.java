package de.etrayed.betrayedlib.commandapi.event;

import org.bukkit.event.HandlerList;

/**
 * @author Etrayed
 */
public final class NoPermissionMessageChangeEvent extends MessageChangeEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public NoPermissionMessageChangeEvent(final String message, final Class<?> callerClass) {
        super(message, callerClass);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
