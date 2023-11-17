package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorbellBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.FurnitureScreens;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public DoorMatBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(2, 0, 0, 14, 1, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(baseShape, state.getValue(DIRECTION));
        })));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(entity instanceof ServerPlayer player)
        {
            CompoundTag tag = BlockItem.getBlockEntityData(stack); // If block entity data on item, don't open menu
            if(tag == null && level.getBlockEntity(pos) instanceof DoorMatBlockEntity doorMat)
            {
                player.openMenu(doorMat);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new DoorMatBlockEntity(pos, state);
    }
}
