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
        general.add(ModBlocks.DRAWER_OAK.get().asItem());
        general.add(ModBlocks.DRAWER_SPRUCE.get().asItem());
        general.add(ModBlocks.DRAWER_BIRCH.get().asItem());
        general.add(ModBlocks.DRAWER_JUNGLE.get().asItem());
        general.add(ModBlocks.DRAWER_ACACIA.get().asItem());
        general.add(ModBlocks.DRAWER_DARK_OAK.get().asItem());
        general.add(ModBlocks.DRAWER_MANGROVE.get().asItem());
        general.add(ModBlocks.DRAWER_CHERRY.get().asItem());
        general.add(ModBlocks.DRAWER_CRIMSON.get().asItem());
        general.add(ModBlocks.DRAWER_WARPED.get().asItem());

        TagBuilder<Item> bedroom = builder.apply(ModTags.Items.BEDROOM);
        bedroom.add(ModBlocks.DESK_OAK.get().asItem());
        bedroom.add(ModBlocks.DESK_SPRUCE.get().asItem());
        bedroom.add(ModBlocks.DESK_BIRCH.get().asItem());
        bedroom.add(ModBlocks.DESK_JUNGLE.get().asItem());
        bedroom.add(ModBlocks.DESK_ACACIA.get().asItem());
        bedroom.add(ModBlocks.DESK_DARK_OAK.get().asItem());
        bedroom.add(ModBlocks.DESK_MANGROVE.get().asItem());
        bedroom.add(ModBlocks.DESK_CHERRY.get().asItem());
        bedroom.add(ModBlocks.DESK_CRIMSON.get().asItem());
        bedroom.add(ModBlocks.DESK_WARPED.get().asItem());
        bedroom.add(ModBlocks.DRAWER_OAK.get().asItem());
        bedroom.add(ModBlocks.DRAWER_SPRUCE.get().asItem());
        bedroom.add(ModBlocks.DRAWER_BIRCH.get().asItem());
        bedroom.add(ModBlocks.DRAWER_JUNGLE.get().asItem());
        bedroom.add(ModBlocks.DRAWER_ACACIA.get().asItem());
        bedroom.add(ModBlocks.DRAWER_DARK_OAK.get().asItem());
        bedroom.add(ModBlocks.DRAWER_MANGROVE.get().asItem());
        bedroom.add(ModBlocks.DRAWER_CHERRY.get().asItem());
        bedroom.add(ModBlocks.DRAWER_CRIMSON.get().asItem());
        bedroom.add(ModBlocks.DRAWER_WARPED.get().asItem());

        TagBuilder<Item> kitchen = builder.apply(ModTags.Items.KITCHEN);
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_OAK.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_SPRUCE.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_BIRCH.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_JUNGLE.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_ACACIA.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_DARK_OAK.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_MANGROVE.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_CHERRY.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_CRIMSON.get().asItem());
        kitchen.add(ModBlocks.KITCHEN_CABINETRY_WARPED.get().asItem());

        TagBuilder<Item> outdoors = builder.apply(ModTags.Items.OUTDOORS);
        outdoors.add(ModBlocks.CRATE_OAK.get().asItem());
        outdoors.add(ModBlocks.CRATE_SPRUCE.get().asItem());
        outdoors.add(ModBlocks.CRATE_BIRCH.get().asItem());
        outdoors.add(ModBlocks.CRATE_JUNGLE.get().asItem());
        outdoors.add(ModBlocks.CRATE_ACACIA.get().asItem());
        outdoors.add(ModBlocks.CRATE_DARK_OAK.get().asItem());
        outdoors.add(ModBlocks.CRATE_MANGROVE.get().asItem());
        outdoors.add(ModBlocks.CRATE_CHERRY.get().asItem());
        outdoors.add(ModBlocks.CRATE_CRIMSON.get().asItem());
        outdoors.add(ModBlocks.CRATE_WARPED.get().asItem());

        TagBuilder<Item> storage = builder.apply(ModTags.Items.STORAGE);
        storage.add(ModBlocks.DRAWER_OAK.get().asItem());
        storage.add(ModBlocks.DRAWER_SPRUCE.get().asItem());
        storage.add(ModBlocks.DRAWER_BIRCH.get().asItem());
        storage.add(ModBlocks.DRAWER_JUNGLE.get().asItem());
        storage.add(ModBlocks.DRAWER_ACACIA.get().asItem());
        storage.add(ModBlocks.DRAWER_DARK_OAK.get().asItem());
        storage.add(ModBlocks.DRAWER_MANGROVE.get().asItem());
        storage.add(ModBlocks.DRAWER_CHERRY.get().asItem());
        storage.add(ModBlocks.DRAWER_CRIMSON.get().asItem());
        storage.add(ModBlocks.DRAWER_WARPED.get().asItem());
        storage.add(ModBlocks.CRATE_OAK.get().asItem());
        storage.add(ModBlocks.CRATE_SPRUCE.get().asItem());
        storage.add(ModBlocks.CRATE_BIRCH.get().asItem());
        storage.add(ModBlocks.CRATE_JUNGLE.get().asItem());
        storage.add(ModBlocks.CRATE_ACACIA.get().asItem());
        storage.add(ModBlocks.CRATE_DARK_OAK.get().asItem());
        storage.add(ModBlocks.CRATE_MANGROVE.get().asItem());
        storage.add(ModBlocks.CRATE_CHERRY.get().asItem());
        storage.add(ModBlocks.CRATE_CRIMSON.get().asItem());
        storage.add(ModBlocks.CRATE_WARPED.get().asItem());
    }
}
