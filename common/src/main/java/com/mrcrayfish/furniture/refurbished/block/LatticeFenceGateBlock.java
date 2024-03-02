package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class LatticeFenceGateBlock extends FenceGateBlock implements BlockTagSupplier
{
    private static final MapCodec<FenceGateBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return ((LatticeFenceGateBlock) block).type;
        }), propertiesCodec()).apply(builder, LatticeFenceGateBlock::new);
    });

    private final WoodType type;

    public LatticeFenceGateBlock(WoodType type, Properties properties)
    {
        super(type, properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    public MapCodec<FenceGateBlock> codec()
    {
        return CODEC;
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
