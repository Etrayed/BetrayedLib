package io.github.etrayed.betrayedlib.inventoryapi.item;

import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Etrayed
 */
public class MetaEditableItemStack extends ItemStack {

    public static final Map<String, MetaEditableItemStack> ITEM_STACK_CACHE;

    public MetaEditableItemStack(final Material type) {
        super(type);
    }

    public MetaEditableItemStack(final Material type, final int amount) {
        super(type, amount);
    }

    public MetaEditableItemStack(final Material type, final short subId) {
        this(type, 0, subId);
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

    static {
        ITEM_STACK_CACHE = Maps.newConcurrentMap();

        ITEM_STACK_CACHE.put("deco_light", new MetaEditableItemStack(Material.STAINED_GLASS_PANE, (short) 7)
                .openItemMetaEditor().setDisplayName("").saveChanges());

        ITEM_STACK_CACHE.put("deco_dark", new MetaEditableItemStack(Material.STAINED_GLASS_PANE, (short) 8)
                .openItemMetaEditor().setDisplayName("").saveChanges());
    }
}