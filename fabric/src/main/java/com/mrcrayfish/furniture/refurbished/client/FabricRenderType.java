package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.image.TextureCache;
import net.minecraft.Util;
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
public class FabricRenderType extends RenderType
{
    private static final Function<ResourceLocation, RenderType> TELEVISION_SCREEN = Util.memoize((id) -> {
        return RenderType.create(Constants.MOD_ID + "_television_screen", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, false, false, RenderType.CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(RENDERTYPE_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(id, TriState.FALSE, false))
                .createCompositeState(true));
    });

    private static final Function<ResourceLocation, RenderType> PALETTE_IMAGE = id -> {
        return RenderType.create(Constants.MOD_ID + "_palette_image", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, false, false, RenderType.CompositeState.builder()
                .setLightmapState(RenderType.LIGHTMAP)
                .setShaderState(RENDERTYPE_CUTOUT_SHADER)
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
                texture.setFilter(TriState.FALSE, false);
                RenderSystem.setShaderTexture(0, texture.getId());
            }, () -> {});
            this.texture = Optional.of(id);
        }

        @Override
        protected Optional<ResourceLocation> cutoutTexture()
        {
            return this.texture;
        }

    }

    // Unused. Don't call
    public FabricRenderType(String id, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean b1, boolean b2, Runnable run1, Runnable run2)
    {
        super(id, format, mode, bufferSize, b1, b2, run1, run2);
        throw new UnsupportedOperationException();
    }
}
