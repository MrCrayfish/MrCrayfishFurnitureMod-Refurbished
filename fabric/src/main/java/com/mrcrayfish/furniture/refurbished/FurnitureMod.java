package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.Direction;

@SuppressWarnings("UnstableApiUsage")
public class FurnitureMod implements ModInitializer, DataGeneratorEntrypoint
{
    @Override
    public void onInitialize()
    {
        Bootstrap.init();
        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> {
            return direction != Direction.UP ? (SingleFluidStorage) sink.getTank() : null;
        }, ModBlockEntities.KITCHEN_SINK.get());
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        FurnitureBlockTagsProvider provider = pack.addProvider(FurnitureBlockTagsProvider::new);
        pack.addProvider((output, lookupProvider) -> new FurnitureItemTagsProvider(output, lookupProvider, provider));
        FurnitureLootTableProvider.addProviders(pack);
        pack.addProvider(FurnitureRecipeProvider::new);
        pack.addProvider(FurnitureModelProvider::new);
    }
}
