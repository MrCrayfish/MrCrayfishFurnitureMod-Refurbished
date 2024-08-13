package com.mrcrayfish.furniture.refurbished.block;

/**
 * Author: MrCrayfish
 */
public enum LeafType
{
    OAK("oak"),
    SPRUCE("spruce"),
    BIRCH("birch"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    MANGROVE("mangrove"),
    AZALEA("azalea");

    private final String name;

    LeafType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
