package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.crafting.*;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class CommonRecipeProvider
{
    private final RecipeOutput output;
    private final ConditionalModConsumer modLoadedConsumer;
    private final Function<ItemLike, Criterion<?>> hasItem;
    private final Function<TagKey<Item>, Criterion<?>> hasTag;

    public CommonRecipeProvider(RecipeOutput output, ConditionalModConsumer modLoadedConsumer, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
    {
        this.output = output;
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
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELECTRICITY_GENERATOR_DARK.get())
                .requires(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get())
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_iron_ingot", this.hasItem.apply(Items.IRON_INGOT))
                .unlockedBy("has_redstone", this.hasItem.apply(Items.REDSTONE))
                .save(this.output);

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
                .save(this.output);

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
                .save(this.output);

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
                .save(this.output);

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
                .save(this.output);

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
        this.freezerSolidifying(ProcessingRecipe.Category.BLOCKS, Items.WATER_BUCKET, Items.ICE, 600, 1.0F);
        this.freezerSolidifying(ProcessingRecipe.Category.BLOCKS, Items.ICE, Items.PACKED_ICE, 1200, 1.0F);
        this.freezerSolidifying(ProcessingRecipe.Category.BLOCKS, Items.PACKED_ICE, Items.BLUE_ICE, 2400, 1.0F);

        // Toasting
        this.toasterHeating(ProcessingRecipe.Category.FOOD, ModItems.BREAD_SLICE.get(), ModItems.TOAST.get(), 300, 0.5F);
        this.toasterHeating(ProcessingRecipe.Category.FOOD, ModItems.CHEESE_SANDWICH.get(), ModItems.CHEESE_TOASTIE.get(), 400, 0.5F);

        // Slicing
        this.cuttingBoardSlicing(Blocks.MELON, Items.MELON_SLICE, 9);
        this.cuttingBoardSlicing(Items.BREAD, ModItems.BREAD_SLICE.get(), 6);
        this.cuttingBoardSlicing(Blocks.SHORT_GRASS, Items.WHEAT_SEEDS, 1);
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
        this.cuttingBoardSlicing(ModItems.COOKED_VEGETABLE_PIZZA.get(), ModItems.VEGETABLE_PIZZA_SLICE.get(), 6);
        this.cuttingBoardSlicing(ModItems.COOKED_MEATLOVERS_PIZZA.get(), ModItems.MEATLOVERS_PIZZA_SLICE.get(), 6);

        // Combining
        this.cuttingBoardCombining(ModItems.WHEAT_FLOUR.get(), 1,
                Ingredient.of(ModItems.SEA_SALT.get()),
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
        this.cuttingBoardCombining(ModItems.RAW_VEGETABLE_PIZZA.get(), 1,
                Ingredient.of(ModItems.CHEESE.get()),
                Ingredient.of(Items.POTATO),
                Ingredient.of(Items.BEETROOT),
                Ingredient.of(Items.CARROT),
                Ingredient.of(ModItems.DOUGH.get()));
        this.cuttingBoardCombining(ModItems.RAW_MEATLOVERS_PIZZA.get(), 1,
                Ingredient.of(ModItems.CHEESE.get()),
                Ingredient.of(Items.COOKED_PORKCHOP),
                Ingredient.of(Items.COOKED_CHICKEN),
                Ingredient.of(Items.COOKED_BEEF),
                Ingredient.of(ModItems.DOUGH.get()));

        // Heating
        this.microwaveHeating(ProcessingRecipe.Category.FOOD, Items.POTATO, Items.BAKED_POTATO, 200, 0.5F);

        // Frying
        this.fryingPanCooking(ProcessingRecipe.Category.FOOD, Items.SWEET_BERRIES, ModItems.SWEET_BERRY_JAM.get(), 400, 0.5F);
        this.fryingPanCooking(ProcessingRecipe.Category.FOOD, Items.GLOW_BERRIES, ModItems.GLOW_BERRY_JAM.get(), 400, 0.5F);
        this.fryingPanCooking(ProcessingRecipe.Category.FOOD, ModItems.CHEESE_SANDWICH.get(), ModItems.CHEESE_TOASTIE.get(), 400, 0.5F);

        // Baking
        this.ovenBaking(ProcessingRecipe.Category.FOOD, Items.POTATO, Items.BAKED_POTATO, 1, 200, 0F);
        this.ovenBaking(ProcessingRecipe.Category.FOOD, Items.WATER_BUCKET, ModItems.SEA_SALT.get(), 4, 1200, 0F);
        this.ovenBaking(ProcessingRecipe.Category.FOOD, ModItems.RAW_VEGETABLE_PIZZA.get(), ModItems.COOKED_VEGETABLE_PIZZA.get(), 1, 1200, 0F);
        this.ovenBaking(ProcessingRecipe.Category.FOOD, ModItems.RAW_MEATLOVERS_PIZZA.get(), ModItems.COOKED_MEATLOVERS_PIZZA.get(), 1, 1200, 0F);

        // Fluid Mixing
        this.sinkFluidTransmuting(Services.FLUID.getMilkFluid(), Ingredient.of(ModItems.SEA_SALT.get()), new ItemStack(ModItems.CHEESE.get(), 2));
        this.sinkFluidTransmuting(Fluids.WATER, Ingredient.of(ModItems.WHEAT_FLOUR.get()), new ItemStack(ModItems.DOUGH.get()));

        SpecialRecipeBuilder.special(DoorMatCopyRecipe::new).save(this.output, Constants.MOD_ID + ":door_mat_copy");
    }

    private void simpleCombined(ItemLike first, ItemLike second, ItemLike result, int count, RecipeCategory category)
    {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(first).requires(second)
                .unlockedBy("has_first", this.hasItem.apply(first))
                .unlockedBy("has_second", this.hasItem.apply(second))
                .save(this.output);
    }

    private void table(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 6));
    }

    private void chair(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 4));
    }

    private void desk(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 6));
    }

    private void drawer(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 10));
    }

    private void woodenKitchenCabinetry(Block plank, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of(plank, 8), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenDrawer(Block plank, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of(plank, 12), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenSink(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 10), Material.of(Items.COPPER_INGOT, 1), Material.of(Items.QUARTZ_BLOCK, 1), Material.of(Items.WHITE_DYE, 1));
    }

    private void woodenKitchenStorageCabinet(Block plank, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of(plank, 12), Material.of(Items.WHITE_DYE, 1));
    }

    private void colouredKitchenCabinetry(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_cabinetry", ModTags.Items.WOODEN_KITCHEN_CABINETRY, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_CABINETRY)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenDrawer(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_drawers", ModTags.Items.WOODEN_KITCHEN_DRAWERS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_DRAWERS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenSink(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_sinks", ModTags.Items.WOODEN_KITCHEN_SINKS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("D")
                .pattern("K")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_SINKS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void colouredKitchenStorageCabinet(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(dye, 1), Material.of("wooden_kitchen_storage_cabinets", ModTags.Items.WOODEN_KITCHEN_STORAGE_CABINETS, 1));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("KD")
                .define('D', dye)
                .define('K', ModTags.Items.COLOURED_KITCHEN_STORAGE_CABINETS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void toaster(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 4), Material.of(Items.REDSTONE, 2));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_toaster", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void microwave(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 6), Material.of(Items.GLASS, 1), Material.of(Items.REDSTONE, 4));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_microwave", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void stove(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 12), Material.of(Items.GLASS, 1), Material.of(Items.REDSTONE, 6));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_stove", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void rangeHood(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 2));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_range_hood", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void fryingPan(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 3), Material.of(Items.LEATHER, 1), Material.of(Items.BLACK_DYE, 1));
    }

    private void recyclingBin(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(Items.PISTON, 1), Material.of(Items.REDSTONE, 2));
    }

    private void cuttingBoard(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 2));
    }

    private void plate(Block result)
    {
        this.workbenchConstructing(result, 4, Material.of(Items.QUARTZ_BLOCK, 1));
    }

    private void crate(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 8));
    }

    private void grill(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.GRILLS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void cooler(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of("planks", ItemTags.PLANKS, 4), Material.of(Items.WHITE_DYE, 1), Material.of("colouring_dye", dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.COOLERS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");

    }

    private void mailbox(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 8));
    }

    private void postBox(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 10), Material.of("planks", ItemTags.PLANKS, 8), Material.of(Items.BLUE_DYE, 1));
    }

    private void trampoline(Item dye, Block result)
    {
        this.workbenchConstructing(result, 4, Material.of(Items.IRON_INGOT, 4), Material.of(Items.STRING, 8), Material.of(dye, 1), Material.of(Items.SLIME_BALL, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.TRAMPOLINES)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void hedge(Block leaf, Block result)
    {
        this.workbenchConstructing(result, 8, Material.of(leaf, 6));
    }

    private void steppingStone(Block stone, Block result)
    {
        this.workbenchConstructing(result, 4, Material.of(stone, 1));
    }

    private void latticeFence(Block plank, Block result)
    {
        this.workbenchConstructing(result, 3, Material.of(plank, 4), Material.of(Items.STICK, 4));
    }

    private void latticeFenceGate(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 2), Material.of(Items.STICK, 4));
    }

    private void doorMat(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.WHEAT, 8));
    }

    private void sofa(Item dye, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of("planks", ItemTags.PLANKS, 6), Material.of(Items.WHEAT, 16), Material.of(Items.WHITE_WOOL, 2), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.SOFAS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void stool(Item dye, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of("planks", ItemTags.PLANKS, 3), Material.of(Items.WHEAT, 8), Material.of(Items.WHITE_WOOL, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.STOOLS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void lamp(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of("planks", ItemTags.PLANKS, 2), Material.of(Items.REDSTONE, 4), Material.of(Items.GLOWSTONE_DUST, 4), Material.of(Items.WHITE_WOOL, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(dye)
                .requires(ModTags.Items.LAMPS)
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_dyeing");
    }

    private void ceilingFan(Block plank, Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 3), Material.of(plank, 4), Material.of(Items.REDSTONE, 4), Material.of(Items.GLOWSTONE_DUST, 4));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_ceiling_fan", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void ceilingLight(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3), Material.of(Items.GLOWSTONE_DUST, 4));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_ceiling_light", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void lightswitch(Block light, Block dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_lightswitch", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void doorbell(Block light)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 2), Material.of(Items.REDSTONE, 3), Material.of(Items.GOLD_INGOT, 1));
    }

    private void storageCabinet(Block plank, Block result)
    {
        this.workbenchConstructing(result, 2, Material.of(plank, 8), Material.of(Items.IRON_INGOT, 1));
    }

    private void storageJar(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 2), Material.of(Items.GLASS, 1));
    }

    private void woodenToilet(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 3), Material.of(Items.QUARTZ_BLOCK, 5), Material.of(Items.IRON_INGOT, 1), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredToilet(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of("toilets", ModTags.Items.WOODEN_TOILETS, 1), Material.of(dye, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                .requires(ModTags.Items.WOODEN_TOILETS)
                .requires(dye)
                .unlockedBy("has_ceiling_fan", this.hasTag.apply(ModTags.Items.WOODEN_TOILETS))
                .unlockedBy("has_dye", this.hasItem.apply(dye))
                .save(this.output);
    }

    private void woodenBasin(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 3), Material.of(Items.QUARTZ_BLOCK, 4), Material.of(Items.IRON_INGOT, 2), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredBasin(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of("basins", ModTags.Items.WOODEN_BASINS, 1), Material.of(dye, 1));
    }

    private void woodenBath(Block plank, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(plank, 5), Material.of(Items.QUARTZ_BLOCK, 8), Material.of(Items.IRON_INGOT, 2), Material.of(Items.COPPER_INGOT, 1));
    }

    private void colouredBath(Item dye, Block result)
    {
        this.workbenchConstructing(result, 1, Material.of("baths", ModTags.Items.WOODEN_BATHS, 1), Material.of(dye, 1));
    }

    private void fridge(Item light, Item dark)
    {
        this.workbenchConstructing(light, 1, Material.of(Items.IRON_INGOT, 9), Material.of(Items.COPPER_INGOT, 3), Material.of(Items.REDSTONE, 4));
        this.workbenchConstructing(dark, 1, Material.of(light, 1), Material.of(Items.BLACK_DYE, 1));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, dark)
                .requires(light)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_fridge", this.hasItem.apply(light))
                .unlockedBy("has_dye", this.hasItem.apply(Items.BLACK_DYE))
                .save(this.output);
    }

    private void television(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 8), Material.of(Items.COPPER_INGOT, 2), Material.of(Blocks.GLASS, 1), Material.of(Items.REDSTONE, 16), Material.of(Items.AMETHYST_SHARD, 2));
    }

    private void computer(Block result)
    {
        this.workbenchConstructing(result, 1, Material.of(Items.IRON_INGOT, 12), Material.of(Items.COPPER_INGOT, 3), Material.of(Blocks.GLASS, 1), Material.of(Items.REDSTONE, 24), Material.of(Items.AMETHYST_SHARD, 2));
    }

    private void workbenchConstructing(ItemLike result, int count, Material<?> ... materials)
    {
        String resultName = Utils.getItemName(result.asItem());
        this.workbenchConstructing(resultName, result, count, materials);
    }

    private void workbenchConstructing(String name, ItemLike result, int count, Material<?> ... materials)
    {
        WorkbenchContructingRecipe.Builder builder = WorkbenchContructingRecipe.builder(result, count, this.hasItem, this.hasTag);
        for(Material<?> material : materials)
        {
            builder.requiresMaterial(material);
        }
        builder.save(this.output, Utils.resource("constructing/" + name));
    }

    private <T extends ProcessingRecipe> void processing(ProcessingRecipe.Factory<T> factory, String folder, ProcessingRecipe.Category category, Ingredient ingredient, ItemLike result, int count, int time)
    {
        ResourceLocation recipeId = Utils.resource(folder + "/" + Utils.getItemName(result.asItem()));
        ProcessingRecipe.builder(factory, category, ingredient, new ItemStack(result, count), time).save(this.output, recipeId);
    }

    private void grillCooking(ProcessingRecipe.Category category, ItemLike rawItem, ItemLike cookedItem, int cookingTime, float experience)
    {
        this.processing(GrillCookingRecipe::new, "grilling", category, Ingredient.of(rawItem), cookedItem, 1, cookingTime);
    }

    private void freezerSolidifying(ProcessingRecipe.Category category, ItemLike baseItem, ItemLike frozenItem, int freezeTime, float experience)
    {
        this.processing(FreezerSolidifyingRecipe::new, "freezing", category, Ingredient.of(baseItem), frozenItem, 1, freezeTime);
    }

    private void toasterHeating(ProcessingRecipe.Category category, ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.processing(ToasterHeatingRecipe::new, "toasting", category, Ingredient.of(baseItem), heatedItem, 1, heatingTime);
    }

    private void microwaveHeating(ProcessingRecipe.Category category, ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.processing(MicrowaveHeatingRecipe::new, "heating", category, Ingredient.of(baseItem), heatedItem, 1, heatingTime);
    }

    private void fryingPanCooking(ProcessingRecipe.Category category, ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        this.processing(FryingPanCookingRecipe::new, "frying", category, Ingredient.of(baseItem), heatedItem, 1, heatingTime);
    }

    private void ovenBaking(ProcessingRecipe.Category category, ItemLike baseItem, ItemLike bakedItem, int count, int bakingTime, float experience)
    {
        this.processing(OvenBakingRecipe::new, "baking", category, Ingredient.of(baseItem), bakedItem, count, bakingTime);
    }

    private void cuttingBoardSlicing(ItemLike baseItem, ItemLike resultItem, int resultCount)
    {
        String baseName = Utils.getItemName(baseItem.asItem());
        String resultName = Utils.getItemName(resultItem.asItem());
        SingleItemRecipeBuilder builder = new SingleItemRecipeBuilder(RecipeCategory.MISC, CuttingBoardSlicingRecipe::new, Ingredient.of(baseItem), resultItem, resultCount);
        builder.unlockedBy("has_" + baseName, this.hasItem.apply(baseItem)).save(this.output, Utils.resource("slicing/" + resultName + "_from_" + baseName));
    }

    private void cuttingBoardCombining(ItemLike combinedItem, int count, Ingredient ... inputs)
    {
        String baseName = Utils.getItemName(combinedItem.asItem());
        CuttingBoardCombiningRecipe.Builder builder = new CuttingBoardCombiningRecipe.Builder(new ItemStack(combinedItem, count));
        for(int i = inputs.length - 1; i >= 0; i--) // Reverse order since the code visualises the stacked items in the level
        {
            builder.add(inputs[i]);
        }
        builder.save(this.output, Utils.resource("combining/" + baseName));
    }

    private void sinkFluidTransmuting(Fluid fluid, Ingredient catalyst, ItemStack result)
    {
        String baseName = Utils.getItemName(result.getItem());
        SinkFluidTransmutingRecipe.Builder builder = SinkFluidTransmutingRecipe.Builder.from(fluid, catalyst, result);
        builder.save(this.output, Utils.resource("fluid_transmuting/" + baseName));
    }
}
