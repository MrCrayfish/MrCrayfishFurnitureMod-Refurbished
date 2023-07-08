package com.mrcrayfish.furniture.refurbished.data;

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
        //builder.apply(BlockTags.MINEABLE_WITH_AXE).add();
    }
}
