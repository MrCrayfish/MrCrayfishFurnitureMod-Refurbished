package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

import java.util.Collections;
import java.util.List;

public class CuttingBoardRenderState extends BlockEntityRenderState
{
    public Direction direction = Direction.NORTH;
    public List<CuttingBoardItemStackRenderState> items = Collections.emptyList();
}
