package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingRecipe implements Recipe<Container>
{
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int time;

    protected ProcessingRecipe(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStack result, int time)
    {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
    }

    @Override
    public RecipeType<?> getType()
    {
        return this.type;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
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

    public static Builder builder(Ingredient input, ItemStack output, int processTime, Serializer<? extends ProcessingRecipe> serializer)
    {
        return new Builder(input, output, processTime, serializer);
    }

    public interface Factory<T extends ProcessingRecipe>
    {
        T create(ResourceLocation id, Ingredient ingredient, ItemStack result, int time);
    }

    public static class Builder implements RecipeBuilder
    {
        protected final Ingredient ingredient;
        protected final ItemStack result;
        protected final int time;
        protected final Serializer<? extends ProcessingRecipe> serializer;

        private Builder(Ingredient ingredient, ItemStack result, int time, Serializer<? extends ProcessingRecipe> serializer)
        {
            this.ingredient = ingredient;
            this.result = result;
            this.time = time;
            this.serializer = serializer;
        }

        @Override
        public RecipeBuilder unlockedBy(String s, CriterionTriggerInstance instance)
        {
            throw new UnsupportedOperationException("Unlocking not supported for ProcessingRecipes");
        }

        @Override
        public RecipeBuilder group(@Nullable String var1)
        {
            throw new UnsupportedOperationException("Group not supported for ProcessingRecipes");
        }

        @Override
        public net.minecraft.world.item.Item getResult()
        {
            return this.result.getItem();
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            consumer.accept(new Result(id, this.ingredient, this.result, this.time, this.serializer));
        }
    }

    public static abstract class Serializer<T extends ProcessingRecipe> implements RecipeSerializer<T>
    {
        protected final Factory<T> factory;
        protected final int defaultTime;

        public Serializer(Factory<T> factory, int defaultTime)
        {
            this.factory = factory;
            this.defaultTime = defaultTime;
        }

        public abstract void toJson(JsonObject object, Result result);

        @Override
        public T fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
        {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack result = buf.readItem();
            int time = buf.readVarInt();
            return this.factory.create(id, ingredient, result, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, T recipe)
        {
            recipe.ingredient.toNetwork(buf);
            buf.writeItem(recipe.result);
            buf.writeVarInt(recipe.time);
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final ItemStack result;
        private final int time;
        private final Serializer<? extends ProcessingRecipe> serializer;

        public Result(ResourceLocation id, Ingredient ingredient, ItemStack result, int time, Serializer<? extends ProcessingRecipe> serializer)
        {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.time = time;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            this.serializer.toJson(object, this);
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return this.serializer;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            // Not supported
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            // Not supported
            return null;
        }
    }

    public static abstract class Item extends ProcessingRecipe
    {
        protected Item(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStack result, int time)
        {
            super(type, id, ingredient, result, time);
        }

        public static ProcessingRecipe from(AbstractCookingRecipe recipe, RegistryAccess access)
        {
            return new ProcessingRecipe(recipe.getType(), recipe.getId(), recipe.getIngredients().get(0), recipe.getResultItem(access), recipe.getCookingTime())
            {
                @Override
                public RecipeSerializer<?> getSerializer()
                {
                    return recipe.getSerializer();
                }
            };
        }

        public static class Serializer<T extends ProcessingRecipe> extends ProcessingRecipe.Serializer<T>
        {
            public Serializer(Factory<T> factory, int defaultTime)
            {
                super(factory, defaultTime);
            }

            @Override
            public void toJson(JsonObject object, Result result)
            {
                String id = BuiltInRegistries.ITEM.getKey(result.result.getItem()).toString();
                object.add("ingredient", result.ingredient.toJson());
                object.addProperty("result", id);
                object.addProperty("time", result.time);
            }

            @Override
            public T fromJson(ResourceLocation id, JsonObject object)
            {
                Ingredient ingredient = Utils.getIngredient(object, "ingredient");
                ResourceLocation resultId = new ResourceLocation(GsonHelper.getAsString(object, "result"));
                net.minecraft.world.item.Item item = BuiltInRegistries.ITEM.getOptional(resultId).orElseThrow(() -> new IllegalStateException("The item '%s' does not exist".formatted(resultId)));
                int time = GsonHelper.getAsInt(object, "time", this.defaultTime);
                return this.factory.create(id, ingredient, new ItemStack(item), time);
            }
        }
    }

    public static abstract class ItemWithCount extends ProcessingRecipe
    {
        protected ItemWithCount(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStack result, int time)
        {
            super(type, id, ingredient, result, time);
        }

        public static class Serializer<T extends ProcessingRecipe> extends ProcessingRecipe.Serializer<T>
        {
            public Serializer(Factory<T> factory, int defaultTime)
            {
                super(factory, defaultTime);
            }

            @Override
            public void toJson(JsonObject object, Result result)
            {
                object.add("ingredient", result.ingredient.toJson());
                String id = BuiltInRegistries.ITEM.getKey(result.result.getItem()).toString();
                int count = result.result.getCount();
                if(count == 1)
                {
                    object.addProperty("result", id);
                }
                else
                {
                    JsonObject itemObject = new JsonObject();
                    itemObject.addProperty("item", id);
                    itemObject.addProperty("count", count);
                    object.add("result", itemObject);
                }
                object.addProperty("time", result.time);
            }

            @Override
            public T fromJson(ResourceLocation id, JsonObject object)
            {
                Ingredient input = Utils.getIngredient(object, "ingredient");
                ItemStack output = Utils.getItemStack(object, "result");
                int processTime = GsonHelper.getAsInt(object, "time", this.defaultTime);
                return this.factory.create(id, input, output, processTime);
            }
        }
    }
}
