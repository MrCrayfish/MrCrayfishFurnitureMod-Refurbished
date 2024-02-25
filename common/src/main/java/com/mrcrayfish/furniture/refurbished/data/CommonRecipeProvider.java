package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.SinkFluidTransmutingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class CommonRecipeProvider
{
    private final Consumer<FinishedRecipe> consumer;
    private final ConditionalModConsumer modLoadedConsumer;
    private final Function<ItemLike, CriterionTriggerInstance> hasItem;
    private final Function<TagKey<Item>, CriterionTriggerInstance> hasTag;

    public CommonRecipeProvider(Consumer<FinishedRecipe> consumer, ConditionalModConsumer modLoadedConsumer, Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag)
    {
        this.consumer = consumer;
        this.modLoadedConsumer = modLoadedConsumer;
        this.hasItem = hasItem;
        this.hasTag = hasTag;
    }

    public void run()
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get())
                .pattern("III")
                .pattern("IRI")
                .pattern("IFI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE_BLOCK)
                .define('F', Items.BLAST_FURNACE)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_redstone", this.hasItem.apply(Items.REDSTONE))
                .save(this.consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELECTRICITY_GENERATOR_DARK.get())
                .requires(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get())
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_redstone", this.hasItem.apply(Items.REDSTONE))
                .save(this.consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WORKBENCH.get())
                .pattern("SBS")
                .pattern("PRP")
                .pattern("PIP")
                .define('S', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .define('B', Items.STONECUTTER)
                .define('R', Items.REDSTONE_BLOCK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_slabs", this.hasTag.apply(ItemTags.WOODEN_SLABS))
                .unlockedBy("has_planks", this.hasTag.apply(ItemTags.PLANKS))
                .unlockedBy("has_redstone", this.hasItem.apply(Items.REDSTONE))
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .save(this.consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WRENCH.get())
                .pattern(" I ")
                .pattern("SII")
                .pattern("LS ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_string", this.hasItem.apply(Items.STRING))
                .unlockedBy("has_leather", this.hasItem.apply(Items.LEATHER))
                .save(this.consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPATULA.get())
                .pattern(" I ")
                .pattern("SIS")
                .pattern(" L ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_string", this.hasItem.apply(Items.STRING))
                .unlockedBy("has_leather", this.hasItem.apply(Items.LEATHER))
                .save(this.consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KNIFE.get())
                .pattern(" S ")
                .pattern("LII")
                .pattern(" S ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_string", this.hasItem.apply(Items.STRING))
                .unlockedBy("has_leather", this.hasItem.apply(Items.LEATHER))
                .save(this.consumer);

        this.table(Blocks.OAK_PLANKS, ModBlocks.TABLE_OAK.get());
        this.table(Blocks.SPRUCE_PLANKS, ModBlocks.TABLE_SPRUCE.get());
        this.table(Blocks.BIRCH_PLANKS, ModBlocks.TABLE_BIRCH.get());
        this.table(Blocks.JUNGLE_PLANKS, ModBlocks.TABLE_JUNGLE.get());
        this.table(Blocks.ACACIA_PLANKS, ModBlocks.TABLE_ACACIA.get());
        this.table(Blocks.DARK_OAK_PLANKS, ModBlocks.TABLE_DARK_OAK.get());
        this.table(Blocks.MANGROVE_PLANKS, ModBlocks.TABLE_MANGROVE.get());
        this.table(Blocks.CHERRY_PLANKS, ModBlocks.TABLE_CHERRY.get());
        this.table(Blocks.CRIMSON_PLANKS, ModBlocks.TABLE_CRIMSON.get());
        this.table(Blocks.WARPED_PLANKS, ModBlocks.TABLE_WARPED.get());
        this.chair(Blocks.OAK_PLANKS, ModBlocks.CHAIR_OAK.get());
        this.chair(Blocks.SPRUCE_PLANKS, ModBlocks.CHAIR_SPRUCE.get());
        this.chair(Blocks.BIRCH_PLANKS, ModBlocks.CHAIR_BIRCH.get());
        this.chair(Blocks.JUNGLE_PLANKS, ModBlocks.CHAIR_JUNGLE.get());
        this.chair(Blocks.ACACIA_PLANKS, ModBlocks.CHAIR_ACACIA.get());
        this.chair(Blocks.DARK_OAK_PLANKS, ModBlocks.CHAIR_DARK_OAK.get());
        this.chair(Blocks.MANGROVE_PLANKS, ModBlocks.CHAIR_MANGROVE.get());
        this.chair(Blocks.CHERRY_PLANKS, ModBlocks.CHAIR_CHERRY.get());
        this.chair(Blocks.CRIMSON_PLANKS, ModBlocks.CHAIR_CRIMSON.get());
        this.chair(Blocks.WARPED_PLANKS, ModBlocks.CHAIR_WARPED.get());
        this.desk(Blocks.OAK_PLANKS, ModBlocks.DESK_OAK.get());
        this.desk(Blocks.SPRUCE_PLANKS, ModBlocks.DESK_SPRUCE.get());
        this.desk(Blocks.BIRCH_PLANKS, ModBlocks.DESK_BIRCH.get());
        this.desk(Blocks.JUNGLE_PLANKS, ModBlocks.DESK_JUNGLE.get());
        this.desk(Blocks.ACACIA_PLANKS, ModBlocks.DESK_ACACIA.get());
        this.desk(Blocks.DARK_OAK_PLANKS, ModBlocks.DESK_DARK_OAK.get());
        this.desk(Blocks.MANGROVE_PLANKS, ModBlocks.DESK_MANGROVE.get());
        this.desk(Blocks.CHERRY_PLANKS, ModBlocks.DESK_CHERRY.get());
        this.desk(Blocks.CRIMSON_PLANKS, ModBlocks.DESK_CRIMSON.get());
        this.desk(Blocks.WARPED_PLANKS, ModBlocks.DESK_WARPED.get());
        this.drawer(Blocks.OAK_PLANKS, ModBlocks.DRAWER_OAK.get());
        this.drawer(Blocks.SPRUCE_PLANKS, ModBlocks.DRAWER_SPRUCE.get());
        this.drawer(Blocks.BIRCH_PLANKS, ModBlocks.DRAWER_BIRCH.get());
        this.drawer(Blocks.JUNGLE_PLANKS, ModBlocks.DRAWER_JUNGLE.get());
        this.drawer(Blocks.ACACIA_PLANKS, ModBlocks.DRAWER_ACACIA.get());
        this.drawer(Blocks.DARK_OAK_PLANKS, ModBlocks.DRAWER_DARK_OAK.get());
        this.drawer(Blocks.MANGROVE_PLANKS, ModBlocks.DRAWER_MANGROVE.get());
        this.drawer(Blocks.CHERRY_PLANKS, ModBlocks.DRAWER_CHERRY.get());
        this.drawer(Blocks.CRIMSON_PLANKS, ModBlocks.DRAWER_CRIMSON.get());
        this.drawer(Blocks.WARPED_PLANKS, ModBlocks.DRAWER_WARPED.get());
        this.woodenKitchenCabinetry(Blocks.OAK_PLANKS, ModBlocks.KITCHEN_CABINETRY_OAK.get());
        this.woodenKitchenCabinetry(Blocks.SPRUCE_PLANKS, ModBlocks.KITCHEN_CABINETRY_SPRUCE.get());
        this.woodenKitchenCabinetry(Blocks.BIRCH_PLANKS, ModBlocks.KITCHEN_CABINETRY_BIRCH.get());
        this.woodenKitchenCabinetry(Blocks.JUNGLE_PLANKS, ModBlocks.KITCHEN_CABINETRY_JUNGLE.get());
        this.woodenKitchenCabinetry(Blocks.ACACIA_PLANKS, ModBlocks.KITCHEN_CABINETRY_ACACIA.get());
        this.woodenKitchenCabinetry(Blocks.DARK_OAK_PLANKS, ModBlocks.KITCHEN_CABINETRY_DARK_OAK.get());
        this.woodenKitchenCabinetry(Blocks.MANGROVE_PLANKS, ModBlocks.KITCHEN_CABINETRY_MANGROVE.get());
        this.woodenKitchenCabinetry(Blocks.CHERRY_PLANKS, ModBlocks.KITCHEN_CABINETRY_CHERRY.get());
        this.woodenKitchenCabinetry(Blocks.CRIMSON_PLANKS, ModBlocks.KITCHEN_CABINETRY_CRIMSON.get());
        this.woodenKitchenCabinetry(Blocks.WARPED_PLANKS, ModBlocks.KITCHEN_CABINETRY_WARPED.get());
        this.woodenKitchenDrawer(Blocks.OAK_PLANKS, ModBlocks.KITCHEN_DRAWER_OAK.get());
        this.woodenKitchenDrawer(Blocks.SPRUCE_PLANKS, ModBlocks.KITCHEN_DRAWER_SPRUCE.get());
        this.woodenKitchenDrawer(Blocks.BIRCH_PLANKS, ModBlocks.KITCHEN_DRAWER_BIRCH.get());
        this.woodenKitchenDrawer(Blocks.JUNGLE_PLANKS, ModBlocks.KITCHEN_DRAWER_JUNGLE.get());
        this.woodenKitchenDrawer(Blocks.ACACIA_PLANKS, ModBlocks.KITCHEN_DRAWER_ACACIA.get());
        this.woodenKitchenDrawer(Blocks.DARK_OAK_PLANKS, ModBlocks.KITCHEN_DRAWER_DARK_OAK.get());
        this.woodenKitchenDrawer(Blocks.MANGROVE_PLANKS, ModBlocks.KITCHEN_DRAWER_MANGROVE.get());
        this.woodenKitchenDrawer(Blocks.CHERRY_PLANKS, ModBlocks.KITCHEN_DRAWER_CHERRY.get());
        this.woodenKitchenDrawer(Blocks.CRIMSON_PLANKS, ModBlocks.KITCHEN_DRAWER_CRIMSON.get());
        this.woodenKitchenDrawer(Blocks.WARPED_PLANKS, ModBlocks.KITCHEN_DRAWER_WARPED.get());
        this.woodenKitchenSink(Blocks.OAK_PLANKS, ModBlocks.KITCHEN_SINK_OAK.get());
        this.woodenKitchenSink(Blocks.SPRUCE_PLANKS, ModBlocks.KITCHEN_SINK_SPRUCE.get());
        this.woodenKitchenSink(Blocks.BIRCH_PLANKS, ModBlocks.KITCHEN_SINK_BIRCH.get());
        this.woodenKitchenSink(Blocks.JUNGLE_PLANKS, ModBlocks.KITCHEN_SINK_JUNGLE.get());
        this.woodenKitchenSink(Blocks.ACACIA_PLANKS, ModBlocks.KITCHEN_SINK_ACACIA.get());
        this.woodenKitchenSink(Blocks.DARK_OAK_PLANKS, ModBlocks.KITCHEN_SINK_DARK_OAK.get());
        this.woodenKitchenSink(Blocks.MANGROVE_PLANKS, ModBlocks.KITCHEN_SINK_MANGROVE.get());
        this.woodenKitchenSink(Blocks.CHERRY_PLANKS, ModBlocks.KITCHEN_SINK_CHERRY.get());
        this.woodenKitchenSink(Blocks.CRIMSON_PLANKS, ModBlocks.KITCHEN_SINK_CRIMSON.get());
        this.woodenKitchenSink(Blocks.WARPED_PLANKS, ModBlocks.KITCHEN_SINK_WARPED.get());
        this.woodenKitchenStorageCabinet(Blocks.OAK_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_OAK.get());
        this.woodenKitchenStorageCabinet(Blocks.SPRUCE_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_SPRUCE.get());
        this.woodenKitchenStorageCabinet(Blocks.BIRCH_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_BIRCH.get());
        this.woodenKitchenStorageCabinet(Blocks.JUNGLE_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_JUNGLE.get());
        this.woodenKitchenStorageCabinet(Blocks.ACACIA_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_ACACIA.get());
        this.woodenKitchenStorageCabinet(Blocks.DARK_OAK_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_DARK_OAK.get());
        this.woodenKitchenStorageCabinet(Blocks.MANGROVE_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_MANGROVE.get());
        this.woodenKitchenStorageCabinet(Blocks.CHERRY_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_CHERRY.get());
        this.woodenKitchenStorageCabinet(Blocks.CRIMSON_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_CRIMSON.get());
        this.woodenKitchenStorageCabinet(Blocks.WARPED_PLANKS, ModBlocks.KITCHEN_STORAGE_CABINET_WARPED.get());
        this.colouredKitchenCabinetry(Items.WHITE_DYE, ModBlocks.KITCHEN_CABINETRY_WHITE.get());
        this.colouredKitchenCabinetry(Items.ORANGE_DYE, ModBlocks.KITCHEN_CABINETRY_ORANGE.get());
        this.colouredKitchenCabinetry(Items.MAGENTA_DYE, ModBlocks.KITCHEN_CABINETRY_MAGENTA.get());
        this.colouredKitchenCabinetry(Items.LIGHT_BLUE_DYE, ModBlocks.KITCHEN_CABINETRY_LIGHT_BLUE.get());
        this.colouredKitchenCabinetry(Items.YELLOW_DYE, ModBlocks.KITCHEN_CABINETRY_YELLOW.get());
        this.colouredKitchenCabinetry(Items.LIME_DYE, ModBlocks.KITCHEN_CABINETRY_LIME.get());
        this.colouredKitchenCabinetry(Items.PINK_DYE, ModBlocks.KITCHEN_CABINETRY_PINK.get());
        this.colouredKitchenCabinetry(Items.GRAY_DYE, ModBlocks.KITCHEN_CABINETRY_GRAY.get());
        this.colouredKitchenCabinetry(Items.LIGHT_GRAY_DYE, ModBlocks.KITCHEN_CABINETRY_LIGHT_GRAY.get());
        this.colouredKitchenCabinetry(Items.CYAN_DYE, ModBlocks.KITCHEN_CABINETRY_CYAN.get());
        this.colouredKitchenCabinetry(Items.PURPLE_DYE, ModBlocks.KITCHEN_CABINETRY_PURPLE.get());
        this.colouredKitchenCabinetry(Items.BLUE_DYE, ModBlocks.KITCHEN_CABINETRY_BLUE.get());
        this.colouredKitchenCabinetry(Items.BROWN_DYE, ModBlocks.KITCHEN_CABINETRY_BROWN.get());
        this.colouredKitchenCabinetry(Items.GREEN_DYE, ModBlocks.KITCHEN_CABINETRY_GREEN.get());
        this.colouredKitchenCabinetry(Items.RED_DYE, ModBlocks.KITCHEN_CABINETRY_RED.get());
        this.colouredKitchenCabinetry(Items.BLACK_DYE, ModBlocks.KITCHEN_CABINETRY_BLACK.get());
        this.colouredKitchenDrawer(Items.WHITE_DYE, ModBlocks.KITCHEN_DRAWER_WHITE.get());
        this.colouredKitchenDrawer(Items.ORANGE_DYE, ModBlocks.KITCHEN_DRAWER_ORANGE.get());
        this.colouredKitchenDrawer(Items.MAGENTA_DYE, ModBlocks.KITCHEN_DRAWER_MAGENTA.get());
        this.colouredKitchenDrawer(Items.LIGHT_BLUE_DYE, ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get());
        this.colouredKitchenDrawer(Items.YELLOW_DYE, ModBlocks.KITCHEN_DRAWER_YELLOW.get());
        this.colouredKitchenDrawer(Items.LIME_DYE, ModBlocks.KITCHEN_DRAWER_LIME.get());
        this.colouredKitchenDrawer(Items.PINK_DYE, ModBlocks.KITCHEN_DRAWER_PINK.get());
        this.colouredKitchenDrawer(Items.GRAY_DYE, ModBlocks.KITCHEN_DRAWER_GRAY.get());
        this.colouredKitchenDrawer(Items.LIGHT_GRAY_DYE, ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get());
        this.colouredKitchenDrawer(Items.CYAN_DYE, ModBlocks.KITCHEN_DRAWER_CYAN.get());
        this.colouredKitchenDrawer(Items.PURPLE_DYE, ModBlocks.KITCHEN_DRAWER_PURPLE.get());
        this.colouredKitchenDrawer(Items.BLUE_DYE, ModBlocks.KITCHEN_DRAWER_BLUE.get());
        this.colouredKitchenDrawer(Items.BROWN_DYE, ModBlocks.KITCHEN_DRAWER_BROWN.get());
        this.colouredKitchenDrawer(Items.GREEN_DYE, ModBlocks.KITCHEN_DRAWER_GREEN.get());
        this.colouredKitchenDrawer(Items.RED_DYE, ModBlocks.KITCHEN_DRAWER_RED.get());
        this.colouredKitchenDrawer(Items.BLACK_DYE, ModBlocks.KITCHEN_DRAWER_BLACK.get());
        this.colouredKitchenSink(Items.WHITE_DYE, ModBlocks.KITCHEN_SINK_WHITE.get());
        this.colouredKitchenSink(Items.ORANGE_DYE, ModBlocks.KITCHEN_SINK_ORANGE.get());
        this.colouredKitchenSink(Items.MAGENTA_DYE, ModBlocks.KITCHEN_SINK_MAGENTA.get());
        this.colouredKitchenSink(Items.LIGHT_BLUE_DYE, ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get());
        this.colouredKitchenSink(Items.YELLOW_DYE, ModBlocks.KITCHEN_SINK_YELLOW.get());
        this.colouredKitchenSink(Items.LIME_DYE, ModBlocks.KITCHEN_SINK_LIME.get());
        this.colouredKitchenSink(Items.PINK_DYE, ModBlocks.KITCHEN_SINK_PINK.get());
        this.colouredKitchenSink(Items.GRAY_DYE, ModBlocks.KITCHEN_SINK_GRAY.get());
        this.colouredKitchenSink(Items.LIGHT_GRAY_DYE, ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get());
        this.colouredKitchenSink(Items.CYAN_DYE, ModBlocks.KITCHEN_SINK_CYAN.get());
        this.colouredKitchenSink(Items.PURPLE_DYE, ModBlocks.KITCHEN_SINK_PURPLE.get());
        this.colouredKitchenSink(Items.BLUE_DYE, ModBlocks.KITCHEN_SINK_BLUE.get());
        this.colouredKitchenSink(Items.BROWN_DYE, ModBlocks.KITCHEN_SINK_BROWN.get());
        this.colouredKitchenSink(Items.GREEN_DYE, ModBlocks.KITCHEN_SINK_GREEN.get());
        this.colouredKitchenSink(Items.RED_DYE, ModBlocks.KITCHEN_SINK_RED.get());
        this.colouredKitchenSink(Items.BLACK_DYE, ModBlocks.KITCHEN_SINK_BLACK.get());
        this.colouredKitchenStorageCabinet(Items.WHITE_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_WHITE.get());
        this.colouredKitchenStorageCabinet(Items.ORANGE_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_ORANGE.get());
        this.colouredKitchenStorageCabinet(Items.MAGENTA_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_MAGENTA.get());
        this.colouredKitchenStorageCabinet(Items.LIGHT_BLUE_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_LIGHT_BLUE.get());
        this.colouredKitchenStorageCabinet(Items.YELLOW_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_YELLOW.get());
        this.colouredKitchenStorageCabinet(Items.LIME_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_LIME.get());
        this.colouredKitchenStorageCabinet(Items.PINK_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_PINK.get());
        this.colouredKitchenStorageCabinet(Items.GRAY_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_GRAY.get());
        this.colouredKitchenStorageCabinet(Items.LIGHT_GRAY_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_LIGHT_GRAY.get());
        this.colouredKitchenStorageCabinet(Items.CYAN_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_CYAN.get());
        this.colouredKitchenStorageCabinet(Items.PURPLE_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_PURPLE.get());
        this.colouredKitchenStorageCabinet(Items.BLUE_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_BLUE.get());
        this.colouredKitchenStorageCabinet(Items.BROWN_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_BROWN.get());
        this.colouredKitchenStorageCabinet(Items.GREEN_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_GREEN.get());
        this.colouredKitchenStorageCabinet(Items.RED_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_RED.get());
        this.colouredKitchenStorageCabinet(Items.BLACK_DYE, ModBlocks.KITCHEN_STORAGE_CABINET_BLACK.get());
        this.toaster(ModBlocks.TOASTER_LIGHT.get(), ModBlocks.TOASTER_DARK.get());
        this.microwave(ModBlocks.MICROWAVE_LIGHT.get(), ModBlocks.MICROWAVE_DARK.get());
        this.stove(ModBlocks.STOVE_LIGHT.get(), ModBlocks.STOVE_DARK.get());
        this.rangeHood(ModBlocks.RANGE_HOOD_LIGHT.get(), ModBlocks.RANGE_HOOD_DARK.get());
        this.fryingPan(ModBlocks.FRYING_PAN.get());
        this.recyclingBin(ModBlocks.RECYCLE_BIN.get());
        this.cuttingBoard(Blocks.OAK_PLANKS, ModBlocks.CUTTING_BOARD_OAK.get());
        this.cuttingBoard(Blocks.SPRUCE_PLANKS, ModBlocks.CUTTING_BOARD_SPRUCE.get());
        this.cuttingBoard(Blocks.BIRCH_PLANKS, ModBlocks.CUTTING_BOARD_BIRCH.get());
        this.cuttingBoard(Blocks.JUNGLE_PLANKS, ModBlocks.CUTTING_BOARD_JUNGLE.get());
        this.cuttingBoard(Blocks.ACACIA_PLANKS, ModBlocks.CUTTING_BOARD_ACACIA.get());
        this.cuttingBoard(Blocks.DARK_OAK_PLANKS, ModBlocks.CUTTING_BOARD_DARK_OAK.get());
        this.cuttingBoard(Blocks.MANGROVE_PLANKS, ModBlocks.CUTTING_BOARD_MANGROVE.get());
        this.cuttingBoard(Blocks.CHERRY_PLANKS, ModBlocks.CUTTING_BOARD_CHERRY.get());
        this.cuttingBoard(Blocks.CRIMSON_PLANKS, ModBlocks.CUTTING_BOARD_CRIMSON.get());
        this.cuttingBoard(Blocks.WARPED_PLANKS, ModBlocks.CUTTING_BOARD_WARPED.get());
        this.plate(ModBlocks.PLATE.get());
        this.crate(Blocks.OAK_PLANKS, ModBlocks.CRATE_OAK.get());
        this.crate(Blocks.SPRUCE_PLANKS, ModBlocks.CRATE_SPRUCE.get());
        this.crate(Blocks.BIRCH_PLANKS, ModBlocks.CRATE_BIRCH.get());
        this.crate(Blocks.JUNGLE_PLANKS, ModBlocks.CRATE_JUNGLE.get());
        this.crate(Blocks.ACACIA_PLANKS, ModBlocks.CRATE_ACACIA.get());
        this.crate(Blocks.DARK_OAK_PLANKS, ModBlocks.CRATE_DARK_OAK.get());
        this.crate(Blocks.MANGROVE_PLANKS, ModBlocks.CRATE_MANGROVE.get());
        this.crate(Blocks.CHERRY_PLANKS, ModBlocks.CRATE_CHERRY.get());
        this.crate(Blocks.CRIMSON_PLANKS, ModBlocks.CRATE_CRIMSON.get());
        this.crate(Blocks.WARPED_PLANKS, ModBlocks.CRATE_WARPED.get());
        this.grill(Items.WHITE_DYE, ModBlocks.GRILL_WHITE.get());
        this.grill(Items.ORANGE_DYE, ModBlocks.GRILL_ORANGE.get());
        this.grill(Items.MAGENTA_DYE, ModBlocks.GRILL_MAGENTA.get());
        this.grill(Items.LIGHT_BLUE_DYE, ModBlocks.GRILL_LIGHT_BLUE.get());
        this.grill(Items.YELLOW_DYE, ModBlocks.GRILL_YELLOW.get());
        this.grill(Items.LIME_DYE, ModBlocks.GRILL_LIME.get());
        this.grill(Items.PINK_DYE, ModBlocks.GRILL_PINK.get());
        this.grill(Items.GRAY_DYE, ModBlocks.GRILL_GRAY.get());
        this.grill(Items.LIGHT_GRAY_DYE, ModBlocks.GRILL_LIGHT_GRAY.get());
        this.grill(Items.CYAN_DYE, ModBlocks.GRILL_CYAN.get());
        this.grill(Items.PURPLE_DYE, ModBlocks.GRILL_PURPLE.get());
        this.grill(Items.BLUE_DYE, ModBlocks.GRILL_BLUE.get());
        this.grill(Items.BROWN_DYE, ModBlocks.GRILL_BROWN.get());
        this.grill(Items.GREEN_DYE, ModBlocks.GRILL_GREEN.get());
        this.grill(Items.RED_DYE, ModBlocks.GRILL_RED.get());
        this.grill(Items.BLACK_DYE, ModBlocks.GRILL_BLACK.get());
        this.cooler(Items.WHITE_DYE, ModBlocks.COOLER_WHITE.get());
        this.cooler(Items.ORANGE_DYE, ModBlocks.COOLER_ORANGE.get());
        this.cooler(Items.MAGENTA_DYE, ModBlocks.COOLER_MAGENTA.get());
        this.cooler(Items.LIGHT_BLUE_DYE, ModBlocks.COOLER_LIGHT_BLUE.get());
        this.cooler(Items.YELLOW_DYE, ModBlocks.COOLER_YELLOW.get());
        this.cooler(Items.LIME_DYE, ModBlocks.COOLER_LIME.get());
        this.cooler(Items.PINK_DYE, ModBlocks.COOLER_PINK.get());
        this.cooler(Items.GRAY_DYE, ModBlocks.COOLER_GRAY.get());
        this.cooler(Items.LIGHT_GRAY_DYE, ModBlocks.COOLER_LIGHT_GRAY.get());
        this.cooler(Items.CYAN_DYE, ModBlocks.COOLER_CYAN.get());
        this.cooler(Items.PURPLE_DYE, ModBlocks.COOLER_PURPLE.get());
        this.cooler(Items.BLUE_DYE, ModBlocks.COOLER_BLUE.get());
        this.cooler(Items.BROWN_DYE, ModBlocks.COOLER_BROWN.get());
        this.cooler(Items.GREEN_DYE, ModBlocks.COOLER_GREEN.get());
        this.cooler(Items.RED_DYE, ModBlocks.COOLER_RED.get());
        this.cooler(Items.BLACK_DYE, ModBlocks.COOLER_BLACK.get());
        this.mailbox(Blocks.OAK_PLANKS, ModBlocks.MAIL_BOX_OAK.get());
        this.mailbox(Blocks.SPRUCE_PLANKS, ModBlocks.MAIL_BOX_SPRUCE.get());
        this.mailbox(Blocks.BIRCH_PLANKS, ModBlocks.MAIL_BOX_BIRCH.get());
        this.mailbox(Blocks.JUNGLE_PLANKS, ModBlocks.MAIL_BOX_JUNGLE.get());
        this.mailbox(Blocks.ACACIA_PLANKS, ModBlocks.MAIL_BOX_ACACIA.get());
        this.mailbox(Blocks.DARK_OAK_PLANKS, ModBlocks.MAIL_BOX_DARK_OAK.get());
        this.mailbox(Blocks.MANGROVE_PLANKS, ModBlocks.MAIL_BOX_MANGROVE.get());
        this.mailbox(Blocks.CHERRY_PLANKS, ModBlocks.MAIL_BOX_CHERRY.get());
        this.mailbox(Blocks.CRIMSON_PLANKS, ModBlocks.MAIL_BOX_CRIMSON.get());
        this.mailbox(Blocks.WARPED_PLANKS, ModBlocks.MAIL_BOX_WARPED.get());
        this.postBox(ModBlocks.POST_BOX.get());
        this.trampoline(Items.WHITE_DYE, ModBlocks.TRAMPOLINE_WHITE.get());
        this.trampoline(Items.ORANGE_DYE, ModBlocks.TRAMPOLINE_ORANGE.get());
        this.trampoline(Items.MAGENTA_DYE, ModBlocks.TRAMPOLINE_MAGENTA.get());
        this.trampoline(Items.LIGHT_BLUE_DYE, ModBlocks.TRAMPOLINE_LIGHT_BLUE.get());
        this.trampoline(Items.YELLOW_DYE, ModBlocks.TRAMPOLINE_YELLOW.get());
        this.trampoline(Items.LIME_DYE, ModBlocks.TRAMPOLINE_LIME.get());
        this.trampoline(Items.PINK_DYE, ModBlocks.TRAMPOLINE_PINK.get());
        this.trampoline(Items.GRAY_DYE, ModBlocks.TRAMPOLINE_GRAY.get());
        this.trampoline(Items.LIGHT_GRAY_DYE, ModBlocks.TRAMPOLINE_LIGHT_GRAY.get());
        this.trampoline(Items.CYAN_DYE, ModBlocks.TRAMPOLINE_CYAN.get());
        this.trampoline(Items.PURPLE_DYE, ModBlocks.TRAMPOLINE_PURPLE.get());
        this.trampoline(Items.BLUE_DYE, ModBlocks.TRAMPOLINE_BLUE.get());
        this.trampoline(Items.BROWN_DYE, ModBlocks.TRAMPOLINE_BROWN.get());
        this.trampoline(Items.GREEN_DYE, ModBlocks.TRAMPOLINE_GREEN.get());
        this.trampoline(Items.RED_DYE, ModBlocks.TRAMPOLINE_RED.get());
        this.trampoline(Items.BLACK_DYE, ModBlocks.TRAMPOLINE_BLACK.get());
        this.hedge(Blocks.OAK_LEAVES, ModBlocks.HEDGE_OAK.get());
        this.hedge(Blocks.SPRUCE_LEAVES, ModBlocks.HEDGE_SPRUCE.get());
        this.hedge(Blocks.BIRCH_LEAVES, ModBlocks.HEDGE_BIRCH.get());
        this.hedge(Blocks.JUNGLE_LEAVES, ModBlocks.HEDGE_JUNGLE.get());
        this.hedge(Blocks.ACACIA_LEAVES, ModBlocks.HEDGE_ACACIA.get());
        this.hedge(Blocks.DARK_OAK_LEAVES, ModBlocks.HEDGE_DARK_OAK.get());
        this.hedge(Blocks.MANGROVE_LEAVES, ModBlocks.HEDGE_MANGROVE.get());
        this.hedge(Blocks.CHERRY_LEAVES, ModBlocks.HEDGE_CHERRY.get());
        this.hedge(Blocks.AZALEA_LEAVES, ModBlocks.HEDGE_AZALEA.get());
        this.steppingStone(Blocks.STONE, ModBlocks.STEPPING_STONES_STONE.get());
        this.steppingStone(Blocks.GRANITE, ModBlocks.STEPPING_STONES_GRANITE.get());
        this.steppingStone(Blocks.DIORITE, ModBlocks.STEPPING_STONES_DIORITE.get());
        this.steppingStone(Blocks.ANDESITE, ModBlocks.STEPPING_STONES_ANDESITE.get());
        this.steppingStone(Blocks.DEEPSLATE, ModBlocks.STEPPING_STONES_DEEPSLATE.get());
        this.latticeFence(Blocks.OAK_PLANKS, ModBlocks.LATTICE_FENCE_OAK.get());
        this.latticeFence(Blocks.SPRUCE_PLANKS, ModBlocks.LATTICE_FENCE_SPRUCE.get());
        this.latticeFence(Blocks.BIRCH_PLANKS, ModBlocks.LATTICE_FENCE_BIRCH.get());
        this.latticeFence(Blocks.JUNGLE_PLANKS, ModBlocks.LATTICE_FENCE_JUNGLE.get());
        this.latticeFence(Blocks.ACACIA_PLANKS, ModBlocks.LATTICE_FENCE_ACACIA.get());
        this.latticeFence(Blocks.DARK_OAK_PLANKS, ModBlocks.LATTICE_FENCE_DARK_OAK.get());
        this.latticeFence(Blocks.MANGROVE_PLANKS, ModBlocks.LATTICE_FENCE_MANGROVE.get());
        this.latticeFence(Blocks.CHERRY_PLANKS, ModBlocks.LATTICE_FENCE_CHERRY.get());
        this.latticeFence(Blocks.CRIMSON_PLANKS, ModBlocks.LATTICE_FENCE_CRIMSON.get());
        this.latticeFence(Blocks.WARPED_PLANKS, ModBlocks.LATTICE_FENCE_WARPED.get());
        this.latticeFenceGate(Blocks.OAK_PLANKS, ModBlocks.LATTICE_FENCE_GATE_OAK.get());
        this.latticeFenceGate(Blocks.SPRUCE_PLANKS, ModBlocks.LATTICE_FENCE_GATE_SPRUCE.get());
        this.latticeFenceGate(Blocks.BIRCH_PLANKS, ModBlocks.LATTICE_FENCE_GATE_BIRCH.get());
        this.latticeFenceGate(Blocks.JUNGLE_PLANKS, ModBlocks.LATTICE_FENCE_GATE_JUNGLE.get());
        this.latticeFenceGate(Blocks.ACACIA_PLANKS, ModBlocks.LATTICE_FENCE_GATE_ACACIA.get());
        this.latticeFenceGate(Blocks.DARK_OAK_PLANKS, ModBlocks.LATTICE_FENCE_GATE_DARK_OAK.get());
        this.latticeFenceGate(Blocks.MANGROVE_PLANKS, ModBlocks.LATTICE_FENCE_GATE_MANGROVE.get());
        this.latticeFenceGate(Blocks.CHERRY_PLANKS, ModBlocks.LATTICE_FENCE_GATE_CHERRY.get());
        this.latticeFenceGate(Blocks.CRIMSON_PLANKS, ModBlocks.LATTICE_FENCE_GATE_CRIMSON.get());
        this.latticeFenceGate(Blocks.WARPED_PLANKS, ModBlocks.LATTICE_FENCE_GATE_WARPED.get());
        this.doorMat(ModBlocks.DOOR_MAT.get());
        this.sofa(Items.WHITE_DYE, ModBlocks.SOFA_WHITE.get());
        this.sofa(Items.ORANGE_DYE, ModBlocks.SOFA_ORANGE.get());
        this.sofa(Items.MAGENTA_DYE, ModBlocks.SOFA_MAGENTA.get());
        this.sofa(Items.LIGHT_BLUE_DYE, ModBlocks.SOFA_LIGHT_BLUE.get());
        this.sofa(Items.YELLOW_DYE, ModBlocks.SOFA_YELLOW.get());
        this.sofa(Items.LIME_DYE, ModBlocks.SOFA_LIME.get());
        this.sofa(Items.PINK_DYE, ModBlocks.SOFA_PINK.get());
        this.sofa(Items.GRAY_DYE, ModBlocks.SOFA_GRAY.get());
        this.sofa(Items.LIGHT_GRAY_DYE, ModBlocks.SOFA_LIGHT_GRAY.get());
        this.sofa(Items.CYAN_DYE, ModBlocks.SOFA_CYAN.get());
        this.sofa(Items.PURPLE_DYE, ModBlocks.SOFA_PURPLE.get());
        this.sofa(Items.BLUE_DYE, ModBlocks.SOFA_BLUE.get());
        this.sofa(Items.BROWN_DYE, ModBlocks.SOFA_BROWN.get());
        this.sofa(Items.GREEN_DYE, ModBlocks.SOFA_GREEN.get());
        this.sofa(Items.RED_DYE, ModBlocks.SOFA_RED.get());
        this.sofa(Items.BLACK_DYE, ModBlocks.SOFA_BLACK.get());
        this.stool(Items.WHITE_DYE, ModBlocks.STOOL_WHITE.get());
        this.stool(Items.ORANGE_DYE, ModBlocks.STOOL_ORANGE.get());
        this.stool(Items.MAGENTA_DYE, ModBlocks.STOOL_MAGENTA.get());
        this.stool(Items.LIGHT_BLUE_DYE, ModBlocks.STOOL_LIGHT_BLUE.get());
        this.stool(Items.YELLOW_DYE, ModBlocks.STOOL_YELLOW.get());
        this.stool(Items.LIME_DYE, ModBlocks.STOOL_LIME.get());
        this.stool(Items.PINK_DYE, ModBlocks.STOOL_PINK.get());
        this.stool(Items.GRAY_DYE, ModBlocks.STOOL_GRAY.get());
        this.stool(Items.LIGHT_GRAY_DYE, ModBlocks.STOOL_LIGHT_GRAY.get());
        this.stool(Items.CYAN_DYE, ModBlocks.STOOL_CYAN.get());
        this.stool(Items.PURPLE_DYE, ModBlocks.STOOL_PURPLE.get());
        this.stool(Items.BLUE_DYE, ModBlocks.STOOL_BLUE.get());
        this.stool(Items.BROWN_DYE, ModBlocks.STOOL_BROWN.get());
        this.stool(Items.GREEN_DYE, ModBlocks.STOOL_GREEN.get());
        this.stool(Items.RED_DYE, ModBlocks.STOOL_RED.get());
        this.stool(Items.BLACK_DYE, ModBlocks.STOOL_BLACK.get());
        this.lamp(Items.WHITE_DYE, ModBlocks.LAMP_WHITE.get());
        this.lamp(Items.ORANGE_DYE, ModBlocks.LAMP_ORANGE.get());
        this.lamp(Items.MAGENTA_DYE, ModBlocks.LAMP_MAGENTA.get());
        this.lamp(Items.LIGHT_BLUE_DYE, ModBlocks.LAMP_LIGHT_BLUE.get());
        this.lamp(Items.YELLOW_DYE, ModBlocks.LAMP_YELLOW.get());
        this.lamp(Items.LIME_DYE, ModBlocks.LAMP_LIME.get());
        this.lamp(Items.PINK_DYE, ModBlocks.LAMP_PINK.get());
        this.lamp(Items.GRAY_DYE, ModBlocks.LAMP_GRAY.get());
        this.lamp(Items.LIGHT_GRAY_DYE, ModBlocks.LAMP_LIGHT_GRAY.get());
        this.lamp(Items.CYAN_DYE, ModBlocks.LAMP_CYAN.get());
        this.lamp(Items.PURPLE_DYE, ModBlocks.LAMP_PURPLE.get());
        this.lamp(Items.BLUE_DYE, ModBlocks.LAMP_BLUE.get());
        this.lamp(Items.BROWN_DYE, ModBlocks.LAMP_BROWN.get());
        this.lamp(Items.GREEN_DYE, ModBlocks.LAMP_GREEN.get());
        this.lamp(Items.RED_DYE, ModBlocks.LAMP_RED.get());
        this.lamp(Items.BLACK_DYE, ModBlocks.LAMP_BLACK.get());
        this.ceilingFan(Blocks.OAK_PLANKS, ModBlocks.CEILING_FAN_OAK_LIGHT.get(), ModBlocks.CEILING_FAN_OAK_DARK.get());
        this.ceilingFan(Blocks.SPRUCE_PLANKS, ModBlocks.CEILING_FAN_SPRUCE_LIGHT.get(), ModBlocks.CEILING_FAN_SPRUCE_DARK.get());
        this.ceilingFan(Blocks.BIRCH_PLANKS, ModBlocks.CEILING_FAN_BIRCH_LIGHT.get(), ModBlocks.CEILING_FAN_BIRCH_DARK.get());
        this.ceilingFan(Blocks.JUNGLE_PLANKS, ModBlocks.CEILING_FAN_JUNGLE_LIGHT.get(), ModBlocks.CEILING_FAN_JUNGLE_DARK.get());
        this.ceilingFan(Blocks.ACACIA_PLANKS, ModBlocks.CEILING_FAN_ACACIA_LIGHT.get(), ModBlocks.CEILING_FAN_ACACIA_DARK.get());
        this.ceilingFan(Blocks.DARK_OAK_PLANKS, ModBlocks.CEILING_FAN_DARK_OAK_LIGHT.get(), ModBlocks.CEILING_FAN_DARK_OAK_DARK.get());
        this.ceilingFan(Blocks.MANGROVE_PLANKS, ModBlocks.CEILING_FAN_MANGROVE_LIGHT.get(), ModBlocks.CEILING_FAN_MANGROVE_DARK.get());
        this.ceilingFan(Blocks.CHERRY_PLANKS, ModBlocks.CEILING_FAN_CHERRY_LIGHT.get(), ModBlocks.CEILING_FAN_CHERRY_DARK.get());
        this.ceilingFan(Blocks.CRIMSON_PLANKS, ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get(), ModBlocks.CEILING_FAN_CRIMSON_DARK.get());
        this.ceilingFan(Blocks.WARPED_PLANKS, ModBlocks.CEILING_FAN_WARPED_LIGHT.get(), ModBlocks.CEILING_FAN_WARPED_DARK.get());
        this.ceilingLight(ModBlocks.CEILING_LIGHT_LIGHT.get(), ModBlocks.CEILING_LIGHT_DARK.get());
        this.lightswitch(ModBlocks.LIGHTSWITCH_LIGHT.get(), ModBlocks.LIGHTSWITCH_DARK.get());
        this.doorbell(ModBlocks.DOORBELL.get());
        this.fridge(ModItems.FRIDGE_LIGHT.get(), ModItems.FRIDGE_DARK.get());
        this.storageCabinet(Blocks.OAK_PLANKS, ModBlocks.STORAGE_CABINET_OAK.get());
        this.storageCabinet(Blocks.SPRUCE_PLANKS, ModBlocks.STORAGE_CABINET_SPRUCE.get());
        this.storageCabinet(Blocks.BIRCH_PLANKS, ModBlocks.STORAGE_CABINET_BIRCH.get());
        this.storageCabinet(Blocks.JUNGLE_PLANKS, ModBlocks.STORAGE_CABINET_JUNGLE.get());
        this.storageCabinet(Blocks.ACACIA_PLANKS, ModBlocks.STORAGE_CABINET_ACACIA.get());
        this.storageCabinet(Blocks.DARK_OAK_PLANKS, ModBlocks.STORAGE_CABINET_DARK_OAK.get());
        this.storageCabinet(Blocks.MANGROVE_PLANKS, ModBlocks.STORAGE_CABINET_MANGROVE.get());
        this.storageCabinet(Blocks.CHERRY_PLANKS, ModBlocks.STORAGE_CABINET_CHERRY.get());
        this.storageCabinet(Blocks.CRIMSON_PLANKS, ModBlocks.STORAGE_CABINET_CRIMSON.get());
        this.storageCabinet(Blocks.WARPED_PLANKS, ModBlocks.STORAGE_CABINET_WARPED.get());
        this.storageJar(Blocks.OAK_PLANKS, ModBlocks.STORAGE_JAR_OAK.get());
        this.storageJar(Blocks.SPRUCE_PLANKS, ModBlocks.STORAGE_JAR_SPRUCE.get());
        this.storageJar(Blocks.BIRCH_PLANKS, ModBlocks.STORAGE_JAR_BIRCH.get());
        this.storageJar(Blocks.JUNGLE_PLANKS, ModBlocks.STORAGE_JAR_JUNGLE.get());
        this.storageJar(Blocks.ACACIA_PLANKS, ModBlocks.STORAGE_JAR_ACACIA.get());
        this.storageJar(Blocks.DARK_OAK_PLANKS, ModBlocks.STORAGE_JAR_DARK_OAK.get());
        this.storageJar(Blocks.MANGROVE_PLANKS, ModBlocks.STORAGE_JAR_MANGROVE.get());
        this.storageJar(Blocks.CHERRY_PLANKS, ModBlocks.STORAGE_JAR_CHERRY.get());
        this.storageJar(Blocks.CRIMSON_PLANKS, ModBlocks.STORAGE_JAR_CRIMSON.get());
        this.storageJar(Blocks.WARPED_PLANKS, ModBlocks.STORAGE_JAR_WARPED.get());
        this.woodenToilet(Blocks.OAK_PLANKS, ModBlocks.TOILET_OAK.get());
        this.woodenToilet(Blocks.SPRUCE_PLANKS, ModBlocks.TOILET_SPRUCE.get());
        this.woodenToilet(Blocks.BIRCH_PLANKS, ModBlocks.TOILET_BIRCH.get());
        this.woodenToilet(Blocks.JUNGLE_PLANKS, ModBlocks.TOILET_JUNGLE.get());
        this.woodenToilet(Blocks.ACACIA_PLANKS, ModBlocks.TOILET_ACACIA.get());
        this.woodenToilet(Blocks.DARK_OAK_PLANKS, ModBlocks.TOILET_DARK_OAK.get());
        this.woodenToilet(Blocks.MANGROVE_PLANKS, ModBlocks.TOILET_MANGROVE.get());
        this.woodenToilet(Blocks.CHERRY_PLANKS, ModBlocks.TOILET_CHERRY.get());
        this.woodenToilet(Blocks.CRIMSON_PLANKS, ModBlocks.TOILET_CRIMSON.get());
        this.woodenToilet(Blocks.WARPED_PLANKS, ModBlocks.TOILET_WARPED.get());
        this.colouredToilet(Items.WHITE_DYE, ModBlocks.TOILET_WHITE.get());
        this.colouredToilet(Items.ORANGE_DYE, ModBlocks.TOILET_ORANGE.get());
        this.colouredToilet(Items.MAGENTA_DYE, ModBlocks.TOILET_MAGENTA.get());
        this.colouredToilet(Items.LIGHT_BLUE_DYE, ModBlocks.TOILET_LIGHT_BLUE.get());
        this.colouredToilet(Items.YELLOW_DYE, ModBlocks.TOILET_YELLOW.get());
        this.colouredToilet(Items.LIME_DYE, ModBlocks.TOILET_LIME.get());
        this.colouredToilet(Items.PINK_DYE, ModBlocks.TOILET_PINK.get());
        this.colouredToilet(Items.GRAY_DYE, ModBlocks.TOILET_GRAY.get());
        this.colouredToilet(Items.LIGHT_GRAY_DYE, ModBlocks.TOILET_LIGHT_GRAY.get());
        this.colouredToilet(Items.CYAN_DYE, ModBlocks.TOILET_CYAN.get());
        this.colouredToilet(Items.PURPLE_DYE, ModBlocks.TOILET_PURPLE.get());
        this.colouredToilet(Items.BLUE_DYE, ModBlocks.TOILET_BLUE.get());
        this.colouredToilet(Items.BROWN_DYE, ModBlocks.TOILET_BROWN.get());
        this.colouredToilet(Items.GREEN_DYE, ModBlocks.TOILET_GREEN.get());
        this.colouredToilet(Items.RED_DYE, ModBlocks.TOILET_RED.get());
        this.colouredToilet(Items.BLACK_DYE, ModBlocks.TOILET_BLACK.get());
        this.woodenBasin(Blocks.OAK_PLANKS, ModBlocks.BASIN_OAK.get());
        this.woodenBasin(Blocks.SPRUCE_PLANKS, ModBlocks.BASIN_SPRUCE.get());
        this.woodenBasin(Blocks.BIRCH_PLANKS, ModBlocks.BASIN_BIRCH.get());
        this.woodenBasin(Blocks.JUNGLE_PLANKS, ModBlocks.BASIN_JUNGLE.get());
        this.woodenBasin(Blocks.ACACIA_PLANKS, ModBlocks.BASIN_ACACIA.get());
        this.woodenBasin(Blocks.DARK_OAK_PLANKS, ModBlocks.BASIN_DARK_OAK.get());
        this.woodenBasin(Blocks.MANGROVE_PLANKS, ModBlocks.BASIN_MANGROVE.get());
        this.woodenBasin(Blocks.CHERRY_PLANKS, ModBlocks.BASIN_CHERRY.get());
        this.woodenBasin(Blocks.CRIMSON_PLANKS, ModBlocks.BASIN_CRIMSON.get());
        this.woodenBasin(Blocks.WARPED_PLANKS, ModBlocks.BASIN_WARPED.get());
        this.colouredBasin(Items.WHITE_DYE, ModBlocks.BASIN_WHITE.get());
        this.colouredBasin(Items.ORANGE_DYE, ModBlocks.BASIN_ORANGE.get());
        this.colouredBasin(Items.MAGENTA_DYE, ModBlocks.BASIN_MAGENTA.get());
        this.colouredBasin(Items.LIGHT_BLUE_DYE, ModBlocks.BASIN_LIGHT_BLUE.get());
        this.colouredBasin(Items.YELLOW_DYE, ModBlocks.BASIN_YELLOW.get());
        this.colouredBasin(Items.LIME_DYE, ModBlocks.BASIN_LIME.get());
        this.colouredBasin(Items.PINK_DYE, ModBlocks.BASIN_PINK.get());
        this.colouredBasin(Items.GRAY_DYE, ModBlocks.BASIN_GRAY.get());
        this.colouredBasin(Items.LIGHT_GRAY_DYE, ModBlocks.BASIN_LIGHT_GRAY.get());
        this.colouredBasin(Items.CYAN_DYE, ModBlocks.BASIN_CYAN.get());
        this.colouredBasin(Items.PURPLE_DYE, ModBlocks.BASIN_PURPLE.get());
        this.colouredBasin(Items.BLUE_DYE, ModBlocks.BASIN_BLUE.get());
        this.colouredBasin(Items.BROWN_DYE, ModBlocks.BASIN_BROWN.get());
        this.colouredBasin(Items.GREEN_DYE, ModBlocks.BASIN_GREEN.get());
        this.colouredBasin(Items.RED_DYE, ModBlocks.BASIN_RED.get());
        this.colouredBasin(Items.BLACK_DYE, ModBlocks.BASIN_BLACK.get());
        this.woodenBath(Blocks.OAK_PLANKS, ModBlocks.BATH_OAK.get());
        this.woodenBath(Blocks.SPRUCE_PLANKS, ModBlocks.BATH_SPRUCE.get());
        this.woodenBath(Blocks.BIRCH_PLANKS, ModBlocks.BATH_BIRCH.get());
        this.woodenBath(Blocks.JUNGLE_PLANKS, ModBlocks.BATH_JUNGLE.get());
        this.woodenBath(Blocks.ACACIA_PLANKS, ModBlocks.BATH_ACACIA.get());
        this.woodenBath(Blocks.DARK_OAK_PLANKS, ModBlocks.BATH_DARK_OAK.get());
        this.woodenBath(Blocks.MANGROVE_PLANKS, ModBlocks.BATH_MANGROVE.get());
        this.woodenBath(Blocks.CHERRY_PLANKS, ModBlocks.BATH_CHERRY.get());
        this.woodenBath(Blocks.CRIMSON_PLANKS, ModBlocks.BATH_CRIMSON.get());
        this.woodenBath(Blocks.WARPED_PLANKS, ModBlocks.BATH_WARPED.get());
        this.colouredBath(Items.WHITE_DYE, ModBlocks.BATH_WHITE.get());
        this.colouredBath(Items.ORANGE_DYE, ModBlocks.BATH_ORANGE.get());
        this.colouredBath(Items.MAGENTA_DYE, ModBlocks.BATH_MAGENTA.get());
        this.colouredBath(Items.LIGHT_BLUE_DYE, ModBlocks.BATH_LIGHT_BLUE.get());
        this.colouredBath(Items.YELLOW_DYE, ModBlocks.BATH_YELLOW.get());
        this.colouredBath(Items.LIME_DYE, ModBlocks.BATH_LIME.get());
        this.colouredBath(Items.PINK_DYE, ModBlocks.BATH_PINK.get());
        this.colouredBath(Items.GRAY_DYE, ModBlocks.BATH_GRAY.get());
        this.colouredBath(Items.LIGHT_GRAY_DYE, ModBlocks.BATH_LIGHT_GRAY.get());
        this.colouredBath(Items.CYAN_DYE, ModBlocks.BATH_CYAN.get());
        this.colouredBath(Items.PURPLE_DYE, ModBlocks.BATH_PURPLE.get());
        this.colouredBath(Items.BLUE_DYE, ModBlocks.BATH_BLUE.get());
        this.colouredBath(Items.BROWN_DYE, ModBlocks.BATH_BROWN.get());
        this.colouredBath(Items.GREEN_DYE, ModBlocks.BATH_GREEN.get());
        this.colouredBath(Items.RED_DYE, ModBlocks.BATH_RED.get());
        this.colouredBath(Items.BLACK_DYE, ModBlocks.BATH_BLACK.get());
        this.television(ModBlocks.TELEVISION.get());
        this.computer(ModBlocks.COMPUTER.get());

        // Shapeless
        this.simpleCombined(ModItems.SWEET_BERRY_JAM.get(), ModItems.TOAST.get(), ModItems.SWEET_BERRY_JAM_TOAST.get(), 1, RecipeCategory.FOOD);
        this.simpleCombined(ModItems.GLOW_BERRY_JAM.get(), ModItems.TOAST.get(), ModItems.GLOW_BERRY_JAM_TOAST.get(), 1, RecipeCategory.FOOD);

        // Solidifying
        this.freezerSolidifying(Items.WATER_BUCKET, Items.ICE, 600, 1.0F);
        this.freezerSolidifying(Items.ICE, Items.PACKED_ICE, 1200, 1.0F);
        this.freezerSolidifying(Items.PACKED_ICE, Items.BLUE_ICE, 2400, 1.0F);

        // Toasting
        this.toasterHeating(ModItems.BREAD_SLICE.get(), ModItems.TOAST.get(), 300, 0.5F);
        this.toasterHeating(ModItems.CHEESE_SANDWICH.get(), ModItems.CHEESE_TOASTIE.get(), 400, 0.5F);

        // Slicing
        this.cuttingBoardSlicing(Blocks.MELON, Items.MELON_SLICE, 9);
        this.cuttingBoardSlicing(Items.BREAD, ModItems.BREAD_SLICE.get(), 6);
        this.cuttingBoardSlicing(Blocks.GRASS, Items.WHEAT_SEEDS, 1);
        this.cuttingBoardSlicing(Blocks.DEAD_BUSH, Items.STICK, 2);
        this.cuttingBoardSlicing(Items.BONE, Items.BONE_MEAL, 4);
        this.cuttingBoardSlicing(Items.FEATHER, Items.STRING, 1);
        this.cuttingBoardSlicing(Blocks.COBWEB, Items.STRING, 2);
        this.cuttingBoardSlicing(Blocks.PUMPKIN, Items.PUMPKIN_SEEDS, 5);
        this.cuttingBoardSlicing(Blocks.HONEYCOMB_BLOCK, Items.HONEYCOMB, 4);
        this.cuttingBoardSlicing(Blocks.MANGROVE_ROOTS, Items.STICK, 8);
        this.cuttingBoardSlicing(Blocks.SLIME_BLOCK, Items.SLIME_BALL, 9);
        this.cuttingBoardSlicing(Items.SPIDER_EYE, Items.RED_DYE, 1);
        this.cuttingBoardSlicing(Items.ROTTEN_FLESH, Items.BROWN_DYE, 1);
        this.cuttingBoardSlicing(Items.SWEET_BERRIES, Items.RED_DYE, 1);
        this.cuttingBoardSlicing(Items.GLOW_BERRIES, Items.ORANGE_DYE, 1);
        this.cuttingBoardSlicing(Items.DRIED_KELP, Items.BLACK_DYE, 1);
        this.cuttingBoardSlicing(Items.SEAGRASS, Items.GREEN_DYE, 1);
        this.cuttingBoardSlicing(Items.LILY_PAD, Items.GREEN_DYE, 1);
        this.cuttingBoardSlicing(Items.DANDELION, Items.YELLOW_DYE, 2);
        this.cuttingBoardSlicing(Items.POPPY, Items.RED_DYE, 2);
        this.cuttingBoardSlicing(Items.BLUE_ORCHID, Items.LIGHT_BLUE_DYE, 2);
        this.cuttingBoardSlicing(Items.ALLIUM, Items.MAGENTA_DYE, 2);
        this.cuttingBoardSlicing(Items.AZURE_BLUET, Items.LIGHT_GRAY_DYE, 2);
        this.cuttingBoardSlicing(Items.RED_TULIP, Items.RED_DYE, 2);
        this.cuttingBoardSlicing(Items.ORANGE_TULIP, Items.ORANGE_DYE, 2);
        this.cuttingBoardSlicing(Items.WHITE_TULIP, Items.WHITE_DYE, 2);
        this.cuttingBoardSlicing(Items.PINK_TULIP, Items.PINK_DYE, 2);
        this.cuttingBoardSlicing(Items.OXEYE_DAISY, Items.WHITE_DYE, 2);
        this.cuttingBoardSlicing(Items.CORNFLOWER, Items.BLUE_DYE, 2);
        this.cuttingBoardSlicing(Items.LILY_OF_THE_VALLEY, Items.WHITE_DYE, 2);
        this.cuttingBoardSlicing(Items.TORCHFLOWER, Items.ORANGE_DYE, 2);
        this.cuttingBoardSlicing(Items.WITHER_ROSE, Items.BLACK_DYE, 2);
        this.cuttingBoardSlicing(Items.PINK_PETALS, Items.PINK_DYE, 2);
        this.cuttingBoardSlicing(Items.SPORE_BLOSSOM, Items.PINK_DYE, 2);
        this.cuttingBoardSlicing(Items.SUNFLOWER, Items.YELLOW_DYE, 3);
        this.cuttingBoardSlicing(Items.LILAC, Items.MAGENTA_DYE, 3);
        this.cuttingBoardSlicing(Items.ROSE_BUSH, Items.RED_DYE, 3);
        this.cuttingBoardSlicing(Items.PEONY, Items.PINK_DYE, 3);
        this.cuttingBoardSlicing(Items.PITCHER_PLANT, Items.CYAN_DYE, 3);

        // Combining
        this.cuttingBoardCombining(ModItems.WHEAT_FLOUR.get(), 1,
                Ingredient.of(Items.WHEAT),
                Ingredient.of(Items.WHEAT),
                Ingredient.of(Items.WHEAT),
                Ingredient.of(Items.WHEAT),
                Ingredient.of(Items.BOWL));
        this.cuttingBoardCombining(ModItems.CHEESE_SANDWICH.get(), 1,
                Ingredient.of(ModItems.BREAD_SLICE.get()),
                Ingredient.of(ModItems.CHEESE.get()),
                Ingredient.of(ModItems.BREAD_SLICE.get()));
        this.cuttingBoardCombining(ModItems.SWEET_BERRY_JAM_TOAST.get(), 1,
                Ingredient.of(ModItems.SWEET_BERRY_JAM.get()),
                Ingredient.of(ModItems.TOAST.get()));
        this.cuttingBoardCombining(ModItems.GLOW_BERRY_JAM_TOAST.get(), 1,
                Ingredient.of(ModItems.GLOW_BERRY_JAM.get()),
                Ingredient.of(ModItems.TOAST.get()));

        // Heating
        this.microwaveHeating(Items.POTATO, Items.BAKED_POTATO, 200, 0.5F);

        // Frying
        this.fryingPanCooking(Items.SWEET_BERRIES, ModItems.SWEET_BERRY_JAM.get(), 400, 0.5F);
        this.fryingPanCooking(Items.GLOW_BERRIES, ModItems.GLOW_BERRY_JAM.get(), 400, 0.5F);
        this.fryingPanCooking(ModItems.CHEESE_SANDWICH.get(), ModItems.CHEESE_TOASTIE.get(), 400, 0.5F);

        // Baking
        this.ovenBaking(Items.WATER_BUCKET, ModItems.SEA_SALT.get(), 4, 1200);

        // Fluid Mixing
        this.sinkFluidTransmuting(Services.FLUID.getMilkFluid(), Ingredient.of(ModItems.SEA_SALT.get()), new ItemStack(ModItems.CHEESE.get(), 2));

        // Recycling
        this.recycleBinSalvaging(Items.OAK_STAIRS, new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.OAK_FENCE, new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.OAK_FENCE_GATE, new ItemStack(Items.OAK_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.OAK_TRAPDOOR, new ItemStack(Items.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.OAK_PRESSURE_PLATE, new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.SPRUCE_STAIRS, new ItemStack(Items.SPRUCE_PLANKS, 1));
        this.recycleBinSalvaging(Items.SPRUCE_FENCE, new ItemStack(Items.SPRUCE_PLANKS, 1));
        this.recycleBinSalvaging(Items.SPRUCE_FENCE_GATE, new ItemStack(Items.SPRUCE_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.SPRUCE_TRAPDOOR, new ItemStack(Items.SPRUCE_PLANKS, 2));
        this.recycleBinSalvaging(Items.SPRUCE_PRESSURE_PLATE, new ItemStack(Items.SPRUCE_PLANKS, 1));
        this.recycleBinSalvaging(Items.BIRCH_STAIRS, new ItemStack(Items.BIRCH_PLANKS, 1));
        this.recycleBinSalvaging(Items.BIRCH_FENCE, new ItemStack(Items.BIRCH_PLANKS, 1));
        this.recycleBinSalvaging(Items.BIRCH_FENCE_GATE, new ItemStack(Items.BIRCH_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.BIRCH_TRAPDOOR, new ItemStack(Items.BIRCH_PLANKS, 2));
        this.recycleBinSalvaging(Items.BIRCH_PRESSURE_PLATE, new ItemStack(Items.BIRCH_PLANKS, 1));
        this.recycleBinSalvaging(Items.JUNGLE_STAIRS, new ItemStack(Items.JUNGLE_PLANKS, 1));
        this.recycleBinSalvaging(Items.JUNGLE_FENCE, new ItemStack(Items.JUNGLE_PLANKS, 1));
        this.recycleBinSalvaging(Items.JUNGLE_FENCE_GATE, new ItemStack(Items.JUNGLE_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.JUNGLE_TRAPDOOR, new ItemStack(Items.JUNGLE_PLANKS, 2));
        this.recycleBinSalvaging(Items.JUNGLE_PRESSURE_PLATE, new ItemStack(Items.JUNGLE_PLANKS, 1));
        this.recycleBinSalvaging(Items.ACACIA_STAIRS, new ItemStack(Items.ACACIA_PLANKS, 1));
        this.recycleBinSalvaging(Items.ACACIA_FENCE, new ItemStack(Items.ACACIA_PLANKS, 1));
        this.recycleBinSalvaging(Items.ACACIA_FENCE_GATE, new ItemStack(Items.ACACIA_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.ACACIA_TRAPDOOR, new ItemStack(Items.ACACIA_PLANKS, 2));
        this.recycleBinSalvaging(Items.ACACIA_PRESSURE_PLATE, new ItemStack(Items.ACACIA_PLANKS, 1));
        this.recycleBinSalvaging(Items.DARK_OAK_STAIRS, new ItemStack(Items.DARK_OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE, new ItemStack(Items.DARK_OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE_GATE, new ItemStack(Items.DARK_OAK_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.DARK_OAK_TRAPDOOR, new ItemStack(Items.DARK_OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.DARK_OAK_PRESSURE_PLATE, new ItemStack(Items.DARK_OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.MANGROVE_STAIRS, new ItemStack(Items.MANGROVE_PLANKS, 1));
        this.recycleBinSalvaging(Items.MANGROVE_FENCE, new ItemStack(Items.MANGROVE_PLANKS, 1));
        this.recycleBinSalvaging(Items.MANGROVE_FENCE_GATE, new ItemStack(Items.MANGROVE_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.MANGROVE_TRAPDOOR, new ItemStack(Items.MANGROVE_PLANKS, 2));
        this.recycleBinSalvaging(Items.MANGROVE_PRESSURE_PLATE, new ItemStack(Items.MANGROVE_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHERRY_STAIRS, new ItemStack(Items.CHERRY_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHERRY_FENCE, new ItemStack(Items.CHERRY_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHERRY_FENCE_GATE, new ItemStack(Items.CHERRY_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.CHERRY_TRAPDOOR, new ItemStack(Items.CHERRY_PLANKS, 2));
        this.recycleBinSalvaging(Items.CHERRY_PRESSURE_PLATE, new ItemStack(Items.CHERRY_PLANKS, 1));
        this.recycleBinSalvaging(Items.BAMBOO_STAIRS, new ItemStack(Items.BAMBOO_PLANKS, 1));
        this.recycleBinSalvaging(Items.BAMBOO_FENCE, new ItemStack(Items.BAMBOO_PLANKS, 1));
        this.recycleBinSalvaging(Items.BAMBOO_FENCE_GATE, new ItemStack(Items.BAMBOO_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.BAMBOO_TRAPDOOR, new ItemStack(Items.BAMBOO_PLANKS, 2));
        this.recycleBinSalvaging(Items.BAMBOO_PRESSURE_PLATE, new ItemStack(Items.BAMBOO_PLANKS, 1));
        this.recycleBinSalvaging(Items.CRIMSON_STAIRS, new ItemStack(Items.CRIMSON_PLANKS, 1));
        this.recycleBinSalvaging(Items.CRIMSON_FENCE, new ItemStack(Items.CRIMSON_PLANKS, 1));
        this.recycleBinSalvaging(Items.CRIMSON_FENCE_GATE, new ItemStack(Items.CRIMSON_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.CRIMSON_TRAPDOOR, new ItemStack(Items.CRIMSON_PLANKS, 2));
        this.recycleBinSalvaging(Items.CRIMSON_PRESSURE_PLATE, new ItemStack(Items.CRIMSON_PLANKS, 1));
        this.recycleBinSalvaging(Items.WARPED_STAIRS, new ItemStack(Items.WARPED_PLANKS, 1));
        this.recycleBinSalvaging(Items.WARPED_FENCE, new ItemStack(Items.WARPED_PLANKS, 1));
        this.recycleBinSalvaging(Items.WARPED_FENCE_GATE, new ItemStack(Items.WARPED_PLANKS, 1), new ItemStack(Items.STICK, 2));
        this.recycleBinSalvaging(Items.WARPED_TRAPDOOR, new ItemStack(Items.WARPED_PLANKS, 2));
        this.recycleBinSalvaging(Items.WARPED_PRESSURE_PLATE, new ItemStack(Items.WARPED_PLANKS, 1));
        this.recycleBinSalvaging(Items.STONE_STAIRS, new ItemStack(Items.STONE, 1));
        this.recycleBinSalvaging(Items.COBBLESTONE_STAIRS, new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.COBBLESTONE_WALL, new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.MOSSY_COBBLESTONE_STAIRS, new ItemStack(Items.MOSSY_COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.MOSSY_COBBLESTONE_WALL, new ItemStack(Items.MOSSY_COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.STONE_BRICK_STAIRS, new ItemStack(Items.STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.STONE_BRICK_WALL, new ItemStack(Items.STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.MOSSY_STONE_BRICK_STAIRS, new ItemStack(Items.MOSSY_STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.MOSSY_STONE_BRICK_WALL, new ItemStack(Items.MOSSY_STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.GRANITE_STAIRS, new ItemStack(Items.GRANITE, 1));
        this.recycleBinSalvaging(Items.GRANITE_WALL, new ItemStack(Items.GRANITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_GRANITE, new ItemStack(Items.GRANITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_GRANITE_STAIRS, new ItemStack(Items.POLISHED_GRANITE, 1));
        this.recycleBinSalvaging(Items.DIORITE_STAIRS, new ItemStack(Items.DIORITE, 1));
        this.recycleBinSalvaging(Items.DIORITE_WALL, new ItemStack(Items.DIORITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_DIORITE, new ItemStack(Items.DIORITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_DIORITE_STAIRS, new ItemStack(Items.POLISHED_DIORITE, 1));
        this.recycleBinSalvaging(Items.ANDESITE_STAIRS, new ItemStack(Items.ANDESITE, 1));
        this.recycleBinSalvaging(Items.ANDESITE_WALL, new ItemStack(Items.ANDESITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_ANDESITE, new ItemStack(Items.ANDESITE, 1));
        this.recycleBinSalvaging(Items.POLISHED_ANDESITE_STAIRS, new ItemStack(Items.POLISHED_ANDESITE, 1));
        this.recycleBinSalvaging(Items.COBBLED_DEEPSLATE_STAIRS, new ItemStack(Items.COBBLED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.COBBLED_DEEPSLATE_WALL, new ItemStack(Items.COBBLED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.CHISELED_DEEPSLATE, new ItemStack(Items.COBBLED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE, new ItemStack(Items.COBBLED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_STAIRS, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_WALL, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICKS, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_STAIRS, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_WALL, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_TILES, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_STAIRS, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_WALL, new ItemStack(Items.COBBLED_DEEPSLATE, 1), new ItemStack(Items.POLISHED_DEEPSLATE, 1));
        this.recycleBinSalvaging(Items.BRICKS, new ItemStack(Items.BRICK, 3));
        this.recycleBinSalvaging(Items.BRICK_STAIRS, new ItemStack(Items.BRICK, 4));
        this.recycleBinSalvaging(Items.BRICK_SLAB, new ItemStack(Items.BRICK, 1));
        this.recycleBinSalvaging(Items.BRICK_WALL, new ItemStack(Items.BRICK, 4));
        this.recycleBinSalvaging(Items.MUD_BRICK_STAIRS, new ItemStack(Items.MUD_BRICKS, 1));
        this.recycleBinSalvaging(Items.MUD_BRICK_WALL, new ItemStack(Items.MUD_BRICKS, 1));
        this.recycleBinSalvaging(Items.SANDSTONE_STAIRS, new ItemStack(Items.SANDSTONE, 1));
        this.recycleBinSalvaging(Items.SANDSTONE_WALL, new ItemStack(Items.SANDSTONE, 1));
        this.recycleBinSalvaging(Items.SMOOTH_SANDSTONE_STAIRS, new ItemStack(Items.SMOOTH_SANDSTONE, 1));
        this.recycleBinSalvaging(Items.CUT_SANDSTONE, new ItemStack(Items.SANDSTONE, 1));
        this.recycleBinSalvaging(Items.RED_SANDSTONE_STAIRS, new ItemStack(Items.RED_SANDSTONE, 1));
        this.recycleBinSalvaging(Items.RED_SANDSTONE_WALL, new ItemStack(Items.RED_SANDSTONE, 1));
        this.recycleBinSalvaging(Items.SMOOTH_RED_SANDSTONE_STAIRS, new ItemStack(Items.SMOOTH_RED_SANDSTONE, 1));
        this.recycleBinSalvaging(Items.CUT_RED_SANDSTONE, new ItemStack(Items.RED_SANDSTONE, 1));
        this.recycleBinSalvaging(Items.PRISMARINE_STAIRS, new ItemStack(Items.PRISMARINE, 1));
        this.recycleBinSalvaging(Items.PRISMARINE_BRICK_STAIRS, new ItemStack(Items.PRISMARINE_BRICKS, 1));
        this.recycleBinSalvaging(Items.DARK_PRISMARINE_STAIRS, new ItemStack(Items.DARK_PRISMARINE, 1));
        this.recycleBinSalvaging(Items.NETHER_BRICKS, new ItemStack(Items.NETHER_BRICK, 3));
        this.recycleBinSalvaging(Items.NETHER_BRICK_STAIRS, new ItemStack(Items.NETHER_BRICK, 4));
        this.recycleBinSalvaging(Items.NETHER_BRICK_SLAB, new ItemStack(Items.NETHER_BRICK, 1));
        this.recycleBinSalvaging(Items.NETHER_BRICK_WALL, new ItemStack(Items.NETHER_BRICK, 4));
        this.recycleBinSalvaging(Items.NETHER_BRICK_FENCE, new ItemStack(Items.NETHER_BRICK, 1));
        this.recycleBinSalvaging(Items.RED_NETHER_BRICK_STAIRS, new ItemStack(Items.RED_NETHER_BRICKS, 1));
        this.recycleBinSalvaging(Items.RED_NETHER_BRICK_WALL, new ItemStack(Items.RED_NETHER_BRICKS, 1));
        this.recycleBinSalvaging(Items.BLACKSTONE_STAIRS, new ItemStack(Items.BLACKSTONE, 1));
        this.recycleBinSalvaging(Items.BLACKSTONE_WALL, new ItemStack(Items.BLACKSTONE, 1));
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE, new ItemStack(Items.BLACKSTONE, 1));
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_STAIRS, new ItemStack(Items.BLACKSTONE, 1), new ItemStack(Items.POLISHED_BLACKSTONE, 1));
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_WALL, new ItemStack(Items.BLACKSTONE, 1), new ItemStack(Items.POLISHED_BLACKSTONE, 1));
        this.recycleBinSalvaging(Items.END_STONE_BRICK_STAIRS, new ItemStack(Items.END_STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.END_STONE_BRICK_WALL, new ItemStack(Items.END_STONE_BRICKS, 1));
        this.recycleBinSalvaging(Items.PURPUR_STAIRS, new ItemStack(Items.PURPUR_BLOCK, 1));
        this.recycleBinSalvaging(Items.PURPUR_PILLAR, new ItemStack(Items.PURPUR_BLOCK, 1));
        this.recycleBinSalvaging(Items.IRON_BARS, new ItemStack(Items.IRON_NUGGET, 2));
        this.recycleBinSalvaging(Items.IRON_DOOR, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.IRON_TRAPDOOR, new ItemStack(Items.IRON_INGOT, 2));
        this.recycleBinSalvaging(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.CHAIN, new ItemStack(Items.IRON_NUGGET, 6));
        this.recycleBinSalvaging(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.QUARTZ_STAIRS, new ItemStack(Items.QUARTZ_BLOCK, 1));
        this.recycleBinSalvaging(Items.QUARTZ_BRICKS, new ItemStack(Items.QUARTZ_BLOCK, 1));
        this.recycleBinSalvaging(Items.SMOOTH_QUARTZ_STAIRS, new ItemStack(Items.SMOOTH_QUARTZ, 1));
        this.recycleBinSalvaging(Items.AMETHYST_BLOCK, new ItemStack(Items.AMETHYST_SHARD, 2));
        this.recycleBinSalvaging(Items.CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER, new ItemStack(Items.COPPER_INGOT, 4));
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS, new ItemStack(Items.COPPER_INGOT, 8));
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, new ItemStack(Items.COPPER_INGOT, 2));
        this.recycleBinSalvaging(Items.WHITE_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.GRAY_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.BLACK_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.BROWN_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.RED_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.ORANGE_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.YELLOW_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.LIME_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.GREEN_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.CYAN_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.BLUE_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.PURPLE_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.MAGENTA_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.PINK_WOOL, new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.WHITE_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.GRAY_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BLACK_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BROWN_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.RED_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.ORANGE_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.YELLOW_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIME_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.GREEN_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.CYAN_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BLUE_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.PURPLE_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.MAGENTA_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.PINK_CARPET, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.WHITE_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.GRAY_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.BLACK_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.BROWN_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.RED_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.ORANGE_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.YELLOW_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.LIME_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.GREEN_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.CYAN_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.BLUE_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.PURPLE_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.MAGENTA_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.PINK_SHULKER_BOX, new ItemStack(Items.SHULKER_SHELL, 2), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.WHITE_BED, new ItemStack(Items.WHITE_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_BED, new ItemStack(Items.LIGHT_GRAY_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.GRAY_BED, new ItemStack(Items.GRAY_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.BLACK_BED, new ItemStack(Items.BLACK_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.BROWN_BED, new ItemStack(Items.BROWN_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.RED_BED, new ItemStack(Items.RED_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.ORANGE_BED, new ItemStack(Items.ORANGE_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.YELLOW_BED, new ItemStack(Items.YELLOW_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.LIME_BED, new ItemStack(Items.LIME_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.GREEN_BED, new ItemStack(Items.GREEN_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.CYAN_BED, new ItemStack(Items.CYAN_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_BED, new ItemStack(Items.LIGHT_BLUE_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.BLUE_BED, new ItemStack(Items.BLUE_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.PURPLE_BED, new ItemStack(Items.PURPLE_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.MAGENTA_BED, new ItemStack(Items.MAGENTA_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.PINK_BED, new ItemStack(Items.PINK_WOOL, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.WHITE_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.GRAY_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BLACK_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BROWN_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.RED_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.ORANGE_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.YELLOW_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIME_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.GREEN_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.CYAN_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BLUE_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.PURPLE_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.MAGENTA_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.PINK_CANDLE, new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.WHITE_BANNER, new ItemStack(Items.WHITE_WOOL, 3));
        this.recycleBinSalvaging(Items.LIGHT_GRAY_BANNER, new ItemStack(Items.LIGHT_GRAY_WOOL, 3));
        this.recycleBinSalvaging(Items.GRAY_BANNER, new ItemStack(Items.GRAY_WOOL, 3));
        this.recycleBinSalvaging(Items.BLACK_BANNER, new ItemStack(Items.BLACK_WOOL, 3));
        this.recycleBinSalvaging(Items.BROWN_BANNER, new ItemStack(Items.BROWN_WOOL, 3));
        this.recycleBinSalvaging(Items.RED_BANNER, new ItemStack(Items.RED_WOOL, 3));
        this.recycleBinSalvaging(Items.ORANGE_BANNER, new ItemStack(Items.ORANGE_WOOL, 3));
        this.recycleBinSalvaging(Items.YELLOW_BANNER, new ItemStack(Items.YELLOW_WOOL, 3));
        this.recycleBinSalvaging(Items.LIME_BANNER, new ItemStack(Items.LIME_WOOL, 3));
        this.recycleBinSalvaging(Items.GREEN_BANNER, new ItemStack(Items.GREEN_WOOL, 3));
        this.recycleBinSalvaging(Items.CYAN_BANNER, new ItemStack(Items.CYAN_WOOL, 3));
        this.recycleBinSalvaging(Items.LIGHT_BLUE_BANNER, new ItemStack(Items.LIGHT_BLUE_WOOL, 3));
        this.recycleBinSalvaging(Items.BLUE_BANNER, new ItemStack(Items.BLUE_WOOL, 3));
        this.recycleBinSalvaging(Items.PURPLE_BANNER, new ItemStack(Items.PURPLE_WOOL, 3));
        this.recycleBinSalvaging(Items.MAGENTA_BANNER, new ItemStack(Items.MAGENTA_WOOL, 3));
        this.recycleBinSalvaging(Items.PINK_BANNER, new ItemStack(Items.PINK_WOOL, 3));
        this.recycleBinSalvaging(Items.CALIBRATED_SCULK_SENSOR, new ItemStack(Items.SCULK_SENSOR, 1), new ItemStack(Items.AMETHYST_SHARD, 2));
        this.recycleBinSalvaging(Items.REDSTONE_TORCH, new ItemStack(Items.REDSTONE, 1));
        this.recycleBinSalvaging(Items.LANTERN, new ItemStack(Items.IRON_NUGGET, 4), new ItemStack(Items.TORCH, 1));
        this.recycleBinSalvaging(Items.SOUL_LANTERN, new ItemStack(Items.IRON_NUGGET, 4), new ItemStack(Items.SOUL_TORCH, 1));
        this.recycleBinSalvaging(Items.SEA_LANTERN, new ItemStack(Items.PRISMARINE_SHARD, 2), new ItemStack(Items.PRISMARINE_CRYSTALS, 3));
        this.recycleBinSalvaging(Items.REDSTONE_LAMP, new ItemStack(Items.REDSTONE, 3));
        this.recycleBinSalvaging(Items.CRAFTING_TABLE, new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.STONECUTTER, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.CARTOGRAPHY_TABLE, new ItemStack(Items.PAPER, 1), new ItemStack(Blocks.DARK_OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.FLETCHING_TABLE, new ItemStack(Items.FLINT, 1), new ItemStack(Blocks.BIRCH_PLANKS, 2));
        this.recycleBinSalvaging(Items.SMITHING_TABLE, new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Blocks.SPRUCE_PLANKS, 2));
        this.recycleBinSalvaging(Items.GRINDSTONE, new ItemStack(Blocks.DARK_OAK_PLANKS, 1), new ItemStack(Items.STONE_SLAB, 1));
        this.recycleBinSalvaging(Items.LOOM, new ItemStack(Items.STRING, 1), new ItemStack(Blocks.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.FURNACE, new ItemStack(Blocks.COBBLESTONE, 4));
        this.recycleBinSalvaging(Items.SMOKER, new ItemStack(Items.FURNACE, 1));
        this.recycleBinSalvaging(Items.BLAST_FURNACE, new ItemStack(Items.FURNACE, 1), new ItemStack(Items.IRON_INGOT, 3));
        this.recycleBinSalvaging(Items.CAMPFIRE, new ItemStack(Items.COAL, 1));
        this.recycleBinSalvaging(Items.SOUL_CAMPFIRE, new ItemStack(Items.SOUL_SAND, 1));
        this.recycleBinSalvaging(Items.ANVIL, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.IRON_BLOCK, 2));
        this.recycleBinSalvaging(Items.CHIPPED_ANVIL, new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.IRON_BLOCK, 1));
        this.recycleBinSalvaging(Items.DAMAGED_ANVIL, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.COMPOSTER, new ItemStack(Items.OAK_SLAB, 3));
        this.recycleBinSalvaging(Items.NOTE_BLOCK, new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.OAK_PLANKS, 3));
        this.recycleBinSalvaging(Items.JUKEBOX, new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.OAK_PLANKS, 3));
        this.recycleBinSalvaging(Items.ENCHANTING_TABLE, new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.OBSIDIAN, 2), new ItemStack(Items.BOOK, 1));
        this.recycleBinSalvaging(Items.END_CRYSTAL, new ItemStack(Items.ENDER_EYE, 1));
        this.recycleBinSalvaging(Items.BREWING_STAND, new ItemStack(Items.BLAZE_ROD, 1), new ItemStack(Blocks.COBBLESTONE, 2));
        this.recycleBinSalvaging(Items.CAULDRON, new ItemStack(Items.IRON_INGOT, 3));
        this.recycleBinSalvaging(Items.BELL, new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.BEACON, new ItemStack(Items.NETHER_STAR, 1), new ItemStack(Items.OBSIDIAN, 2));
        this.recycleBinSalvaging(Items.CONDUIT, new ItemStack(Items.NAUTILUS_SHELL, 3), new ItemStack(Items.HEART_OF_THE_SEA, 1));
        this.recycleBinSalvaging(Items.LODESTONE, new ItemStack(Items.NETHERITE_INGOT, 1), new ItemStack(Items.CHISELED_STONE_BRICKS, 3));
        this.recycleBinSalvaging(Items.LADDER, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.SCAFFOLDING, new ItemStack(Items.BAMBOO, 1), new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.BEEHIVE, new ItemStack(Items.HONEYCOMB, 2), new ItemStack(Blocks.OAK_PLANKS, 3));
        this.recycleBinSalvaging(Items.LIGHTNING_ROD, new ItemStack(Items.COPPER_INGOT, 1));
        this.recycleBinSalvaging(Items.FLOWER_POT, new ItemStack(Items.BRICK, 1));
        this.recycleBinSalvaging(Items.DECORATED_POT, new ItemStack(Items.BRICK, 1));
        this.recycleBinSalvaging(Items.ARMOR_STAND, new ItemStack(Items.STICK, 3));
        this.recycleBinSalvaging(Items.ITEM_FRAME, new ItemStack(Items.LEATHER, 1), new ItemStack(Items.STICK, 3));
        this.recycleBinSalvaging(Items.GLOW_ITEM_FRAME, new ItemStack(Items.LEATHER, 1), new ItemStack(Items.STICK, 3));
        this.recycleBinSalvaging(Items.PAINTING, new ItemStack(Items.STICK, 3), new ItemStack(Blocks.WHITE_WOOL, 1));
        this.recycleBinSalvaging(Items.BOOKSHELF, new ItemStack(Items.BOOK, 2), new ItemStack(Blocks.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.CHISELED_BOOKSHELF, new ItemStack(Blocks.OAK_SLAB, 1), new ItemStack(Blocks.OAK_PLANKS, 3));
        this.recycleBinSalvaging(Items.LECTERN, new ItemStack(Items.BOOKSHELF, 1), new ItemStack(Blocks.OAK_SLAB, 2));
        this.recycleBinSalvaging(Items.OAK_SIGN, new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.SPRUCE_SIGN, new ItemStack(Items.SPRUCE_PLANKS, 1));
        this.recycleBinSalvaging(Items.BIRCH_SIGN, new ItemStack(Items.BIRCH_PLANKS, 1));
        this.recycleBinSalvaging(Items.JUNGLE_SIGN, new ItemStack(Items.JUNGLE_PLANKS, 1));
        this.recycleBinSalvaging(Items.ACACIA_SIGN, new ItemStack(Items.ACACIA_PLANKS, 1));
        this.recycleBinSalvaging(Items.DARK_OAK_SIGN, new ItemStack(Items.DARK_OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.MANGROVE_SIGN, new ItemStack(Items.MANGROVE_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHERRY_SIGN, new ItemStack(Items.CHERRY_PLANKS, 1));
        this.recycleBinSalvaging(Items.CRIMSON_SIGN, new ItemStack(Items.CRIMSON_PLANKS, 1));
        this.recycleBinSalvaging(Items.WARPED_SIGN, new ItemStack(Items.WARPED_PLANKS, 1));
        this.recycleBinSalvaging(Items.BAMBOO_SIGN, new ItemStack(Items.BAMBOO_PLANKS, 1));
        this.recycleBinSalvaging(Items.OAK_HANGING_SIGN, new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.SPRUCE_HANGING_SIGN, new ItemStack(Items.SPRUCE_PLANKS, 1));
        this.recycleBinSalvaging(Items.BIRCH_HANGING_SIGN, new ItemStack(Items.BIRCH_PLANKS, 1));
        this.recycleBinSalvaging(Items.JUNGLE_HANGING_SIGN, new ItemStack(Items.JUNGLE_PLANKS, 1));
        this.recycleBinSalvaging(Items.ACACIA_HANGING_SIGN, new ItemStack(Items.ACACIA_PLANKS, 1));
        this.recycleBinSalvaging(Items.DARK_OAK_HANGING_SIGN, new ItemStack(Items.DARK_OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.MANGROVE_HANGING_SIGN, new ItemStack(Items.MANGROVE_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHERRY_HANGING_SIGN, new ItemStack(Items.CHERRY_PLANKS, 1));
        this.recycleBinSalvaging(Items.CRIMSON_HANGING_SIGN, new ItemStack(Items.CRIMSON_PLANKS, 1));
        this.recycleBinSalvaging(Items.WARPED_HANGING_SIGN, new ItemStack(Items.WARPED_PLANKS, 1));
        this.recycleBinSalvaging(Items.BAMBOO_HANGING_SIGN, new ItemStack(Items.BAMBOO_PLANKS, 1));
        this.recycleBinSalvaging(Items.CHEST, new ItemStack(Items.OAK_PLANKS, 3));
        this.recycleBinSalvaging(Items.TRAPPED_CHEST, new ItemStack(Items.CHEST, 1), new ItemStack(Items.TRIPWIRE_HOOK, 1));
        this.recycleBinSalvaging(Items.BARREL, new ItemStack(Items.SPRUCE_PLANKS, 2));
        this.recycleBinSalvaging(Items.ENDER_CHEST, new ItemStack(Items.OBSIDIAN, 3), new ItemStack(Items.ENDER_EYE, 1));
        this.recycleBinSalvaging(Items.RESPAWN_ANCHOR, new ItemStack(Items.CRYING_OBSIDIAN, 3), new ItemStack(Items.GLOWSTONE, 1));
        this.recycleBinSalvaging(Items.REPEATER, new ItemStack(Items.REDSTONE_TORCH, 1), new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.STONE, 2));
        this.recycleBinSalvaging(Items.COMPARATOR, new ItemStack(Items.REDSTONE_TORCH, 1), new ItemStack(Items.QUARTZ, 1), new ItemStack(Items.STONE, 2));
        this.recycleBinSalvaging(Items.TARGET, new ItemStack(Items.REDSTONE, 2), new ItemStack(Items.HAY_BLOCK, 1));
        this.recycleBinSalvaging(Items.LEVER, new ItemStack(Items.COBBLESTONE, 1), new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.TRIPWIRE_HOOK, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.DAYLIGHT_DETECTOR, new ItemStack(Items.QUARTZ, 2), new ItemStack(Items.GLASS, 1), new ItemStack(Items.OAK_SLAB, 1));
        this.recycleBinSalvaging(Items.PISTON, new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.COBBLESTONE, 2), new ItemStack(Items.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.STICKY_PISTON, new ItemStack(Items.PISTON, 1), new ItemStack(Items.SLIME_BALL, 1));
        this.recycleBinSalvaging(Items.DISPENSER, new ItemStack(Items.COBBLESTONE, 3), new ItemStack(Items.BOW, 1), new ItemStack(Items.REDSTONE, 1));
        this.recycleBinSalvaging(Items.DROPPER, new ItemStack(Items.COBBLESTONE, 3), new ItemStack(Items.REDSTONE, 1));
        this.recycleBinSalvaging(Items.HOPPER, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.CHEST, 1));
        this.recycleBinSalvaging(Items.OBSERVER, new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.QUARTZ, 1), new ItemStack(Items.COBBLESTONE, 3));
        this.recycleBinSalvaging(Items.RAIL, new ItemStack(Items.IRON_NUGGET, 2), new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.POWERED_RAIL, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_NUGGET, 6));
        this.recycleBinSalvaging(Items.ACTIVATOR_RAIL, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_NUGGET, 6));
        this.recycleBinSalvaging(Items.DETECTOR_RAIL, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_NUGGET, 6));
        this.recycleBinSalvaging(Items.MINECART, new ItemStack(Items.IRON_INGOT, 3));
        this.recycleBinSalvaging(Items.CHEST_MINECART, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Blocks.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.HOPPER_MINECART, new ItemStack(Items.IRON_INGOT, 6));
        this.recycleBinSalvaging(Items.TNT_MINECART, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.GUNPOWDER, 3));
        this.recycleBinSalvaging(Items.FURNACE_MINECART, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.COBBLESTONE, 4));
        this.recycleBinSalvaging(Items.WOODEN_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.WOODEN_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.WOODEN_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.WOODEN_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.WOODEN_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.OAK_PLANKS, 1));
        this.recycleBinSalvaging(Items.STONE_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.STONE_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.STONE_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.STONE_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.STONE_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.COBBLESTONE, 1));
        this.recycleBinSalvaging(Items.IRON_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.IRON_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.IRON_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.IRON_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.IRON_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.GOLDEN_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.GOLDEN_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.GOLDEN_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.GOLDEN_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.GOLDEN_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.DIAMOND_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.DIAMOND_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1));
        this.recycleBinSalvaging(Items.DIAMOND_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1));
        this.recycleBinSalvaging(Items.DIAMOND_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1));
        this.recycleBinSalvaging(Items.DIAMOND_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1));
        this.recycleBinSalvaging(Items.NETHERITE_SHOVEL, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.NETHERITE_PICKAXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_AXE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_HOE, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.WATER_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.LAVA_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.POWDER_SNOW_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.MILK_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.PUFFERFISH_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.SALMON_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.COD_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.TROPICAL_FISH_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.AXOLOTL_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.TADPOLE_BUCKET, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.FISHING_ROD, new ItemStack(Items.STICK, 1), new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.FLINT_AND_STEEL, new ItemStack(Items.FLINT, 1));
        this.recycleBinSalvaging(Items.SHEARS, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.BRUSH, new ItemStack(Items.COPPER_INGOT, 1), new ItemStack(Items.FEATHER, 1));
        this.recycleBinSalvaging(Items.NAME_TAG, new ItemStack(Items.STRING, 1), new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.LEAD, new ItemStack(Items.STRING, 3));
        this.recycleBinSalvaging(Items.COMPASS, new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.REDSTONE, 1));
        this.recycleBinSalvaging(Items.RECOVERY_COMPASS, new ItemStack(Items.COMPASS, 1), new ItemStack(Items.ECHO_SHARD, 5));
        this.recycleBinSalvaging(Items.CLOCK, new ItemStack(Items.GOLD_INGOT, 3), new ItemStack(Items.REDSTONE, 1));
        this.recycleBinSalvaging(Items.SPYGLASS, new ItemStack(Items.COPPER_INGOT, 1), new ItemStack(Items.AMETHYST_SHARD, 1));
        this.recycleBinSalvaging(Items.MAP, new ItemStack(Items.PAPER, 4));
        this.recycleBinSalvaging(Items.WRITABLE_BOOK, new ItemStack(Items.BOOK, 1), new ItemStack(Items.FEATHER, 1));
        this.recycleBinSalvaging(Items.SADDLE, new ItemStack(Items.LEATHER, 1));
        this.recycleBinSalvaging(Items.CARROT_ON_A_STICK, new ItemStack(Items.CARROT, 1), new ItemStack(Items.STICK, 1), new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.WARPED_FUNGUS_ON_A_STICK, new ItemStack(Items.WARPED_FUNGUS, 1), new ItemStack(Items.STICK, 1), new ItemStack(Items.STRING, 1));
        this.recycleBinSalvaging(Items.OAK_BOAT, new ItemStack(Items.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.OAK_CHEST_BOAT, new ItemStack(Items.OAK_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.SPRUCE_BOAT, new ItemStack(Items.SPRUCE_PLANKS, 2));
        this.recycleBinSalvaging(Items.SPRUCE_CHEST_BOAT, new ItemStack(Items.SPRUCE_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.BIRCH_BOAT, new ItemStack(Items.BIRCH_PLANKS, 2));
        this.recycleBinSalvaging(Items.BIRCH_CHEST_BOAT, new ItemStack(Items.BIRCH_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.JUNGLE_BOAT, new ItemStack(Items.JUNGLE_PLANKS, 2));
        this.recycleBinSalvaging(Items.JUNGLE_CHEST_BOAT, new ItemStack(Items.JUNGLE_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.ACACIA_BOAT, new ItemStack(Items.ACACIA_PLANKS, 2));
        this.recycleBinSalvaging(Items.ACACIA_CHEST_BOAT, new ItemStack(Items.ACACIA_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.DARK_OAK_BOAT, new ItemStack(Items.DARK_OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.DARK_OAK_CHEST_BOAT, new ItemStack(Items.DARK_OAK_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.MANGROVE_BOAT, new ItemStack(Items.MANGROVE_PLANKS, 2));
        this.recycleBinSalvaging(Items.MANGROVE_CHEST_BOAT, new ItemStack(Items.MANGROVE_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.CHERRY_BOAT, new ItemStack(Items.CHERRY_PLANKS, 2));
        this.recycleBinSalvaging(Items.CHERRY_CHEST_BOAT, new ItemStack(Items.CHERRY_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.BAMBOO_RAFT, new ItemStack(Items.BAMBOO_PLANKS, 2));
        this.recycleBinSalvaging(Items.BAMBOO_CHEST_RAFT, new ItemStack(Items.BAMBOO_PLANKS, 2), new ItemStack(Items.OAK_PLANKS, 4));
        this.recycleBinSalvaging(Items.TRIDENT, new ItemStack(Items.PRISMARINE_SHARD, 2));
        this.recycleBinSalvaging(Items.SHIELD, new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.LEATHER_HELMET, new ItemStack(Items.LEATHER, 2));
        this.recycleBinSalvaging(Items.LEATHER_CHESTPLATE, new ItemStack(Items.LEATHER, 3));
        this.recycleBinSalvaging(Items.LEATHER_LEGGINGS, new ItemStack(Items.LEATHER, 2));
        this.recycleBinSalvaging(Items.LEATHER_BOOTS, new ItemStack(Items.LEATHER, 1));
        this.recycleBinSalvaging(Items.IRON_HELMET, new ItemStack(Items.IRON_INGOT, 2));
        this.recycleBinSalvaging(Items.IRON_CHESTPLATE, new ItemStack(Items.IRON_INGOT, 3));
        this.recycleBinSalvaging(Items.IRON_LEGGINGS, new ItemStack(Items.IRON_INGOT, 2));
        this.recycleBinSalvaging(Items.IRON_BOOTS, new ItemStack(Items.IRON_INGOT, 1));
        this.recycleBinSalvaging(Items.CHAINMAIL_HELMET, new ItemStack(Items.IRON_NUGGET, 4));
        this.recycleBinSalvaging(Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.IRON_NUGGET, 6));
        this.recycleBinSalvaging(Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.IRON_NUGGET, 4));
        this.recycleBinSalvaging(Items.CHAINMAIL_BOOTS, new ItemStack(Items.IRON_NUGGET, 2));
        this.recycleBinSalvaging(Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_INGOT, 2));
        this.recycleBinSalvaging(Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_INGOT, 3));
        this.recycleBinSalvaging(Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_INGOT, 2));
        this.recycleBinSalvaging(Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_INGOT, 1));
        this.recycleBinSalvaging(Items.DIAMOND_HELMET, new ItemStack(Items.DIAMOND, 2));
        this.recycleBinSalvaging(Items.DIAMOND_CHESTPLATE, new ItemStack(Items.DIAMOND, 3));
        this.recycleBinSalvaging(Items.DIAMOND_LEGGINGS, new ItemStack(Items.DIAMOND, 2));
        this.recycleBinSalvaging(Items.DIAMOND_BOOTS, new ItemStack(Items.DIAMOND, 1));
        this.recycleBinSalvaging(Items.NETHERITE_HELMET, new ItemStack(Items.DIAMOND, 2), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_CHESTPLATE, new ItemStack(Items.DIAMOND, 3), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_LEGGINGS, new ItemStack(Items.DIAMOND, 2), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.NETHERITE_BOOTS, new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_SCRAP, 1));
        this.recycleBinSalvaging(Items.TURTLE_HELMET, new ItemStack(Items.SCUTE, 2));
        this.recycleBinSalvaging(Items.LEATHER_HORSE_ARMOR, new ItemStack(Items.LEATHER, 3));
        this.recycleBinSalvaging(Items.IRON_HORSE_ARMOR, new ItemStack(Items.IRON_INGOT, 3));
        this.recycleBinSalvaging(Items.GOLDEN_HORSE_ARMOR, new ItemStack(Items.GOLD_INGOT, 3));
        this.recycleBinSalvaging(Items.DIAMOND_HORSE_ARMOR, new ItemStack(Items.DIAMOND, 3));
        this.recycleBinSalvaging(Items.TNT, new ItemStack(Items.GUNPOWDER, 3), new ItemStack(Items.SAND, 2));
        this.recycleBinSalvaging(Items.TOTEM_OF_UNDYING, new ItemStack(Items.EMERALD, 4), new ItemStack(Items.OAK_PLANKS, 2));
        this.recycleBinSalvaging(Items.BOW, new ItemStack(Items.STICK, 2), new ItemStack(Items.STRING, 2));
        this.recycleBinSalvaging(Items.CROSSBOW, new ItemStack(Items.STICK, 1), new ItemStack(Items.STRING, 1), new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.TRIPWIRE_HOOK, 1));
        this.recycleBinSalvaging(Items.ARROW, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.TIPPED_ARROW, new ItemStack(Items.STICK, 1));
        this.recycleBinSalvaging(Items.SPECTRAL_ARROW, new ItemStack(Items.STICK, 1), new ItemStack(Items.GLOWSTONE_DUST, 1));
        this.recycleBinSalvaging(Items.POTION, new ItemStack(Items.GLASS_BOTTLE, 1));
        this.recycleBinSalvaging(Items.LINGERING_POTION, new ItemStack(Items.GLASS_BOTTLE, 1));
        this.recycleBinSalvaging(Items.SPLASH_POTION, new ItemStack(Items.GLASS_BOTTLE, 1));
        this.recycleBinSalvaging(Items.BOOK, new ItemStack(Items.LEATHER, 1), new ItemStack(Items.PAPER, 2));
        this.recycleBinSalvaging(Items.FLOWER_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.CREEPER_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.SKULL_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.MOJANG_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.GLOBE_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.PIGLIN_BANNER_PATTERN, new ItemStack(Items.PAPER, 1));
        this.recycleBinSalvaging(Items.ENCHANTED_BOOK, new ItemStack(Items.LEATHER, 1), new ItemStack(Items.PAPER, 2));

        SpecialRecipeBuilder.special(ModRecipeSerializers.DOOR_MAT_COPY_RECIPE.get()).save(this.consumer, Constants.MOD_ID + ":door_mat_copy");
    }

    private void simpleCombined(ItemLike first, ItemLike second, ItemLike result, int count, RecipeCategory category)
    {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(first).requires(second)
                .unlockedBy("has_first", this.hasItem.apply(first))
                .unlockedBy("has_second", this.hasItem.apply(second))
                .save(this.consumer);
    }

    private void table(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 6));
    }

    private void chair(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 4));
    }

    private void desk(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 6));
    }

    private void drawer(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 10));
    }

    private void woodenKitchenCabinetry(Block plank, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of(plank, 8), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenDrawer(Block plank, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of(plank, 12), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenSink(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 10), Material.of(Items.COPPER_INGOT, 1), Material.of(Items.QUARTZ_BLOCK, 1), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenStorageCabinet(Block plank, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of(plank, 12), Material.of(Items.WHITE_DYE, 1));
    }

    private void colouredKitchenCabinetry(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_cabinetry", ModTags.Items.WOODEN_KITCHEN_CABINETRY, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_CABINETRY)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenDrawer(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_drawers", ModTags.Items.WOODEN_KITCHEN_DRAWERS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_DRAWERS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenSink(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_sinks", ModTags.Items.WOODEN_KITCHEN_SINKS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_SINKS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenStorageCabinet(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_storage_cabinets", ModTags.Items.WOODEN_KITCHEN_STORAGE_CABINETS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("KD")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_STORAGE_CABINETS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void toaster(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 4), Material.of(Items.REDSTONE, 2));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_toaster", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void microwave(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 6), Material.of(Items.GLASS, 1), Material.of(Items.REDSTONE, 4));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_microwave", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void stove(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 12), Material.of(Items.GLASS, 1), Material.of(Items.REDSTONE, 6));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_stove", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void rangeHood(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 2));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_range_hood", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void fryingPan(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 3), Material.of(Items.LEATHER, 1), Material.of(Items.BLACK_DYE, 1));
    }

    private void recyclingBin(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(Items.PISTON, 1), Material.of(Items.REDSTONE, 2));
    }

    private void cuttingBoard(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 2));
    }

    private void plate(Block result)
    {
        this.workbenchCrafting(result, 4, Material.of(Items.QUARTZ_BLOCK, 1));
    }

    private void crate(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 8));
    }

    private void grill(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.GRILLS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void cooler(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of("planks", ItemTags.PLANKS, 4), Material.of(Items.WHITE_DYE, 1), Material.of("colouring_dye", dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.COOLERS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");

    }

    private void mailbox(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 8));
    }

    private void postBox(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 10), Material.of("planks", ItemTags.PLANKS, 8), Material.of(Items.BLUE_DYE, 1));
    }

    private void trampoline(Item dye, Block result)
    {
        this.workbenchCrafting(result, 4, Material.of(Items.IRON_INGOT, 4), Material.of(Items.STRING, 8), Material.of(dye, 1), Material.of(Items.SLIME_BALL, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.TRAMPOLINES)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void hedge(Block leaf, Block result)
    {
        this.workbenchCrafting(result, 8, Material.of(leaf, 6));
    }

    private void steppingStone(Block stone, Block result)
    {
        this.workbenchCrafting(result, 4, Material.of(stone, 1));
    }

    private void latticeFence(Block plank, Block result)
    {
        this.workbenchCrafting(result, 3, Material.of(plank, 4), Material.of(Items.STICK, 4));
    }

    private void latticeFenceGate(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 2), Material.of(Items.STICK, 4));
    }

    private void doorMat(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.WHEAT, 8));
    }

    private void sofa(Item dye, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of("planks", ItemTags.PLANKS, 6), Material.of(Items.WHEAT, 16), Material.of(Items.WHITE_WOOL, 2), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.SOFAS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void stool(Item dye, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of("planks", ItemTags.PLANKS, 3), Material.of(Items.WHEAT, 8), Material.of(Items.WHITE_WOOL, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.STOOLS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void lamp(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of("planks", ItemTags.PLANKS, 2), Material.of(Items.REDSTONE, 4), Material.of(Items.GLOWSTONE_DUST, 4), Material.of(Items.WHITE_WOOL, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.LAMPS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void ceilingFan(Block plank, Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 3), Material.of(plank, 4), Material.of(Items.REDSTONE, 4), Material.of(Items.GLOWSTONE_DUST, 4));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_ceiling_fan", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void ceilingLight(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3), Material.of(Items.GLOWSTONE_DUST, 4));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_ceiling_light", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void lightswitch(Block light, Block dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_lightswitch", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void doorbell(Block light)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3), Material.of(Items.GOLD_INGOT, 1));
    }

    private void storageCabinet(Block plank, Block result)
    {
        this.workbenchCrafting(result, 2, Material.of(plank, 8), Material.of(Items.IRON_INGOT, 1));
    }

    private void storageJar(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 2), Material.of(Items.GLASS, 1));
    }

    private void woodenToilet(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 3), Material.of(Items.QUARTZ_BLOCK, 5), Material.of(Items.IRON_INGOT, 1), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredToilet(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of("toilets", ModTags.Items.WOODEN_TOILETS, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(ModTags.Items.WOODEN_TOILETS)
                .requires(dye)
                .unlockedBy("has_ceiling_fan", this.hasTag.apply(ModTags.Items.WOODEN_TOILETS))
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.consumer);
    }

    private void woodenBasin(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 3), Material.of(Items.QUARTZ_BLOCK, 4), Material.of(Items.IRON_INGOT, 2), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredBasin(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of("basins", ModTags.Items.WOODEN_BASINS, 1), Material.of(dye, 1));
    }

    private void woodenBath(Block plank, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(plank, 5), Material.of(Items.QUARTZ_BLOCK, 8), Material.of(Items.IRON_INGOT, 2), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredBath(Item dye, Block result)
    {
        this.workbenchCrafting(result, 1, Material.of("baths", ModTags.Items.WOODEN_BATHS, 1), Material.of(dye, 1));
    }

    private void fridge(Item light, Item dark)
    {
        this.workbenchCrafting(light, 1, Material.of(Items.IRON_INGOT, 9), Material.of(Items.COPPER_INGOT, 3), Material.of(Items.REDSTONE, 4));
        this.workbenchCrafting(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_fridge", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.consumer);
    }

    private void television(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(Items.COPPER_INGOT, 2), Material.of(Blocks.GLASS, 1), Material.of(Items.REDSTONE, 16), Material.of(Items.AMETHYST_SHARD, 2));
    }

    private void computer(Block result)
    {
        this.workbenchCrafting(result, 1, Material.of(Items.IRON_INGOT, 12), Material.of(Items.COPPER_INGOT, 3), Material.of(Blocks.GLASS, 1), Material.of(Items.REDSTONE, 24), Material.of(Items.AMETHYST_SHARD, 2));
    }

    private void workbenchCrafting(ItemLike result, int count, Material<?> ... materials)
    {
        String resultName = result.asItem().toString();
        this.workbenchCrafting(resultName, result, count, materials);
    }

    private void workbenchCrafting(String name, ItemLike result, int count, Material<?> ... materials)
    {
        WorkbenchCraftingRecipe.Builder builder = WorkbenchCraftingRecipe.builder(result, count, this.hasItem, this.hasTag);
        for(Material<?> material : materials)
        {
            builder.requiresMaterial(material);
        }
        builder.save(this.consumer, Utils.resource("crafting/" + name));
    }

    private void cooking(String folder, RecipeCategory category, RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemLike input, ItemLike output, int time, float experience)
    {
        String baseName = input.asItem().toString();
        String resultName = output.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(input), category, output, experience, time, serializer)
                .unlockedBy("has_" + baseName, this.hasItem.apply(input))
                .save(this.consumer, Utils.resource(folder + "/" + resultName + "_from_" + baseName));
    }

    private void processing(String folder, RecipeSerializer<? extends ProcessingRecipe> serializer, Ingredient input, ItemLike output, int count, int time)
    {
        ResourceLocation recipeId = Utils.resource(folder + "/" + output.asItem());
        ProcessingRecipe.builder(input, new ItemStack(output, count), time, serializer).save(this.consumer, recipeId);
    }

    private void grillCooking(ItemLike rawItem, ItemLike cookedItem, int cookingTime, float experience)
    {
        this.cooking("grilling", RecipeCategory.FOOD, ModRecipeSerializers.GRILL_RECIPE.get(), rawItem, cookedItem, cookingTime, experience);
    }

    private void freezerSolidifying(ItemLike baseItem, ItemLike frozenItem, int freezeTime, float experience)
    {
        this.cooking("freezing", RecipeCategory.MISC, ModRecipeSerializers.FREEZER_RECIPE.get(), baseItem, frozenItem, freezeTime, experience);
    }

    private void toasterHeating(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.cooking("toasting", RecipeCategory.FOOD, ModRecipeSerializers.TOASTER_RECIPE.get(), baseItem, heatedItem, heatingTime, experience);
    }

    private void microwaveHeating(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.cooking("heating", RecipeCategory.FOOD, ModRecipeSerializers.MICROWAVE_RECIPE.get(), baseItem, heatedItem, heatingTime, experience);
    }

    private void fryingPanCooking(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.cooking("frying", RecipeCategory.FOOD, ModRecipeSerializers.FRYING_PAN_RECIPE.get(), baseItem, heatedItem, heatingTime, experience);
    }

    private void ovenBaking(ItemLike baseItem, ItemLike bakedItem, int count, int bakingTime)
    {
        this.processing("baking", ModRecipeSerializers.OVEN_BAKING.get(), Ingredient.of(baseItem), bakedItem, count, bakingTime);
    }

    private void cuttingBoardSlicing(ItemLike baseItem, ItemLike resultItem, int resultCount)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = resultItem.asItem().toString();
        SingleItemRecipeBuilder builder = new SingleItemRecipeBuilder(RecipeCategory.MISC, ModRecipeSerializers.CUTTING_BOARD_SLICING_RECIPE.get(), Ingredient.of(baseItem), resultItem, resultCount);
        builder.unlockedBy("has_" + baseName, this.hasItem.apply(baseItem)).save(this.consumer, Utils.resource("slicing/" + resultName + "_from_" + baseName));
    }

    private void cuttingBoardCombining(ItemLike combinedItem, int count, Ingredient ... inputs)
    {
        String baseName = combinedItem.asItem().toString();
        CuttingBoardCombiningRecipe.Builder builder = new CuttingBoardCombiningRecipe.Builder(new ItemStack(combinedItem, count));
        for(int i = inputs.length - 1; i >= 0; i--) // Reverse order since the code visualises the stacked items in the level
        {
            builder.add(inputs[i]);
        }
        builder.save(this.consumer, Utils.resource("combining/" + baseName));
    }

    private void sinkFluidTransmuting(Fluid fluid, Ingredient catalyst, ItemStack result)
    {
        String baseName = result.getItem().toString();
        SinkFluidTransmutingRecipe.Builder builder = SinkFluidTransmutingRecipe.Builder.from(fluid, catalyst, result);
        builder.save(this.consumer, Utils.resource("fluid_transmuting/" + baseName));
    }

    private Set<Item> recycledItems = new HashSet<>();

    private void recycleBinSalvaging(ItemLike baseItem, ItemStack ... outputItems)
    {
        if(this.recycledItems.contains(baseItem.asItem())) {
            throw new IllegalArgumentException(baseItem.asItem() + " is already recycled");
        }
        String baseName = baseItem.asItem().toString();
        RecycleBinRecyclingRecipe.Builder builder = new RecycleBinRecyclingRecipe.Builder(baseItem.asItem(), outputItems);
        builder.save(this.consumer, Utils.resource("recycling/" + baseName));
        this.recycledItems.add(baseItem.asItem());
    }
}
