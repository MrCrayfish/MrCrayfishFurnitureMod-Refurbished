package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingRecipe implements Recipe<Container>
{
    protected final RecipeType<?> type;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int processTime;

    protected ProcessingRecipe(RecipeType<?> type, Ingredient input, ItemStack output, int processTime)
    {
        this.type = type;
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

    public static <T extends ProcessingRecipe> Builder<T> builder(Factory<T> factory, Ingredient input, ItemStack output, int processTime)
    {
        return new Builder<T>(factory, input, output, processTime);
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
                return builder.group(Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter((recipe) -> {
                    return recipe.input;
                }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("output").forGetter((recipe) -> {
                    return recipe.output;
                }),  Codec.INT.fieldOf("processTime").orElse(this.defaultTime).forGetter(recipe -> {
                    return recipe.processTime;
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
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int processTime = buf.readVarInt();
            return this.factory.create(input, output, processTime);
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
        T create(Ingredient input, ItemStack result, int processTime);
    }

    public static class Builder<T extends ProcessingRecipe> implements RecipeBuilder
    {
        protected final Factory<T> factory;
        protected final Ingredient input;
        protected final ItemStack output;
        protected final int processTime;

        private Builder(Factory<T> factory, Ingredient input, ItemStack output, int processTime)
        {
            this.factory = factory;
            this.input = input;
            this.output = output;
            this.processTime = processTime;
        }

        @Override
        public RecipeBuilder unlockedBy(String s, Criterion<?> instance)
        {
            throw new UnsupportedOperationException("Unlocking not supported for ProcessingRecipes");
        }

        @Override
        public RecipeBuilder group(@Nullable String group)
        {
            throw new UnsupportedOperationException("Group not supported for ProcessingRecipes");
        }

        @Override
        public Item getResult()
        {
            return this.output.getItem();
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id)
        {
            output.accept(id, this.factory.create(this.input, this.output, this.processTime), null);
        }
    }
}
