package com.mrcrayfish.furniture.refurbished.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * Author: MrCrayfish
 */
public class ContainerInput implements RecipeInput
{
    private final Container container;

    public ContainerInput(Container container)
    {
        this.container = container;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return this.container.getItem(i);
    }

    @Override
    public int size()
    {
        return this.container.getContainerSize();
    }
}
