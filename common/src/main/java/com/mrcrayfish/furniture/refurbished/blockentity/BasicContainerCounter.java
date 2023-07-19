package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class BasicContainerCounter extends ContainerOpenersCounter
{
    private final BasicLootBlockEntity basicLootBlock;

    public BasicContainerCounter(BasicLootBlockEntity basicLootBlock)
    {
        this.basicLootBlock = basicLootBlock;
    }

    @Override
    protected void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.basicLootBlock.onOpen(level, pos, state);
    }

    @Override
    protected void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.basicLootBlock.onClose(level, pos, state);
    }

    @Override
    protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int oldCount, int count)
    {
        level.blockEvent(pos, state.getBlock(), 1, count);
    }

    @Override
    protected boolean isOwnContainer(Player player)
    {
        return this.basicLootBlock.isMatchingContainerMenu(player.containerMenu);
    }
}
