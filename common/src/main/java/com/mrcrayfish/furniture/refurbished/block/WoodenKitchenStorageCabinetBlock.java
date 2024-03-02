package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
public class WoodenKitchenStorageCabinetBlock extends KitchenStorageCabinetBlock
{
    private static final MapCodec<WoodenKitchenStorageCabinetBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, WoodenKitchenStorageCabinetBlock::new);
    });

    private final WoodType type;

    public WoodenKitchenStorageCabinetBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<WoodenKitchenStorageCabinetBlock> codec()
    {
        return CODEC;
    }
}
