package com.mrcrayfish.furniture.refurbished.util;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class Utils
{
    /**
     * Utility to create a ResourceLocation specific to this mod
     *
     * @param name the name of the resource. can be a path
     * @return a resource location instance
     */
    public static ResourceLocation resource(String name)
    {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    /**
     * Creates a translatable Component specific to this mod. The translation key
     * uses a common format of a category, followed by the mod id, and then a path.
     *
     * @param category the category of the translation
     * @param path     the path of the translation
     * @return A translatable component
     */
    public static MutableComponent translation(String category, String path, Object ... params)
    {
        return Component.translatable(String.format("%s.%s.%s", category, Constants.MOD_ID, path), params);
    }

    /**
     * A Fisher–Yates shuffle implementation that uses Minecraft's RandomSource.
     *
     * @param list   a list
     * @param random a random source instance
     * @param <T>    any object type
     */
    public static <T> void shuffle(List<T> list, RandomSource random)
    {
        for(int i = list.size() - 1; i > 0; i--)
        {
            int randIndex = random.nextIntBetweenInclusive(0, i);
            T temp = list.get(i);
            list.set(i, list.get(randIndex));
            list.set(randIndex, temp);
        }
    }
}
