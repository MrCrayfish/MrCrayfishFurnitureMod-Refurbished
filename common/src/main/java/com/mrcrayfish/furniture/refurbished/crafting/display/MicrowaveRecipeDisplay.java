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
public record MicrowaveRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay craftingStation, int time) implements RecipeDisplay
{
    public static final MapCodec<MicrowaveRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(MicrowaveRecipeDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("result").forGetter(MicrowaveRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(MicrowaveRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("time").forGetter(MicrowaveRecipeDisplay::time)
        ).apply(builder, MicrowaveRecipeDisplay::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, MicrowaveRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC, MicrowaveRecipeDisplay::ingredient,
        SlotDisplay.STREAM_CODEC, MicrowaveRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, MicrowaveRecipeDisplay::craftingStation,
        ByteBufCodecs.VAR_INT, MicrowaveRecipeDisplay::time,
        MicrowaveRecipeDisplay::new
    );
    public static final Type<MicrowaveRecipeDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

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
    public Type<MicrowaveRecipeDisplay> type()
    {
        return TYPE;
    }
}
