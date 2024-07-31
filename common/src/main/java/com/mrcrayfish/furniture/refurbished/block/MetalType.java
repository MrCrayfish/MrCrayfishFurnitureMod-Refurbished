package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public enum MetalType implements StringRepresentable
{
    LIGHT("light"),
    DARK("dark");

    public static final Codec<MetalType> CODEC = StringRepresentable.fromEnum(MetalType::values);

    private final String name;

    MetalType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public static MetalType byName(String value)
    {
        return Stream.of(values()).filter(type -> type.name.equalsIgnoreCase(value)).findFirst().orElse(LIGHT);
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}
