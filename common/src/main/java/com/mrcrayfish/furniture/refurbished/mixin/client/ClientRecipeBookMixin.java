package com.mrcrayfish.furniture.refurbished.mixin.client;

import net.minecraft.client.ClientRecipeBook;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Author: MrCrayfish
 */
@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin
{
    /*@Inject(method = "getCategory", at = @At(value = "HEAD"), cancellable = true)
    private static void refurbished_furniture$GetCategoryHead(RecipeHolder<?> holder, CallbackInfoReturnable<RecipeBookCategories> cir)
    {
        RecipeType<?> type = holder.value().getType();
        if(ModRecipeTypes.IGNORED_RECIPE_TYPES.get().contains(type))
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }*/
}
