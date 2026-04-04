package com.mrcrayfish.furniture.refurbished.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class DoorMatCloneRecipe extends CustomRecipe
{
    public static final MapCodec<DoorMatCloneRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        Ingredient.CODEC.fieldOf("material").forGetter(o -> o.material),
        ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result)
    ).apply(builder, DoorMatCloneRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DoorMatCloneRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, o -> o.material,
        ItemStackTemplate.STREAM_CODEC, o -> o.result,
        DoorMatCloneRecipe::new
    );

    private final Ingredient material;
    private final ItemStackTemplate result;

    public DoorMatCloneRecipe(Ingredient material, ItemStackTemplate result)
    {
        this.material = material;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level)
    {
        if(input.ingredientCount() < 2)
            return false;

        boolean foundSource = false;
        boolean foundMaterial = false;
        for(int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if(stack.isEmpty())
                continue;

            if(this.isSource(stack))
            {
                if(foundSource)
                    return false;
                foundSource = true;
                continue;
            }

            if(!this.material.test(stack))
                return false;

            foundMaterial = true;
        }
        return foundSource && foundMaterial;
    }

    @Override
    public ItemStack assemble(CraftingInput input)
    {
        if(input.ingredientCount() < 2)
            return ItemStack.EMPTY;

        ItemStack source = ItemStack.EMPTY;
        int outputCount = 0;
        for(int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if(stack.isEmpty())
                continue;

            if(this.isSource(stack))
            {
                if(!source.isEmpty())
                    return ItemStack.EMPTY;
                source = stack;
                outputCount++;
                continue;
            }

            if(!this.material.test(stack))
                return ItemStack.EMPTY;

            outputCount++;
        }

        ItemStack result = this.result.create();
        result.setCount(outputCount);
        result.set(ModDataComponents.PALETTE_IMAGE.get(), source.get(ModDataComponents.PALETTE_IMAGE.get()));
        return result;
    }

    @Override
    public RecipeSerializer<DoorMatCloneRecipe> getSerializer()
    {
        return ModRecipeSerializers.DOOR_MAT_COPY_RECIPE.get();
    }

    private boolean isSource(ItemStack stack)
    {
        return stack.has(ModDataComponents.PALETTE_IMAGE.get());
    }
}
