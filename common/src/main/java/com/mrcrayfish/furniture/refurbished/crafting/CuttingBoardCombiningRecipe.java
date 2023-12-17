package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.advancements.CriterionTriggerInstance;
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
    protected final Ingredient[] inputs;
    protected final ItemStack output;

    public CuttingBoardCombiningRecipe(ResourceLocation id, Ingredient[] inputs, ItemStack output)
    {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        for(int i = 0; i < this.inputs.length && i < container.getContainerSize(); i++)
        {
            if(!this.inputs[i].test(container.getItem(i)))
            {
                return false;
            }
        }
        return true;
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
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.output;
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

    public boolean completelyMatches(Container container)
    {
        if(this.inputs.length <= container.getContainerSize())
        {
            for(int i = 0; i < this.inputs.length; i++)
            {
                if(!this.inputs[i].test(container.getItem(i)))
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
            for(Ingredient ingredient : result.inputs)
            {
                inputArray.add(ingredient.toJson());
            }
            object.add("input", inputArray);
            object.addProperty("output", BuiltInRegistries.ITEM.getKey(result.output.getItem()).toString());
        }

        @Override
        public CuttingBoardCombiningRecipe fromJson(ResourceLocation id, JsonObject object)
        {
            JsonArray inputArray = GsonHelper.getAsJsonArray(object, "input");
            Ingredient[] inputs = StreamSupport.stream(inputArray.spliterator(), false).map(element -> {
                return Ingredient.fromJson(element, false);
            }).toArray(Ingredient[]::new);
            String outputString = GsonHelper.getAsString(object, "output");
            Item item = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(outputString)).orElseThrow(() -> new IllegalStateException("Item: " + outputString + " does not exist"));
            ItemStack output = new ItemStack(item, 1);
            return new CuttingBoardCombiningRecipe(id, inputs, output);
        }

        @Override
        public CuttingBoardCombiningRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            int inputCount = buffer.readInt();
            Ingredient[] inputs = IntStream.range(0, inputCount)
                    .mapToObj(val -> Ingredient.fromNetwork(buffer))
                    .toArray(Ingredient[]::new);
            ItemStack output = buffer.readItem();
            return new CuttingBoardCombiningRecipe(id, inputs, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CuttingBoardCombiningRecipe recipe)
        {
            buffer.writeInt(recipe.inputs.length);
            for(Ingredient ingredient : recipe.inputs)
            {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final List<Ingredient> inputs = new ArrayList<>();
        private final ItemStack output;

        public Builder(ItemStack output)
        {
            this.output = output;
        }

        public Builder add(Ingredient ingredient)
        {
            this.inputs.add(ingredient);
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
            return this.output.getItem();
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            this.validate(id);
            consumer.accept(new CuttingBoardCombiningRecipe.Result(id, this.inputs.toArray(Ingredient[]::new), this.output));
        }

        private void validate(ResourceLocation id)
        {
            if(this.inputs.size() < 2)
            {
                throw new IllegalStateException("Cutting Board combining recipe must have at least 2 input ingredients");
            }
            if(this.inputs.size() > MAX_INGREDIENTS)
            {
                throw new IllegalStateException("Cutting Board combining recipe only supports up to " + MAX_INGREDIENTS + " input ingredients");
            }
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Ingredient[] inputs;
        private final ItemStack output;

        public Result(ResourceLocation id, Ingredient[] inputs, ItemStack output)
        {
            this.id = id;
            this.inputs = inputs;
            this.output = output;
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
