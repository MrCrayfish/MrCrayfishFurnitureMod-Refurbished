package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingRecipe implements Recipe<Container>
{
    protected final RecipeType<?> type;
    protected final Category category;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int time;

    public ProcessingRecipe(RecipeType<?> type, Category category, Ingredient ingredient, ItemStack result, int time)
    {
        this.type = type;
        this.category = category;
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
    }

    @Override
    public RecipeType<?> getType()
    {
        return this.type;
    }

    public Category getCategory()
    {
        return this.category;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.result;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.ingredient.test(container.getItem(0));
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
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.ingredient);
        return ingredients;
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
    public ItemStack getResult()
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

    public static <T extends ProcessingRecipe> Builder<T> builder(Factory<T> factory, Category category, Ingredient input, ItemStack output, int processTime)
    {
        return new Builder<>(factory, category, input, output, processTime);
    }

    public interface Factory<T extends ProcessingRecipe>
    {
        T create(Category category, Ingredient input, ItemStack result, int processTime);
    }

    public static class Builder<T extends ProcessingRecipe> implements RecipeBuilder
    {
        protected final Category category;
        protected final Factory<T> factory;
        protected final Ingredient input;
        protected final ItemStack output;
        protected final int processTime;
        protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

        private Builder(Factory<T> factory, Category category, Ingredient input, ItemStack output, int processTime)
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
        public net.minecraft.world.item.Item getResult()
        {
            return this.output.getItem();
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id)
        {
            Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(builder::addCriterion);
            output.accept(id, this.factory.create(this.category, this.input, this.output, this.processTime), builder.build(id.withPrefix("recipes/" + this.category.getSerializedName() + "/")));
        }
    }

    public static abstract class Item extends ProcessingRecipe
    {
        public static final Codec<ItemStack> SINGLE_ITEMSTACK = RecordCodecBuilder.create(builder -> {
            return builder.group(
                BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStack::getItemHolder),
                CompoundTag.CODEC.optionalFieldOf("tag").forGetter(stack -> {
                    return Optional.ofNullable(stack.getTag());
                })).apply(builder, (holder, tag) -> new ItemStack(holder, 1, tag));
        });
        public static final Codec<ItemStack> RESULT = ExtraCodecs.xor(SINGLE_ITEMSTACK, ItemStack.SINGLE_ITEM_CODEC).xmap(either -> {
            return either.map(Function.identity(), Function.identity());
        }, stack -> stack.getTag() == null ? Either.right(stack) : Either.left(stack));

        public Item(RecipeType<?> type, Category category, Ingredient ingredient, ItemStack result, int time)
        {
            super(type, category, ingredient, result, time);
        }

        public static ProcessingRecipe from(AbstractCookingRecipe recipe, RegistryAccess access)
        {
            return new ProcessingRecipe(recipe.getType(), Category.FOOD, recipe.getIngredients().get(0), recipe.getResultItem(access), recipe.getCookingTime())
            {
                @Override
                public RecipeSerializer<?> getSerializer()
                {
                    return recipe.getSerializer();
                }
            };
        }

        public static class Serializer<T extends ProcessingRecipe> implements RecipeSerializer<T>
        {
            private final Factory<T> factory;
            private final int defaultTime;
            private final Codec<T> codec;

            public Serializer(Factory<T> factory, int defaultTime)
            {
                this.factory = factory;
                this.defaultTime = defaultTime;
                this.codec = RecordCodecBuilder.create(builder -> {
                    return builder.group(Category.CODEC.fieldOf("category").forGetter(recipe -> {
                            return recipe.category;
                    }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> {
                        return recipe.ingredient;
                    }), RESULT.fieldOf("result").forGetter((recipe) -> {
                        return recipe.result;
                    }),  Codec.INT.fieldOf("time").orElse(this.defaultTime).forGetter(recipe -> {
                        return recipe.time;
                    })).apply(builder, this.factory::create);
                });
            }

            @Override
            public Codec<T> codec()
            {
                return this.codec;
            }

            @Override
            public T fromNetwork(FriendlyByteBuf buf)
            {
                Category category = Category.fromNetwork(buf);
                Ingredient input = Ingredient.fromNetwork(buf);
                ItemStack output = buf.readItem();
                int processTime = buf.readVarInt();
                return this.factory.create(category, input, output, processTime);
            }

            @Override
            public void toNetwork(FriendlyByteBuf buf, T recipe)
            {
                recipe.category.toNetwork(buf);
                recipe.ingredient.toNetwork(buf);
                buf.writeItem(recipe.result);
                buf.writeVarInt(recipe.time);
            }
        }
    }

    public static abstract class ItemWithCount extends ProcessingRecipe
    {
        public static final Codec<ItemStack> ITEMSTACK = RecordCodecBuilder.create(builder -> {
            return builder.group(
                BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStack::getItemHolder),
                Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
                CompoundTag.CODEC.optionalFieldOf("tag").forGetter(stack -> {
                    return Optional.ofNullable(stack.getTag());
                })).apply(builder, ItemStack::new);
        });
        public static final Codec<ItemStack> RESULT = ExtraCodecs.xor(ITEMSTACK, ItemStack.SINGLE_ITEM_CODEC).xmap(either -> {
            return either.map(Function.identity(), Function.identity());
        }, stack -> stack.getCount() == 1 && stack.getTag() == null ? Either.right(stack) : Either.left(stack));

        public ItemWithCount(RecipeType<?> type, Category category, Ingredient ingredient, ItemStack result, int time)
        {
            super(type, category, ingredient, result, time);
        }

        public static class Serializer<T extends ProcessingRecipe> implements RecipeSerializer<T>
        {
            private final Factory<T> factory;
            private final int defaultTime;
            private final Codec<T> codec;

            public Serializer(Factory<T> factory, int defaultTime)
            {
                this.factory = factory;
                this.defaultTime = defaultTime;
                this.codec = RecordCodecBuilder.create(builder -> {
                    return builder.group(Category.CODEC.fieldOf("category").forGetter(recipe -> {
                        return recipe.category;
                    }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> {
                        return recipe.ingredient;
                    }), RESULT.fieldOf("result").forGetter((recipe) -> {
                        return recipe.result;
                    }),  Codec.INT.fieldOf("time").orElse(this.defaultTime).forGetter(recipe -> {
                        return recipe.time;
                    })).apply(builder, this.factory::create);
                });
            }

            @Override
            public Codec<T> codec()
            {
                return this.codec;
            }

            @Override
            public T fromNetwork(FriendlyByteBuf buf)
            {
                Category category = Category.fromNetwork(buf);
                Ingredient input = Ingredient.fromNetwork(buf);
                ItemStack output = buf.readItem();
                int processTime = buf.readVarInt();
                return this.factory.create(category, input, output, processTime);
            }

            @Override
            public void toNetwork(FriendlyByteBuf buf, T recipe)
            {
                recipe.category.toNetwork(buf);
                recipe.ingredient.toNetwork(buf);
                buf.writeItem(recipe.result);
                buf.writeVarInt(recipe.time);
            }
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
