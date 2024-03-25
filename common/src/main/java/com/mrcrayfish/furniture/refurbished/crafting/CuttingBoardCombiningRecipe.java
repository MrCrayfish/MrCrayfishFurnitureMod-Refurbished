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
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCombiningRecipe implements Recipe<Container>
{
    public static final int MAX_INGREDIENTS = 5;

    protected final NonNullList<Ingredient> ingredients;
    protected final ItemStack result;

    public CuttingBoardCombiningRecipe(NonNullList<Ingredient> ingredients, ItemStack result)
    {
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
        public static final Codec<CuttingBoardCombiningRecipe> CODEC = RecordCodecBuilder.create(builder -> {
            return builder.group(Ingredient.CODEC_NONEMPTY.listOf().fieldOf("input").flatXmap(ingredients -> {
                Ingredient[] inputs = ingredients.stream().filter((ingredient) -> {
                    return !ingredient.isEmpty();
                }).toArray(Ingredient[]::new);
                if(inputs.length > MAX_INGREDIENTS) {
                    return DataResult.error(() -> "Too many ingredients");
                } else if(inputs.length == 0) {
                    return DataResult.error(() -> "No ingredients");
                }
                return DataResult.success(NonNullList.of(Ingredient.EMPTY, inputs));
            }, DataResult::success).forGetter((recipe) -> {
                return recipe.getIngredients();
            }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.result;
            })).apply(builder, CuttingBoardCombiningRecipe::new);
        });

        @Override
        public Codec<CuttingBoardCombiningRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public CuttingBoardCombiningRecipe fromNetwork(FriendlyByteBuf buffer)
        {
            int inputCount = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(inputCount, Ingredient.EMPTY);
            IntStream.range(0, inputCount).forEach(i -> inputs.set(i, Ingredient.fromNetwork(buffer)));
            ItemStack output = buffer.readItem();
            return new CuttingBoardCombiningRecipe(inputs, output);
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
        private final NonNullList<Ingredient> inputs = NonNullList.create();
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
        public RecipeBuilder unlockedBy(String name, Criterion<?> criterion)
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
        public void save(RecipeOutput output, ResourceLocation id)
        {
            this.validate();
            output.accept(id, new CuttingBoardCombiningRecipe(this.inputs, this.output), null);
        }

        private void validate()
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
}
