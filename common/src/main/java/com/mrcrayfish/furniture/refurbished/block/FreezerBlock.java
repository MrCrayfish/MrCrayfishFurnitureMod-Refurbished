package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class FreezerBlock extends FridgeBlock
{
    private static final MapCodec<FridgeBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(MetalType.CODEC.fieldOf("metal_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec(), BuiltInRegistries.BLOCK.byNameCodec().xmap(block -> (Supplier<Block>) () -> block, Supplier::get).fieldOf("fridge").forGetter(block -> {
            return ((FreezerBlock) block).fridge;
        })).apply(builder, FreezerBlock::new);
    });

    private final Supplier<Block> fridge;

    public FreezerBlock(MetalType type, Properties properties, Supplier<Block> fridge)
    {
        super(type, properties);
        this.fridge = fridge;
    }

    @Override
    public boolean isFreezer()
    {
        return true;
    }

    public Supplier<Block> getFridge()
    {
        return this.fridge;
    }

    @Override
    protected MapCodec<FridgeBlock> codec()
    {
        return CODEC;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result)
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
        if(abovePos.getY() < level.getMaxY() && level.getBlockState(abovePos).canBeReplaced(context))
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
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if(this.isFreezer() && !level.isClientSide())
        {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            if(aboveState.getBlock() instanceof FridgeBlock)
            {
                level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
                level.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, abovePos, Block.getId(aboveState));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
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
