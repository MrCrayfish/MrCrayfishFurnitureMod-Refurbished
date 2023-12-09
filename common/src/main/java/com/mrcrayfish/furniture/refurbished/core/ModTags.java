package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
public class ModTags
{
    public static class Items
    {
        public static final TagKey<Item> GENERAL = tag("general");
        public static final TagKey<Item> BEDROOM = tag("bedroom");
        public static final TagKey<Item> KITCHEN = tag("kitchen");
        public static final TagKey<Item> OUTDOORS = tag("outdoors");
        public static final TagKey<Item> BATHROOM = tag("bathroom");
        public static final TagKey<Item> ELECTRONICS = tag("electronics");
        public static final TagKey<Item> STORAGE = tag("storage");
        public static final TagKey<Item> ITEMS = tag("items");
        public static final TagKey<Item> WOODEN_KITCHEN_CABINETRY = tag("wooden_kitchen_cabinetry");
        public static final TagKey<Item> WOODEN_KITCHEN_DRAWERS = tag("wooden_kitchen_drawers");
        public static final TagKey<Item> WOODEN_KITCHEN_SINKS = tag("wooden_kitchen_sinks");
        public static final TagKey<Item> WOODEN_KITCHEN_STORAGE_CABINETS = tag("wooden_kitchen_storage_cabinets");
        public static final TagKey<Item> COLOURED_KITCHEN_CABINETRY = tag("coloured_kitchen_cabinetry");
        public static final TagKey<Item> COLOURED_KITCHEN_DRAWERS = tag("coloured_kitchen_drawers");
        public static final TagKey<Item> COLOURED_KITCHEN_SINKS = tag("coloured_kitchen_sinks");
        public static final TagKey<Item> COLOURED_KITCHEN_STORAGE_CABINETS = tag("coloured_kitchen_storage_cabinets");
        public static final TagKey<Item> TOOLS_KNIVES = tag("tools/knives");

        private static TagKey<Item> tag(String name)
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation(Constants.MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final TagKey<Block> TUCKABLE = tag("tuckable");

        private static TagKey<Block> tag(String name)
        {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(Constants.MOD_ID, name));
        }
    }
}
