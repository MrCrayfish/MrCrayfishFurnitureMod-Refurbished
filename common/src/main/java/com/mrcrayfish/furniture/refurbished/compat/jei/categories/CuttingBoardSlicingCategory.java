package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.ConventionalTags;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import com.mrcrayfish.furniture.refurbished.util.reflection.ReflectedMethod;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleItemRecipe;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardSlicingCategory extends FurnitureRecipeCategory<CuttingBoardSlicingRecipe>
{
    private static final ReflectedMethod<SingleItemRecipe, ItemStackTemplate> RESULT_METHOD = new ReflectedMethod<>(SingleItemRecipe.class, "result");
    public static final Supplier<IRecipeHolderType<CuttingBoardSlicingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.CUTTING_BOARD_SLICING::get);

    private final List<ItemStack> knives;

    public CuttingBoardSlicingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "cutting_board_slicing"),
            helper.createDrawable(Plugin.TEXTURES, 0, 36, 133, 36),
            helper.createDrawableItemStack(new ItemStack(ModItems.KNIFE.get()))
        );
        this.knives = Plugin.getTagItems(ConventionalTags.Items.TOOLS_KNIVES);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CuttingBoardSlicingRecipe> holder, IFocusGroup focuses)
    {
        CuttingBoardSlicingRecipe recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 6).add(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 111, 10).add(RESULT_METHOD.invoke(recipe));
        builder.addSlot(RecipeIngredientRole.CRAFTING_STATION, 73, 11).addItemStacks(this.knives);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<CuttingBoardSlicingRecipe> recipe, IRecipeSlotsView view, double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 5, 16, 55, 15) && !ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 25, 6, 16, 16))
        {
            tooltip.addAll(Plugin.getItemTooltip(ModBlocks.CUTTING_BOARD_OAK.get()));
        }
    }
}
