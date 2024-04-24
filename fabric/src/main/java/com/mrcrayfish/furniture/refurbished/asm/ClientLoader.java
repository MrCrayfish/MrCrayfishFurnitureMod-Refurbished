package com.mrcrayfish.furniture.refurbished.asm;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import com.mrcrayfish.furniture.refurbished.client.RecipeBookCategoryHolder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

/**
 * Author: MrCrayfish
 */
public class ClientLoader implements Runnable
{
    @Override
    public void run()
    {
        ModRecipeBookCategories.getAllCategories().forEach(this::registerRecipeBookCategory);
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
