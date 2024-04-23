package com.mrcrayfish.furniture.refurbished.asm;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class Loader implements Runnable
{
    public static final String RECIPE_BOOK_TYPE_FREEZER = "REFURBISHED_FURNITURE_FREEZER";
    public static final String RECIPE_BOOK_CATEGORY_SEARCH = "REFURBISHED_FURNITURE_SEARCH";
    public static final String RECIPE_BOOK_CATEGORY_BLOCKS = "REFURBISHED_FURNITURE_BLOCKS";
    public static final String RECIPE_BOOK_CATEGORY_ITEMS = "REFURBISHED_FURNITURE_ITEMS";
    public static final String RECIPE_BOOK_CATEGORY_FOOD = "REFURBISHED_FURNITURE_FOOD";
    public static final String RECIPE_BOOK_CATEGORY_MISC = "REFURBISHED_FURNITURE_MISC";

    @Override
    public void run()
    {
        this.registerRecipeBookTypes(RECIPE_BOOK_TYPE_FREEZER);
        this.registerRecipeBookCategory(RECIPE_BOOK_CATEGORY_SEARCH, () -> new ItemStack(Items.COMPASS));
        this.registerRecipeBookCategory(RECIPE_BOOK_CATEGORY_BLOCKS, () -> new ItemStack(Items.STONE));
        this.registerRecipeBookCategory(RECIPE_BOOK_CATEGORY_ITEMS, () -> new ItemStack(Items.WOODEN_SWORD));
        this.registerRecipeBookCategory(RECIPE_BOOK_CATEGORY_FOOD, () -> new ItemStack(Items.PORKCHOP));
        this.registerRecipeBookCategory(RECIPE_BOOK_CATEGORY_MISC, () -> new ItemStack(Items.LAVA_BUCKET));
    }

    /**
     * Registers new RecipeBookType constants. It is important to make sure the constant names are
     * unique; prefixing with the id of your mod should suffice.
     *
     * @param constantNames names of constants
     */
    private void registerRecipeBookTypes(String ... constantNames)
    {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        String recipeBookTypeClass = resolver.mapClassName("intermediary", "net.minecraft.class_5421");
        EnumAdder adder = ClassTinkerers.enumBuilder(recipeBookTypeClass);
        Arrays.stream(constantNames).forEach(adder::addEnum);
        adder.build();
    }

    /**
     * Registers a new RecipeBookCategories constant. It is important to make sure the constant name
     * is unique; prefixing with the id of your mod should suffice.
     *
     * @param constantName the name of the constant
     * @param icon         the icon to display in the crafting book
     */
    private void registerRecipeBookCategory(String constantName, Supplier<ItemStack> icon)
    {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        String recipeBookCategoriesClass = resolver.mapClassName("intermediary", "net.minecraft.class_314");
        String itemStackClass = "[L" + resolver.mapClassName("intermediary", "net.minecraft.class_1799") + ";";
        EnumAdder adder = ClassTinkerers.enumBuilder(recipeBookCategoriesClass, itemStackClass);
        adder.addEnum(constantName, () -> new Object[]{new ItemStack[]{icon.get()}});
        adder.build();
    }
}
