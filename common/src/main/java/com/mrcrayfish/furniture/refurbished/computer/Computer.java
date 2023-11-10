package com.mrcrayfish.furniture.refurbished.computer;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class Computer
{
    private static Computer instance;

    public static Computer get()
    {
        if(instance == null)
        {
            instance = new Computer();
        }
        return instance;
    }

    private final Map<ResourceLocation, Function<ResourceLocation, Program>> programs = new HashMap<>();

    public void install(ResourceLocation id, Function<ResourceLocation, Program> program)
    {
        this.programs.putIfAbsent(id, program);
    }

    public Optional<Program> createProgramInstance(ResourceLocation id)
    {
        return Optional.ofNullable(this.programs.get(id)).map(function -> function.apply(id));
    }
}
