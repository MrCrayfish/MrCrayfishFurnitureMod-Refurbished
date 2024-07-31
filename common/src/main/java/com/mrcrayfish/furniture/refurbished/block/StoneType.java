package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public enum StoneType implements StringRepresentable
{
    STONE("stone"),
    GRANITE("granite"),
    DIORITE("diorite"),
    ANDESITE("andesite"),
    DEEPSLATE("deepslate");

    public static final Codec<StoneType> CODEC = StringRepresentable.fromEnum(StoneType::values);

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

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}
