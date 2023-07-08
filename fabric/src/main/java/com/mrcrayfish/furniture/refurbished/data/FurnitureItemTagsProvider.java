package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureItemTagsProvider extends FabricTagProvider.ItemTagProvider
{
    public FurnitureItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, FabricTagProvider.BlockTagProvider blockProvider)
    {
        super(output, lookupProvider, blockProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonItemTagsProvider.accept(key -> new PlatformTagBuilder<>(this.getOrCreateTagBuilder(key)));
    }
}
