package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class RecycleBinRecyclingRecipe implements Recipe<Container>
{
    public static final int MAX_OUTPUT_COUNT = 9;

    protected final ItemStack recyclable;
    protected final NonNullList<ItemStack> scraps;

    public RecycleBinRecyclingRecipe(ItemStack recyclable, NonNullList<ItemStack> scraps)
    {
        this.recyclable = recyclable;
        this.scraps = scraps;
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
        return this.scraps.get(0);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access)
    {
        return this.scraps.get(0);
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
        public static final Codec<RecycleBinRecyclingRecipe> CODEC = RecordCodecBuilder.create(builder -> {
            return builder.group(ItemStack.SINGLE_ITEM_CODEC.fieldOf("recyclable").forGetter(recipe -> {
                return recipe.recyclable;
            }), ItemStack.SINGLE_ITEM_CODEC.listOf().fieldOf("scraps").flatXmap(items -> { // TODO change to allow nbt
                ItemStack[] inputs = items.stream().filter((ingredient) -> {
                    return !ingredient.isEmpty();
                }).toArray(ItemStack[]::new);
                if(inputs.length > MAX_OUTPUT_COUNT) {
                    return DataResult.error(() -> "Too many scraps");
                } else if(inputs.length == 0) {
                    return DataResult.error(() -> "No scraps");
                }
                return DataResult.success(NonNullList.of(ItemStack.EMPTY, inputs));
            }, DataResult::success).forGetter((recipe) -> {
                return recipe.getScraps();
            })).apply(builder, RecycleBinRecyclingRecipe::new);
        });

        @Override
        public Codec<RecycleBinRecyclingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public RecycleBinRecyclingRecipe fromNetwork(FriendlyByteBuf buffer)
        {
            ItemStack recyclable = buffer.readItem();
            int scrapCount = buffer.readInt();
            NonNullList<ItemStack> scraps = NonNullList.withSize(scrapCount, ItemStack.EMPTY);
            IntStream.range(0, scrapCount).forEach(i -> scraps.set(i, buffer.readItem()));
            return new RecycleBinRecyclingRecipe(recyclable, scraps);
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
        private final Item recyclable;
        private final NonNullList<ItemStack> scraps = NonNullList.create();

        public Builder(Item recyclable)
        {
            this.recyclable = recyclable;
        }

        public void addScrap(ItemStack stack)
        {
            this.scraps.add(stack);
        }

        @Override
        public RecipeBuilder unlockedBy(String name, Criterion<?> trigger)
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
            return this.recyclable;
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id)
        {
            this.validate(id);
            output.accept(id, new RecycleBinRecyclingRecipe(new ItemStack(this.recyclable), this.scraps), null);
        }

        private void validate(ResourceLocation id)
        {
            if(this.scraps.isEmpty())
            {
                throw new IllegalStateException("Cannot have an empty output for recycling bin recipe");
            }
            if(this.scraps.size() > MAX_OUTPUT_COUNT)
            {
                throw new IllegalStateException("Recycling bin recipe only supports up to " + MAX_OUTPUT_COUNT + " scraps");
            }
        }
    }
}
