package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class SinkFluidTransmutingRecipe extends SingleItemRecipe
{
    private final Fluid fluid;

    public SinkFluidTransmutingRecipe(Fluid fluid, Ingredient catalyst, ItemStack result)
    {
        super(ModRecipeTypes.SINK_FLUID_TRANSMUTING.get(), ModRecipeSerializers.SINK_FLUID_TRANSMUTING_RECIPE.get(), "", catalyst, result);
        this.fluid = fluid;
    }

    public Fluid getFluid()
    {
        return this.fluid;
    }

    public Ingredient getCatalyst()
    {
        return this.ingredient;
    }

    public ItemStack getResult()
    {
        return this.result;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return this.ingredient.test(container.getItem(0));
    }

    public static class Serializer implements RecipeSerializer<SinkFluidTransmutingRecipe>
    {
        public static final Codec<SinkFluidTransmutingRecipe> CODEC = RecordCodecBuilder.create(builder -> {
           return builder.group(FluidState.CODEC.fieldOf("fluid").forGetter(recipe -> {
               return recipe.fluid.defaultFluidState();
           }), Ingredient.CODEC_NONEMPTY.fieldOf("catalyst").forGetter(recipe -> {
               return recipe.ingredient;
           }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> {
               return recipe.result;
           })).apply(builder, (state, catalyst, result) -> {
               return new SinkFluidTransmutingRecipe(state.getType(), catalyst, result);
           });
        });

        @Override
        public Codec<SinkFluidTransmutingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public SinkFluidTransmutingRecipe fromNetwork(FriendlyByteBuf buffer)
        {
            Fluid fluid = BuiltInRegistries.FLUID.get(buffer.readResourceLocation());
            Ingredient catalyst = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new SinkFluidTransmutingRecipe(fluid, catalyst, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SinkFluidTransmutingRecipe recipe)
        {
            buffer.writeResourceLocation(BuiltInRegistries.FLUID.getKey(recipe.fluid));
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final Fluid fluid;
        private final Ingredient catalyst;
        private final ItemStack result;

        private Builder(Fluid fluid, Ingredient catalyst, ItemStack result)
        {
            this.fluid = fluid;
            this.catalyst = catalyst;
            this.result = result;
        }

        @Override
        public RecipeBuilder unlockedBy(String name, Criterion<?> trigger)
        {
            throw new UnsupportedOperationException("Sink Fluid Mixing recipes don't support unlocking");
        }

        @Override
        public RecipeBuilder group(@Nullable String group)
        {
            throw new UnsupportedOperationException("Sink Fluid Mixing recipes don't support setting the group");
        }

        @Override
        public Item getResult()
        {
            return this.result.getItem();
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id)
        {
            output.accept(id, new SinkFluidTransmutingRecipe(this.fluid, this.catalyst, this.result), null);
        }

        public static Builder from(Fluid fluid, Ingredient catalyst, ItemStack result)
        {
            return new Builder(fluid, catalyst, result);
        }
    }
}
