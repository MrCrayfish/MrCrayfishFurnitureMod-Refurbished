package com.mrcrayfish.furniture.refurbished.block;

import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class FreezerBlock extends FridgeBlock
{
    private final Supplier<Block> fridge;

    public FreezerBlock(MetalType type, Properties properties, Supplier<Block> fridge)
    {
        super(type, properties);
        this.fridge = fridge;
    }

    public Supplier<Block> getFridge()
    {
        return this.fridge;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide() && level.getBlockEntity(pos) instanceof FreezerBlockEntity freezer)
            {
                player.openMenu(freezer);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos abovePos = context.getClickedPos().above();
        Level level = context.getLevel();
        if(abovePos.getY() < level.getMaxBuildHeight() && level.getBlockState(abovePos).canBeReplaced(context))
        {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack)
    {
        BlockState fridge = this.fridge.get().defaultBlockState().setValue(DIRECTION, state.getValue(DIRECTION));
        level.setBlock(pos.above(), fridge, Block.UPDATE_ALL);
        fridge.getBlock().setPlacedBy(level, pos.above(), fridge, entity, stack);
        if(stack.hasCustomHoverName() && level.getBlockEntity(pos) instanceof FreezerBlockEntity freezer)
        {
            freezer.setCustomName(stack.getHoverName());
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
        if(!state.is(newState.getBlock()) && level.getBlockState(pos.above()).getBlock() instanceof FridgeBlock)
        {
            level.removeBlock(pos.above(), false);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return Services.BLOCK_ENTITY.createFreezerBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        if(!level.isClientSide())
        {
            return createTicker(type,  ModBlockEntities.FREEZER.get(), FreezerBlockEntity::serverTick);
        }
        return null;
    }
}
