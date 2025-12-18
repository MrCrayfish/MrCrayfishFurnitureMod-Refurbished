package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCombiningRecipe implements Recipe<ContainerInput>
{
    public static final int MAX_INGREDIENTS = 5;

    protected final NonNullList<Ingredient> ingredients;
    protected final ItemStack result;
    protected @Nullable PlacementInfo placementInfo;

    public CuttingBoardCombiningRecipe(NonNullList<Ingredient> ingredients, ItemStack result)
    {
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(ContainerInput input, Level level)
    {
        for(int i = 0; i < this.ingredients.size() && i < input.size(); i++)
        {
            if(!this.ingredients.get(i).test(input.getItem(i)))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(ContainerInput input, HolderLookup.Provider provider)
    {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<CuttingBoardCombiningRecipe> getSerializer()
    {
        return ModRecipeSerializers.CUTTING_BOARD_COMBINING_RECIPE.get();
    }

    @Override
    public RecipeType<CuttingBoardCombiningRecipe> getType()
    {
        return ModRecipeTypes.CUTTING_BOARD_COMBINING.get();
    }

    @Override
    public PlacementInfo placementInfo()
    {
        if(this.placementInfo == null)
        {
            this.placementInfo = PlacementInfo.createFromOptionals(this.ingredients.stream().map(Optional::of).toList());
        }
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }

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
        public static final MapCodec<CuttingBoardCombiningRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> {
            return builder.group(Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                NonNullList<Ingredient> inputs = NonNullList.create();
                inputs.addAll(ingredients);
                if(inputs.size() > MAX_INGREDIENTS) {
                    return DataResult.error(() -> "Too many ingredients");
                } else if(inputs.isEmpty()) {
                    return DataResult.error(() -> "No ingredients");
                }
                return DataResult.success(inputs);
            }, DataResult::success).forGetter((recipe) -> {
                return recipe.ingredients;
            }), ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.result;
            })).apply(builder, CuttingBoardCombiningRecipe::new);
        });

        public static final StreamCodec<RegistryFriendlyByteBuf, CuttingBoardCombiningRecipe> STREAM_CODEC = StreamCodec.of((buf, recipe) -> {
            buf.writeInt(recipe.ingredients.size());
            recipe.ingredients.forEach(ingredient -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            });
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        }, buf -> {
            int ingredientCount = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            IntStream.range(0, ingredientCount).forEach(i -> ingredients.add(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf)));
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new CuttingBoardCombiningRecipe(ingredients, result);
        });

        @Override
        public MapCodec<CuttingBoardCombiningRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CuttingBoardCombiningRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final NonNullList<Ingredient> ingredients = NonNullList.create();
        private final ItemStack result;

        public Builder(ItemStack output)
        {
            this.result = output;
        }

        public Builder add(Ingredient ingredient)
        {
            this.ingredients.add(ingredient);
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
            return this.result.getItem();
        }


        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> id)
        {
            this.validate();
            output.accept(id, new CuttingBoardCombiningRecipe(this.ingredients, this.result), null);
        }

        private void validate()
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
}
