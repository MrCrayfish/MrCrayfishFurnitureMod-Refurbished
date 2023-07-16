package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CommonModelProvider
{
    private final Consumer<PreparedBlockState> consumer;

    public CommonModelProvider(Consumer<PreparedBlockState> consumer)
    {
        this.consumer = consumer;
    }

    public void run()
    {
    }

    private ResourceLocation blockTexture(Block block)
    {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        return new ResourceLocation(name.getNamespace(), "block/" + name.getPath());
    }
}
