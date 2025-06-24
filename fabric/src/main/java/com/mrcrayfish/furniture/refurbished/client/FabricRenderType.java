package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import com.mrcrayfish.furniture.refurbished.image.TextureCache;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.Optional;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class FabricRenderType
{
    private static final Function<ResourceLocation, RenderType> TELEVISION_SCREEN = Util.memoize((id) -> {
        return RenderType.create(Constants.MOD_ID + "_television_screen", 0x200000, false, false, RenderPipelines.SOLID, RenderType.CompositeState.builder()
                .setLightmapState(RenderType.LIGHTMAP)
                .setTextureState(new RenderStateShard.TextureStateShard(id, false))
                .createCompositeState(true));
    });

    private static final Function<ResourceLocation, RenderType> PALETTE_IMAGE = id -> {
        return RenderType.create(Constants.MOD_ID + "_palette_image", 0x200000, false, false, RenderPipelines.CUTOUT, RenderType.CompositeState.builder()
                .setLightmapState(RenderType.LIGHTMAP)
                .setTextureState(new DoorMatTextureStateShard(id))
                .createCompositeState(true));
    };

    public static RenderType televisionScreen(ResourceLocation id)
    {
        return TELEVISION_SCREEN.apply(id);
    }

    public static RenderType createPaletteImage(ResourceLocation id)
    {
        return PALETTE_IMAGE.apply(id);
    }

    private static class DoorMatTextureStateShard extends RenderStateShard.EmptyTextureStateShard
    {
        private final Optional<ResourceLocation> texture;

        public DoorMatTextureStateShard(ResourceLocation id)
        {
            super(() -> {
                AbstractTexture texture = TextureCache.get().getTexture(id);
                texture.setFilter(false, false);
                RenderSystem.setShaderTexture(0, texture.getTextureView());
            }, () -> {});
            this.texture = Optional.of(id);
        }

        @Override
        protected Optional<ResourceLocation> cutoutTexture()
        {
            return this.texture;
        }
    }
}
