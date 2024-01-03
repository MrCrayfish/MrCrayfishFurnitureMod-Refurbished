package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Author: MrCrayfish
 */
public record StackedIngredient(Ingredient ingredient, int count)
{
    public static final StackedIngredient EMPTY = new StackedIngredient(Ingredient.EMPTY, 0);

    public static StackedIngredient fromJson(JsonElement element)
    {
        Preconditions.checkArgument(element.isJsonObject(), "Element must be a json object");
        Ingredient ingredient = Ingredient.fromJson(element);
        int count = GsonHelper.getAsInt((JsonObject) element, "count", 1);
        return new StackedIngredient(ingredient, count);
    }

    public static StackedIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        int count = buffer.readInt();
        return new StackedIngredient(ingredient, count);
    }

    public static StackedIngredient of(TagKey<Item> tag, int count)
    {
        return new StackedIngredient(Ingredient.of(tag), count);
    }

    public static StackedIngredient of(ItemStack stack)
    {
        return new StackedIngredient(Ingredient.of(stack), stack.getCount());
    }

    public static StackedIngredient of(Ingredient ingredient, int count)
    {
        return new StackedIngredient(ingredient, count);
    }

    public void toNetwork(FriendlyByteBuf buffer)
    {
        this.ingredient.toNetwork(buffer);
        buffer.writeInt(this.count);
    }

    public JsonElement toJson()
    {
        JsonElement element = this.ingredient.toJson();
        if(element instanceof JsonObject object)
        {
            object.addProperty("count", this.count);
        }
        return element;
    }
}
