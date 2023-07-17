package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.data.tag.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class CommonItemTagsProvider
{
    public static void accept(Function<TagKey<Item>, TagBuilder<Item>> builder)
    {
        TagBuilder<Item> general = builder.apply(ModTags.Items.GENERAL);
        general.add(ModBlocks.TABLE_OAK.get().asItem());
        general.add(ModBlocks.TABLE_SPRUCE.get().asItem());
        general.add(ModBlocks.TABLE_BIRCH.get().asItem());
        general.add(ModBlocks.TABLE_JUNGLE.get().asItem());
        general.add(ModBlocks.TABLE_ACACIA.get().asItem());
        general.add(ModBlocks.TABLE_DARK_OAK.get().asItem());
        general.add(ModBlocks.TABLE_CRIMSON.get().asItem());
        general.add(ModBlocks.TABLE_WARPED.get().asItem());
        general.add(ModBlocks.TABLE_MANGROVE.get().asItem());
        general.add(ModBlocks.TABLE_CHERRY.get().asItem());
    }
}
