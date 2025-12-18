package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
import com.mrcrayfish.furniture.refurbished.block.FridgeBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class FridgeItem extends BlockItem
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> lines, TooltipFlag flag)
    {
        lines.accept(PoweredItem.POWER_TOOLTIP);
    }
}
