package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Author: MrCrayfish
 */
public record StackedIngredient(Ingredient ingredient, int count)
{
    public static final Codec<StackedIngredient> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(Ingredient.CODEC.fieldOf("ingredient").forGetter(o -> {
            return o.ingredient;
        }), Codec.INT.fieldOf("count").orElse(1).forGetter(o -> {
            return o.count;
        })).apply(builder, StackedIngredient::new);
    });

    public static final StreamCodec<RegistryFriendlyByteBuf, StackedIngredient> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        StackedIngredient::ingredient,
        ByteBufCodecs.VAR_INT,
        StackedIngredient::count,
        StackedIngredient::new);

    public static StackedIngredient of(HolderSet<Item> items, int count)
    {
        return new StackedIngredient(Ingredient.of(items), count);
    }

    public static StackedIngredient of(ItemStack stack)
    {
        return new StackedIngredient(Ingredient.of(stack.getItem()), stack.getCount());
    }

    public static StackedIngredient of(Ingredient ingredient, int count)
    {
        return new StackedIngredient(ingredient, count);
    }
}
