package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
public class ColouredKitchenStorageCabinetBlock extends KitchenStorageCabinetBlock
{
    private static final MapCodec<ColouredKitchenStorageCabinetBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(DyeColor.CODEC.fieldOf("color").forGetter(block -> {
            return block.color;
        }), propertiesCodec()).apply(builder, ColouredKitchenStorageCabinetBlock::new);
    });

    private final DyeColor color;

    public ColouredKitchenStorageCabinetBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected MapCodec<ColouredKitchenStorageCabinetBlock> codec()
    {
        return CODEC;
    }
}
