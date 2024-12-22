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
public record OvenRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay craftingStation, int time) implements RecipeDisplay
{
    public static final MapCodec<OvenRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(OvenRecipeDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("result").forGetter(OvenRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(OvenRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("time").forGetter(OvenRecipeDisplay::time)
        ).apply(builder, OvenRecipeDisplay::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::ingredient,
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::craftingStation,
        ByteBufCodecs.VAR_INT, OvenRecipeDisplay::time,
        OvenRecipeDisplay::new
    );
    public static final Type<OvenRecipeDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

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
    public Type<OvenRecipeDisplay> type()
    {
        return TYPE;
    }
}
