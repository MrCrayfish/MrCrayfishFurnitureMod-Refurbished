package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
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

    private final Map<ResourceLocation, BiFunction<ResourceLocation, IComputer, Program>> programs = new HashMap<>();

    public void install(ResourceLocation id, BiFunction<ResourceLocation, IComputer, Program> program)
    {
        this.programs.putIfAbsent(id, program);
    }

    public Optional<Program> createProgramInstance(ResourceLocation id, IComputer computer)
    {
        return Optional.ofNullable(this.programs.get(id)).map(function -> function.apply(id, computer));
    }
}
