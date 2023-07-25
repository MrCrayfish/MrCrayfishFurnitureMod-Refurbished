package com.mrcrayfish.furniture.refurbished.block;

import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class WoodenKitchenCabinetryBlock extends KitchenCabinetryBlock implements BlockTagSupplier
{
    private final WoodType type;

    public WoodenKitchenCabinetryBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}
