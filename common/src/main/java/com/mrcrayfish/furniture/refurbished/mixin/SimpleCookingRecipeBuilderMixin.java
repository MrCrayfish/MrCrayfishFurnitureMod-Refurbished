package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(SimpleCookingRecipeBuilder.class)
public class SimpleCookingRecipeBuilderMixin
{
    @Inject(method = "determineRecipeCategory", at = @At(value = "HEAD"), cancellable = true)
    private static void refurbishedFurnitureFixCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemLike item, CallbackInfoReturnable<CookingBookCategory> cir)
    {
        if(serializer == ModRecipeSerializers.GRILL_RECIPE.get())
        {
            cir.setReturnValue(CookingBookCategory.FOOD);
        }
    }
}
