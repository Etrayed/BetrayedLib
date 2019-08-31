package io.github.etrayed.betrayedlib.commandapi;

import io.github.etrayed.betrayedlib.commandapi.annotation.CommandMethod;
import io.github.etrayed.betrayedlib.commandapi.annotation.CommandMethodResolver;
import io.github.etrayed.betrayedlib.commandapi.event.NoPermissionMessageChangeEvent;
import io.github.etrayed.betrayedlib.commandapi.executor.RemoteCommandExecutor;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Etrayed
 */
public class BungeeCommandAPI {

    private static final Constructor<?> REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR;

    private static String noPermissionMessage = "§cI'm sorry, but you do not have permission to perform this command. " +
            "Please contact the server administrators if you believe that this is in error.";

    static {
        REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR = RemoteCommandExecutor.class.getDeclaredConstructors()[0];

        REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR.setAccessible(true);
    }

    public static void registerCommandHolder(Plugin plugin, Object commandHolderInstance) {
        CommandMethodResolver commandMethodResolver = new CommandMethodResolver();

        commandMethodResolver.prepare(Objects.requireNonNull(commandHolderInstance,
                "commandInstance cannot be null!"));

        Map<Method, CommandMethod> commandMethods = commandMethodResolver.resolveCommandMethods();

        commandMethods.forEach(new BiConsumer<Method, CommandMethod>() {

            @Override
            public void accept(Method method, CommandMethod commandMethod) {
                try {
                    REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR.newInstance(commandHolderInstance,
                            Objects.requireNonNull(plugin, "javaPlugin cannot be null!"),
                            method, commandMethod);
                } catch (ReflectiveOperationException e) {
                    System.err.println("WARNING: An unexpected error occurred! Please report it!");
                    e.printStackTrace();
                }
            }
        });
    }

    public static String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public static void setNoPermissionMessage(String noPermissionMessage) {
        NoPermissionMessageChangeEvent noPermissionMessageChangeEvent
                = new NoPermissionMessageChangeEvent(noPermissionMessage, getCallerClass());

        ProxyServer.getInstance().getPluginManager().callEvent(noPermissionMessageChangeEvent);

        if(noPermissionMessageChangeEvent.isCancelled()) {
            return;
        }

        BungeeCommandAPI.noPermissionMessage = noPermissionMessageChangeEvent.getMessage();
    }

    private static Class<?> getCallerClass() {
        try {
            return Class.forName(new Exception().getStackTrace()[2].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return BungeeCommandAPI.class;
    }
}
