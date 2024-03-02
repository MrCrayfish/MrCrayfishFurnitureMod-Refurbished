package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Author: MrCrayfish
 */
public record StackedIngredient(Ingredient ingredient, int count)
{
    public static final StackedIngredient EMPTY = new StackedIngredient(Ingredient.EMPTY, 0);
    public static final Codec<StackedIngredient> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(o -> {
            return o.ingredient;
        }), Codec.INT.fieldOf("count").orElse(1).forGetter(o -> {
            return o.count;
        })).apply(builder, StackedIngredient::new);
    });

    public static StackedIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        int count = buffer.readInt();
        return new StackedIngredient(ingredient, count);
    }

    public void toNetwork(FriendlyByteBuf buffer)
    {
        this.ingredient.toNetwork(buffer);
        buffer.writeInt(this.count);
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
}
