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
            } else {
                throw new IllegalArgumentException("Block doesn't implement BlockTagSupplier: " + entry.getId());
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

        builder.apply(BlockTags.FENCES)
            .add(ModBlocks.LATTICE_FENCE_OAK.get())
            .add(ModBlocks.LATTICE_FENCE_SPRUCE.get())
            .add(ModBlocks.LATTICE_FENCE_BIRCH.get())
            .add(ModBlocks.LATTICE_FENCE_JUNGLE.get())
            .add(ModBlocks.LATTICE_FENCE_ACACIA.get())
            .add(ModBlocks.LATTICE_FENCE_DARK_OAK.get())
            .add(ModBlocks.LATTICE_FENCE_MANGROVE.get())
            .add(ModBlocks.LATTICE_FENCE_CHERRY.get())
            .add(ModBlocks.LATTICE_FENCE_CRIMSON.get())
            .add(ModBlocks.LATTICE_FENCE_WARPED.get());

        builder.apply(BlockTags.FENCE_GATES)
                .add(ModBlocks.LATTICE_FENCE_GATE_OAK.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_SPRUCE.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_BIRCH.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_JUNGLE.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_ACACIA.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_DARK_OAK.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_MANGROVE.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_CHERRY.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_CRIMSON.get())
                .add(ModBlocks.LATTICE_FENCE_GATE_WARPED.get());    
    }
}
