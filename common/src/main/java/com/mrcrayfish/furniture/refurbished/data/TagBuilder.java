package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

/**
 * Author: MrCrayfish
 */
public interface TagBuilder<T>
{
    void add(T t);

    void add(ResourceLocation id);

    void add(TagKey<T> key);

    void addOptional(ResourceLocation id);

    void addOptional(TagKey<T> key);
}
