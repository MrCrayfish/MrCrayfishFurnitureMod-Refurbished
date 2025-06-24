package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface RenderTypeRegister
{
    void apply(Block block, ChunkSectionLayer layer);
}
