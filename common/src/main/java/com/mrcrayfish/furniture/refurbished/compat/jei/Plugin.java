package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.compat.jei.categories.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
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
    public static final Identifier TEXTURES = Utils.resource("textures/gui/jei.png");
    public static final Identifier TEXTURES_2 = Utils.resource("textures/gui/jei2.png");
    public static final DecimalFormat FORMATTER = new DecimalFormat("0.##s");

    @Override
    public Identifier getPluginUid()
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
        registration.addRecipes(FreezerSolidifyingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.FREEZER_SOLIDIFYING.get()));
        registration.addRecipes(CuttingBoardSlicingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.CUTTING_BOARD_SLICING.get()));
        registration.addRecipes(FryingPanCookingCategory.TYPE.get(), this.getFryingPanRecipes());
        registration.addRecipes(MicrowaveHeatingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.MICROWAVE_HEATING.get()));
        registration.addRecipes(ToasterToastingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.TOASTER_HEATING.get()));
        registration.addRecipes(GrillCookingCategory.TYPE.get(), this.getGrillRecipes());
        registration.addRecipes(CuttingBoardCombiningCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.CUTTING_BOARD_COMBINING.get()));
        registration.addRecipes(WorkbenchConstructingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.WORKBENCH_CONSTRUCTING.get()));
        registration.addRecipes(OvenBakingCategory.TYPE.get(), this.getRecipes(ModRecipeTypes.OVEN_BAKING.get()));

        // TODO ingredient info
        //registration.addIngredientInfo(new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get()), VanillaTypes.ITEM_STACK, Utils.translation("jei_ingredient_info", "electricity_generator"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addCraftingStation(RecipeTypes.SMELTING_FUEL, new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get()));
        registration.addCraftingStation(RecipeTypes.SMELTING_FUEL, new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_DARK.get()));
        registration.addCraftingStation(RecipeTypes.CAMPFIRE_COOKING, new ItemStack(ModBlocks.FRYING_PAN.get()));
        registration.addCraftingStation(RecipeTypes.CAMPFIRE_COOKING, new ItemStack(ModBlocks.GRILL_RED.get()));
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

    private <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getRecipes(RecipeType<T> type)
    {
        return List.copyOf(SyncedRecipes.getMap().byType(type));
    }

    private List<RecipeHolder<ProcessingRecipe.Item>> getFryingPanRecipes()
    {
        List<RecipeHolder<ProcessingRecipe.Item>> holders = new ArrayList<>();
        holders.addAll(this.getRecipes(ModRecipeTypes.FRYING_PAN_COOKING.get()));
        return this.mergeWithCampfireRecipeHolders(holders);
    }

    private List<RecipeHolder<ProcessingRecipe.Item>> getGrillRecipes()
    {
        List<RecipeHolder<ProcessingRecipe.Item>> holders = new ArrayList<>();
        holders.addAll(this.getRecipes(ModRecipeTypes.GRILL_COOKING.get()));
        return this.mergeWithCampfireRecipeHolders(holders);
    }

    private List<RecipeHolder<ProcessingRecipe.Item>> mergeWithCampfireRecipeHolders(List<RecipeHolder<ProcessingRecipe.Item>> holders)
    {
        if(Config.SERVER.recipes.inheritCampfireRecipes.get())
        {
            holders.addAll(this.getRecipes(RecipeType.CAMPFIRE_COOKING).stream().map(holder -> {
                return Pair.of(holder.id(), ProcessingRecipe.Item.fromCookingRecipe(holder.value(), getRegistryAccess()));
            }).map(pair -> {
                ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, pair.left().identifier());
                return new RecipeHolder<>(key, pair.right());
            }).toList());
        }
        return holders;
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
        return new ItemStack(item).getTooltipLines(Item.TooltipContext.of(minecraft.level), player, advanced ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }
}
