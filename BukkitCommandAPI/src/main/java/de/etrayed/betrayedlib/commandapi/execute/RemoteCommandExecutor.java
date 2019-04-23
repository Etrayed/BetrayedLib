package de.etrayed.betrayedlib.commandapi.execute;

import de.etrayed.betrayedlib.commandapi.CommandAPI;
import de.etrayed.betrayedlib.commandapi.annotation.CommandMethod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * @author Etrayed
 */
public class RemoteCommandExecutor implements CommandExecutor {

    private final Object commandMethodHolderInstance;
    private final Method method;
    private final CommandMethod commandMethod;

    private RemoteCommandExecutor(final Object commandMethodHolderInstance, final JavaPlugin javaPlugin,
                                  final Method method, final CommandMethod commandMethod) {
        this.commandMethodHolderInstance = commandMethodHolderInstance;
        this.method = method;
        this.commandMethod = commandMethod;

        javaPlugin.getCommand(commandMethod.name()).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandMethod.permission().isEmpty() && !commandSender.hasPermission(commandMethod.permission())) {
            if(commandMethod.useDefaultNoPermissionMessage()) {
                commandSender.sendMessage(CommandAPI.getNoPermissionMessage());

                return false;
            } else {
                if(!commandMethod.customNoPermissionMessage().isEmpty()) {
                    commandSender.sendMessage(commandMethod.customNoPermissionMessage());

                    return false;
                }
            }
        }

        final CommandMethodExecutor commandMethodExecutor
                = new CommandMethodExecutor(commandSender, command, s, strings);

        commandMethodExecutor.prepareExecution(method);

        try {
            commandMethodExecutor.execute(commandMethodHolderInstance);
        } catch (ReflectiveOperationException e) {
            System.err.println("Error while executing command \"" + commandMethod.name() + "\" with the properties \""
                    + commandMethod + "\":");
            e.printStackTrace();
        }
        return false;
    }
}
