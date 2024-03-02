package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
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

/**
 * Author: MrCrayfish
 */
public class RecycleBinRecyclingCategory extends FurnitureRecipeCategory<RecycleBinRecyclingRecipe>
{
    public static final RecipeType<RecycleBinRecyclingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "recycle_bin_recycling", RecycleBinRecyclingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    public RecycleBinRecyclingCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 190, 118, 64);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.RECYCLE_BIN.get()));
        this.arrow = helper.createAnimatedDrawable(helper.createDrawable(Plugin.TEXTURES, 93, 173, 24, 17), Config.SERVER.recycleBin.processingTime.get(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<RecycleBinRecyclingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "recycle_bin_recycling");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecycleBinRecyclingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 24).addItemStack(recipe.getInput());
        for(int i = 0; i < recipe.getOutput().size(); i++)
        {
            int slotX = 60 + (i % 3) * 18;
            int slotY = 6 + (i / 3) * 18;
            builder.addSlot(RecipeIngredientRole.OUTPUT, slotX, slotY).addItemStack(recipe.getOutput().get(i));
        }
    }

    @Override
    public void draw(RecycleBinRecyclingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.arrow.draw(graphics, 29, 23);
    }
}
