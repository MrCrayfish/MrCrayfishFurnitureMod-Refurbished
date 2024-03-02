package com.mrcrayfish.furniture.refurbished.platform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class FabricItemHelper implements IItemHelper
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type)
    {
        return Optional.ofNullable(FuelRegistry.INSTANCE.get(stack.getItem())).orElse(0);
    }

    private Optional<CompoundTag> parseTag(JsonElement element)
    {
        try
        {
            if(element.isJsonObject())
            {
                return Optional.of(TagParser.parseTag(GSON.toJson(element)));
            }
            return Optional.of(TagParser.parseTag(GsonHelper.convertToString(element, "nbt")));
        }
        catch(CommandSyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }
}
