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

import com.google.common.base.Preconditions;

import io.github.etrayed.betrayedlib.commandapi.BungeeCommandAPI;
import io.github.etrayed.betrayedlib.commandapi.annotation.CommandMethod;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.lang.reflect.Method;

/**
 * @author Etrayed
 */
public class RemoteCommandExecutor extends Command {

    private final Object commandMethodHolderInstance;
    private final Method method;
    private final CommandMethod commandMethod;

    private RemoteCommandExecutor(Object commandMethodHolderInstance, Plugin plugin,
                                  Method method, CommandMethod commandMethod) {
        super(commandMethod.name());

        this.commandMethodHolderInstance = commandMethodHolderInstance;
        this.method = method;
        this.commandMethod = commandMethod;

        Preconditions.checkArgument(ProxyServer.getInstance().getPluginManager().isExecutableCommand(commandMethod.name(), null),
                "Command \"" + commandMethod.name() + "\" is not registered in the plugin.yml of \""
                        + plugin.getDescription().getName() + "\".");

        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandMethod.blockConsoleCommandSenders() && !(commandSender instanceof ProxiedPlayer)) {
            return;
        }

        if(!commandMethod.permission().isEmpty() && !commandSender.hasPermission(commandMethod.permission())) {
            if(commandMethod.useDefaultNoPermissionMessage()) {
                commandSender.sendMessage(TextComponent.fromLegacyText(BungeeCommandAPI.getNoPermissionMessage()));

                return;
            } else {
                if(!commandMethod.customNoPermissionMessage().isEmpty()) {
                    commandSender.sendMessage(TextComponent.fromLegacyText(commandMethod.customNoPermissionMessage()));

                    return;
                }
            }
        }

        CommandMethodExecutor commandMethodExecutor = new CommandMethodExecutor(commandSender, strings);

        commandMethodExecutor.prepareExecution(method);

        try {
            commandMethodExecutor.execute(commandMethodHolderInstance);
        } catch (ReflectiveOperationException e) {
            System.err.println("Error while executing command \"" + commandMethod.name() + "\" with the properties \""
                    + commandMethod + "\":");
            e.printStackTrace();
        }
    }
}
