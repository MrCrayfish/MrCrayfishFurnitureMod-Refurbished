package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.data.tag.TagBuilder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

/**
 * Author: MrCrayfish
 */
public class PlatformTagBuilder<T> implements TagBuilder<T>
{
    private final FabricTagProvider<T>.FabricTagBuilder builder;

    public PlatformTagBuilder(FabricTagProvider<T>.FabricTagBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public PlatformTagBuilder<T> add(T t)
    {
        this.builder.add(t);
        return this;
    }

    @Override
    public void add(ResourceLocation id)
    {
        this.builder.add(id);
    }

    @Override
    public void add(TagKey<T> key)
    {
        this.builder.addTag(key);
    }

    @Override
    public void addOptional(ResourceLocation id)
    {
        this.builder.addOptional(id);
    }

    @Override
    public void addOptional(TagKey<T> key)
    {
        this.builder.addOptionalTag(key);
    }
}
