package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class SinkFluidTransmutingRecipe extends SingleItemRecipe
{
    private final Fluid fluid;

    public SinkFluidTransmutingRecipe(ResourceLocation id, Fluid fluid, Ingredient catalyst, ItemStack result)
    {
        super(ModRecipeTypes.SINK_FLUID_TRANSMUTING.get(), ModRecipeSerializers.SINK_FLUID_TRANSMUTING_RECIPE.get(), id, "", catalyst, result);
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
        @Override
        public SinkFluidTransmutingRecipe fromJson(ResourceLocation id, JsonObject object)
        {
            Fluid fluid = Utils.getFluid(object, "fluid");
            Ingredient catalyst = Utils.getIngredient(object, "catalyst");
            String itemId = GsonHelper.getAsString(object, "result");
            int count = GsonHelper.getAsInt(object, "count");
            ItemStack result = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(itemId)), count);
            return new SinkFluidTransmutingRecipe(id, fluid, catalyst, result);
        }

        public static void toJson(SinkFluidTransmutingRecipe.Result result, JsonObject object)
        {
            object.addProperty("fluid", BuiltInRegistries.FLUID.getKey(result.fluid).toString());
            object.add("catalyst", result.catalyst.toJson());
            object.addProperty("result", BuiltInRegistries.ITEM.getKey(result.result.getItem()).toString());
            object.addProperty("count", result.result.getCount());
        }

        @Override
        public SinkFluidTransmutingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            Fluid fluid = BuiltInRegistries.FLUID.get(buffer.readResourceLocation());
            Ingredient catalyst = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new SinkFluidTransmutingRecipe(id, fluid, catalyst, result);
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
        public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger)
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
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            consumer.accept(new SinkFluidTransmutingRecipe.Result(id, this.fluid, this.catalyst, this.result));
        }

        public static Builder from(Fluid fluid, Ingredient catalyst, ItemStack result)
        {
            return new Builder(fluid, catalyst, result);
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Fluid fluid;
        private final Ingredient catalyst;
        private final ItemStack result;

        public Result(ResourceLocation id, Fluid fluid, Ingredient catalyst, ItemStack result)
        {
            this.id = id;
            this.fluid = fluid;
            this.catalyst = catalyst;
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
            return ModRecipeSerializers.SINK_FLUID_TRANSMUTING_RECIPE.get();
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            SinkFluidTransmutingRecipe.Serializer.toJson(this, object);
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
