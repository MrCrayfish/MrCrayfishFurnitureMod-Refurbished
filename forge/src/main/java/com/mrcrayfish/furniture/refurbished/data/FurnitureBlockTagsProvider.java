package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureBlockTagsProvider extends BlockTagsProvider
{
    public FurnitureBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper helper)
    {
        super(output, lookupProvider, Constants.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        CommonBlockTagsProvider.accept(key -> new PlatformTagBuilder<>(this.tag(key)));
    }
}
