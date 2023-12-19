package com.mrcrayfish.furniture.refurbished.compat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
public class CompatibilityTags
{
    public static class Blocks
    {
        public static final TagKey<Block> FARMERS_DELIGHT_HEAT_SOURCES = tag("farmersdelight", "heat_sources");

        private static TagKey<Block> tag(String modId, String name)
        {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(modId, name));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> FORGE_TOOLS_KNIVES = tag("forge", "tools/knives");
        public static final TagKey<Item> FABRIC_TOOLS_KNIVES = tag("c", "knives");

        private static TagKey<Item> tag(String modId, String name)
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation(modId, name));
        }
    }
}
