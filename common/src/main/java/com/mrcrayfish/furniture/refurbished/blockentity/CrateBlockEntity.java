package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.CrateBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class CrateBlockEntity extends RowedStorageBlockEntity
{
    public static final int ROWS = 3;

    public CrateBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CRATE.get(), pos, state, ROWS);
    }

    public CrateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "crate");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 top = Vec3.upFromBottomCenterOf(pos, 1.0);
        level.playSound(null, top.x, top.y, top.z, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.5F, 0.7F + 0.1F * level.random.nextFloat());
        this.setLidState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 top = Vec3.upFromBottomCenterOf(pos, 1.0);
        level.playSound(null, top.x, top.y, top.z, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 0.5F, 0.7F + 0.1F * level.random.nextFloat());
        this.setLidState(state, false);
    }

    private void setLidState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(CrateBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }
}
