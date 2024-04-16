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
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Author: MrCrayfish
 */
public class RecycleBinRecyclingRecipe implements Recipe<Container>
{
    public static final int MAX_OUTPUT_COUNT = 9;

    protected final ResourceLocation id;
    protected final ItemStack recyclable;
    protected final NonNullList<ItemStack> scraps;

    public RecycleBinRecyclingRecipe(ResourceLocation id, ItemStack recyclable, NonNullList<ItemStack> scraps)
    {
        this.id = id;
        this.recyclable = recyclable;
        this.scraps = scraps;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.RECYCLE_BIN_RECYCLING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.RECYCLE_BIN_RECIPE.get();
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return ItemStack.isSameItem(this.recyclable, container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access)
    {
        return ItemStack.EMPTY;
    }

    public ItemStack getRecyclable()
    {
        return this.recyclable;
    }

    public NonNullList<ItemStack> getScraps()
    {
        return this.scraps;
    }

    /**
     * Creates a randomised output of this recycling recipe. This takes each possible output of
     * this recipe, creates a new list, then adds the output to the new list if the random roll
     * generated from the given random source if within the given chance value. The itemstacks
     * contained within the new list as safe to use as a new copy is created before adding to
     * the output list.

     * @param random a random source
     * @param chance the maximum chance for an output to be included
     * @return a list of
     */
    public List<ItemStack> createRandomisedOutput(RandomSource random, float chance)
    {
        return this.scraps.stream().map(stack -> {
            return random.nextFloat() < chance ? stack.copy() : null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static class Serializer implements RecipeSerializer<RecycleBinRecyclingRecipe>
    {
        public static void toJson(Result result, JsonObject object)
        {
            object.addProperty("recyclable", BuiltInRegistries.ITEM.getKey(result.input).toString());
            JsonArray outputArray = new JsonArray();
            for(ItemStack stack : result.outputs)
            {
                JsonObject outputObject = new JsonObject();
                outputObject.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
                outputObject.addProperty("count", stack.getCount());
                Optional.ofNullable(stack.getTag()).ifPresent(tag -> {
                    outputObject.addProperty("nbt", tag.toString());
                });
                outputArray.add(outputObject);
            }
            object.add("scraps", outputArray);
        }

        @Override
        public RecycleBinRecyclingRecipe fromJson(ResourceLocation id, JsonObject object)
        {
            String inputString = GsonHelper.getAsString(object, "recyclable");
            ItemStack recyclable = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(inputString)), 1);
            JsonArray scrapsArray = GsonHelper.getAsJsonArray(object, "scraps");
            ItemStack[] scraps = StreamSupport.stream(scrapsArray.spliterator(), false).map(element -> {
                if(element instanceof JsonObject outputObject) {
                    return ShapedRecipe.itemStackFromJson(outputObject);
                }
                return null;
            }).toArray(ItemStack[]::new);
            return new RecycleBinRecyclingRecipe(id, recyclable, NonNullList.of(ItemStack.EMPTY, scraps));
        }

        @Override
        public RecycleBinRecyclingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            ItemStack recyclable = buffer.readItem();
            int scrapsCount = buffer.readInt();
            ItemStack[] scraps = IntStream.range(0, scrapsCount)
                    .mapToObj(val -> buffer.readItem())
                    .toArray(ItemStack[]::new);
            return new RecycleBinRecyclingRecipe(id, recyclable, NonNullList.of(ItemStack.EMPTY, scraps));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RecycleBinRecyclingRecipe recipe)
        {
            buffer.writeItem(recipe.recyclable);
            buffer.writeInt(recipe.scraps.size());
            for(ItemStack stack : recipe.scraps)
            {
                buffer.writeItem(stack);
            }
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final Item input;
        private final ItemStack[] outputs;

        public Builder(Item input, ItemStack[] outputs)
        {
            this.input = input;
            this.outputs = outputs;
        }

        @Override
        public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger)
        {
            throw new UnsupportedOperationException("Recycling Bin recipes don't support unlocking");
        }

        @Override
        public RecipeBuilder group(@Nullable String group)
        {
            throw new UnsupportedOperationException("Recycling Bin recipes don't support setting the group");
        }

        @Override
        public Item getResult()
        {
            return this.input;
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            this.validate(id);
            consumer.accept(new Result(id, this.input, this.outputs));
        }

        private void validate(ResourceLocation id)
        {
            if(this.outputs.length == 0)
            {
                throw new IllegalStateException("Cannot have an empty output for recycling bin recipe");
            }
            if(this.outputs.length > MAX_OUTPUT_COUNT)
            {
                throw new IllegalStateException("Recycling bin recipe only supports up to " + MAX_OUTPUT_COUNT + " outputs");
            }
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Item input;
        private final ItemStack[] outputs;

        public Result(ResourceLocation id, Item input, ItemStack[] outputs)
        {
            this.id = id;
            this.input = input;
            this.outputs = outputs;
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return ModRecipeSerializers.RECYCLE_BIN_RECIPE.get();
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            Serializer.toJson(this, object);
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
