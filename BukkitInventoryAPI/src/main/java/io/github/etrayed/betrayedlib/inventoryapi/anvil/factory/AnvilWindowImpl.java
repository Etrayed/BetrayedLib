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
package io.github.etrayed.betrayedlib.inventoryapi.anvil.factory;

import com.google.common.collect.Maps;

import io.github.etrayed.betrayedlib.inventoryapi.anvil.AnvilWindow;
import io.github.etrayed.betrayedlib.inventoryapi.anvil.util.AnvilWindowClickDispatcher;

import net.minecraft.server.v1_8_R3.*;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class AnvilWindowImpl implements AnvilWindow {

    private final AlwaysReachableAnvilContainer alwaysReachableAnvilContainer;

    private final Map<SlotType, ItemStack> items;

    private boolean open;

    public final AnvilWindowClickDispatcher onAnvilWindowClick;

    public final Consumer<AnvilWindow> onAnvilWindowClose;

    AnvilWindowImpl(final Player player, final AnvilWindowClickDispatcher onAnvilWindowClick,
                    final Consumer<AnvilWindow> onAnvilWindowClose) {
        this.alwaysReachableAnvilContainer = new AlwaysReachableAnvilContainer(this,
                ((CraftPlayer) player).getHandle());

        this.items = Maps.newConcurrentMap();

        this.onAnvilWindowClick = onAnvilWindowClick;

        this.onAnvilWindowClose = onAnvilWindowClose;
    }

    @Override
    public Player getOwner() {
        return (Player) alwaysReachableAnvilContainer.getBukkitView().getPlayer();
    }

    @Override
    public ItemStack getItem(final SlotType slotType) {
        return items.get(slotType);
    }

    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    @Override
    public ItemStack[] getItems() {
        return items.values().toArray(new ItemStack[items.size()]);
    }

    @Override
    public void setItem(final SlotType slotType, final ItemStack itemStack) {
        items.remove(slotType);
        items.put(slotType, itemStack);

        if(isOpen()) {
            alwaysReachableAnvilContainer.setItem(slotType.getRawSlot(), CraftItemStack.asNMSCopy(itemStack));
        }
    }

    @Override
    public void setItems(final Map<SlotType, ItemStack> slotTypeItemStackMap) {
        slotTypeItemStackMap.forEach(new BiConsumer<SlotType, ItemStack>() {

            @Override
            public void accept(final SlotType slotType, final ItemStack itemStack) {
                setItem(slotType, itemStack);
            }
        });
    }

    @Override
    public void openWindow() {
        if(isOpen()) {
            return;
        }

        items.forEach(new BiConsumer<SlotType, ItemStack>() {

            @Override
            public void accept(final SlotType slotType, final ItemStack itemStack) {
                alwaysReachableAnvilContainer.setItem(slotType.getRawSlot(), CraftItemStack.asNMSCopy(itemStack));
            }
        });

        final EntityPlayer entityPlayer
                = ((CraftPlayer) this.getOwner()).getHandle();

        final int containerId = entityPlayer.nextContainerCounter();

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil",
                new ChatMessage("Repairing"), 0));

        entityPlayer.activeContainer = alwaysReachableAnvilContainer;

        alwaysReachableAnvilContainer.windowId = containerId;
        alwaysReachableAnvilContainer.addSlotListener(entityPlayer);

        open = true;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void close() {
        if(!isOpen()) {
            return;
        }

        open = false;

        alwaysReachableAnvilContainer.getBukkitView().close();
    }

    @Override
    public String getRenameText() {
        return alwaysReachableAnvilContainer.getRenameText();
    }

    @Override
    public void setRenameText(final String renameText) {
        if(!items.containsKey(SlotType.INPUT_LEFT)) {
            return;
        }

        final ItemMeta itemMeta
                = alwaysReachableAnvilContainer.getBukkitView().getTopInventory().getItem(0).getItemMeta();

        itemMeta.setDisplayName(renameText);

        alwaysReachableAnvilContainer.getBukkitView().getTopInventory().getItem(0).setItemMeta(itemMeta);

        alwaysReachableAnvilContainer.a(renameText);
    }

    public AlwaysReachableAnvilContainer getContainer() {
        return alwaysReachableAnvilContainer;
    }

    public static final class AlwaysReachableAnvilContainer extends ContainerAnvil {

        private static final Field CONTAINER_ANVIL_L_FIELD;

        private final AnvilWindow anvilWindow;

        private AlwaysReachableAnvilContainer(final AnvilWindow anvilWindow, final EntityPlayer entityPlayer) {
            super(entityPlayer.inventory, entityPlayer.world, new BlockPosition(0, 0, 0), entityPlayer);

            this.anvilWindow = anvilWindow;
        }

        @Override
        public boolean a(final EntityHuman entityhuman) {
            return true;
        }

        private String getRenameText() {
            try {
                return CONTAINER_ANVIL_L_FIELD.get(this).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return "N/A";
        }

        public AnvilWindow getAnvilWindow() {
            return anvilWindow;
        }

        static {
            try {
                CONTAINER_ANVIL_L_FIELD = ContainerAnvil.class.getDeclaredField("l");

                CONTAINER_ANVIL_L_FIELD.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
