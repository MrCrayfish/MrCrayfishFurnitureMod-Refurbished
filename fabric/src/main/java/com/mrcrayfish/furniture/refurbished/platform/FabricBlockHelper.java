package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import com.mrcrayfish.furniture.refurbished.core.ModDamageTypes;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Author: MrCrayfish
 */
public class FabricBlockHelper implements IBlockHelper
{
    @Override
    public TrampolineBlock createTrampolineBlock(DyeColor color, BlockBehaviour.Properties properties)
    {
        return new TrampolineBlock(color, properties);
    }

    @Override
    public DamageSource createDamageSource(String name)
    {
        return new DamageSource(name);
    }
}
