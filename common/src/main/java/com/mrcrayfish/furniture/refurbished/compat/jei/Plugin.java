package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Author: MrCrayfish
 */
@JeiPlugin
public class Plugin implements IModPlugin
{
    static final ResourceLocation TEXTURES = Utils.resource("textures/gui/jei.png");
    static final DecimalFormat FORMATTER = new DecimalFormat("0.##s");

    @Override
    public ResourceLocation getPluginUid()
    {
        return Utils.resource("plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FreezerSolidifyingCategory(helper));
        registration.addRecipeCategories(new CuttingBoardCategory(helper));
        registration.addRecipeCategories(new FryingPanCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        RecipeManager manager = getRecipeManager();
        registration.addRecipes(FreezerSolidifyingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.FREEZER_SOLIDIFYING.get()));
        registration.addRecipes(CuttingBoardCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.CUTTING_BOARD_SLICING.get()));
        registration.addRecipes(FryingPanCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.FRYING_PAN_COOKING.get()));
    }

    static RecipeManager getRecipeManager()
    {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        return listener.getRecipeManager();
    }

    static Font getFont()
    {
        return Minecraft.getInstance().font;
    }

    static ItemStack getResult(Recipe<?> recipe)
    {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        return recipe.getResultItem(listener.registryAccess());
    }
}
