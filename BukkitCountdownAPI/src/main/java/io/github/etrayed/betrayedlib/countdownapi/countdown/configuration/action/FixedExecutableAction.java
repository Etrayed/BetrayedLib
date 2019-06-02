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
package io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author Etrayed
 */
public class FixedExecutableAction implements ExecutableAction {

    private final int identifier;

    private final ExecutableActionType executableActionType;

    private final Object value;

    private final String jsonString;

    public FixedExecutableAction(final int identifier,
                                 final ExecutableActionType executableActionType, final Object value) {
        this.identifier = identifier;
        this.executableActionType = executableActionType;
        this.value = value;
        this.jsonString = "{\"text\": \"" + value + "\"}";
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    public ExecutableActionType getExecutableActionType() {
        return executableActionType;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void execute(final Plugin plugin) {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {

            @Override
            public void run() {
                if (executableActionType.equals(ExecutableActionType.MESSAGE)) {
                    Bukkit.broadcastMessage(String.valueOf(value));
                } else if (executableActionType.equals(ExecutableActionType.TITLE)) {
                    for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        sendTitle(onlinePlayer);
                    }
                } else if (executableActionType.equals(ExecutableActionType.SUBTITLE)) {
                    for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        sendSubTitle(onlinePlayer);
                    }
                } else if (executableActionType.equals(ExecutableActionType.ACTIONBAR)) {
                    for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        sendActionBar(onlinePlayer);
                    }
                } else if (executableActionType.equals(ExecutableActionType.SOUND)) {
                    for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.playSound(onlinePlayer.getLocation(), (Sound) value, 10F, 1F);
                    }
                }
            }
        });
    }

    private void sendTitle(final Player player) {
        final PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a(jsonString));

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }

    private void sendSubTitle(final Player player) {
        final PacketPlayOutTitle packetPlayOutSubTitle
                = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer
                .a(jsonString));

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutSubTitle);
    }

    private void sendActionBar(final Player player) {
        final PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                .a(jsonString), (byte) 2);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
