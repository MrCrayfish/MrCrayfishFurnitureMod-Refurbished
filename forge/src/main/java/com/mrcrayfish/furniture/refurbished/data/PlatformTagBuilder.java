package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.data.tag.TagBuilder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;

/**
 * Author: MrCrayfish
 */
public class PlatformTagBuilder<T> implements TagBuilder<T>
{
    private final TagsProvider.TagAppender<T> appender;

    public PlatformTagBuilder(TagsProvider.TagAppender<T> appender)
    {
        this.appender = appender;
    }

    @Override
    public PlatformTagBuilder<T> add(T t)
    {
        this.appender.add(t);
        return this;
    }

    @Override
    public void add(ResourceLocation id)
    {
        this.appender.add(TagEntry.element(id));
    }

    @Override
    public void add(TagKey<T> key)
    {
        this.appender.addTag(key);
    }

    @Override
    public void addOptional(ResourceLocation id)
    {
        this.appender.addOptional(id);
    }

    @Override
    public void addOptional(TagKey<T> key)
    {
        this.appender.addOptionalTag(key.location());
    }
}
