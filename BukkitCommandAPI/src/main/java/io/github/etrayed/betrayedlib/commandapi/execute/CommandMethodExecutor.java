/*
MIT License

Copyright (c) 2019 Miklas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package io.github.etrayed.betrayedlib.commandapi.execute;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

/**
 * @author Etrayed
 */
class CommandMethodExecutor {

    private final CommandSender commandSender;
    private final Command command;
    private final String commandLabel;
    private final String[] args;

    private int commandSenderIndex = -1, commandIndex = -1, labelIndex = -1, argsIndex = -1;

    private Method method;

    CommandMethodExecutor(final CommandSender commandSender, final Command command,
                          final String commandLabel, final String[] args) {
        this.commandSender = commandSender;
        this.command = command;
        this.commandLabel = commandLabel;
        this.args = args;
    }

    void prepareExecution(final Method method) {
        if(method.getParameterCount() > 4)
            throw new IllegalArgumentException("Method parameters of a @CommandMethod marked method cannot be more than 4!");

        for (int i = 0; i < method.getParameterCount(); i++) {
            final Class<?> parameterClass = method.getParameterTypes()[i];

            if(CommandSender.class.isAssignableFrom(parameterClass)) {
                commandSenderIndex = i;
            } else if(Command.class.isAssignableFrom(parameterClass)) {
                commandIndex = i;
            } else if(String[].class.isAssignableFrom(parameterClass)) {
                argsIndex = i;
            } else if(String.class.isAssignableFrom(parameterClass)) {
                labelIndex = i;
            }
        }

        this.method = method;
        this.method.setAccessible(true);
    }

    void execute(final Object commandMethodHolderInstance) throws ReflectiveOperationException {
        final Object[] parameters = new Object[4];

        int realParameterCount = 0;

        if(commandSenderIndex != -1) {
            parameters[commandSenderIndex] = commandSender;

            realParameterCount++;
        }

        if(commandIndex != -1) {
            parameters[commandIndex] = command;

            realParameterCount++;
        }

        if(labelIndex != -1) {
            parameters[labelIndex] = commandLabel;

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
        } else if(realParameterCount == 4) {
            method.invoke(commandMethodHolderInstance, parameters[0], parameters[1], parameters[2], parameters[3]);
        }
    }
}
