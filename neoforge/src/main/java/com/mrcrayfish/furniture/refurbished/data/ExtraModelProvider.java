package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class ExtraModelProvider extends ModelProvider<BlockModelBuilder>
{
    private static final String OUTPUT_FOLDER = "extra";

    public ExtraModelProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper)
    {
        super(output, modId, OUTPUT_FOLDER, BlockModelBuilder::new, existingFileHelper);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        return CompletableFuture.allOf();
    }

    @Override
    protected void registerModels() {}

    @Override
    public CompletableFuture<?> generateAll(CachedOutput cache)
    {
        return super.generateAll(cache);
    }

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
}
