package com.mrcrayfish.furniture.refurbished;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Author: MrCrayfish
 */
public class PlatformTags
{
    public static class Items
    {
        public static final TagKey<Item> TOOLS_KNIVES = tag("c", "knives");

        public static TagKey<Item> tag(String modId, String name)
        {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, name));
        }
    }
}
