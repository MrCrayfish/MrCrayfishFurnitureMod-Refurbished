package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureItemTagsProvider extends ItemTagsProvider
{
    public FurnitureItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper helper)
    {
        super(output, lookupProvider, blockLookup, Constants.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonItemTagsProvider.accept(key -> new PlatformTagBuilder<>(this.tag(key)));

        // Allows knife to be used with farmers delight and other mods
        this.tag(CompatibilityTags.Items.FORGE_TOOLS_KNIVES)
                .addTag(ModTags.Items.TOOLS_KNIVES);
    }
}
