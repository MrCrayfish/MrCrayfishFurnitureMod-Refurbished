package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.data.tag.TagBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
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
        builder.apply(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
                .add(ModBlocks.STEPPING_STONES_STONE.get())
                .add(ModBlocks.STEPPING_STONES_GRANITE.get())
                .add(ModBlocks.STEPPING_STONES_DIORITE.get())
                .add(ModBlocks.STEPPING_STONES_ANDESITE.get())
                .add(ModBlocks.STEPPING_STONES_DEEPSLATE.get());

        // Compatibility to allow stove to act as a heating source for farmers delight
        builder.apply(CompatibilityTags.Blocks.FARMERS_DELIGHT_HEAT_SOURCES)
                .add(ModBlocks.STOVE_LIGHT.get())
                .add(ModBlocks.STOVE_DARK.get());
    }
}
