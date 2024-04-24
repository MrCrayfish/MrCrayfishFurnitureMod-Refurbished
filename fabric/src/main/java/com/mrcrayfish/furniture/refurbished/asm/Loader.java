package com.mrcrayfish.furniture.refurbished.asm;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import com.mrcrayfish.furniture.refurbished.client.RecipeBookTypeHolder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import net.fabricmc.api.EnvType;
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
        ModRecipeBookTypes.getAllTypes().forEach(this::registerRecipeBookType);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            new ClientLoader().run();
        }
    }

    /**
     * Registers new RecipeBookType constants. It is important to make sure the constant names are
     * unique; prefixing with the id of your mod should suffice.
     *
     * @param constantNames names of constants
     */
    private void registerRecipeBookType(RecipeBookTypeHolder holder)
    {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        String recipeBookTypeClass = resolver.mapClassName("intermediary", "net.minecraft.class_5421");
        EnumAdder adder = ClassTinkerers.enumBuilder(recipeBookTypeClass);
        adder.addEnum(holder.getConstantName());
        adder.build();
    }
}
