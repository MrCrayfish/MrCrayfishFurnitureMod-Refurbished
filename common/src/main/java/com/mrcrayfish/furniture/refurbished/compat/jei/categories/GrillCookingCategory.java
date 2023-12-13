package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class GrillCookingCategory extends FurnitureRecipeCategory<AbstractCookingRecipe>
{
    public static final RecipeType<AbstractCookingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "grill_cooking", AbstractCookingRecipe.class);

    private final ItemStack campfireStack = new ItemStack(Items.CAMPFIRE);
    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable arrow;

    public GrillCookingCategory(IGuiHelper helper)
    {
        this.helper = helper;
        this.background = helper.createDrawable(Plugin.TEXTURES, 135, 57, 121, 79);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.GRILL_RED.get()));
    }

    @Override
    public RecipeType<AbstractCookingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "grill_cooking");
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AbstractCookingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 31).addItemStack(Plugin.getResult(recipe));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 71, 3).addItemStack(new ItemStack(ModItems.SPATULA.get()));
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES, 135, 136, 24, 17), recipe.getCookingTime(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(AbstractCookingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.arrow.draw(graphics, 68, 31);
        this.drawSeconds(graphics, 80, 50, recipe.getCookingTime());
        if(recipe instanceof CampfireCookingRecipe)
        {
            graphics.fill(102, 5, 102 + 16, 5 + 16, 0x33000000);
            graphics.renderFakeItem(this.campfireStack, 102, 5);
        }
    }

    @Override
    public List<Component> getTooltipStrings(AbstractCookingRecipe recipe, IRecipeSlotsView view, double mouseX, double mouseY)
    {
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 5, 15, 57, 61) && !ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 26, 6, 16, 16))
        {
            return Plugin.getItemTooltip(ModBlocks.GRILL_RED.get());
        }
        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, 103, 5, 16, 16))
        {
            return List.of(Utils.translation("gui", "jei_campfire_info"));
        }
        return super.getTooltipStrings(recipe, view, mouseX, mouseY);
    }
}
