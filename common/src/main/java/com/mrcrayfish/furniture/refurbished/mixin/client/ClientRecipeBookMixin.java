package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
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
    @Inject(method = "getCategory", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), cancellable = true)
    private static void refurbishedFurnitureCategoryFix(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir)
    {
        RecipeType<?> type = recipe.getType();
        if(type == ModRecipeTypes.RECYCLE_BIN_RECYCLING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.CUTTING_BOARD_SLICING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.FREEZER_SOLIDIFYING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.FRYING_PAN_COOKING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.GRILL_COOKING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.MICROWAVE_HEATING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
        else if(type == ModRecipeTypes.TOASTER_HEATING.get())
        {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
