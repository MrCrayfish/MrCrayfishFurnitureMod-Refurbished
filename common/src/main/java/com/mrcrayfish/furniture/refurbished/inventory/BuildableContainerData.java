package com.mrcrayfish.furniture.refurbished.inventory;

import net.minecraft.world.inventory.ContainerData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class BuildableContainerData implements ContainerData
{
    private final Entry[] entries;

    public BuildableContainerData(Consumer<Builder> builder)
    {
        Map<Integer, Entry> entries = new LinkedHashMap<>();
        builder.accept((index, getter, setter) -> {
            entries.put(index, new Entry(getter, setter));
        });
        this.entries = new Entry[entries.size()];
        entries.forEach((index, entry) -> {
            this.entries[index] = entry;
        });
    }

    @Override
    public int get(int index)
    {
        if(index >= 0 && index < this.entries.length)
        {
            return this.entries[index].getter.get();
        }
        return 0;
    }

    @Override
    public void set(int index, int value)
    {
        if(index >= 0 && index < this.entries.length)
        {
            this.entries[index].setter.accept(value);
        }
    }

    @Override
    public int getCount()
    {
        return this.entries.length;
    }

    private record Entry(Supplier<Integer> getter, Consumer<Integer> setter) {}

    @FunctionalInterface
    public interface Builder
    {
        void add(Integer index, Supplier<Integer> getter, Consumer<Integer> setter);
    }
}
