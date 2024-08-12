package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class FurnitureItemTagsProvider extends ItemTagsProvider
{
    public FurnitureItemTagsProvider(DataGenerator generator, BlockTagsProvider blockLookup, @Nullable ExistingFileHelper helper)
    {
        super(generator, blockLookup, Constants.MOD_ID, helper);
    }

    @Override
    protected void addTags()
    {
        CommonItemTagsProvider.accept(key -> new PlatformTagBuilder<>(this.tag(key)));

        // Allows knife to be used with farmers delight and other mods
        this.tag(CompatibilityTags.Items.FORGE_TOOLS_KNIVES)
                .addTag(ModTags.Items.TOOLS_KNIVES);
    }
}
