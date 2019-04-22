package de.etrayed.betrayedlib.commandapi;

import de.etrayed.betrayedlib.commandapi.annotation.CommandMethod;
import de.etrayed.betrayedlib.commandapi.annotation.CommandMethodResolver;
import de.etrayed.betrayedlib.commandapi.event.NoPermissionMessageChangeEvent;
import de.etrayed.betrayedlib.commandapi.execute.RemoteCommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Etrayed
 */
public class CommandAPI {

    private static final Constructor<?> REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR;

    private static String noPermissionMessage = "§cI'm sorry, but you do not have permission to perform this command. " +
            "Please contact the server administrators if you believe that this is in error.";

    public static void registerCommandHolder(final JavaPlugin javaPlugin, final Object commandHolderInstance) {
        final CommandMethodResolver commandMethodResolver = new CommandMethodResolver();

        commandMethodResolver.prepare(Objects.requireNonNull(commandHolderInstance,
                "commandInstance cannot be null!"));

        final Map<Method, CommandMethod> commandMethods = commandMethodResolver.resolveCommandMethods();

        commandMethods.forEach(new BiConsumer<Method, CommandMethod>() {

            @Override
            public void accept(final Method method, final CommandMethod commandMethod) {
                try {
                    REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR.newInstance(commandHolderInstance,
                            Objects.requireNonNull(javaPlugin, "javaPlugin cannot be null!"),
                            method, commandMethod);
                } catch (ReflectiveOperationException e) {
                    System.err.println("An unexpected error occurred! Please report it!");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setNoPermissionMessage(final String noPermissionMessage) {
        final NoPermissionMessageChangeEvent noPermissionMessageChangeEvent
                = new NoPermissionMessageChangeEvent(noPermissionMessage, Reflection.getCallerClass());

        Bukkit.getPluginManager().callEvent(noPermissionMessageChangeEvent);

        if(noPermissionMessageChangeEvent.isCancelled())
            return;

        CommandAPI.noPermissionMessage = noPermissionMessageChangeEvent.getMessage();
    }

    public static String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    static {
        REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR = RemoteCommandExecutor.class.getDeclaredConstructors()[0];

        REMOTE_COMMAND_EXECUTOR_CONSTRUCTOR.setAccessible(true);
    }
}
