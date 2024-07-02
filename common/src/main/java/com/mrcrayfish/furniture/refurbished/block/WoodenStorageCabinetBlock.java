package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class WoodenStorageCabinetBlock extends StorageCabinetBlock implements BlockTagSupplier
{
    private static final MapCodec<WoodenStorageCabinetBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, WoodenStorageCabinetBlock::new);
    });

    private final WoodType type;

    public WoodenStorageCabinetBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<WoodenStorageCabinetBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(0, 0, 0, 16, 16, 16);
        VoxelShape openShape = Block.box(2, 0, 0, 16, 16, 16);
        VoxelShape leftDoorShape = Block.box(-14, 0, 0, 2, 16, 2);
        VoxelShape rightDoorShape = Block.box(-14, 0, 14, 2, 16, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            boolean open = state.getValue(OPEN);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(VoxelShapeHelper.rotateHorizontally(open ? openShape : baseShape, state.getValue(DIRECTION)));
            if(open) {
                VoxelShape door = state.getValue(HINGE) == DoorHingeSide.LEFT ? leftDoorShape : rightDoorShape;
                shapes.add(VoxelShapeHelper.rotateHorizontally(door, state.getValue(DIRECTION)));
            }
            return VoxelShapeHelper.combine(shapes);
        })));
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
