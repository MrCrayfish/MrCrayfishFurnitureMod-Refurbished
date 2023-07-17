package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.data.tag.TagBuilder;
import com.mrcrayfish.furniture.refurbished.data.tag.TagSupplier;
import net.minecraft.core.registries.Registries;
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
        // Dynamically registers block tags using a provider implemented on the block
        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            Block block = (Block) entry.get();
            if(block instanceof BlockTagSupplier provider) {
                provider.getTags().forEach(key -> builder.apply(key).add(block));
            }
        });
    }
}
