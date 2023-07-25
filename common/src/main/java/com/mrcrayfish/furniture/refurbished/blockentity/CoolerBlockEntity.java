package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.CoolerBlock;
import com.mrcrayfish.furniture.refurbished.block.DrawerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class CoolerBlockEntity extends RowedStorageBlockEntity
{
    public static final int ROWS = 1;

    public CoolerBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.COOLER.get(), pos, state, ROWS);
    }

    public CoolerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "cooler");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        // TODO sounds
        this.setLidState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.setLidState(state, false);
    }

    private void setLidState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(CoolerBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }
}
