package com.mrcrayfish.furniture.refurbished.client.registration;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.resources.Identifier;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface ItemTintRegister
{
    void apply(Identifier id, MapCodec<? extends ItemTintSource> codec);
}
