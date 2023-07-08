package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class CommonBlockTagsProvider
{
    public static void accept(Function<TagKey<Block>, TagBuilder<Block>> builder)
    {
        //builder.apply(BlockTags.MINEABLE_WITH_AXE).add();
    }
}
