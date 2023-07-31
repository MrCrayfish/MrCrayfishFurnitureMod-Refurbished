package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
import com.mrcrayfish.furniture.refurbished.block.FridgeBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FridgeItem extends ItemNameBlockItem
{
    private final FridgeBlock fridge;

    public FridgeItem(FridgeBlock fridge, FreezerBlock freezer, Properties properties)
    {
        super(freezer, properties);
        this.fridge = fridge;
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item)
    {
        super.registerBlocks(map, item);
        map.put(this.fridge, item);
    }

    public MetalType getMetalType()
    {
        return this.fridge.getMetalType();
    }
}
