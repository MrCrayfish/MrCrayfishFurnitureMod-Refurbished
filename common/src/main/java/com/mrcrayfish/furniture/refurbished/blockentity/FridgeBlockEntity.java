package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.FridgeBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class FridgeBlockEntity extends RowedStorageBlockEntity
{
    public static final int ROWS = 3;

    public FridgeBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FRIDGE.get(), pos, state, ROWS);
    }

    public FridgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "fridge");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(FridgeBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_FRIDGE_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(FridgeBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_FRIDGE_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, false);
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(FridgeBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }
}
