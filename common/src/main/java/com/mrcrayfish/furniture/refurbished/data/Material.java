package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public abstract sealed class Material<T>
{
    protected final String name;
    protected final T t;
    protected final int count;

    public Material(String name, T t, int count)
    {
        this.name = name;
        this.t = t;
        this.count = count;
    }

    public abstract StackedIngredient asStackedIngredient(HolderLookup.RegistryLookup<Item> items);

    public abstract Criterion<?> createTrigger(Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag);

    public String getName()
    {
        return this.name;
    }

    public T getValue()
    {
        return this.t;
    }

    public int getCount()
    {
        return this.count;
    }

    public static Material<?> of(ItemLike item, int count)
    {
        return new ItemValue(item.asItem(), count);
    }

    public static Material<?> of(String name, ItemLike item, int count)
    {
        return new ItemValue(name, item.asItem(), count);
    }

    public static Material<?> of(String name, TagKey<Item> tag, int count)
    {
        return new TagValue(name, tag, count);
    }

    private static final class ItemValue extends Material<Item>
    {
        public ItemValue(Item item, int count)
        {
            super(item.toString(), item, count);
        }

        public ItemValue(String name, Item item, int count)
        {
            super(name, item, count);
        }

        @Override
        public StackedIngredient asStackedIngredient(HolderLookup.RegistryLookup<Item> items)
        {
            return new StackedIngredient(Ingredient.of(this.t), this.count);
        }

        @Override
        public Criterion<?> createTrigger(Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
        {
            return hasItem.apply(this.t);
        }
    }

    private static final class TagValue extends Material<TagKey<Item>>
    {
        public TagValue(String name, TagKey<Item> itemTagKey, int count)
        {
            super(name, itemTagKey, count);
        }

        @Override
        public StackedIngredient asStackedIngredient(HolderLookup.RegistryLookup<Item> items)
        {
            HolderSet<Item> tagItems = items.get(this.t).orElseThrow(() -> new IllegalStateException("Missing tag " + this.t));
            return new StackedIngredient(Ingredient.of(tagItems), this.count);
        }

        @Override
        public Criterion<?> createTrigger(Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
        {
            return hasTag.apply(this.t);
        }
    }
}
