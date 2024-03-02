package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public enum StoneType
{
    STONE("stone"),
    GRANITE("granite"),
    DIORITE("diorite"),
    ANDESITE("andesite"),
    DEEPSLATE("deepslate");

    public static final Codec<StoneType> CODEC = ExtraCodecs.stringResolverCodec(StoneType::getName, StoneType::byName);

    private final String name;

    StoneType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public static StoneType byName(String value)
    {
        return Stream.of(values()).filter(type -> type.name.equalsIgnoreCase(value)).findFirst().orElse(STONE);
    }
}
