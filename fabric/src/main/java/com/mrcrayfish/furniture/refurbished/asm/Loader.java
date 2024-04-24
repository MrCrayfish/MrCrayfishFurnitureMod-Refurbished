package com.mrcrayfish.furniture.refurbished.asm;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import com.mrcrayfish.furniture.refurbished.client.RecipeBookCategoryHolder;
import com.mrcrayfish.furniture.refurbished.client.RecipeBookTypeHolder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

/**
 * Author: MrCrayfish
 */
public class Loader implements Runnable
{
    @Override
    public void run()
    {
        this.registerRecipeBookTypes(ModRecipeBookTypes.FREEZER);
        this.registerRecipeBookTypes(ModRecipeBookTypes.MICROWAVE);
        this.registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_SEARCH);
        this.registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_BLOCKS);
        this.registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_ITEMS);
        this.registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_FOOD);
        this.registerRecipeBookCategory(ModRecipeBookCategories.FREEZER_MISC);
        this.registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_SEARCH);
        this.registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_BLOCKS);
        this.registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_ITEMS);
        this.registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_FOOD);
        this.registerRecipeBookCategory(ModRecipeBookCategories.MICROWAVE_MISC);
    }

    /**
     * Registers new RecipeBookType constants. It is important to make sure the constant names are
     * unique; prefixing with the id of your mod should suffice.
     *
     * @param constantNames names of constants
     */
    private void registerRecipeBookTypes(RecipeBookTypeHolder holder)
    {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        String recipeBookTypeClass = resolver.mapClassName("intermediary", "net.minecraft.class_5421");
        EnumAdder adder = ClassTinkerers.enumBuilder(recipeBookTypeClass);
        adder.addEnum(holder.getConstantName());
        adder.build();
    }

    /**
     * Registers a new RecipeBookCategories constant. It is important to make sure the constant name
     * is unique; prefixing with the id of your mod should suffice.
     *
     * @param constantName the name of the constant
     * @param icon         the icon to display in the crafting book
     */
    private void registerRecipeBookCategory(RecipeBookCategoryHolder holder)
    {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        String recipeBookCategoriesClass = resolver.mapClassName("intermediary", "net.minecraft.class_314");
        String itemStackClass = "[L" + resolver.mapClassName("intermediary", "net.minecraft.class_1799") + ";";
        EnumAdder adder = ClassTinkerers.enumBuilder(recipeBookCategoriesClass, itemStackClass);
        adder.addEnum(holder.getConstantName(), () -> new Object[]{holder.getIcons().get()});
        adder.build();
    }
}
