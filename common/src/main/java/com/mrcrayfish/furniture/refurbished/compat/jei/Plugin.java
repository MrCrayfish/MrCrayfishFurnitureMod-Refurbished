package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

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
        registration.addRecipeCategories(new CuttingBoardSlicingCategory(helper));
        registration.addRecipeCategories(new FryingPanCookingCategory(helper));
        registration.addRecipeCategories(new MicrowaveHeatingCategory(helper));
        registration.addRecipeCategories(new RecycleBinRecyclingCategory(helper));
        registration.addRecipeCategories(new ToasterToastingCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        RecipeManager manager = getRecipeManager();
        registration.addRecipes(FreezerSolidifyingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.FREEZER_SOLIDIFYING.get()));
        registration.addRecipes(CuttingBoardSlicingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.CUTTING_BOARD_SLICING.get()));
        registration.addRecipes(FryingPanCookingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.FRYING_PAN_COOKING.get()));
        registration.addRecipes(MicrowaveHeatingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.MICROWAVE_HEATING.get()));
        registration.addRecipes(RecycleBinRecyclingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.RECYCLE_BIN_RECYCLING.get()));
        registration.addRecipes(ToasterToastingCategory.TYPE, manager.getAllRecipesFor(ModRecipeTypes.TOASTER_HEATING.get()));
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

    static List<ItemStack> getTagItems(TagKey<Item> tag)
    {
        return StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(tag).spliterator(), false).map(holder -> {
            return new ItemStack(holder.value());
        }).toList();
    }

    static List<Component> getItemTooltip(ItemLike item)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean advanced = minecraft.options.advancedItemTooltips;
        return new ItemStack(item).getTooltipLines(player, advanced ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }
}
