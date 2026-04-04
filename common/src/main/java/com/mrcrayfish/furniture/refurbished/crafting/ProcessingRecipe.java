package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.util.reflection.ReflectedMethod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingRecipe implements Recipe<SingleRecipeInput>
{
    protected final RecipeType<? extends Recipe<SingleRecipeInput>> type;
    protected final Category category;
    protected final Ingredient ingredient;
    protected final ItemStackTemplate result;
    protected final int time;
    private @Nullable PlacementInfo placementInfo;

    public ProcessingRecipe(RecipeType<? extends Recipe<SingleRecipeInput>> type, Category category, Ingredient ingredient, ItemStackTemplate result, int time)
    {
        this.type = type;
        this.category = category;
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType()
    {
        return this.type;
    }

    public Category getCategory()
    {
        return this.category;
    }

    @Override
    public String group()
    {
        return "";
    }

    @Override
    public boolean showNotification()
    {
        return false;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level)
    {
        return this.ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input)
    {
        return this.result.create();
    }

    @Override
    public PlacementInfo placementInfo()
    {
        if(this.placementInfo == null)
        {
            this.placementInfo = PlacementInfo.create(this.ingredient);
        }
        return this.placementInfo;
    }

    /**
     * @return The input ingredient of this recipe
     */
    public Ingredient getIngredient()
    {
        return this.ingredient;
    }

    /**
     * Gets the output of this recipe. Careful, this returns the non-copy itemstack.
     *
     * @return The output itemstack
     */
    public ItemStackTemplate getResult()
    {
        return this.result;
    }

    /**
     * @return The time in ticks to process this recipe
     */
    public int getTime()
    {
        return this.time;
    }

    public static <T extends ProcessingRecipe> Builder<T> builder(Factory<T> factory, Category category, Ingredient input, ItemStackTemplate output, int processTime)
    {
        return new Builder<>(factory, category, input, output, processTime);
    }

    public interface Factory<T extends ProcessingRecipe>
    {
        T create(Category category, Ingredient input, ItemStackTemplate result, int processTime);
    }

    public static class Builder<T extends ProcessingRecipe> implements RecipeBuilder
    {
        protected final Category category;
        protected final Factory<T> factory;
        protected final Ingredient input;
        protected final ItemStackTemplate output;
        protected final int processTime;
        protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

        private Builder(Factory<T> factory, Category category, Ingredient input, ItemStackTemplate output, int processTime)
        {
            this.factory = factory;
            this.category = category;
            this.input = input;
            this.output = output;
            this.processTime = processTime;
        }

        @Override
        public RecipeBuilder unlockedBy(String s, Criterion<?> instance)
        {
            this.criteria.put(s, instance);
            return this;
        }

        @Override
        public RecipeBuilder group(@Nullable String group)
        {
            throw new UnsupportedOperationException("Group not supported for ProcessingRecipes");
        }

        @Override
        public ResourceKey<Recipe<?>> defaultId()
        {
            return RecipeBuilder.getDefaultRecipeId(this.output);
        }

        public ItemStackTemplate getOutput()
        {
            return this.output;
        }

        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> id)
        {
            Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(builder::addCriterion);
            output.accept(id, this.factory.create(this.category, this.input, this.output, this.processTime), builder.build(id.identifier().withPrefix("recipes/" + this.category.getSerializedName() + "/")));
        }
    }

    public static abstract class Item extends ProcessingRecipe
    {
        private static final ReflectedMethod<SingleItemRecipe, ItemStackTemplate> RESULT_METHOD = new ReflectedMethod<>(SingleItemRecipe.class, "result");

        public Item(RecipeType<? extends Recipe<SingleRecipeInput>> type, Category category, Ingredient ingredient, ItemStackTemplate result, int time)
        {
            super(type, category, ingredient, result, time);
        }

        public static ProcessingRecipe.Item fromCookingRecipe(AbstractCookingRecipe recipe)
        {
            return new ProcessingRecipe.Item(recipe.getType(), Category.FOOD, recipe.input(), RESULT_METHOD.invoke(recipe), recipe.cookingTime())
            {
                @Override
                public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer()
                {
                    return recipe.getSerializer();
                }

                @Override
                public RecipeBookCategory recipeBookCategory()
                {
                    return recipe.recipeBookCategory();
                }

                @Override
                public PlacementInfo placementInfo()
                {
                    return recipe.placementInfo();
                }
            };
        }

        public static <T extends ProcessingRecipe> MapCodec<T> createCodec(Factory<T> factory, int defaultTime)
        {
            return RecordCodecBuilder.mapCodec(builder -> {
                return builder.group(Category.CODEC.fieldOf("category").forGetter(recipe -> {
                    return recipe.category;
                }), Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> {
                    return recipe.ingredient;
                }), ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> {
                    return recipe.result;
                }),  Codec.INT.fieldOf("time").orElse(defaultTime).forGetter(recipe -> {
                    return recipe.time;
                })).apply(builder, factory::create);
            });
        }

        public static <T extends ProcessingRecipe> StreamCodec<RegistryFriendlyByteBuf, T> createStreamCodec(Factory<T> factory, int defaultTime)
        {
            return StreamCodec.of((buf, recipe) -> {
                recipe.category.toNetwork(buf);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.result);
                buf.writeVarInt(recipe.time);
            }, buf -> {
                Category category = Category.fromNetwork(buf);
                Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                ItemStackTemplate output = ItemStackTemplate.STREAM_CODEC.decode(buf);
                int processTime = buf.readVarInt();
                return factory.create(category, input, output, processTime);
            });
        }
    }

    public enum Category implements StringRepresentable
    {
        BLOCKS("blocks"),
        ITEMS("items"),
        FOOD("food"),
        MISC("misc");

        public static final StringRepresentable.EnumCodec<Category> CODEC = StringRepresentable.fromEnum(Category::values);

        private final String name;

        Category(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }

        public void toNetwork(FriendlyByteBuf buf)
        {
            buf.writeUtf(this.name, 6);
        }

        public static Category fromNetwork(FriendlyByteBuf buf)
        {
            return byName(buf.readUtf(6));
        }

        public static Category byName(String name)
        {
            return CODEC.byName(name, Category.MISC);
        }
    }
}
