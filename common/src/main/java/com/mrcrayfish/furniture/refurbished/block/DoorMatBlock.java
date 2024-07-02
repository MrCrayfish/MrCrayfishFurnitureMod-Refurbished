package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    private static final MapCodec<DoorMatBlock> CODEC = simpleCodec(DoorMatBlock::new);

    public DoorMatBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected MapCodec<DoorMatBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(2, 0, 0, 14, 1, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(baseShape, state.getValue(DIRECTION));
        })));
    }

    /*(@Override
    public InteractionResult use(BlockState $$0, Level level, BlockPos pos, Player $$3, InteractionHand $$4, BlockHitResult $$5)
    {
        if(level instanceof ServerLevel)
        {
            if(level.getBlockEntity(pos) instanceof DoorMatBlockEntity doorMat)
            {
                PaletteImage image = doorMat.getImage();
                if(image != null)
                {
                    for(int y = 0; y < image.getHeight(); y++)
                    {
                        for(int x = 0; x < image.getWidth(); x++)
                        {
                            if(image.get(x, y) != 0)
                                System.out.println("image.set(" + x + ", " + y + ", " + image.get(x, y) + ");");
                        }
                    }
                }
            }
        }
        return super.use($$0, level, pos, $$3, $$4, $$5);
    }*/

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(entity instanceof ServerPlayer player)
        {
            CompoundTag tag = BlockItem.getBlockEntityData(stack); // If block entity data on item, don't open menu
            if((tag == null || !tag.getBoolean("Finalised")) && level.getBlockEntity(pos) instanceof DoorMatBlockEntity doorMat)
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

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_HOE);
    }
}
