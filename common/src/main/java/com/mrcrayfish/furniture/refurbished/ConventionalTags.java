package com.mrcrayfish.furniture.refurbished;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Author: MrCrayfish
 */
public class ConventionalTags
{
    public static class Items
    {
        public static final TagKey<Item> TOOLS_KNIFE = tag("c", "tools/knife");
        public static final TagKey<Item> TOOLS_KNIVES = tag("c", "tools/knives");
        public static final TagKey<Item> FARMERS_DELIGHT_TOOLS_KNIVES = tag("farmersdelight", "tools/knives");

        public static TagKey<Item> tag(String modId, String name)
        {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modId, name));
        }
    }
}
