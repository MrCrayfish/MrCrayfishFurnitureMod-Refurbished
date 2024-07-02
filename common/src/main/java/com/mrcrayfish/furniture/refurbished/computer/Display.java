package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
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
    private Map<ResourceLocation, Icon> icons;

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

    @Nullable
    public Icon getIcon(ResourceLocation programId)
    {
        if(this.icons == null)
        {
            this.icons = this.computeIconMap();
        }
        return this.icons.get(programId);
    }

    private Map<ResourceLocation, Icon> computeIconMap()
    {
        Map<ResourceLocation, Icon> icons = new HashMap<>();
        Map<String, ResourceLocation> textures = new HashMap<>();
        Map<String, Integer> indexTracker = new HashMap<>();
        Computer.get().getPrograms().forEach(id -> {
            String namespace = id.getNamespace();
            textures.putIfAbsent(namespace, new ResourceLocation(namespace, "textures/gui/program_icons.png"));
            int nextIndex = indexTracker.getOrDefault(namespace, -1) + 1;
            indexTracker.put(namespace, nextIndex);
            icons.put(id, new Icon(textures.get(namespace), (nextIndex % 8) * 16, (nextIndex / 8) * 16));
        });
        return icons;
    }
}
