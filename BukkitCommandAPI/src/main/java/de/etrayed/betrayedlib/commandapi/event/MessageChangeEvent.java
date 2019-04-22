package de.etrayed.betrayedlib.commandapi.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Etrayed
 */
abstract class MessageChangeEvent extends Event implements Cancellable {

    private final String message;
    private final Class<?> callerClass;

    private boolean cancelled;

    MessageChangeEvent(final String message, final Class<?> callerClass) {
        super(false);

        this.message = message;
        this.callerClass = callerClass;
    }

    public final String getMessage() {
        return message;
    }

    public final Class<?> getCallerClass() {
        return callerClass;
    }

    @Override
    public final boolean isCancelled() {
        return cancelled;
    }

    @Override
    public final void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public abstract HandlerList getHandlers();
}
