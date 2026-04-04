package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureRegistryProvider extends FabricDynamicRegistryProvider
{
    public FurnitureRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        entries.add(ModDamageTypes.CEILING_FAN, new DamageType("refurbished_furniture.ceiling_fan", 0.1F));
    }

    @Override
    public String getName()
    {
        return "Refurbished Furniture Registry Provider";
    }
}
