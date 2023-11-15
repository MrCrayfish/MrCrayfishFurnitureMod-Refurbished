package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

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

    private final Map<ResourceLocation, BiFunction<ResourceLocation, IComputer, Program>> programs = new LinkedHashMap<>();
    private final List<IService> services = new ArrayList<>();

    public void installProgram(ResourceLocation id, BiFunction<ResourceLocation, IComputer, Program> program)
    {
        this.programs.putIfAbsent(id, program);
    }

    public void installService(IService service)
    {
        this.services.add(service);
    }

    public Optional<Program> createProgramInstance(ResourceLocation id, IComputer computer)
    {
        return Optional.ofNullable(this.programs.get(id)).map(function -> function.apply(id, computer));
    }

    public Set<ResourceLocation> getPrograms()
    {
        return this.programs.keySet();
    }

    public List<IService> getServices()
    {
        return this.services;
    }
}
