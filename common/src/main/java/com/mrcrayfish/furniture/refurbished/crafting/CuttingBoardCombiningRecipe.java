package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;
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
    public ItemStack assemble(Container container, HolderLookup.Provider provider)
    {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider)
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
        public static final MapCodec<CuttingBoardCombiningRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> {
            return builder.group(Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
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
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            IntStream.range(0, ingredientCount).forEach(i -> ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf)));
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
        public void save(RecipeOutput output, ResourceLocation id)
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
