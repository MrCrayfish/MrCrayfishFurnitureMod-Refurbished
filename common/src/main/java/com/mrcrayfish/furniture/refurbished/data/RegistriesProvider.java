package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModDamageTypes;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

/**
 * Author: MrCrayfish
 */
public class RegistriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap);
}
