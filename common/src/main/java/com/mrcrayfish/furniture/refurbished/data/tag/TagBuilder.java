package com.mrcrayfish.furniture.refurbished.data.tag;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

/**
 * Author: MrCrayfish
 */
public interface TagBuilder<T>
{
    TagBuilder<T> add(T t);

    void add(Identifier id);

    void add(TagKey<T> key);

    void addOptional(Identifier id);

    void addOptional(TagKey<T> key);
}
