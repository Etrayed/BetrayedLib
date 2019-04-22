package de.etrayed.betrayedlib.commandapi.execute;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

/**
 * @author Etrayed
 */
class CommandMethodExecutor {

    private final CommandSender commandSender;
    private final Command command;
    private final String[] args;

    private int commandSenderIndex = -1, commandIndex = -1, argsIndex = -1;

    private Method method;

    CommandMethodExecutor(final CommandSender commandSender, final Command command, final String[] args) {
        this.commandSender = commandSender;
        this.command = command;
        this.args = args;
    }

    void prepareExecution(final Method method) {
        if(method.getParameterCount() > 3)
            throw new IllegalArgumentException("Method parameters of a @CommandMethod marked method cannot be more than 3!");

        for (int i = 0; i < method.getParameterCount(); i++) {
            final Class<?> parameterClass = method.getParameterTypes()[i];

            if(CommandSender.class.isAssignableFrom(parameterClass)) {
                commandSenderIndex = i;
            } else if(Command.class.isAssignableFrom(parameterClass)) {
                commandIndex = i;
            } else if(String[].class.isAssignableFrom(parameterClass)) {
                argsIndex = i;
            }
        }

        this.method = method;
    }

    void execute(final Object commandMethodHolderInstance) throws ReflectiveOperationException {
        final Object[] parameters = new Object[3];

        int realParameterCount = 0;

        if(commandSenderIndex != -1) {
            parameters[commandSenderIndex] = commandSender;

            realParameterCount++;
        }

        if(commandIndex != -1) {
            parameters[commandIndex] = command;

            realParameterCount++;
        }

        if(argsIndex != -1) {
            parameters[argsIndex] = args;

            realParameterCount++;
        }

        if(realParameterCount == 0) {
            method.invoke(commandMethodHolderInstance);
        } else if(realParameterCount == 1) {
            method.invoke(commandMethodHolderInstance, parameters[0]);
        } else if(realParameterCount == 2) {
            method.invoke(commandMethodHolderInstance, parameters[0], parameters[1]);
        } else if(realParameterCount == 3) {
            method.invoke(commandMethodHolderInstance, parameters[0], parameters[1], parameters[2]);
        }
    }
}
