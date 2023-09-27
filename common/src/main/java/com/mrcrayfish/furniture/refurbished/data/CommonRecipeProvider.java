package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class CommonRecipeProvider
{
    private final Consumer<FinishedRecipe> consumer;
    private final Function<ItemLike, CriterionTriggerInstance> has;

    public CommonRecipeProvider(Consumer<FinishedRecipe> consumer, Function<ItemLike, CriterionTriggerInstance> has)
    {
        this.consumer = consumer;
        this.has = has;
    }

    public void run()
    {
        this.mailbox(Blocks.OAK_SLAB, Blocks.OAK_PLANKS, Blocks.OAK_FENCE, ModBlocks.MAIL_BOX_OAK.get());
        this.mailbox(Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_FENCE, ModBlocks.MAIL_BOX_SPRUCE.get());
        this.mailbox(Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS, Blocks.BIRCH_FENCE, ModBlocks.MAIL_BOX_BIRCH.get());
        this.mailbox(Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_FENCE, ModBlocks.MAIL_BOX_JUNGLE.get());
        this.mailbox(Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS, Blocks.ACACIA_FENCE, ModBlocks.MAIL_BOX_ACACIA.get());
        this.mailbox(Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE, ModBlocks.MAIL_BOX_DARK_OAK.get());
        this.mailbox(Blocks.MANGROVE_SLAB, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_FENCE, ModBlocks.MAIL_BOX_MANGROVE.get());
        this.mailbox(Blocks.CHERRY_SLAB, Blocks.CHERRY_PLANKS, Blocks.CHERRY_FENCE, ModBlocks.MAIL_BOX_CHERRY.get());
        this.mailbox(Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_FENCE, ModBlocks.MAIL_BOX_CRIMSON.get());
        this.mailbox(Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS, Blocks.WARPED_FENCE, ModBlocks.MAIL_BOX_WARPED.get());
        this.postBox(ModBlocks.POST_BOX.get());
        this.grillCooking(Items.BEEF, Items.COOKED_BEEF, 200, 0.5F);
        this.grillCooking(Items.CHICKEN, Items.COOKED_CHICKEN, 200, 0.5F);
        this.grillCooking(Items.COD, Items.COOKED_COD, 200, 0.5F);
        this.grillCooking(Items.KELP, Items.DRIED_KELP, 200, 0.25F);
        this.grillCooking(Items.SALMON, Items.COOKED_SALMON, 200, 0.5F);
        this.grillCooking(Items.MUTTON, Items.COOKED_MUTTON, 200, 0.5F);
        this.grillCooking(Items.PORKCHOP, Items.COOKED_PORKCHOP, 200, 0.5F);
        this.grillCooking(Items.POTATO, Items.BAKED_POTATO, 200, 0.5F);
        this.grillCooking(Items.RABBIT, Items.COOKED_RABBIT, 200, 0.5F);
        this.freezerSolidifying(Items.WATER_BUCKET, Items.ICE, 300, 1.0F);
        this.toasterHeating(Items.POTATO, Items.BAKED_POTATO, 100, 0.5F);
        this.cuttingBoardSlicing(Blocks.MELON, Items.MELON_SLICE, 6);
        this.cuttingBoardSlicing(Items.APPLE, Items.DIAMOND, 8);
        this.microwaveHeating(Items.POTATO, Items.BAKED_POTATO, 200, 0.5F);
        this.fryingPanCooking(Items.BEEF, Items.COOKED_BEEF, 200, 0.5F);

        // Recycling
        this.recycleBinSalvaging(Items.OAK_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.OAK_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.OAK_FENCE, Items.OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.OAK_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.OAK_FENCE_GATE, Items.OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.OAK_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.OAK_TRAPDOOR, Items.OAK_PLANKS, 2);
        this.recycleBinSalvaging(Items.OAK_PRESSURE_PLATE, Items.OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.SPRUCE_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.SPRUCE_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.SPRUCE_FENCE, Items.SPRUCE_PLANKS, 1);
        this.recycleBinSalvaging(Items.SPRUCE_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.SPRUCE_FENCE_GATE, Items.SPRUCE_PLANKS, 1);
        this.recycleBinSalvaging(Items.SPRUCE_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.SPRUCE_TRAPDOOR, Items.SPRUCE_PLANKS, 2);
        this.recycleBinSalvaging(Items.SPRUCE_PRESSURE_PLATE, Items.SPRUCE_PLANKS, 1);
        this.recycleBinSalvaging(Items.BIRCH_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BIRCH_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BIRCH_FENCE, Items.BIRCH_PLANKS, 1);
        this.recycleBinSalvaging(Items.BIRCH_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.BIRCH_FENCE_GATE, Items.BIRCH_PLANKS, 1);
        this.recycleBinSalvaging(Items.BIRCH_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BIRCH_TRAPDOOR, Items.BIRCH_PLANKS, 2);
        this.recycleBinSalvaging(Items.BIRCH_PRESSURE_PLATE, Items.BIRCH_PLANKS, 1);
        this.recycleBinSalvaging(Items.JUNGLE_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.JUNGLE_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.JUNGLE_FENCE, Items.JUNGLE_PLANKS, 1);
        this.recycleBinSalvaging(Items.JUNGLE_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.JUNGLE_FENCE_GATE, Items.JUNGLE_PLANKS, 1);
        this.recycleBinSalvaging(Items.JUNGLE_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.JUNGLE_TRAPDOOR, Items.JUNGLE_PLANKS, 2);
        this.recycleBinSalvaging(Items.JUNGLE_PRESSURE_PLATE, Items.JUNGLE_PLANKS, 1);
        this.recycleBinSalvaging(Items.ACACIA_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.ACACIA_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.ACACIA_FENCE, Items.ACACIA_PLANKS, 1);
        this.recycleBinSalvaging(Items.ACACIA_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.ACACIA_FENCE_GATE, Items.ACACIA_PLANKS, 1);
        this.recycleBinSalvaging(Items.ACACIA_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.ACACIA_TRAPDOOR, Items.ACACIA_PLANKS, 2);
        this.recycleBinSalvaging(Items.ACACIA_PRESSURE_PLATE, Items.ACACIA_PLANKS, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.DARK_OAK_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE, Items.DARK_OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE_GATE, Items.DARK_OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.DARK_OAK_TRAPDOOR, Items.DARK_OAK_PLANKS, 2);
        this.recycleBinSalvaging(Items.DARK_OAK_PRESSURE_PLATE, Items.DARK_OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.MANGROVE_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.MANGROVE_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.MANGROVE_FENCE, Items.MANGROVE_PLANKS, 1);
        this.recycleBinSalvaging(Items.MANGROVE_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.MANGROVE_FENCE_GATE, Items.MANGROVE_PLANKS, 1);
        this.recycleBinSalvaging(Items.MANGROVE_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.MANGROVE_TRAPDOOR, Items.MANGROVE_PLANKS, 2);
        this.recycleBinSalvaging(Items.MANGROVE_PRESSURE_PLATE, Items.MANGROVE_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHERRY_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CHERRY_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CHERRY_FENCE, Items.CHERRY_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHERRY_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.CHERRY_FENCE_GATE, Items.CHERRY_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHERRY_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CHERRY_TRAPDOOR, Items.CHERRY_PLANKS, 2);
        this.recycleBinSalvaging(Items.CHERRY_PRESSURE_PLATE, Items.CHERRY_PLANKS, 1);
        this.recycleBinSalvaging(Items.BAMBOO_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BAMBOO_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BAMBOO_FENCE, Items.BAMBOO_PLANKS, 1);
        this.recycleBinSalvaging(Items.BAMBOO_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.BAMBOO_FENCE_GATE, Items.BAMBOO_PLANKS, 1);
        this.recycleBinSalvaging(Items.BAMBOO_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.BAMBOO_TRAPDOOR, Items.BAMBOO_PLANKS, 2);
        this.recycleBinSalvaging(Items.BAMBOO_PRESSURE_PLATE, Items.BAMBOO_PLANKS, 1);
        this.recycleBinSalvaging(Items.CRIMSON_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CRIMSON_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CRIMSON_FENCE, Items.CRIMSON_PLANKS, 1);
        this.recycleBinSalvaging(Items.CRIMSON_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.CRIMSON_FENCE_GATE, Items.CRIMSON_PLANKS, 1);
        this.recycleBinSalvaging(Items.CRIMSON_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.CRIMSON_TRAPDOOR, Items.CRIMSON_PLANKS, 2);
        this.recycleBinSalvaging(Items.CRIMSON_PRESSURE_PLATE, Items.CRIMSON_PLANKS, 1);
        this.recycleBinSalvaging(Items.WARPED_STAIRS, Items.STICK, 2);
        this.recycleBinSalvaging(Items.WARPED_SLAB, Items.STICK, 2);
        this.recycleBinSalvaging(Items.WARPED_FENCE, Items.WARPED_PLANKS, 1);
        this.recycleBinSalvaging(Items.WARPED_FENCE, Items.STICK, 1);
        this.recycleBinSalvaging(Items.WARPED_FENCE_GATE, Items.WARPED_PLANKS, 1);
        this.recycleBinSalvaging(Items.WARPED_FENCE_GATE, Items.STICK, 2);
        this.recycleBinSalvaging(Items.WARPED_TRAPDOOR, Items.WARPED_PLANKS, 2);
        this.recycleBinSalvaging(Items.WARPED_PRESSURE_PLATE, Items.WARPED_PLANKS, 1);
        this.recycleBinSalvaging(Items.STONE_STAIRS, Items.STONE, 1);
        this.recycleBinSalvaging(Items.COBBLESTONE_STAIRS, Items.COBBLESTONE, 1);
        this.recycleBinSalvaging(Items.COBBLESTONE_WALL, Items.COBBLESTONE, 1);
        this.recycleBinSalvaging(Items.MOSSY_COBBLESTONE_STAIRS, Items.MOSSY_COBBLESTONE, 1);
        this.recycleBinSalvaging(Items.MOSSY_COBBLESTONE_WALL, Items.MOSSY_COBBLESTONE, 1);
        this.recycleBinSalvaging(Items.STONE_BRICK_STAIRS, Items.STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.STONE_BRICK_WALL, Items.STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.MOSSY_STONE_BRICK_STAIRS, Items.MOSSY_STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.MOSSY_STONE_BRICK_WALL, Items.MOSSY_STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.GRANITE_STAIRS, Items.GRANITE, 1);
        this.recycleBinSalvaging(Items.GRANITE_WALL, Items.GRANITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_GRANITE, Items.GRANITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_GRANITE_STAIRS, Items.POLISHED_GRANITE, 1);
        this.recycleBinSalvaging(Items.DIORITE_STAIRS, Items.DIORITE, 1);
        this.recycleBinSalvaging(Items.DIORITE_WALL, Items.DIORITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DIORITE, Items.DIORITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DIORITE_STAIRS, Items.POLISHED_DIORITE, 1);
        this.recycleBinSalvaging(Items.ANDESITE_STAIRS, Items.ANDESITE, 1);
        this.recycleBinSalvaging(Items.ANDESITE_WALL, Items.ANDESITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_ANDESITE, Items.ANDESITE, 1);
        this.recycleBinSalvaging(Items.POLISHED_ANDESITE_STAIRS, Items.POLISHED_ANDESITE, 1);
        this.recycleBinSalvaging(Items.COBBLED_DEEPSLATE_STAIRS, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.COBBLED_DEEPSLATE_WALL, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.CHISELED_DEEPSLATE, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_STAIRS, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_STAIRS, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_WALL, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.POLISHED_DEEPSLATE_WALL, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICKS, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICKS, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_STAIRS, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_STAIRS, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_WALL, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_BRICK_WALL, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILES, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILES, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_STAIRS, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_STAIRS, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_WALL, Items.COBBLED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.DEEPSLATE_TILE_WALL, Items.POLISHED_DEEPSLATE, 1);
        this.recycleBinSalvaging(Items.BRICKS, Items.BRICK, 3);
        this.recycleBinSalvaging(Items.BRICK_STAIRS, Items.BRICK, 4);
        this.recycleBinSalvaging(Items.BRICK_SLAB, Items.BRICK, 1);
        this.recycleBinSalvaging(Items.BRICK_WALL, Items.BRICK, 4);
        this.recycleBinSalvaging(Items.MUD_BRICK_STAIRS, Items.MUD_BRICKS, 1);
        this.recycleBinSalvaging(Items.MUD_BRICK_WALL, Items.MUD_BRICKS, 1);
        this.recycleBinSalvaging(Items.SANDSTONE_STAIRS, Items.SANDSTONE, 1);
        this.recycleBinSalvaging(Items.SANDSTONE_WALL, Items.SANDSTONE, 1);
        this.recycleBinSalvaging(Items.SMOOTH_SANDSTONE_STAIRS, Items.SMOOTH_SANDSTONE, 1);
        this.recycleBinSalvaging(Items.CUT_SANDSTONE, Items.SANDSTONE, 1);
        this.recycleBinSalvaging(Items.RED_SANDSTONE_STAIRS, Items.RED_SANDSTONE, 1);
        this.recycleBinSalvaging(Items.RED_SANDSTONE_WALL, Items.RED_SANDSTONE, 1);
        this.recycleBinSalvaging(Items.SMOOTH_RED_SANDSTONE_STAIRS, Items.SMOOTH_RED_SANDSTONE, 1);
        this.recycleBinSalvaging(Items.CUT_RED_SANDSTONE, Items.RED_SANDSTONE, 1);
        this.recycleBinSalvaging(Items.PRISMARINE_STAIRS, Items.PRISMARINE, 1);
        this.recycleBinSalvaging(Items.PRISMARINE_BRICK_STAIRS, Items.PRISMARINE_BRICKS, 1);
        this.recycleBinSalvaging(Items.DARK_PRISMARINE_STAIRS, Items.DARK_PRISMARINE, 1);
        this.recycleBinSalvaging(Items.NETHER_BRICKS, Items.NETHER_BRICK, 3);
        this.recycleBinSalvaging(Items.NETHER_BRICK_STAIRS, Items.NETHER_BRICK, 4);
        this.recycleBinSalvaging(Items.NETHER_BRICK_SLAB, Items.NETHER_BRICK, 1);
        this.recycleBinSalvaging(Items.NETHER_BRICK_WALL, Items.NETHER_BRICK, 4);
        this.recycleBinSalvaging(Items.NETHER_BRICK_FENCE, Items.NETHER_BRICK, 1);
        this.recycleBinSalvaging(Items.RED_NETHER_BRICK_STAIRS, Items.RED_NETHER_BRICKS, 1);
        this.recycleBinSalvaging(Items.RED_NETHER_BRICK_WALL, Items.RED_NETHER_BRICKS, 1);
        this.recycleBinSalvaging(Items.BLACKSTONE_STAIRS, Items.BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.BLACKSTONE_WALL, Items.BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE, Items.BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_STAIRS, Items.BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_STAIRS, Items.POLISHED_BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_WALL, Items.BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.POLISHED_BLACKSTONE_WALL, Items.POLISHED_BLACKSTONE, 1);
        this.recycleBinSalvaging(Items.END_STONE_BRICK_STAIRS, Items.END_STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.END_STONE_BRICK_WALL, Items.END_STONE_BRICKS, 1);
        this.recycleBinSalvaging(Items.PURPUR_STAIRS, Items.PURPUR_BLOCK, 1);
        this.recycleBinSalvaging(Items.PURPUR_PILLAR, Items.PURPUR_BLOCK, 1);
        this.recycleBinSalvaging(Items.IRON_BARS, Items.IRON_NUGGET, 2);
        this.recycleBinSalvaging(Items.IRON_DOOR, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.IRON_TRAPDOOR, Items.IRON_INGOT, 2);
        this.recycleBinSalvaging(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.CHAIN, Items.IRON_NUGGET, 6);
        this.recycleBinSalvaging(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, Items.GOLD_INGOT, 1);
        this.recycleBinSalvaging(Items.QUARTZ_STAIRS, Items.QUARTZ_BLOCK, 1);
        this.recycleBinSalvaging(Items.QUARTZ_BRICKS, Items.QUARTZ_BLOCK, 1);
        this.recycleBinSalvaging(Items.SMOOTH_QUARTZ_STAIRS, Items.SMOOTH_QUARTZ, 1);
        this.recycleBinSalvaging(Items.AMETHYST_BLOCK, Items.AMETHYST_SHARD, 2);
        this.recycleBinSalvaging(Items.CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.EXPOSED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.WEATHERED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.OXIDIZED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.WAXED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER, Items.COPPER_INGOT, 4);
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Items.COPPER_INGOT, 8);
        this.recycleBinSalvaging(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, Items.COPPER_INGOT, 2);
        this.recycleBinSalvaging(Items.WHITE_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.GRAY_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.BLACK_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.BROWN_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.RED_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.ORANGE_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.YELLOW_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.LIME_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.GREEN_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.CYAN_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.BLUE_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.PURPLE_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.MAGENTA_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.PINK_WOOL, Items.STRING, 2);
        this.recycleBinSalvaging(Items.WHITE_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.GRAY_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BLACK_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BROWN_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.RED_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.ORANGE_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.YELLOW_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIME_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.GREEN_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.CYAN_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BLUE_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.PURPLE_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.MAGENTA_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.PINK_CARPET, Items.STRING, 1);
        this.recycleBinSalvaging(Items.SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.WHITE_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.GRAY_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.BLACK_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.BROWN_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.RED_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.ORANGE_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.YELLOW_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.LIME_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.GREEN_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.CYAN_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.BLUE_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.PURPLE_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.MAGENTA_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.PINK_SHULKER_BOX, Items.SHULKER_SHELL, 2);
        this.recycleBinSalvaging(Items.SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.WHITE_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.GRAY_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.BLACK_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.BROWN_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.RED_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.ORANGE_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.YELLOW_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.LIME_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.GREEN_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.CYAN_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.BLUE_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.PURPLE_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.MAGENTA_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.PINK_SHULKER_BOX, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.WHITE_BED, Items.WHITE_WOOL, 2);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_BED, Items.LIGHT_GRAY_WOOL, 2);
        this.recycleBinSalvaging(Items.GRAY_BED, Items.GRAY_WOOL, 2);
        this.recycleBinSalvaging(Items.BLACK_BED, Items.BLACK_WOOL, 2);
        this.recycleBinSalvaging(Items.BROWN_BED, Items.BROWN_WOOL, 2);
        this.recycleBinSalvaging(Items.RED_BED, Items.RED_WOOL, 2);
        this.recycleBinSalvaging(Items.ORANGE_BED, Items.ORANGE_WOOL, 2);
        this.recycleBinSalvaging(Items.YELLOW_BED, Items.YELLOW_WOOL, 2);
        this.recycleBinSalvaging(Items.LIME_BED, Items.LIME_WOOL, 2);
        this.recycleBinSalvaging(Items.GREEN_BED, Items.GREEN_WOOL, 2);
        this.recycleBinSalvaging(Items.CYAN_BED, Items.CYAN_WOOL, 2);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_BED, Items.LIGHT_BLUE_WOOL, 2);
        this.recycleBinSalvaging(Items.BLUE_BED, Items.BLUE_WOOL, 2);
        this.recycleBinSalvaging(Items.PURPLE_BED, Items.PURPLE_WOOL, 2);
        this.recycleBinSalvaging(Items.MAGENTA_BED, Items.MAGENTA_WOOL, 2);
        this.recycleBinSalvaging(Items.PINK_BED, Items.PINK_WOOL, 2);
        this.recycleBinSalvaging(Items.WHITE_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.GRAY_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.BLACK_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.BROWN_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.RED_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.ORANGE_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.YELLOW_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.LIME_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.GREEN_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.CYAN_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.BLUE_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.PURPLE_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.MAGENTA_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.PINK_BED, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.WHITE_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.GRAY_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BLACK_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BROWN_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.RED_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.ORANGE_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.YELLOW_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIME_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.GREEN_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.CYAN_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BLUE_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.PURPLE_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.MAGENTA_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.PINK_CANDLE, Items.STRING, 1);
        this.recycleBinSalvaging(Items.WHITE_BANNER, Items.WHITE_WOOL, 3);
        this.recycleBinSalvaging(Items.LIGHT_GRAY_BANNER, Items.LIGHT_GRAY_WOOL, 3);
        this.recycleBinSalvaging(Items.GRAY_BANNER, Items.GRAY_WOOL, 3);
        this.recycleBinSalvaging(Items.BLACK_BANNER, Items.BLACK_WOOL, 3);
        this.recycleBinSalvaging(Items.BROWN_BANNER, Items.BROWN_WOOL, 3);
        this.recycleBinSalvaging(Items.RED_BANNER, Items.RED_WOOL, 3);
        this.recycleBinSalvaging(Items.ORANGE_BANNER, Items.ORANGE_WOOL, 3);
        this.recycleBinSalvaging(Items.YELLOW_BANNER, Items.YELLOW_WOOL, 3);
        this.recycleBinSalvaging(Items.LIME_BANNER, Items.LIME_WOOL, 3);
        this.recycleBinSalvaging(Items.GREEN_BANNER, Items.GREEN_WOOL, 3);
        this.recycleBinSalvaging(Items.CYAN_BANNER, Items.CYAN_WOOL, 3);
        this.recycleBinSalvaging(Items.LIGHT_BLUE_BANNER, Items.LIGHT_BLUE_WOOL, 3);
        this.recycleBinSalvaging(Items.BLUE_BANNER, Items.BLUE_WOOL, 3);
        this.recycleBinSalvaging(Items.PURPLE_BANNER, Items.PURPLE_WOOL, 3);
        this.recycleBinSalvaging(Items.MAGENTA_BANNER, Items.MAGENTA_WOOL, 3);
        this.recycleBinSalvaging(Items.PINK_BANNER, Items.PINK_WOOL, 3);
        this.recycleBinSalvaging(Items.CALIBRATED_SCULK_SENSOR, Items.SCULK_SENSOR, 1);
        this.recycleBinSalvaging(Items.CALIBRATED_SCULK_SENSOR, Items.AMETHYST_SHARD, 2);
        this.recycleBinSalvaging(Items.REDSTONE_TORCH, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.LANTERN, Items.IRON_NUGGET, 4);
        this.recycleBinSalvaging(Items.LANTERN, Items.TORCH, 1);
        this.recycleBinSalvaging(Items.SOUL_LANTERN, Items.IRON_NUGGET, 4);
        this.recycleBinSalvaging(Items.SOUL_LANTERN, Items.SOUL_TORCH, 1);
        this.recycleBinSalvaging(Items.SEA_LANTERN, Items.PRISMARINE_SHARD, 2);
        this.recycleBinSalvaging(Items.SEA_LANTERN, Items.PRISMARINE_CRYSTALS, 3);
        this.recycleBinSalvaging(Items.REDSTONE_LAMP, Items.REDSTONE, 3);
        this.recycleBinSalvaging(Items.CRAFTING_TABLE, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.STONECUTTER, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.CARTOGRAPHY_TABLE, Items.PAPER, 1);
        this.recycleBinSalvaging(Items.CARTOGRAPHY_TABLE, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.FLETCHING_TABLE, Items.FLINT, 1);
        this.recycleBinSalvaging(Items.FLETCHING_TABLE, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.SMITHING_TABLE, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.SMITHING_TABLE, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.GRINDSTONE, ItemTags.PLANKS, 1);
        this.recycleBinSalvaging(Items.GRINDSTONE, Items.STONE_SLAB, 1);
        this.recycleBinSalvaging(Items.LOOM, Items.STRING, 1);
        this.recycleBinSalvaging(Items.LOOM, ItemTags.PLANKS, 1);
        this.recycleBinSalvaging(Items.FURNACE, ItemTags.STONE_CRAFTING_MATERIALS, 4);
        this.recycleBinSalvaging(Items.SMOKER, Items.FURNACE, 1);
        this.recycleBinSalvaging(Items.BLAST_FURNACE, Items.FURNACE, 1);
        this.recycleBinSalvaging(Items.BLAST_FURNACE, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.CAMPFIRE, Items.COAL, 1);
        this.recycleBinSalvaging(Items.SOUL_CAMPFIRE, Items.SOUL_SAND, 1);
        this.recycleBinSalvaging(Items.ANVIL, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.ANVIL, Items.IRON_BLOCK, 2);
        this.recycleBinSalvaging(Items.CHIPPED_ANVIL, Items.IRON_INGOT, 2);
        this.recycleBinSalvaging(Items.CHIPPED_ANVIL, Items.IRON_BLOCK, 1);
        this.recycleBinSalvaging(Items.DAMAGED_ANVIL, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.COMPOSTER, ItemTags.WOODEN_SLABS, 3);
        this.recycleBinSalvaging(Items.NOTE_BLOCK, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.NOTE_BLOCK, ItemTags.PLANKS, 3);
        this.recycleBinSalvaging(Items.JUKEBOX, Items.DIAMOND, 1);
        this.recycleBinSalvaging(Items.JUKEBOX, ItemTags.PLANKS, 3);
        this.recycleBinSalvaging(Items.ENCHANTING_TABLE, Items.DIAMOND, 1);
        this.recycleBinSalvaging(Items.ENCHANTING_TABLE, Items.OBSIDIAN, 2);
        this.recycleBinSalvaging(Items.ENCHANTING_TABLE, Items.BOOK, 1);
        this.recycleBinSalvaging(Items.END_CRYSTAL, Items.ENDER_EYE, 1);
        this.recycleBinSalvaging(Items.BREWING_STAND, ItemTags.STONE_CRAFTING_MATERIALS, 2);
        this.recycleBinSalvaging(Items.BREWING_STAND, Items.BLAZE_ROD, 1);
        this.recycleBinSalvaging(Items.CAULDRON, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.BELL, Items.GOLD_INGOT, 2);
        this.recycleBinSalvaging(Items.BELL, Items.STICK, 1);
        this.recycleBinSalvaging(Items.BEACON, Items.NETHER_STAR, 1);
        this.recycleBinSalvaging(Items.BEACON, Items.OBSIDIAN, 2);
        this.recycleBinSalvaging(Items.CONDUIT, Items.NAUTILUS_SHELL, 3);
        this.recycleBinSalvaging(Items.CONDUIT, Items.HEART_OF_THE_SEA, 1);
        this.recycleBinSalvaging(Items.LODESTONE, Items.NETHERITE_INGOT, 1);
        this.recycleBinSalvaging(Items.LODESTONE, Items.CHISELED_STONE_BRICKS, 3);
        this.recycleBinSalvaging(Items.LADDER, Items.STICK, 1);
        this.recycleBinSalvaging(Items.SCAFFOLDING, Items.BAMBOO, 1);
        this.recycleBinSalvaging(Items.SCAFFOLDING, Items.STRING, 1);
        this.recycleBinSalvaging(Items.BEEHIVE, Items.HONEYCOMB, 2);
        this.recycleBinSalvaging(Items.BEEHIVE, ItemTags.PLANKS, 3);
        this.recycleBinSalvaging(Items.LIGHTNING_ROD, Items.COPPER_INGOT, 1);
        this.recycleBinSalvaging(Items.FLOWER_POT, Items.BRICK, 1);
        this.recycleBinSalvaging(Items.DECORATED_POT, Items.BRICK, 1);
        this.recycleBinSalvaging(Items.ARMOR_STAND, Items.STICK, 3);
        this.recycleBinSalvaging(Items.ITEM_FRAME, Items.LEATHER, 1);
        this.recycleBinSalvaging(Items.ITEM_FRAME, Items.STICK, 3);
        this.recycleBinSalvaging(Items.GLOW_ITEM_FRAME, Items.LEATHER, 1);
        this.recycleBinSalvaging(Items.GLOW_ITEM_FRAME, Items.STICK, 3);
        this.recycleBinSalvaging(Items.PAINTING, Items.STICK, 3);
        this.recycleBinSalvaging(Items.PAINTING, ItemTags.WOOL, 1);
        this.recycleBinSalvaging(Items.BOOKSHELF, Items.BOOK, 2);
        this.recycleBinSalvaging(Items.BOOKSHELF, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.CHISELED_BOOKSHELF, ItemTags.WOODEN_SLABS, 1);
        this.recycleBinSalvaging(Items.CHISELED_BOOKSHELF, ItemTags.PLANKS, 3);
        this.recycleBinSalvaging(Items.LECTERN, ItemTags.WOODEN_SLABS, 2);
        this.recycleBinSalvaging(Items.LECTERN, Items.BOOKSHELF, 1);
        this.recycleBinSalvaging(Items.OAK_SIGN, Items.OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.SPRUCE_SIGN, Items.SPRUCE_PLANKS, 1);
        this.recycleBinSalvaging(Items.BIRCH_SIGN, Items.BIRCH_PLANKS, 1);
        this.recycleBinSalvaging(Items.JUNGLE_SIGN, Items.JUNGLE_PLANKS, 1);
        this.recycleBinSalvaging(Items.ACACIA_SIGN, Items.ACACIA_PLANKS, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_SIGN, Items.DARK_OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.MANGROVE_SIGN, Items.MANGROVE_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHERRY_SIGN, Items.CHERRY_PLANKS, 1);
        this.recycleBinSalvaging(Items.CRIMSON_SIGN, Items.CRIMSON_PLANKS, 1);
        this.recycleBinSalvaging(Items.WARPED_SIGN, Items.WARPED_PLANKS, 1);
        this.recycleBinSalvaging(Items.BAMBOO_SIGN, Items.BAMBOO_PLANKS, 1);
        this.recycleBinSalvaging(Items.OAK_HANGING_SIGN, Items.OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.SPRUCE_HANGING_SIGN, Items.SPRUCE_PLANKS, 1);
        this.recycleBinSalvaging(Items.BIRCH_HANGING_SIGN, Items.BIRCH_PLANKS, 1);
        this.recycleBinSalvaging(Items.JUNGLE_HANGING_SIGN, Items.JUNGLE_PLANKS, 1);
        this.recycleBinSalvaging(Items.ACACIA_HANGING_SIGN, Items.ACACIA_PLANKS, 1);
        this.recycleBinSalvaging(Items.DARK_OAK_HANGING_SIGN, Items.DARK_OAK_PLANKS, 1);
        this.recycleBinSalvaging(Items.MANGROVE_HANGING_SIGN, Items.MANGROVE_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHERRY_HANGING_SIGN, Items.CHERRY_PLANKS, 1);
        this.recycleBinSalvaging(Items.CRIMSON_HANGING_SIGN, Items.CRIMSON_PLANKS, 1);
        this.recycleBinSalvaging(Items.WARPED_HANGING_SIGN, Items.WARPED_PLANKS, 1);
        this.recycleBinSalvaging(Items.BAMBOO_HANGING_SIGN, Items.BAMBOO_PLANKS, 1);
        this.recycleBinSalvaging(Items.CHEST, ItemTags.PLANKS, 3);
        this.recycleBinSalvaging(Items.TRAPPED_CHEST, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.TRAPPED_CHEST, Items.TRIPWIRE_HOOK, 1);
        this.recycleBinSalvaging(Items.BARREL, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.ENDER_CHEST, Items.OBSIDIAN, 3);
        this.recycleBinSalvaging(Items.ENDER_CHEST, Items.ENDER_EYE, 1);
        this.recycleBinSalvaging(Items.RESPAWN_ANCHOR, Items.CRYING_OBSIDIAN, 3);
        this.recycleBinSalvaging(Items.RESPAWN_ANCHOR, Items.GLOWSTONE, 1);
        this.recycleBinSalvaging(Items.REPEATER, Items.REDSTONE_TORCH, 1);
        this.recycleBinSalvaging(Items.REPEATER, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.REPEATER, Items.STONE, 2);
        this.recycleBinSalvaging(Items.COMPARATOR, Items.REDSTONE_TORCH, 1);
        this.recycleBinSalvaging(Items.COMPARATOR, Items.QUARTZ, 1);
        this.recycleBinSalvaging(Items.COMPARATOR, Items.STONE, 2);
        this.recycleBinSalvaging(Items.TARGET, Items.REDSTONE, 2);
        this.recycleBinSalvaging(Items.TARGET, Items.HAY_BLOCK, 1);
        this.recycleBinSalvaging(Items.LEVER, Items.COBBLESTONE, 1);
        this.recycleBinSalvaging(Items.LEVER, Items.STICK, 1);
        this.recycleBinSalvaging(Items.TRIPWIRE_HOOK, Items.STICK, 1);
        this.recycleBinSalvaging(Items.DAYLIGHT_DETECTOR, Items.QUARTZ, 2);
        this.recycleBinSalvaging(Items.DAYLIGHT_DETECTOR, Items.GLASS, 1);
        this.recycleBinSalvaging(Items.DAYLIGHT_DETECTOR, ItemTags.WOODEN_SLABS, 1);
        this.recycleBinSalvaging(Items.PISTON, Items.IRON_INGOT, 1);
        this.recycleBinSalvaging(Items.PISTON, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.PISTON, Items.COBBLESTONE, 2);
        this.recycleBinSalvaging(Items.PISTON, ItemTags.PLANKS, 2);
        this.recycleBinSalvaging(Items.STICKY_PISTON, Items.PISTON, 1);
        this.recycleBinSalvaging(Items.STICKY_PISTON, Items.SLIME_BALL, 1);
        this.recycleBinSalvaging(Items.DISPENSER, Items.COBBLESTONE, 3);
        this.recycleBinSalvaging(Items.DISPENSER, Items.BOW, 1);
        this.recycleBinSalvaging(Items.DISPENSER, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.DROPPER, Items.COBBLESTONE, 3);
        this.recycleBinSalvaging(Items.DROPPER, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.HOPPER, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.HOPPER, Items.CHEST, 1);
        this.recycleBinSalvaging(Items.OBSERVER, Items.REDSTONE, 1);
        this.recycleBinSalvaging(Items.OBSERVER, Items.QUARTZ, 1);
        this.recycleBinSalvaging(Items.OBSERVER, Items.COBBLESTONE, 3);
        this.recycleBinSalvaging(Items.RAIL, Items.IRON_NUGGET, 2);
        this.recycleBinSalvaging(Items.RAIL, Items.STICK, 1);
        this.recycleBinSalvaging(Items.POWERED_RAIL, Items.STICK, 1);
        this.recycleBinSalvaging(Items.POWERED_RAIL, Items.GOLD_NUGGET, 6);
        this.recycleBinSalvaging(Items.ACTIVATOR_RAIL, Items.STICK, 1);
        this.recycleBinSalvaging(Items.ACTIVATOR_RAIL, Items.IRON_NUGGET, 6);
        this.recycleBinSalvaging(Items.DETECTOR_RAIL, Items.STICK, 1);
        this.recycleBinSalvaging(Items.DETECTOR_RAIL, Items.IRON_NUGGET, 6);
        this.recycleBinSalvaging(Items.MINECART, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.CHEST_MINECART, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.CHEST_MINECART, ItemTags.PLANKS, 4);
        this.recycleBinSalvaging(Items.HOPPER_MINECART, Items.IRON_INGOT, 6);
        this.recycleBinSalvaging(Items.TNT_MINECART, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.TNT_MINECART, Items.GUNPOWDER, 3);
        this.recycleBinSalvaging(Items.FURNACE_MINECART, Items.IRON_INGOT, 3);
        this.recycleBinSalvaging(Items.FURNACE_MINECART, ItemTags.STONE_CRAFTING_MATERIALS, 4);
        this.recycleBinSalvaging(Items.TNT, Items.GUNPOWDER, 3);
    }
    
    private void mailbox(Block slab, Block plank, Block fence, Block result)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("SSS")
                .pattern("PCP")
                .pattern(" F ")
                .define('S', slab)
                .define('P', plank)
                .define('C', Blocks.CHEST)
                .define('F', fence)
                .unlockedBy("has_planks", this.has.apply(plank))
                .save(this.consumer);
    }

    private void postBox(Block result)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("DID")
                .pattern("ICI")
                .pattern("IBI")
                .define('D', Items.BLUE_DYE)
                .define('I', Items.IRON_INGOT)
                .define('B', Blocks.IRON_BLOCK)
                .define('C', Blocks.CHEST)
                .unlockedBy("has_ingots", this.has.apply(Items.IRON_INGOT))
                .save(this.consumer);
    }

    private void grillCooking(ItemLike rawItem, ItemLike cookedItem, int cookingTime, float experience)
    {
        String baseName = rawItem.asItem().toString();
        String resultName = cookedItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(rawItem), RecipeCategory.FOOD, cookedItem, experience, cookingTime, ModRecipeSerializers.GRILL_RECIPE.get())
                .unlockedBy("has_" + baseName, this.has.apply(rawItem))
                .save(this.consumer, resultName + "_from_grill_cooking");
    }

    private void freezerSolidifying(ItemLike baseItem, ItemLike frozenItem, int freezeTime, float experience)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = frozenItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(baseItem), RecipeCategory.MISC, frozenItem, experience, freezeTime, ModRecipeSerializers.FREEZER_RECIPE.get())
                .unlockedBy("has_" + baseName, this.has.apply(baseItem))
                .save(this.consumer, resultName + "_from_freezer_solidifying");
    }

    private void toasterHeating(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = heatedItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(baseItem), RecipeCategory.FOOD, heatedItem, experience, heatingTime, ModRecipeSerializers.TOASTER_RECIPE.get())
                .unlockedBy("has_" + baseName, this.has.apply(baseItem))
                .save(this.consumer, resultName + "_from_toaster_heating");
    }

    private void cuttingBoardSlicing(ItemLike baseItem, ItemLike resultItem, int resultCount)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = resultItem.asItem().toString();
        SingleItemRecipeBuilder builder = new SingleItemRecipeBuilder(RecipeCategory.MISC, ModRecipeSerializers.CUTTING_BOARD_RECIPE.get(), Ingredient.of(baseItem), resultItem, resultCount);
        builder.unlockedBy("has_" + baseName, this.has.apply(baseItem)).save(this.consumer, resultName + "_from_cutting_board_slicing");
    }

    private void microwaveHeating(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = heatedItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(baseItem), RecipeCategory.MISC, heatedItem, experience, heatingTime, ModRecipeSerializers.MICROWAVE_RECIPE.get())
                .unlockedBy("has_" + baseName, this.has.apply(baseItem))
                .save(this.consumer, resultName + "_from_microwave_heating");
    }

    private void fryingPanCooking(ItemLike baseItem, ItemLike heatedItem, int heatingTime, float experience)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = heatedItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(baseItem), RecipeCategory.FOOD, heatedItem, experience, heatingTime, ModRecipeSerializers.FRYING_PAN_RECIPE.get())
                .unlockedBy("has_" + baseName, this.has.apply(baseItem))
                .save(this.consumer, resultName + "_from_frying_pan_cooking");
    }

    private void recycleBinSalvaging(ItemLike baseItem, ItemLike resultItem, int resultCount)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = resultItem.asItem().toString();
        SingleItemRecipeBuilder builder = new SingleItemRecipeBuilder(RecipeCategory.MISC, ModRecipeSerializers.RECYCLE_BIN_RECIPE.get(), Ingredient.of(resultItem), baseItem, resultCount);
        builder.unlockedBy("has_" + baseName, this.has.apply(baseItem)).save(this.consumer, resultName + "_from_recycling_" + baseName);
    }

    private void recycleBinSalvaging(ItemLike baseItem, TagKey<Item> resultItem, int resultCount)
    {
        String baseName = baseItem.asItem().toString();
        String resultName = resultItem.location().toString().replace(":", "_");
        SingleItemRecipeBuilder builder = new SingleItemRecipeBuilder(RecipeCategory.MISC, ModRecipeSerializers.RECYCLE_BIN_RECIPE.get(), Ingredient.of(resultItem), baseItem, resultCount);
        builder.unlockedBy("has_" + baseName, this.has.apply(baseItem)).save(this.consumer, resultName + "_from_recycling_" + baseName);
    }
}
