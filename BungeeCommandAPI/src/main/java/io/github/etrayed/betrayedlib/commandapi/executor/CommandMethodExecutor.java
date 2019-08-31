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
package io.github.etrayed.betrayedlib.commandapi.executor;

import net.md_5.bungee.api.CommandSender;

import java.lang.reflect.Method;

/**
 * @author Etrayed
 */
class CommandMethodExecutor {

    private final CommandSender commandSender;
    private final String[] args;

    private int commandSenderIndex = -1, argsIndex = -1;

    private Method method;

    CommandMethodExecutor(CommandSender commandSender, String[] args) {
        this.commandSender = commandSender;
        this.args = args;
    }

    void prepareExecution(Method method) {
        if(method.getParameterCount() > 2)
            throw new IllegalArgumentException("Method parameters of a @CommandMethod marked method cannot be more than 2!");

        for (int i = 0; i < method.getParameterCount(); i++) {
            Class<?> parameterClass = method.getParameterTypes()[i];

            if(CommandSender.class.isAssignableFrom(parameterClass)) {
                commandSenderIndex = i;
            } else if(String[].class.isAssignableFrom(parameterClass)) {
                argsIndex = i;
            }
        }

        this.method = method;
        this.method.setAccessible(true);
    }

    void execute(Object commandMethodHolderInstance) throws ReflectiveOperationException {
        Object[] parameters = new Object[4];

        int realParameterCount = 0;

        if(commandSenderIndex != -1) {
            parameters[commandSenderIndex] = commandSender;

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
        }
    }
}
