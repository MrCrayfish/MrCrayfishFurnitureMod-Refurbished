package com.mrcrayfish.furniture.refurbished.block;

/**
 * Author: MrCrayfish
 */
public enum MetalType
{
    LIGHT("light"), DARK("dark");

    private final String name;

    MetalType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
