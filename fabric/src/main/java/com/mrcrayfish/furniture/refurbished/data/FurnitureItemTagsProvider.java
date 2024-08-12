package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

/**
 * Author: MrCrayfish
 */
public class FurnitureItemTagsProvider extends FabricTagProvider.ItemTagProvider
{
    public FurnitureItemTagsProvider(FabricDataGenerator generator, FabricTagProvider.BlockTagProvider blockProvider)
    {
        super(generator, blockProvider);
    }

    @Override
    protected void generateTags()
    {
        CommonItemTagsProvider.accept(key -> new PlatformTagBuilder<>(this.getOrCreateTagBuilder(key)));

        this.getOrCreateTagBuilder(CompatibilityTags.Items.FABRIC_TOOLS_KNIVES)
            .addTag(ModTags.Items.TOOLS_KNIVES);
    }
}
