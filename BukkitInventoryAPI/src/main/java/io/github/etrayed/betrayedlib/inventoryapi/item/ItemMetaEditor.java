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

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class ItemMetaEditor {

    public static final LoreFormat EMPTY_LORE_FORMAT = new LoreFormat((char) -1, ""),
            STAR_LORE_FORMAT = new LoreFormat('\n', "§8× §7");

    private final MetaEditableItemStack metaEditableItemStack;
    private ItemMeta itemMeta;

    public ItemMetaEditor(final MetaEditableItemStack metaEditableItemStack) {
        this.metaEditableItemStack = metaEditableItemStack;
        this.itemMeta = metaEditableItemStack.getItemMeta();
    }

    public ItemMetaEditor copyOf(final ItemMeta itemMeta) {
        this.itemMeta = itemMeta;

        return this;
    }

    public ItemMetaEditor setDisplayName(final String displayName) {
        this.itemMeta.setDisplayName(displayName);

        return this;
    }

    public ItemMetaEditor setLore(final LoreFormat loreFormat, final String lore) {
        final List<String> formattedLines = Lists.newArrayList();
        final String[] lines = lore.split(String.valueOf(loreFormat.lineSeparator));

        for (final String line : lines) {
            formattedLines.add(loreFormat.linePrefix + line);
        }

        this.itemMeta.setLore(formattedLines);

        return this;
    }

    public ItemMetaEditor addEnchantment(final Enchantment enchantment, final int level) {
        this.itemMeta.addEnchant(enchantment, level, true);

        return this;
    }

    public ItemMetaEditor addItemFlag(final ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(itemFlag);

        return this;
    }

    public ItemMetaEditor addUnbreakability() {
        this.itemMeta.spigot().setUnbreakable(true);

        return this;
    }

    public ItemMetaEditor withAddition(final ItemMetaAddition itemMetaAddition) {
        itemMetaAddition.accept(this.itemMeta);

        return this;
    }

    public MetaEditableItemStack saveChanges() {
        this.metaEditableItemStack.setItemMeta(this.itemMeta);

        return this.metaEditableItemStack;
    }

    public static class LoreFormat {

        private final char lineSeparator;

        private final String linePrefix;

        public LoreFormat(final char lineSeparator, final String linePrefix) {
            this.lineSeparator = lineSeparator;
            this.linePrefix = linePrefix;
        }

        public char getLineSeparator() {
            return lineSeparator;
        }

        public String getLinePrefix() {
            return linePrefix;
        }
    }

    public static abstract class ItemMetaAddition implements Consumer<ItemMeta> {

        @Override
        public abstract void accept(final ItemMeta itemMeta);

        @Override
        public final Consumer<ItemMeta> andThen(final Consumer<? super ItemMeta> after) {
            return this;
        }
    }

    public static class SkullItemMetaAddition extends ItemMetaAddition {

        private static final Field GAME_PROFILE_FIELD;

        private final GameProfile gameProfile;

        public SkullItemMetaAddition(final String name) {
            this(new GameProfile(null, name));
        }

        public SkullItemMetaAddition(final GameProfile gameProfile) {
            this.gameProfile = new GameProfile(null, gameProfile.getName());
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            try {
                GAME_PROFILE_FIELD.set(itemMeta, gameProfile);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        static {
            try {
                GAME_PROFILE_FIELD = Class.forName("org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaSkull")
                        .getDeclaredField("profile");

                GAME_PROFILE_FIELD.setAccessible(true);
            } catch (NoSuchFieldException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class LeatherArmorItemMetaAddition extends ItemMetaAddition {

        private final Color color;

        public LeatherArmorItemMetaAddition(final Color color) {
            this.color = color;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(color);
        }
    }

    public static class BannerItemMetaAddition extends ItemMetaAddition {

        private final DyeColor baseColor;

        private final List<Pattern> patterns;

        public BannerItemMetaAddition(final DyeColor baseColor, final List<Pattern> patterns) {
            this.baseColor = baseColor;
            this.patterns = patterns;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((BannerMeta) itemMeta).setBaseColor(baseColor);
            ((BannerMeta) itemMeta).setPatterns(patterns);
        }
    }

    public static class BookItemMetaAddition extends ItemMetaAddition {

        private final List<String> pages;

        private final String title, author;

        public BookItemMetaAddition(final List<String> pages, final String title, final String author) {
            this.pages = pages;
            this.title = title;
            this.author = author;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((BookMeta) itemMeta).setTitle(title);
            ((BookMeta) itemMeta).setAuthor(author);
            ((BookMeta) itemMeta).setPages(pages);
        }
    }

    public static class MapItemMetaAddition extends ItemMetaAddition {

        private final boolean scaling;

        public MapItemMetaAddition(final boolean scaling) {
            this.scaling = scaling;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((MapMeta) itemMeta).setScaling(scaling);
        }
    }

    public static class PotionItemMetaAddition extends ItemMetaAddition {

        private final PotionEffectType mainEffect;

        private final List<PotionEffect> customEffects;

        private final boolean overwriteOld;

        public PotionItemMetaAddition(final PotionEffectType mainEffect,
                                      final List<PotionEffect> customEffects, final boolean overwriteOld) {
            this.mainEffect = mainEffect;
            this.customEffects = customEffects;
            this.overwriteOld = overwriteOld;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            final PotionMeta potionMeta = (PotionMeta) itemMeta;

            potionMeta.setMainEffect(mainEffect);

            for (final PotionEffect potionEffect : customEffects) {
                potionMeta.addCustomEffect(potionEffect, overwriteOld);
            }
        }
    }

    public static class EnchantmentStorageItemMetaAddition extends ItemMetaAddition {

        private final Map<Enchantment, Integer> storedEnchantments;

        private final boolean ignoreRestrictions;

        public EnchantmentStorageItemMetaAddition(final Map<Enchantment, Integer> storedEnchantments,
                                                  final boolean ignoreRestrictions) {
            this.storedEnchantments = storedEnchantments;
            this.ignoreRestrictions = ignoreRestrictions;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            final EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemMeta;

            storedEnchantments.forEach(new BiConsumer<Enchantment, Integer>() {

                @Override
                public void accept(final Enchantment enchantment, final Integer integer) {
                    enchantmentStorageMeta.addStoredEnchant(enchantment, integer, ignoreRestrictions);
                }
            });
        }
    }

    public static class BlockStateItemMetaAddition extends ItemMetaAddition {

        private final BlockState blockState;

        public BlockStateItemMetaAddition(final BlockState blockState) {
            this.blockState = blockState;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((BlockStateMeta) itemMeta).setBlockState(blockState);
        }
    }

    public static class FireworkEffectItemMetaAddition extends ItemMetaAddition {

        private final FireworkEffect fireworkEffect;

        public FireworkEffectItemMetaAddition(final FireworkEffect fireworkEffect) {
            this.fireworkEffect = fireworkEffect;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            ((FireworkEffectMeta) itemMeta).setEffect(fireworkEffect);
        }
    }

    public static class FireworkItemMetaAddition extends ItemMetaAddition {

        private final List<FireworkEffect> fireworkEffects;

        private final int power;

        public FireworkItemMetaAddition(final List<FireworkEffect> fireworkEffects, final int power) {
            this.fireworkEffects = fireworkEffects;
            this.power = power;
        }

        @Override
        public void accept(final ItemMeta itemMeta) {
            final FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;

            for (final FireworkEffect fireworkEffect : fireworkEffects) {
                fireworkMeta.addEffects(fireworkEffects);
            }

            fireworkMeta.setPower(power);
        }
    }
}