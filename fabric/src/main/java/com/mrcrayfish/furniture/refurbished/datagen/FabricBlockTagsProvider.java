package com.mrcrayfish.furniture.refurbished.datagen;

import com.mrcrayfish.furniture.refurbished.data.CommonBlockTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

// Fabric's data generator mixes into vanilla TagsProvider and casts the instance to FabricTagsProvider,
// so the shared CommonBlockTagsProvider (plain TagsProvider) can't be registered directly on Fabric.
public class FabricBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider
{
    public FabricBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonBlockTagsProvider.addCommonBlockTags(this::tag);
    }
}
