package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
}
