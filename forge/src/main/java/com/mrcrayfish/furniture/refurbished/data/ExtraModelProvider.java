package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;

/**
 * Author: MrCrayfish
 */
public class ExtraModelProvider extends ModelProvider<BlockModelBuilder>
{
    private static final String OUTPUT_FOLDER = "extra";

    public ExtraModelProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper)
    {
        super(generator, modId, OUTPUT_FOLDER, BlockModelBuilder::new, existingFileHelper);
    }

    @Override
    protected void registerModels() {}

    @Override
    public void clear()
    {
        super.clear();
    }

    @Override
    public String getName()
    {
        return "Extra Models: " + this.modid;
    }

    @Override
    public void run(CachedOutput cache)
    {
        generateAll(cache);
    }
}
