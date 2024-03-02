package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;

/**
 * Author: MrCrayfish
 */
public class ColouredBathBlock extends BathBlock
{
    private static final MapCodec<ColouredBathBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(DyeColor.CODEC.fieldOf("color").forGetter(block -> {
            return block.color;
        }), propertiesCodec()).apply(builder, ColouredBathBlock::new);
    });

    private final DyeColor color;

    public ColouredBathBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected MapCodec<ColouredBathBlock> codec()
    {
        return CODEC;
    }
}
