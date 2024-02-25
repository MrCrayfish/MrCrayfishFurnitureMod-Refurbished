package com.mrcrayfish.furniture.refurbished.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.data.Material;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class WorkbenchContructingRecipe implements Recipe<Container>
{
    // TODO allow recipe to change sound (drill for wood, saw for stone, weld for electronics)
    private final ResourceLocation id;
    private final NonNullList<StackedIngredient> materials;
    private final ItemStack result;
    private final boolean notification;

    public WorkbenchContructingRecipe(ResourceLocation id, NonNullList<StackedIngredient> materials, ItemStack result, boolean notification)
    {
        this.id = id;
        this.materials = materials;
        this.result = result;
        this.notification = notification;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.WORKBENCH_CONSTRUCTING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.WORKBENCH_RECIPE.get();
    }

    @Override
    public boolean matches(Container container, Level level)
    {
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
    public boolean showNotification()
    {
        return this.notification;
    }

    public NonNullList<StackedIngredient> getMaterials()
    {
        return this.materials;
    }

    public int getResultId()
    {
        return Item.getId(this.result.getItem());
    }

    public ItemStack getResult()
    {
        return this.result;
    }

    public static Builder builder(ItemLike result, int count, Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag)
    {
        return new Builder(result.asItem(), count, hasItem, hasTag);
    }

    public static class Serializer implements RecipeSerializer<WorkbenchContructingRecipe>
    {
        public static void toJson(WorkbenchContructingRecipe.Result result, JsonObject object)
        {
            JsonArray materialsArray = new JsonArray();
            for(StackedIngredient ingredient : result.materials)
            {
                materialsArray.add(ingredient.toJson());
            }
            object.add("materials", materialsArray);
            object.addProperty("result", BuiltInRegistries.ITEM.getKey(result.result).toString());
            object.addProperty("count", result.count);
            object.addProperty("show_notification", result.notification);
        }

        @Override
        public WorkbenchContructingRecipe fromJson(ResourceLocation id, JsonObject object)
        {
            JsonArray materialArray = GsonHelper.getAsJsonArray(object, "materials");
            NonNullList<StackedIngredient> materials = NonNullList.withSize(materialArray.size(), StackedIngredient.EMPTY);
            IntStream.range(0, materialArray.size()).forEach(i -> materials.set(i, StackedIngredient.fromJson(materialArray.get(i))));
            String inputString = GsonHelper.getAsString(object, "result");
            int count = GsonHelper.getAsInt(object, "count", 1);
            // TODO error out here iuf item not found
            ItemStack result = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(inputString)), count);
            boolean notification = GsonHelper.getAsBoolean(object, "show_notification", true);
            return new WorkbenchContructingRecipe(id, materials, result, notification);
        }

        @Override
        public WorkbenchContructingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            int materialCount = buffer.readInt();
            NonNullList<StackedIngredient> materials = NonNullList.withSize(materialCount, StackedIngredient.EMPTY);
            IntStream.range(0, materialCount).forEach(value -> materials.set(value, StackedIngredient.fromNetwork(buffer)));
            ItemStack result = buffer.readItem();
            boolean notification = buffer.readBoolean();
            return new WorkbenchContructingRecipe(id, materials, result, notification);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WorkbenchContructingRecipe recipe)
        {
            buffer.writeInt(recipe.materials.size());
            recipe.materials.forEach(ingredient -> ingredient.toNetwork(buffer));
            buffer.writeItem(recipe.result);
            buffer.writeBoolean(recipe.notification);
        }
    }

    public static class Builder implements RecipeBuilder
    {
        private final Item result;
        private final int count;
        private final Function<ItemLike, CriterionTriggerInstance> hasItem;
        private final Function<TagKey<Item>, CriterionTriggerInstance> hasTag;
        private final List<StackedIngredient> materials = new ArrayList<>();
        private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
        private RecipeCategory category = RecipeCategory.MISC;
        private boolean showNotification;

        private Builder(Item result, int count, Function<ItemLike, CriterionTriggerInstance> hasItem, Function<TagKey<Item>, CriterionTriggerInstance> hasTag)
        {
            this.result = result;
            this.count = count;
            this.hasItem = hasItem;
            this.hasTag = hasTag;
        }

        public Builder requiresMaterial(Material<?> material)
        {
            this.materials.add(material.asStackedIngredient());
            return this.unlockedBy("has_" + material.getName(), material.createTrigger(this.hasItem, this.hasTag));
        }

        @Override
        public Builder unlockedBy(String name, CriterionTriggerInstance trigger)
        {
            this.advancement.addCriterion(name, trigger);
            return this;
        }

        @Override
        public Builder group(@Nullable String group)
        {
            return this;
        }

        public Builder category(RecipeCategory category)
        {
            this.category = category;
            return this;
        }

        public Builder showNotification(boolean show)
        {
            this.showNotification = show;
            return this;
        }

        @Override
        public Item getResult()
        {
            return this.result;
        }

        @Override
        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
        {
            this.validate(id);
            this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
            consumer.accept(new Result(id, this.result, this.count, this.materials, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification));
        }

        private void validate(ResourceLocation id)
        {
            if(this.materials.isEmpty())
            {
                throw new IllegalArgumentException("There must be at least one material for workbench crafting recipe %s".formatted(id));
            }
            if(this.advancement.getCriteria().isEmpty())
            {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final List<StackedIngredient> materials;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final boolean notification;

        public Result(ResourceLocation id, Item result, int count, List<StackedIngredient> materials, Advancement.Builder advancement, ResourceLocation advancementId, boolean notification)
        {
            this.id = id;
            this.result = result;
            this.count = count;
            this.materials = materials;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.notification = notification;
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return ModRecipeSerializers.WORKBENCH_RECIPE.get();
        }

        @Override
        public void serializeRecipeData(JsonObject object)
        {
            WorkbenchContructingRecipe.Serializer.toJson(this, object);
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
