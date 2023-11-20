package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
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

    /**
     * Registers a program that can be used on a computer
     *
     * @param id the id of the program
     * @param program a function to create the program instance
     */
    public void installProgram(ResourceLocation id, BiFunction<ResourceLocation, IComputer, Program> program)
    {
        this.programs.putIfAbsent(id, program);
    }

    /**
     * Registers a tick service for computer programs. See {@link PaddleBall#SERVICE} for an example.
     *
     * @param service the service instance
     */
    public void installService(IService service)
    {
        this.services.add(service);
    }

    /**
     * Creates an optional program instance from the given id. If no program matches the provided id,
     * an empty optional will be returned.
     *
     * @param id the id of the program
     * @param computer the computer opening the program
     * @return an optional of the program or empty if no matching program for the id
     */
    public Optional<Program> createProgramInstance(ResourceLocation id, IComputer computer)
    {
        return Optional.ofNullable(this.programs.get(id)).map(function -> function.apply(id, computer));
    }

    /**
     * @return A set of all the ids of registered programs
     */
    public Set<ResourceLocation> getPrograms()
    {
        return this.programs.keySet();
    }

    /**
     * @return A list of all installed services
     */
    public List<IService> getServices()
    {
        return this.services;
    }
}
