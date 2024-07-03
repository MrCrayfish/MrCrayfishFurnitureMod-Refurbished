package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

/**
 * Author: MrCrayfish
 */
public class ModDamageTypes
{
    public static final ResourceKey<DamageType> CEILING_FAN = ResourceKey.create(Registries.DAMAGE_TYPE, Utils.resource("ceiling_fan"));

    public static void bootstrap(BootstapContext<DamageType> context)
    {
        context.register(CEILING_FAN, new DamageType("refurbished_furniture.ceiling_fan", 0.1F));
    }
}
