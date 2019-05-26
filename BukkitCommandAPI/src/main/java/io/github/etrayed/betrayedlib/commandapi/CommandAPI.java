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
package io.github.etrayed.betrayedlib.commandapi;

import io.github.etrayed.betrayedlib.commandapi.annotation.CommandMethod;
import io.github.etrayed.betrayedlib.commandapi.annotation.CommandMethodResolver;
import io.github.etrayed.betrayedlib.commandapi.event.NoPermissionMessageChangeEvent;
import io.github.etrayed.betrayedlib.commandapi.execute.RemoteCommandExecutor;

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
                    System.err.println("WARNING: An unexpected error occurred! Please report it!");
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
