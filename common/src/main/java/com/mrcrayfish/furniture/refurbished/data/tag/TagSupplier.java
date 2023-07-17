package com.mrcrayfish.furniture.refurbished.data.tag;

import net.minecraft.tags.TagKey;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface TagSupplier<T>
{
    List<TagKey<T>> getTags();
}
