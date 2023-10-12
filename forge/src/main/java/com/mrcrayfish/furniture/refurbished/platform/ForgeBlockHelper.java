package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.block.ForgeTrampolineBlock;
import com.mrcrayfish.furniture.refurbished.block.TrampolineBlock;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Author: MrCrayfish
 */
public class ForgeBlockHelper implements IBlockHelper
{
    @Override
    public TrampolineBlock createTrampolineBlock(DyeColor color, BlockBehaviour.Properties properties)
    {
        return new ForgeTrampolineBlock(color, properties);
    }
}
