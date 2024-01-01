package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import net.minecraft.advancements.CriterionTriggerInstance;
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

    public abstract StackedIngredient asStackedIngredient();

    public abstract CriterionTriggerInstance createTrigger(Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag);

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
        public StackedIngredient asStackedIngredient()
        {
            return new StackedIngredient(Ingredient.of(this.t), this.count);
        }

        @Override
        public CriterionTriggerInstance createTrigger(Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag)
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
        public StackedIngredient asStackedIngredient()
        {
            return new StackedIngredient(Ingredient.of(this.t), this.count);
        }

        @Override
        public CriterionTriggerInstance createTrigger(Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag)
        {
            return hasTag.apply(this.t);
        }
    }
}
