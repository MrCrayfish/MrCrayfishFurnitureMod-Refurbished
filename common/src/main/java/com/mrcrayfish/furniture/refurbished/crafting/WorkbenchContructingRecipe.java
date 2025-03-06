package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.data.Material;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class WorkbenchContructingRecipe implements Recipe<SingleRecipeInput>
{
    // TODO allow recipe to change sound (drill for wood, saw for stone, weld for electronics)
    private final NonNullList<StackedIngredient> materials;
    private final ItemStack result;
    private final boolean notification;
    private @Nullable PlacementInfo placementInfo;

    public WorkbenchContructingRecipe(NonNullList<StackedIngredient> materials, ItemStack result, boolean notification)
    {
        this.materials = materials;
        this.result = result;
        this.notification = notification;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level)
    {
        return true;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider provider)
    {
        return this.result.copy();
    }

    @Override
    public boolean showNotification()
    {
        return this.notification;
    }

    @Override
    public RecipeSerializer<WorkbenchContructingRecipe> getSerializer()
    {
        return ModRecipeSerializers.WORKBENCH_RECIPE.get();
    }

    @Override
    public RecipeType<WorkbenchContructingRecipe> getType()
    {
        return ModRecipeTypes.WORKBENCH_CONSTRUCTING.get();
    }

    @Override
    public PlacementInfo placementInfo()
    {
        if(this.placementInfo == null)
        {
            this.placementInfo = PlacementInfo.createFromOptionals(this.materials.stream().map(i -> Optional.of(i.ingredient())).toList());
        }
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public NonNullList<StackedIngredient> getMaterials()
    {
        return this.materials;
    }

    public int getResultId()
    {
        return Item.getId(this.result.getItem());
    }

    public ItemStack getResult()
    {
        return this.result;
    }

    public static Builder builder(HolderLookup.RegistryLookup<Item> items, ItemLike result, int count, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
    {
        return new Builder(items, result.asItem(), count, hasItem, hasTag);
    }

    public static class Serializer implements RecipeSerializer<WorkbenchContructingRecipe>
    {
        public static final MapCodec<WorkbenchContructingRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> {
            return builder.group(StackedIngredient.CODEC.listOf().fieldOf("materials").flatXmap(materials -> {
                NonNullList<StackedIngredient> inputs = NonNullList.create();
                inputs.addAll(materials);
                return DataResult.success(inputs);
            }, DataResult::success).forGetter(o -> {
                return o.materials;
            }), ItemStack.CODEC.fieldOf("result").forGetter(recipe -> {
                return recipe.result;
            }), Codec.BOOL.optionalFieldOf("show_notification", false).forGetter(recipe -> {
                return recipe.notification;
            })).apply(builder, WorkbenchContructingRecipe::new);
        });

        public static final StreamCodec<RegistryFriendlyByteBuf, WorkbenchContructingRecipe> STREAM_CODEC = StreamCodec.composite(
            StackedIngredient.STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)),
            WorkbenchContructingRecipe::getMaterials,
            ItemStack.STREAM_CODEC,
            WorkbenchContructingRecipe::getResult,
            ByteBufCodecs.BOOL,
            WorkbenchContructingRecipe::showNotification,
            WorkbenchContructingRecipe::new
        );

        @Override
        public MapCodec<WorkbenchContructingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WorkbenchContructingRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final HolderLookup.RegistryLookup<Item> items;
        private final Item result;
        private final int count;
        private final Function<ItemLike, Criterion<?>> hasItem;
        private final Function<TagKey<Item>, Criterion<?>> hasTag;
        private final NonNullList<StackedIngredient> materials = NonNullList.create();
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        private RecipeCategory category = RecipeCategory.MISC;
        private boolean showNotification;

        private Builder(HolderLookup.RegistryLookup<Item> items, Item result, int count, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
        {
            this.items = items;
            this.result = result;
            this.count = count;
            this.hasItem = hasItem;
            this.hasTag = hasTag;
        }

        public Builder requiresMaterial(Material<?> material)
        {
            this.materials.add(material.asStackedIngredient(this.items));
            return this.unlockedBy("has_" + material.getName(), material.createTrigger(this.hasItem, this.hasTag));
        }

        @Override
        public Builder unlockedBy(String name, Criterion<?> trigger)
        {
            this.criteria.put(name, trigger);
            return this;
        }

        @Override
        public Builder group(@Nullable String group)
        {
            return this;
        }

        public Builder category(RecipeCategory category)
        {
            this.category = category;
            return this;
        }

        public Builder showNotification(boolean show)
        {
            this.showNotification = show;
            return this;
        }

        @Override
        public Item getResult()
        {
            return this.result;
        }

        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> id)
        {
            this.validate(id);
            Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(builder::addCriterion);
            output.accept(id, new WorkbenchContructingRecipe(this.materials, new ItemStack(this.result), this.showNotification), builder.build(id.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }

        private void validate(ResourceKey<Recipe<?>> id)
        {
            if(this.materials.isEmpty())
            {
                throw new IllegalArgumentException("There must be at least one material for workbench crafting recipe %s".formatted(id.location()));
            }
            if(this.criteria.isEmpty())
            {
                throw new IllegalStateException("No way of obtaining recipe " + id.location());
            }
        }
    }
}
