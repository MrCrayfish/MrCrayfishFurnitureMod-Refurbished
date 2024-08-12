package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class FurnitureBlockTagsProvider extends BlockTagsProvider
{
    public FurnitureBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper helper)
    {
        super(generator, Constants.MOD_ID, helper);
    }

    @Override
    protected void addTags()
    {
        CommonBlockTagsProvider.accept(key -> new PlatformTagBuilder<>(this.tag(key)));
    }
}
