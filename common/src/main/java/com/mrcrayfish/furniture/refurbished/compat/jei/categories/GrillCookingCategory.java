package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GrillCookingCategory extends FurnitureRecipeCategory<ProcessingRecipe.Item>
{
    public static final Supplier<IRecipeHolderType<ProcessingRecipe.Item>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.GRILL_COOKING::get);

    private final ItemStack campfireStack = new ItemStack(Items.CAMPFIRE);
    private final IGuiHelper helper;
    private IDrawable arrow;

    public GrillCookingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "grill_cooking"),
            helper.createDrawable(Plugin.TEXTURES, 135, 57, 121, 79),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.GRILL_RED.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ProcessingRecipe.Item> holder, IFocusGroup focuses)
    {
        ProcessingRecipe.Item recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 6).add(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 31).add(recipe.getResult().create()); // TODO 26.1.1 test
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 71, 3).add(new ItemStack(ModItems.SPATULA.get()));
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 133, 136, 24, 17), recipe.getTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(RecipeHolder<ProcessingRecipe.Item> holder, IRecipeSlotsView view, GuiGraphicsExtractor extractor, double mouseX, double mouseY)
    {
        super.draw(holder, view, extractor, mouseX, mouseY);
        ProcessingRecipe.Item recipe = holder.value();
        this.arrow.draw(extractor, 68, 31);
        this.drawSeconds(extractor, 80, 50, recipe.getTime());
        if(recipe.getType() == net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING)
        {
            extractor.fill(99, 5, 99 + 16, 5 + 16, 0x33000000);
            extractor.fakeItem(this.campfireStack, 99, 5);
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<ProcessingRecipe.Item> holder, IRecipeSlotsView view, double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 5, 15, 57, 61) && !ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 26, 6, 16, 16))
        {
            tooltip.addAll(Plugin.getItemTooltip(ModBlocks.GRILL_RED.get()));
        }
        else if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 99, 5, 16, 16))
        {
            tooltip.add(Utils.translation("gui", "jei_campfire_info"));
        }
    }
}
