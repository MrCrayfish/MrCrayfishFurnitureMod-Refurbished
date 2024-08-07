package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class OvenBakingCategory implements IRecipeCategory<OvenBakingRecipe>
{
    public static final RecipeType<OvenBakingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "oven_baking", OvenBakingRecipe.class);

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable arrow;

    public OvenBakingCategory(IGuiHelper helper)
    {
        this.helper = helper;
        this.background = helper.createDrawable(Plugin.TEXTURES_2, 177, 0, 79, 62);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.STOVE_LIGHT.get()));
    }

    @Override
    public RecipeType<OvenBakingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "oven_baking");
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
    public void setRecipe(IRecipeLayoutBuilder builder, OvenBakingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 58, 5).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 58, 41).addItemStack(Plugin.getResult(recipe));
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES_2, 160, 0, 17, 16), recipe.getTime(), IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public void draw(OvenBakingRecipe recipe, IRecipeSlotsView view, PoseStack poseStack, double mouseX, double mouseY)
    {
        int offset = (int) (Util.getMillis() / 100) % 3;
        RenderSystem.setShaderTexture(0, Plugin.TEXTURES_2);
        GuiComponent.blit(poseStack, 5, 10, 120, offset * 40, 40, 40);
        this.arrow.draw(poseStack, 57, 23);
    }
}
