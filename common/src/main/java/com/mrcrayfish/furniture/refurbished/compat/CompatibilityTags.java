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
        public static final TagKey<Block> CARRY_ON_BLACKLIST = tag("carryon", "block_blacklist");

        private static TagKey<Block> tag(String modId, String name)
        {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, name));
        }
    }
}
