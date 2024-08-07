package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCombiningRecipe implements Recipe<Container>
{
    public static final int MAX_INGREDIENTS = 5;

    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> ingredients;
    protected final ItemStack result;

    public CuttingBoardCombiningRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result)
    {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        for(int i = 0; i < this.ingredients.size() && i < container.getContainerSize(); i++)
        {
            if(!this.ingredients.get(i).test(container.getItem(i)))
            {
                return false;
            }
        }
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
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.CUTTING_BOARD_COMBINING_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.CUTTING_BOARD_COMBINING.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return this.ingredients;
    }

    public ItemStack getResult()
    {
        return this.result;
    }

    public boolean completelyMatches(Container container)
    {
        if(this.ingredients.size() <= container.getContainerSize())
        {
            for(int i = 0; i < this.ingredients.size(); i++)
            {
                if(!this.ingredients.get(i).test(container.getItem(i)))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static class Serializer implements RecipeSerializer<CuttingBoardCombiningRecipe>
    {
        public static void toJson(CuttingBoardCombiningRecipe.Result result, JsonObject object)
        {
            JsonArray inputArray = new JsonArray();
            for(Ingredient ingredient : result.ingredients)
            {
                inputArray.add(ingredient.toJson());
            }
            object.add("ingredients", inputArray);
            object.addProperty("result", BuiltInRegistries.ITEM.getKey(result.result.getItem()).toString());
            object.addProperty("count", result.result.getCount());
        }

        @Override
        public CuttingBoardCombiningRecipe fromJson(ResourceLocation id, JsonObject object)
        {
            JsonArray inputArray = GsonHelper.getAsJsonArray(object, "ingredients");
            Ingredient[] ingredients = StreamSupport.stream(inputArray.spliterator(), false).map(element -> {
                return Ingredient.fromJson(element);
            }).toArray(Ingredient[]::new);
            String resultString = GsonHelper.getAsString(object, "result");
            Item item = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(resultString)).orElseThrow(() -> new IllegalStateException("Item: " + resultString + " does not exist"));
            int count = GsonHelper.getAsInt(object, "count", 1);
            ItemStack result = new ItemStack(item, count);
            return new CuttingBoardCombiningRecipe(id, NonNullList.of(Ingredient.EMPTY, ingredients), result);
        }

        @Override
        public CuttingBoardCombiningRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            int inputCount = buffer.readInt();
            Ingredient[] ingredients = IntStream.range(0, inputCount)
                    .mapToObj(val -> Ingredient.fromNetwork(buffer))
                    .toArray(Ingredient[]::new);
            ItemStack result = buffer.readItem();
            return new CuttingBoardCombiningRecipe(id, NonNullList.of(Ingredient.EMPTY, ingredients), result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CuttingBoardCombiningRecipe recipe)
        {
            buffer.writeInt(recipe.ingredients.size());
            for(Ingredient ingredient : recipe.ingredients)
            {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final List<Ingredient> ingredients = new ArrayList<>();
        private final ItemStack result;

        public Builder(ItemStack result)
        {
            this.result = result;
        }

        public Builder add(Ingredient ingredient)
        {
            this.ingredients.add(ingredient);
            return this;
        }

        @Override
        public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger)
        {
            throw new UnsupportedOperationException("Cutting Board combining recipes don't support unlocking");
        }

        @Override
        public RecipeBuilder group(@Nullable String group)
        {
            throw new UnsupportedOperationException("Cutting Board combining recipes don't support setting the group");
        }

        @Override
        public Item getResult()
        {
            return this.result.getItem();
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            this.validate(id);
            consumer.accept(new CuttingBoardCombiningRecipe.Result(id, this.ingredients.toArray(Ingredient[]::new), this.result));
        }

        private void validate(ResourceLocation id)
        {
            if(this.ingredients.size() < 2)
            {
                throw new IllegalStateException("Cutting Board combining recipe must have at least 2 input ingredients");
            }
            if(this.ingredients.size() > MAX_INGREDIENTS)
            {
                throw new IllegalStateException("Cutting Board combining recipe only supports up to " + MAX_INGREDIENTS + " input ingredients");
            }
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Ingredient[] ingredients;
        private final ItemStack result;

        public Result(ResourceLocation id, Ingredient[] ingredients, ItemStack result)
        {
            this.id = id;
            this.ingredients = ingredients;
            this.result = result;
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return ModRecipeSerializers.CUTTING_BOARD_COMBINING_RECIPE.get();
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            CuttingBoardCombiningRecipe.Serializer.toJson(this, object);
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
