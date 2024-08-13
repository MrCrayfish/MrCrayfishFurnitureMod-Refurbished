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
        builder.apply(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
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
            .add(ModBlocks.LATTICE_FENCE_GATE_CRIMSON.get())
            .add(ModBlocks.LATTICE_FENCE_GATE_WARPED.get());

        // Prevent these blocks from being picked up in Carry On mod
        builder.apply(CompatibilityTags.Blocks.CARRY_ON_BLACKLIST)
            .add(ModBlocks.FRIDGE_LIGHT.get())
            .add(ModBlocks.FRIDGE_DARK.get())
            .add(ModBlocks.FREEZER_LIGHT.get())
            .add(ModBlocks.FREEZER_DARK.get())
            .add(ModBlocks.BATH_OAK.get())
            .add(ModBlocks.BATH_SPRUCE.get())
            .add(ModBlocks.BATH_BIRCH.get())
            .add(ModBlocks.BATH_JUNGLE.get())
            .add(ModBlocks.BATH_ACACIA.get())
            .add(ModBlocks.BATH_DARK_OAK.get())
            .add(ModBlocks.BATH_MANGROVE.get())
            .add(ModBlocks.BATH_CRIMSON.get())
            .add(ModBlocks.BATH_WARPED.get())
            .add(ModBlocks.BATH_WHITE.get())
            .add(ModBlocks.BATH_ORANGE.get())
            .add(ModBlocks.BATH_MAGENTA.get())
            .add(ModBlocks.BATH_LIGHT_BLUE.get())
            .add(ModBlocks.BATH_YELLOW.get())
            .add(ModBlocks.BATH_LIME.get())
            .add(ModBlocks.BATH_PINK.get())
            .add(ModBlocks.BATH_GRAY.get())
            .add(ModBlocks.BATH_LIGHT_GRAY.get())
            .add(ModBlocks.BATH_CYAN.get())
            .add(ModBlocks.BATH_PURPLE.get())
            .add(ModBlocks.BATH_BLUE.get())
            .add(ModBlocks.BATH_BROWN.get())
            .add(ModBlocks.BATH_GREEN.get())
            .add(ModBlocks.BATH_RED.get())
            .add(ModBlocks.BATH_BLACK.get());
    }
}
