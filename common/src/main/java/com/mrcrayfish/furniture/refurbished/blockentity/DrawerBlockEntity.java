package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.DrawerBlock;
import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
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
public class DrawerBlockEntity extends RowedStorageBlockEntity
{
    private static final int ROWS = 1;

    public DrawerBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.DRAWER.get(), pos, state, ROWS);
    }

    public DrawerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "drawer");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 draw = Vec3.atCenterOf(this.worldPosition).add(0, 0.1875, 0).relative(state.getValue(DrawerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, draw.x, draw.y, draw.z, ModSounds.BLOCK_DRAWER_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDrawState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 draw = Vec3.atCenterOf(this.worldPosition).add(0, 0.1875, 0).relative(state.getValue(DrawerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, draw.x, draw.y, draw.z, ModSounds.BLOCK_DRAWER_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDrawState(state, false);
    }

    protected void setDrawState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(DrawerBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }
}
