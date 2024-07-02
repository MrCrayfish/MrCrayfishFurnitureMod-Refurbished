package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.data.Material;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class WorkbenchContructingRecipe implements Recipe<Container>
{
    // TODO allow recipe to change sound (drill for wood, saw for stone, weld for electronics)
    private final NonNullList<StackedIngredient> materials;
    private final ItemStack result;
    private final boolean notification;

    public WorkbenchContructingRecipe(NonNullList<StackedIngredient> materials, ItemStack result, boolean notification)
    {
        this.materials = materials;
        this.result = result;
        this.notification = notification;
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.WORKBENCH_CONSTRUCTING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.WORKBENCH_RECIPE.get();
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access)
    {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.result;
    }

    @Override
    public boolean showNotification()
    {
        return this.notification;
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

    public static Builder builder(ItemLike result, int count, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
    {
        return new Builder(result.asItem(), count, hasItem, hasTag);
    }

    public static class Serializer implements RecipeSerializer<WorkbenchContructingRecipe>
    {
        public static final Codec<WorkbenchContructingRecipe> CODEC = RecordCodecBuilder.create(builder -> {
            return builder.group(StackedIngredient.CODEC.listOf().fieldOf("materials").flatXmap(materials -> {
                StackedIngredient[] inputs = materials.stream().filter((ingredient) -> {
                    return !ingredient.ingredient().isEmpty() || ingredient.count() <= 0;
                }).toArray(StackedIngredient[]::new);
                return DataResult.success(NonNullList.of(StackedIngredient.EMPTY, inputs));
            }, DataResult::success).forGetter(o -> {
                return o.materials;
            }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> {
                return recipe.result;
            }), Codec.BOOL.optionalFieldOf("show_notification", false).forGetter(recipe -> {
                return recipe.notification;
            })).apply(builder, WorkbenchContructingRecipe::new);
        });

        @Override
        public Codec<WorkbenchContructingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public WorkbenchContructingRecipe fromNetwork(FriendlyByteBuf buffer)
        {
            int materialCount = buffer.readInt();
            NonNullList<StackedIngredient> materials = NonNullList.withSize(materialCount, StackedIngredient.EMPTY);
            IntStream.range(0, materialCount).forEach(i -> materials.set(i, StackedIngredient.fromNetwork(buffer)));
            ItemStack result = buffer.readItem();
            boolean notification = buffer.readBoolean();
            return new WorkbenchContructingRecipe(materials, result, notification);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WorkbenchContructingRecipe recipe)
        {
            buffer.writeInt(recipe.materials.size());
            recipe.materials.forEach(ingredient -> ingredient.toNetwork(buffer));
            buffer.writeItem(recipe.result);
            buffer.writeBoolean(recipe.notification);
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final Item result;
        private final int count;
        private final Function<ItemLike, Criterion<?>> hasItem;
        private final Function<TagKey<Item>, Criterion<?>> hasTag;
        private final NonNullList<StackedIngredient> materials = NonNullList.create();
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        private RecipeCategory category = RecipeCategory.MISC;
        private boolean showNotification;

        private Builder(Item result, int count, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag)
        {
            this.result = result;
            this.count = count;
            this.hasItem = hasItem;
            this.hasTag = hasTag;
        }

        public Builder requiresMaterial(Material<?> material)
        {
            this.materials.add(material.asStackedIngredient());
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
        public void save(RecipeOutput output, ResourceLocation id)
        {
            this.validate(id);
            Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(builder::addCriterion);
            output.accept(id, new WorkbenchContructingRecipe(this.materials, new ItemStack(this.result), this.showNotification), builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }

        private void validate(ResourceLocation id)
        {
            if(this.materials.isEmpty())
            {
                throw new IllegalArgumentException("There must be at least one material for workbench crafting recipe %s".formatted(id));
            }
            if(this.criteria.isEmpty())
            {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }
}
