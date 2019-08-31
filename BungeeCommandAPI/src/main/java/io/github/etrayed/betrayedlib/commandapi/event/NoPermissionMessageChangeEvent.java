package io.github.etrayed.betrayedlib.commandapi.event;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author Etrayed
 */
public class NoPermissionMessageChangeEvent extends Event implements Cancellable {

    private boolean cancelled;

    private final String message;
    private final Class<?> callerClass;

    public NoPermissionMessageChangeEvent(String message, Class<?> callerClass) {
        this.message = message;
        this.callerClass = callerClass;
    }

    public String getMessage() {
        return message;
    }

    public Class<?> getCallerClass() {
        return callerClass;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
