package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureBlockTagsProvider extends FabricTagProvider.BlockTagProvider
{
    public FurnitureBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonBlockTagsProvider.accept(key -> new PlatformTagBuilder<>(this.getOrCreateTagBuilder(key)));
    }
}
