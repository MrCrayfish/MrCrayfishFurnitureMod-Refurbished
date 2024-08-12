package com.mrcrayfish.furniture.refurbished.data.model;

import com.google.common.base.Preconditions;
import net.minecraft.core.Registry;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Author: MrCrayfish
 */
public class PreparedVariantBlockState
{
    private final Block block;
    private final List<Entry> entries = new ArrayList<>();

    public PreparedVariantBlockState(Block block)
    {
        this.block = block;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public List<Entry> getVariants()
    {
        // Verify that all block states have been covered
        this.block.getStateDefinition().getPossibleStates().forEach(state -> {
            if(this.entries.stream().noneMatch(entry -> entry.is(state))) {
                ResourceLocation key = Registry.BLOCK.getKey(this.block);
                throw new IllegalStateException("Missing variant for " + key + state.toString());
            }
        });
        return List.copyOf(this.entries);
    }

    public Entry getVariantForItem()
    {
        return this.entries.stream().filter(entry -> entry.useForItem).findFirst().orElse(null);
    }

    public Entry createVariant()
    {
        Entry states = Entry.of(this.block);
        this.entries.add(states);
        return states;
    }

    @SuppressWarnings("rawtypes")
    public static class Entry
    {
        private final Block block;
        private final Map<Property, Comparable> map = new TreeMap<>(Comparator.comparing(Property::getName));
        private Model[] models = new Model[0];
        private boolean useForItem;

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

        public Entry addExistingModel(Model builder)
        {
            this.models = ArrayUtils.add(this.models, builder);
            return this;
        }

        public Entry addTexturedModel(Model builder)
        {
            builder.markAsChild();
            this.models = ArrayUtils.add(this.models, builder);
            return this;
        }

        public void markAsItem()
        {
            this.useForItem = true;
        }

        public Model[] getModels()
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

    public static class Model extends ParentModel<Model>
    {
        private VariantProperties.Rotation xRotation = VariantProperties.Rotation.R0;
        private VariantProperties.Rotation yRotation = VariantProperties.Rotation.R0;

        private Model(String name, ResourceLocation model, TextureSlot[] slots)
        {
            super(name, model, slots);
        }

        @Override
        public Model self()
        {
            return this;
        }

        public VariantProperties.Rotation getXRotation()
        {
            return this.xRotation;
        }

        public Model setXRotation(VariantProperties.Rotation rotation)
        {
            this.xRotation = rotation;
            return this;
        }

        public VariantProperties.Rotation getYRotation()
        {
            return this.yRotation;
        }

        public Model setYRotation(VariantProperties.Rotation rotation)
        {
            this.yRotation = rotation;
            return this;
        }

        public static Model create(String name, ResourceLocation model, TextureSlot[] slots)
        {
            return new Model(name, model, slots);
        }
    }
}
