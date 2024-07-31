package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public enum LeafType implements StringRepresentable
{
    OAK("oak"),
    SPRUCE("spruce"),
    BIRCH("birch"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    CHERRY("cherry"),
    DARK_OAK("dark_oak"),
    MANGROVE("mangrove"),
    AZALEA("azalea");

    public static final Codec<LeafType> CODEC = StringRepresentable.fromEnum(LeafType::values);

    private final String name;

    LeafType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public static LeafType byName(String value)
    {
        return Stream.of(values()).filter(type -> type.name.equalsIgnoreCase(value)).findFirst().orElse(OAK);
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}
