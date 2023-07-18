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
        // TODO throw exception if items are not in a category tag
        TagBuilder<Item> general = builder.apply(ModTags.Items.GENERAL);
        general.add(ModBlocks.TABLE_OAK.get().asItem());
        general.add(ModBlocks.TABLE_SPRUCE.get().asItem());
        general.add(ModBlocks.TABLE_BIRCH.get().asItem());
        general.add(ModBlocks.TABLE_JUNGLE.get().asItem());
        general.add(ModBlocks.TABLE_ACACIA.get().asItem());
        general.add(ModBlocks.TABLE_DARK_OAK.get().asItem());
        general.add(ModBlocks.TABLE_MANGROVE.get().asItem());
        general.add(ModBlocks.TABLE_CHERRY.get().asItem());
        general.add(ModBlocks.TABLE_CRIMSON.get().asItem());
        general.add(ModBlocks.TABLE_WARPED.get().asItem());
        general.add(ModBlocks.CHAIR_OAK.get().asItem());
        general.add(ModBlocks.CHAIR_SPRUCE.get().asItem());
        general.add(ModBlocks.CHAIR_BIRCH.get().asItem());
        general.add(ModBlocks.CHAIR_JUNGLE.get().asItem());
        general.add(ModBlocks.CHAIR_ACACIA.get().asItem());
        general.add(ModBlocks.CHAIR_DARK_OAK.get().asItem());
        general.add(ModBlocks.CHAIR_MANGROVE.get().asItem());
        general.add(ModBlocks.CHAIR_CHERRY.get().asItem());
        general.add(ModBlocks.CHAIR_CRIMSON.get().asItem());
        general.add(ModBlocks.CHAIR_WARPED.get().asItem());
        general.add(ModBlocks.DESK_OAK.get().asItem());
        general.add(ModBlocks.DESK_SPRUCE.get().asItem());
        general.add(ModBlocks.DESK_BIRCH.get().asItem());
        general.add(ModBlocks.DESK_JUNGLE.get().asItem());
        general.add(ModBlocks.DESK_ACACIA.get().asItem());
        general.add(ModBlocks.DESK_DARK_OAK.get().asItem());
        general.add(ModBlocks.DESK_MANGROVE.get().asItem());
        general.add(ModBlocks.DESK_CHERRY.get().asItem());
        general.add(ModBlocks.DESK_CRIMSON.get().asItem());
        general.add(ModBlocks.DESK_WARPED.get().asItem());
    }
}