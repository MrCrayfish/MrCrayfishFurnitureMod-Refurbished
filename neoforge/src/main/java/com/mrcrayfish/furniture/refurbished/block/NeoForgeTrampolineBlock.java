package com.mrcrayfish.furniture.refurbished.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class NeoForgeTrampolineBlock extends TrampolineBlock
{
    public NeoForgeTrampolineBlock(DyeColor color, Properties properties)
    {
        super(color, properties);
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles)
    {
        return true;
    }
}
