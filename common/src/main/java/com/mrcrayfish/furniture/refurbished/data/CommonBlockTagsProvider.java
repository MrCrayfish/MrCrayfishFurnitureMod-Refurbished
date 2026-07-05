package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class CommonBlockTagsProvider extends TagsProvider<Block>
{
    public CommonBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(output, Registries.BLOCK, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        // Dynamically registers block tags using a provider implemented on the block
        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            Block block = (Block) entry.get();
            if(block instanceof BlockTagSupplier supplier) {
                supplier.getTags().forEach(key -> this.tag(key).add(block.builtInRegistryHolder().key()));
            } else {
                throw new IllegalArgumentException("Block doesn't implement BlockTagSupplier: " + entry.getId());
            }
        });
        this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
            .add(ModBlocks.STEPPING_STONES_STONE.getBlockKey())
            .add(ModBlocks.STEPPING_STONES_GRANITE.getBlockKey())
            .add(ModBlocks.STEPPING_STONES_DIORITE.getBlockKey())
            .add(ModBlocks.STEPPING_STONES_ANDESITE.getBlockKey())
            .add(ModBlocks.STEPPING_STONES_DEEPSLATE.getBlockKey());

        // Compatibility to allow stove to act as a heating source for farmers delight
        this.tag(CompatibilityTags.Blocks.FARMERS_DELIGHT_HEAT_SOURCES)
            .add(ModBlocks.STOVE_LIGHT.getBlockKey())
            .add(ModBlocks.STOVE_DARK.getBlockKey());

        this.tag(BlockTags.FENCES)
            .add(ModBlocks.LATTICE_FENCE_OAK.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_SPRUCE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_BIRCH.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_JUNGLE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_ACACIA.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_DARK_OAK.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_MANGROVE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_CHERRY.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_CRIMSON.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_WARPED.getBlockKey());

        this.tag(BlockTags.FENCE_GATES)
            .add(ModBlocks.LATTICE_FENCE_GATE_OAK.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_SPRUCE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_BIRCH.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_JUNGLE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_ACACIA.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_DARK_OAK.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_MANGROVE.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_CHERRY.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_CRIMSON.getBlockKey())
            .add(ModBlocks.LATTICE_FENCE_GATE_WARPED.getBlockKey());

        // Prevent these blocks from being picked up in Carry On mod
        this.tag(CompatibilityTags.Blocks.CARRY_ON_BLACKLIST)
            .add(ModBlocks.FRIDGE_LIGHT.getBlockKey())
            .add(ModBlocks.FRIDGE_DARK.getBlockKey())
            .add(ModBlocks.FREEZER_LIGHT.getBlockKey())
            .add(ModBlocks.FREEZER_DARK.getBlockKey())
            .add(ModBlocks.BATH_OAK.getBlockKey())
            .add(ModBlocks.BATH_SPRUCE.getBlockKey())
            .add(ModBlocks.BATH_BIRCH.getBlockKey())
            .add(ModBlocks.BATH_JUNGLE.getBlockKey())
            .add(ModBlocks.BATH_ACACIA.getBlockKey())
            .add(ModBlocks.BATH_DARK_OAK.getBlockKey())
            .add(ModBlocks.BATH_MANGROVE.getBlockKey())
            .add(ModBlocks.BATH_CHERRY.getBlockKey())
            .add(ModBlocks.BATH_CRIMSON.getBlockKey())
            .add(ModBlocks.BATH_WARPED.getBlockKey())
            .add(ModBlocks.BATH_WHITE.getBlockKey())
            .add(ModBlocks.BATH_ORANGE.getBlockKey())
            .add(ModBlocks.BATH_MAGENTA.getBlockKey())
            .add(ModBlocks.BATH_LIGHT_BLUE.getBlockKey())
            .add(ModBlocks.BATH_YELLOW.getBlockKey())
            .add(ModBlocks.BATH_LIME.getBlockKey())
            .add(ModBlocks.BATH_PINK.getBlockKey())
            .add(ModBlocks.BATH_GRAY.getBlockKey())
            .add(ModBlocks.BATH_LIGHT_GRAY.getBlockKey())
            .add(ModBlocks.BATH_CYAN.getBlockKey())
            .add(ModBlocks.BATH_PURPLE.getBlockKey())
            .add(ModBlocks.BATH_BLUE.getBlockKey())
            .add(ModBlocks.BATH_BROWN.getBlockKey())
            .add(ModBlocks.BATH_GREEN.getBlockKey())
            .add(ModBlocks.BATH_RED.getBlockKey())
            .add(ModBlocks.BATH_BLACK.getBlockKey());
    }
}
