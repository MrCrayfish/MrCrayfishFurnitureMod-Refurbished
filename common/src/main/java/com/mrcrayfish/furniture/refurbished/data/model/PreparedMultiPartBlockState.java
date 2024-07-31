package com.mrcrayfish.furniture.refurbished.data.model;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.ArrayUtils;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Author: MrCrayfish
 */
public class PreparedMultiPartBlockState
{
    private final Block block;
    private final List<Entry> entries = new ArrayList<>();
    private PreparedVariantBlockState.Model itemModel;

    public PreparedMultiPartBlockState(Block block)
    {
        this.block = block;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public Entry createPart()
    {
        Entry entry = Entry.of(this.block);
        this.entries.add(entry);
        return entry;
    }

    public List<Entry> getParts()
    {
        return this.entries;
    }

    public void setItemModel(PreparedVariantBlockState.Model itemModel)
    {
        this.itemModel = itemModel;
    }

    @Nullable
    public PreparedVariantBlockState.Model getModelForItem()
    {
        return this.itemModel;
    }

    @SuppressWarnings("rawtypes")
    public static class Entry
    {
        private final Block block;
        private final Map<Property, Comparable> map = new TreeMap<>(Comparator.comparing(Property::getName));
        private PreparedVariantBlockState.Model[] models = new PreparedVariantBlockState.Model[0];
        private boolean orMode;

        private Entry(Block block)
        {
            this.block = block;
        }

        public <T extends Comparable<T>> Entry prop(Property<T> key, T value)
        {
            Preconditions.checkArgument(this.block.getStateDefinition().getProperties().contains(key));
            this.map.put(key, value);
            return this;
        }

        public Entry addExistingModel(PreparedVariantBlockState.Model builder)
        {
            this.models = ArrayUtils.add(this.models, builder);
            return this;
        }

        public Entry addTexturedModel(PreparedVariantBlockState.Model builder)
        {
            builder.markAsChild();
            this.models = ArrayUtils.add(this.models, builder);
            return this;
        }

        public Entry orMode()
        {
            this.orMode = true;
            return this;
        }

        public boolean isOrMode()
        {
            return this.orMode;
        }

        public PreparedVariantBlockState.Model[] getModels()
        {
            return this.models;
        }

        public Map<Property, Comparable> getValueMap()
        {
            return this.map;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(this.block, this.map);
        }

        @Override
        public boolean equals(Object o)
        {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Entry that = (Entry) o;
            return this.block.equals(that.block);
        }

        public static Entry of(Block block)
        {
            return new Entry(block);
        }

        @SuppressWarnings("unchecked")
        public boolean is(BlockState state)
        {
            return this.map.entrySet().stream().allMatch(entry -> Objects.equals(state.getValue(entry.getKey()), entry.getValue()));
        }
    }
}
