package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class DoorMatCopyRecipe extends CustomRecipe
{
    public DoorMatCopyRecipe(CraftingBookCategory category)
    {
        super(category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level)
    {
        return !this.constructOutput(container).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer container, HolderLookup.Provider provider)
    {
        return this.constructOutput(container);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.DOOR_MAT_COPY_RECIPE.get();
    }

    private ItemStack constructOutput(CraftingContainer container)
    {
        int outputCount = 0;
        ItemStack source = ItemStack.EMPTY;
        for(int i = 0; i < container.getContainerSize(); i++)
        {
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty())
            {
                if(!stack.is(ModBlocks.DOOR_MAT.get().asItem()))
                    return ItemStack.EMPTY;

                if(stack.has(ModDataComponents.PALETTE_IMAGE.get()))
                {
                    if(!source.isEmpty())
                        return ItemStack.EMPTY;

                    source = stack;
                }
                outputCount++;
            }
        }

        if(!source.isEmpty() && outputCount > 0)
        {
            ItemStack copy = source.copy();
            copy.setCount(outputCount);
            return copy;
        }

        return ItemStack.EMPTY;
    }
}
