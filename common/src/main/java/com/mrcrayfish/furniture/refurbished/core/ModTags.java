package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.Registry;
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
        public static final TagKey<Item> FOOD = tag("food");
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
        public static final TagKey<Item> GRILLS = tag("grills");
        public static final TagKey<Item> COOLERS = tag("coolers");
        public static final TagKey<Item> TRAMPOLINES = tag("trampolines");
        public static final TagKey<Item> SOFAS = tag("sofas");
        public static final TagKey<Item> STOOLS = tag("stools");
        public static final TagKey<Item> LAMPS = tag("lamps");
        public static final TagKey<Item> WOODEN_TOILETS = tag("wooden_toilets");
        public static final TagKey<Item> WOODEN_BASINS = tag("wooden_basins");
        public static final TagKey<Item> WOODEN_BATHS = tag("wooden_baths");
        public static final TagKey<Item> DISPLAY_AS_BLOCK = tag("display_as_block");

        private static TagKey<Item> tag(String name)
        {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final TagKey<Block> TUCKABLE = tag("tuckable");

        private static TagKey<Block> tag(String name)
        {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Constants.MOD_ID, name));
        }
    }
}
