package com.mrcrayfish.furniture.refurbished.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

/**
 * Author: MrCrayfish
 */
public record FreezerRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay craftingStation, int time) implements RecipeDisplay
{
    public static final MapCodec<FreezerRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(FreezerRecipeDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("result").forGetter(FreezerRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(FreezerRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("time").forGetter(FreezerRecipeDisplay::time)
        ).apply(builder, FreezerRecipeDisplay::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, FreezerRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC, FreezerRecipeDisplay::ingredient,
        SlotDisplay.STREAM_CODEC, FreezerRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, FreezerRecipeDisplay::craftingStation,
        ByteBufCodecs.VAR_INT, FreezerRecipeDisplay::time,
        FreezerRecipeDisplay::new
    );
    public static final Type<FreezerRecipeDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public SlotDisplay result()
    {
        return this.result;
    }

    @Override
    public SlotDisplay craftingStation()
    {
        return this.craftingStation;
    }

    @Override
    public Type<FreezerRecipeDisplay> type()
    {
        return TYPE;
    }
}
