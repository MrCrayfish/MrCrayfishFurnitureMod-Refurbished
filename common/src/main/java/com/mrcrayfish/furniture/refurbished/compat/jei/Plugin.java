package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.compat.jei.categories.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.gui.handlers.IScreenHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

/**
 * Author: MrCrayfish
 */
@JeiPlugin
public class Plugin implements IModPlugin
{
    public static final ResourceLocation TEXTURES = Utils.resource("textures/gui/jei.png");
    public static final ResourceLocation TEXTURES_2 = Utils.resource("textures/gui/jei2.png");
    public static final DecimalFormat FORMATTER = new DecimalFormat("0.##s");

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
        registration.addRecipeCategories(new ToasterToastingCategory(helper));
        registration.addRecipeCategories(new GrillCookingCategory(helper));
        registration.addRecipeCategories(new CuttingBoardCombiningCategory(helper));
        registration.addRecipeCategories(new WorkbenchConstructingCategory(helper));
        registration.addRecipeCategories(new OvenBakingCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        RecipeManager manager = getRecipeManager();
        registration.addRecipes(FreezerSolidifyingCategory.TYPE, this.getRecipes(ModRecipeTypes.FREEZER_SOLIDIFYING.get()));
        registration.addRecipes(CuttingBoardSlicingCategory.TYPE, this.getRecipes(ModRecipeTypes.CUTTING_BOARD_SLICING.get()));
        registration.addRecipes(FryingPanCookingCategory.TYPE, this.getFryingPanRecipes());
        registration.addRecipes(MicrowaveHeatingCategory.TYPE, this.getRecipes(ModRecipeTypes.MICROWAVE_HEATING.get()));
        registration.addRecipes(ToasterToastingCategory.TYPE, this.getRecipes(ModRecipeTypes.TOASTER_HEATING.get()));
        registration.addRecipes(GrillCookingCategory.TYPE, this.getGrillRecipes());
        registration.addRecipes(CuttingBoardCombiningCategory.TYPE, this.getRecipes(ModRecipeTypes.CUTTING_BOARD_COMBINING.get()));
        registration.addRecipes(WorkbenchConstructingCategory.TYPE, this.getRecipes(ModRecipeTypes.WORKBENCH_CONSTRUCTING.get()));
        registration.addRecipes(OvenBakingCategory.TYPE, this.getRecipes(ModRecipeTypes.OVEN_BAKING.get()));

        // TODO ingredient info
        //registration.addIngredientInfo(new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get()), VanillaTypes.ITEM_STACK, Utils.translation("jei_ingredient_info", "electricity_generator"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get()), RecipeTypes.FUELING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_DARK.get()), RecipeTypes.FUELING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.FRYING_PAN.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.GRILL_RED.get()), RecipeTypes.CAMPFIRE_COOKING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addGuiScreenHandler(ComputerScreen.class, new IScreenHandler<>() {
            // Return null to prevent JEI showing when using computer
            @Override
            public @Nullable IGuiProperties apply(ComputerScreen guiScreen)
            {
                return null;
            }
        });
    }

    private <C extends Container, T extends Recipe<C>> List<T> getRecipes(RecipeType<T> type)
    {
        return getRecipeManager().getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    private List<ProcessingRecipe> getFryingPanRecipes()
    {
        List<ProcessingRecipe> recipes = new ArrayList<>();
        recipes.addAll(this.getRecipes(ModRecipeTypes.FRYING_PAN_COOKING.get()));
        recipes.addAll(this.getRecipes(RecipeType.CAMPFIRE_COOKING).stream().map(recipe -> {
            return ProcessingRecipe.Item.from(recipe, getRegistryAccess());
        }).toList());
        return recipes;
    }

    private List<ProcessingRecipe> getGrillRecipes()
    {
        List<ProcessingRecipe> recipes = new ArrayList<>();
        recipes.addAll(this.getRecipes(ModRecipeTypes.GRILL_COOKING.get()));
        recipes.addAll(this.getRecipes(RecipeType.CAMPFIRE_COOKING).stream().map(recipe -> {
            return ProcessingRecipe.Item.from(recipe, getRegistryAccess());
        }).toList());
        return recipes;
    }

    public static RecipeManager getRecipeManager()
    {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        return listener.getRecipeManager();
    }

    public static Font getFont()
    {
        return Minecraft.getInstance().font;
    }

    private static RegistryAccess getRegistryAccess()
    {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        return listener.registryAccess();
    }

    public static ItemStack getResult(Recipe<?> recipe)
    {
        return recipe.getResultItem(getRegistryAccess());
    }

    public static List<ItemStack> getTagItems(TagKey<Item> tag)
    {
        return StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(tag).spliterator(), false).map(holder -> {
            return new ItemStack(holder.value());
        }).toList();
    }

    public static List<Component> getItemTooltip(ItemLike item)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean advanced = minecraft.options.advancedItemTooltips;
        return new ItemStack(item).getTooltipLines(player, advanced ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }
}
