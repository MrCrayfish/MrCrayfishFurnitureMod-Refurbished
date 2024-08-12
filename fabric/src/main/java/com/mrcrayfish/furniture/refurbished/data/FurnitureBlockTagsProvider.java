package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

/**
 * Author: MrCrayfish
 */
public class FurnitureBlockTagsProvider extends FabricTagProvider.BlockTagProvider
{
    public FurnitureBlockTagsProvider(FabricDataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void generateTags()
    {
        CommonBlockTagsProvider.accept(key -> new PlatformTagBuilder<>(this.getOrCreateTagBuilder(key)));
    }
}
