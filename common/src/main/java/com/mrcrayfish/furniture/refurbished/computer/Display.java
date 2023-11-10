package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class Display
{
    private static Display instance;

    public static Display get()
    {
        if(instance == null)
        {
            instance = new Display();
        }
        return instance;
    }

    private final Map<Class<? extends Program>, Function<Program, DisplayableProgram<? extends Program>>> programs = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Program, D extends DisplayableProgram<T>> void bind(Class<T> programClass, Function<T, D> displayableProvider)
    {
        this.programs.putIfAbsent(programClass, (Function<Program, DisplayableProgram<? extends Program>>) displayableProvider);
    }

    @SuppressWarnings("unchecked")
    public <T extends Program> DisplayableProgram<T> getDisplay(T program)
    {
        return (DisplayableProgram<T>) this.programs.get(program.getClass()).apply(program);
    }
}
