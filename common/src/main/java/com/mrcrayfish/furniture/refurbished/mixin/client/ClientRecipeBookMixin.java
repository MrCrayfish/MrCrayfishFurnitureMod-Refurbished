package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin
{
    @Inject(method = "getCategory", at = @At(value = "HEAD"), cancellable = true)
    private static void refurbishedFurniture$GetCategoryHead(RecipeHolder<?> holder, CallbackInfoReturnable<RecipeBookCategories> cir)
    {
        RecipeType<?> type = holder.value().getType();
        if(ModRecipeTypes.IGNORED_RECIPE_TYPES.get().contains(type))
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
