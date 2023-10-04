package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.WoodenStorageCabinetBlock;
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
public class StorageCabinetBlockEntity extends RowedStorageBlockEntity
{
    private static final int ROWS = 3;

    public StorageCabinetBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.STORAGE_CABINET.get(), pos, state, ROWS);
    }

    public StorageCabinetBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "storage_cabinet");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        // TODO sounds
        this.setDrawState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.setDrawState(state, false);
    }

    private void setDrawState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(WoodenStorageCabinetBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }
}
