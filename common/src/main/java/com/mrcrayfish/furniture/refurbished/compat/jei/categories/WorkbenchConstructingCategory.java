package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class WorkbenchConstructingCategory extends FurnitureRecipeCategory<WorkbenchContructingRecipe>
{
    public static final Supplier<IRecipeHolderType<WorkbenchContructingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.WORKBENCH_CONSTRUCTING::get);

    private final IGuiHelper helper;

    public WorkbenchConstructingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "workbench_constructing"),
            helper.createDrawable(Plugin.TEXTURES_2, 0, 0, 118, 64),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.WORKBENCH.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<WorkbenchContructingRecipe> holder, IFocusGroup focuses)
    {
        WorkbenchContructingRecipe recipe = holder.value();
        NonNullList<StackedIngredient> materials = recipe.getMaterials();
        int left = 6;
        int top = 6;
        int slotSize = 18;
        int boxSize = 54;
        int width = slotSize * Mth.clamp(materials.size(), 1, 3);
        int height = slotSize * Mth.clamp(Mth.ceil(materials.size() / (float) 3), 1, 3);
        for(int i = 0; i < materials.size(); i++)
        {
            int x = left + (i % 3) * slotSize + (boxSize - width) / 2;
            int y = top + (i / 3) * slotSize + (boxSize - height) / 2;
            StackedIngredient material = materials.get(i);
            List<ItemStack> stacks = material.ingredient().items().map(itemHolder -> {
                ItemStack copy = new ItemStack(itemHolder.value());
                copy.setCount(material.count());
                return copy;
            }).toList();
            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .setBackground(this.helper.createDrawable(Plugin.TEXTURES_2, 0, 64, 18, 18), -1, -1)
                    .addItemStacks(stacks);
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 24).add(recipe.getResult().create());
    }
}
