package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class WoodenBathBlock extends BathBlock
{
    private static final MapCodec<WoodenBathBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, WoodenBathBlock::new);
    });

    private final WoodType type;

    public WoodenBathBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<WoodenBathBlock> codec()
    {
        return CODEC;
    }
}
