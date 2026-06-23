package com.mrcrayfish.furniture.refurbished.datagen;

import com.mrcrayfish.furniture.refurbished.data.CommonItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

// Fabric's data generator mixes into vanilla TagsProvider and casts the instance to FabricTagsProvider,
// so the shared CommonItemTagsProvider (plain VanillaItemTagsProvider) can't be registered directly on Fabric.
public class FabricItemTagsProvider extends FabricTagsProvider.ItemTagsProvider
{
    public FabricItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonItemTagsProvider.addCommonItemTags(this::tag);
    }
}
