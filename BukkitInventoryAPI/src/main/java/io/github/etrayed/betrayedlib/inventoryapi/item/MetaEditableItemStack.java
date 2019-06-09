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
package io.github.etrayed.betrayedlib.inventoryapi.item;

import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author Etrayed
 */
public class MetaEditableItemStack extends ItemStack implements Cloneable {

    public static final Map<String, MetaEditableItemStack> ITEM_STACK_CACHE;

    public MetaEditableItemStack(final Material type) {
        super(type);
    }

    public MetaEditableItemStack(final Material type, final int amount) {
        super(type, amount);
    }

    public MetaEditableItemStack(final Material type, final short subId) {
        this(type, 1, subId);
    }

    public MetaEditableItemStack(final Material type, final int amount, final short subId) {
        super(type, amount, subId);
    }

    public MetaEditableItemStack(final ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public ItemMetaEditor openItemMetaEditor() {
        return new ItemMetaEditor(this);
    }

    @Override
    public MetaEditableItemStack clone() {
        return new MetaEditableItemStack(this);
    }

    static {
        ITEM_STACK_CACHE = Maps.newConcurrentMap();

        ITEM_STACK_CACHE.put("deco_light", new MetaEditableItemStack(Material.STAINED_GLASS_PANE, (short) 7)
                .openItemMetaEditor().setDisplayName(" ").saveChanges());

        ITEM_STACK_CACHE.put("deco_dark", new MetaEditableItemStack(Material.STAINED_GLASS_PANE, (short) 15)
                .openItemMetaEditor().setDisplayName(" ").saveChanges());
    }
}
