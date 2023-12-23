package com.mrcrayfish.furniture.refurbished.util;

import com.mrcrayfish.furniture.refurbished.Constants;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

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

    /**
     * Utility method to convert pixel units into world units. One pixel equals 0.0625 world units.
     *
     * @param value the input pixel unit
     * @return a world unit representation of the pixel input units.
     */
    public static double pixels(double value)
    {
        return value * 0.0625;
    }

    /**
     * Counts all the items in the given containers from the direction they are being accessed.
     *
     * @param skipDamaged if items that are damaged should be skipped in the count
     * @param containers  an array of container and direction pairs. The direction is the side the container is being accessed. The direction can be null.
     * @return a map containing items and their counts
     */
    @SafeVarargs
    public static Map<Item, Integer> countItems(boolean skipDamaged, Pair<Direction, Container>... containers)
    {
        Map<Item, Integer> map = new Object2IntOpenCustomHashMap<>(ItemHash.INSTANCE);
        for(Pair<Direction, Container> pair : containers)
        {
            Direction direction = pair.first();
            if(direction != null && pair.second() instanceof WorldlyContainer container)
            {
                int[] slots = container.getSlotsForFace(direction);
                for(int slot : slots)
                {
                    ItemStack stack = container.getItem(slot);
                    if(stack.isEmpty())
                        continue;

                    if(skipDamaged && stack.isDamaged())
                        continue;

                    if(!container.canTakeItem(container, slot, stack))
                        continue;

                    if(!container.canTakeItemThroughFace(slot, stack, direction))
                        continue;

                    map.merge(stack.getItem(), stack.getCount(), (item, count) -> {
                        return count + stack.getCount();
                    });
                }
            }
            else
            {
                Container container = pair.second();
                for(int i = 0; i < container.getContainerSize(); i++)
                {
                    ItemStack stack = container.getItem(i);
                    if(stack.isEmpty())
                        continue;

                    if(skipDamaged && stack.isDamaged())
                        continue;

                    if(!container.canTakeItem(container, i, stack))
                        continue;

                    map.merge(stack.getItem(), stack.getCount(), (item, count) -> {
                        return count + stack.getCount();
                    });
                }
            }
        }
        return map;
    }
}
