package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class OvenBakingCategory extends FurnitureRecipeCategory<OvenBakingRecipe>
{
    public static final Supplier<IRecipeHolderType<OvenBakingRecipe>> TYPE = IRecipeHolderType.createDeferred(ModRecipeTypes.OVEN_BAKING::get);

    private final IGuiHelper helper;
    private IDrawable arrow;

    public OvenBakingCategory(IGuiHelper helper)
    {
        super(TYPE,
            Utils.translation("jei_category", "oven_baking"),
            helper.createDrawable(Plugin.TEXTURES_2, 177, 0, 79, 62),
            helper.createDrawableItemStack(new ItemStack(ModBlocks.STOVE_LIGHT.get()))
        );
        this.helper = helper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<OvenBakingRecipe> holder, IFocusGroup focuses)
    {
        OvenBakingRecipe recipe = holder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 58, 5).add(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 58, 41).add(recipe.getResult());
        this.arrow = this.helper.createAnimatedDrawable(this.helper.createDrawable(Plugin.TEXTURES_2, 160, 0, 17, 16), recipe.getTime(), IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public void draw(RecipeHolder<OvenBakingRecipe> holder, IRecipeSlotsView view, GuiGraphicsExtractor extractor, double mouseX, double mouseY)
    {
        super.draw(holder, view, extractor, mouseX, mouseY);
        int offset = (int) (Util.getMillis() / 100) % 3;
        extractor.blit(RenderPipelines.GUI_TEXTURED, Plugin.TEXTURES_2, 5, 10, 120, offset * 40, 40, 40, 40, 40, 256, 256);
        this.arrow.draw(extractor, 57, 23);
        this.drawSeconds(extractor, 14, 53, holder.value().getTime());
    }
}
