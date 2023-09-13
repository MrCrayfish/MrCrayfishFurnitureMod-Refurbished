package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
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
}
