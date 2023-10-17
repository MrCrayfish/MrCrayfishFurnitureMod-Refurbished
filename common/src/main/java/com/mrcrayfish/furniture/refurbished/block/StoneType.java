package com.mrcrayfish.furniture.refurbished.block;

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

    private final String name;

    StoneType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
