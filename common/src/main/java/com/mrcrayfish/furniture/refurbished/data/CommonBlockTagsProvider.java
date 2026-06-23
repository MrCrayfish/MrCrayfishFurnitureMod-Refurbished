package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
// IntrinsicHolderTagsProvider was removed in MC 26.2; use TagsProvider directly and pass
// resource keys (via builtInRegistryHolder().key()) instead of raw Block instances.
public class CommonBlockTagsProvider extends TagsProvider<Block>
{
    public CommonBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(output, Registries.BLOCK, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        addCommonBlockTags(this::tag);
    }

    // Extracted so the Fabric-specific FabricBlockTagsProvider (which can't extend this class, since
    // Fabric's datagen requires the instance to be a FabricTagsProvider) can reuse this logic without
    // duplicating it - takes a tag() method reference instead of calling the protected method directly.
    public static void addCommonBlockTags(Function<TagKey<Block>, TagAppender<Block>> tagFunction)
    {
        // Dynamically registers block tags using a provider implemented on the block
        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            Block block = (Block) entry.get();
            if(block instanceof BlockTagSupplier supplier) {
                supplier.getTags().forEach(key -> tagFunction.apply(key).add(block.builtInRegistryHolder().key()));
            } else {
                throw new IllegalArgumentException("Block doesn't implement BlockTagSupplier: " + entry.getId());
            }
        });
        tagFunction.apply(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
            .add(ModBlocks.STEPPING_STONES_STONE.get().builtInRegistryHolder().key())
            .add(ModBlocks.STEPPING_STONES_GRANITE.get().builtInRegistryHolder().key())
            .add(ModBlocks.STEPPING_STONES_DIORITE.get().builtInRegistryHolder().key())
            .add(ModBlocks.STEPPING_STONES_ANDESITE.get().builtInRegistryHolder().key())
            .add(ModBlocks.STEPPING_STONES_DEEPSLATE.get().builtInRegistryHolder().key());

        // Compatibility to allow stove to act as a heating source for farmers delight
        tagFunction.apply(CompatibilityTags.Blocks.FARMERS_DELIGHT_HEAT_SOURCES)
            .add(ModBlocks.STOVE_LIGHT.get().builtInRegistryHolder().key())
            .add(ModBlocks.STOVE_DARK.get().builtInRegistryHolder().key());

        tagFunction.apply(BlockTags.FENCES)
            .add(ModBlocks.LATTICE_FENCE_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_SPRUCE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_BIRCH.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_JUNGLE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_ACACIA.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_DARK_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_MANGROVE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_CHERRY.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_CRIMSON.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_WARPED.get().builtInRegistryHolder().key());

        tagFunction.apply(BlockTags.FENCE_GATES)
            .add(ModBlocks.LATTICE_FENCE_GATE_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_SPRUCE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_BIRCH.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_JUNGLE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_ACACIA.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_DARK_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_MANGROVE.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_CHERRY.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_CRIMSON.get().builtInRegistryHolder().key())
            .add(ModBlocks.LATTICE_FENCE_GATE_WARPED.get().builtInRegistryHolder().key());

        // Prevent these blocks from being picked up in Carry On mod
        tagFunction.apply(CompatibilityTags.Blocks.CARRY_ON_BLACKLIST)
            .add(ModBlocks.FRIDGE_LIGHT.get().builtInRegistryHolder().key())
            .add(ModBlocks.FRIDGE_DARK.get().builtInRegistryHolder().key())
            .add(ModBlocks.FREEZER_LIGHT.get().builtInRegistryHolder().key())
            .add(ModBlocks.FREEZER_DARK.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_SPRUCE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_BIRCH.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_JUNGLE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_ACACIA.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_DARK_OAK.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_MANGROVE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_CHERRY.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_CRIMSON.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_WARPED.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_WHITE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_ORANGE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_MAGENTA.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_LIGHT_BLUE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_YELLOW.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_LIME.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_PINK.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_GRAY.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_LIGHT_GRAY.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_CYAN.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_PURPLE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_BLUE.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_BROWN.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_GREEN.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_RED.get().builtInRegistryHolder().key())
            .add(ModBlocks.BATH_BLACK.get().builtInRegistryHolder().key());
    }
}
