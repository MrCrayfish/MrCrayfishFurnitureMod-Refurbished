package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.util.EnvironmentHelper;
import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
import com.mrcrayfish.furniture.refurbished.block.FridgeBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag)
    {
        EnvironmentHelper.runOn(Environment.CLIENT, () -> () -> {
            Minecraft.getInstance().font.getSplitter().splitLines(PoweredItem.POWER_TOOLTIP, 150, Style.EMPTY).forEach(text -> {
                // Dumb but works
                MutableComponent line = Component.empty();
                text.visit((style, s) -> {
                    line.append(Component.literal(s).withStyle(style));
                    return Optional.empty();
                }, Style.EMPTY);
                lines.add(line);
            });
        });
        super.appendHoverText(stack, level, lines, flag);
    }
}
