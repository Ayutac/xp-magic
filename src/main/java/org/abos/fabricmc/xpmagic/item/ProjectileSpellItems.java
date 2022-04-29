package org.abos.fabricmc.xpmagic.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.abos.fabricmc.xpmagic.XpMagic;

import java.util.Locale;

public enum ProjectileSpellItems implements ItemConvertible {

    FIRE_BOLT;

    private Item item;

    ProjectileSpellItems() {
        item = Registry.register(Registry.ITEM, new Identifier(XpMagic.MOD_ID, name().toLowerCase(Locale.ROOT) + "_spell"),
                new ProjectileSpellItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1)));
    }

    @Override
    public Item asItem() {
        return item;
    }
}
