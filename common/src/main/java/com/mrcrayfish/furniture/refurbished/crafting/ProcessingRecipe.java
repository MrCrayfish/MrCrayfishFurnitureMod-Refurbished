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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int processTime;

    protected ProcessingRecipe(RecipeType<?> type, ResourceLocation id, Ingredient input, ItemStack output, int processTime)
    {
        this.type = type;
        this.id = id;
        this.input = input;
        this.output = output;
        this.processTime = processTime;
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
        return this.output;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access)
    {
        return this.output.copy();
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
        ingredients.add(this.input);
        return ingredients;
    }

    /**
     * @return The input ingredient of this recipe
     */
    public Ingredient getInput()
    {
        return this.input;
    }

    /**
     * Gets the output of this recipe. Careful, this returns the non-copy itemstack.
     *
     * @return The output itemstack
     */
    public ItemStack getOutput()
    {
        return this.output;
    }

    /**
     * @return The time in ticks to process this recipe
     */
    public int getProcessTime()
    {
        return this.processTime;
    }

    public static Builder builder(Ingredient input, ItemStack output, int processTime, RecipeSerializer<? extends ProcessingRecipe> serializer)
    {
        return new Builder(input, output, processTime, serializer);
    }

    public static class Serializer<T extends ProcessingRecipe> implements RecipeSerializer<T>
    {
        private final Factory<T> factory;
        private final int defaultTime;

        public Serializer(Factory<T> factory, int defaultTime)
        {
            this.factory = factory;
            this.defaultTime = defaultTime;
        }

        @Override
        public T fromJson(ResourceLocation id, JsonObject object)
        {
            Ingredient input = Utils.getIngredient(object, "input");
            ItemStack output = Utils.getItemStack(object, "output");
            int processTime = GsonHelper.getAsInt(object, "processTime", this.defaultTime);
            return this.factory.create(id, input, output, processTime);
        }

        @Override
        public T fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
        {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int processTime = buf.readVarInt();
            return this.factory.create(id, input, output, processTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, T recipe)
        {
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeVarInt(recipe.processTime);
        }
    }

    public interface Factory<T extends ProcessingRecipe>
    {
        T create(ResourceLocation id, Ingredient input, ItemStack result, int processTime);
    }

    public static class Builder implements RecipeBuilder
    {
        protected final Ingredient input;
        protected final ItemStack output;
        protected final int processTime;
        protected final RecipeSerializer<? extends ProcessingRecipe> serializer;

        private Builder(Ingredient input, ItemStack output, int processTime, RecipeSerializer<? extends ProcessingRecipe> serializer)
        {
            this.input = input;
            this.output = output;
            this.processTime = processTime;
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
        public Item getResult()
        {
            return this.output.getItem();
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            consumer.accept(new Result(id, this.input, this.output, this.processTime, this.serializer));
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack output;
        private final int processTime;
        private final RecipeSerializer<? extends ProcessingRecipe> serializer;

        public Result(ResourceLocation id, Ingredient input, ItemStack output, int processTime, RecipeSerializer<? extends ProcessingRecipe> serializer)
        {
            this.id = id;
            this.input = input;
            this.output = output;
            this.processTime = processTime;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            object.add("input", this.input.toJson());
            String id = BuiltInRegistries.ITEM.getKey(this.output.getItem()).toString();
            int count = this.output.getCount();
            if(count == 1)
            {
                object.addProperty("output", id);
            }
            else
            {
                JsonObject itemObject = new JsonObject();
                itemObject.addProperty("item", id);
                itemObject.addProperty("count", count);
                object.add("output", itemObject);
            }
            object.addProperty("processTime", this.processTime);
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
}
