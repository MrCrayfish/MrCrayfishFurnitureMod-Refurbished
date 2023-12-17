package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.DrawerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class KitchenDrawerBlockEntity extends DrawerBlockEntity
{
    public static final int ROWS = 1;

    public KitchenDrawerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.KITCHEN_DRAWER.get(), pos, state, ROWS);
    }

    public KitchenDrawerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int rows)
    {
        super(type, pos, state, rows);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "kitchen_drawer");
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 draw = Vec3.atCenterOf(this.worldPosition).add(0, 0.1875, 0).relative(state.getValue(DrawerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, draw.x, draw.y, draw.z, ModSounds.BLOCK_KITCHEN_DRAWER_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDrawState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 draw = Vec3.atCenterOf(this.worldPosition).add(0, 0.1875, 0).relative(state.getValue(DrawerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, draw.x, draw.y, draw.z, ModSounds.BLOCK_KITCHEN_DRAWER_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDrawState(state, false);
    }
}
