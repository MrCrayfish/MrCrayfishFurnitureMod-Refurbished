package com.mrcrayfish.furniture.refurbished.core;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;

@RegistryContainer(clientOnly = true)
public class ModRenderPipelines
{
    public static final RenderPipeline ELECTRICITY = RenderPipeline.builder(ClientServices.PLATFORM.getMatricesProjectionSnippet())
            .withLocation(Utils.resource("pipeline/electricity"))
            .withVertexShader("core/position_tex_color")
            .withFragmentShader("core/position_tex_color")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withSampler("Sampler0")
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .build();

    public static final RenderPipeline ELECTRICITY_BLIT = RenderPipeline.builder()
            .withLocation(Utils.resource("pipeline/electricity_blit"))
            .withVertexShader("core/screenquad")
            .withFragmentShader("core/blit_screen")
            .withSampler("InSampler")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthWrite(false)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
            .build();

    public static final RenderPipeline POWERABLE_AREA = RenderPipeline.builder(ClientServices.PLATFORM.getMatricesProjectionSnippet())
            .withLocation(Utils.resource("pipeline/powerable_area"))
            .withVertexShader("core/rendertype_world_border")
            .withFragmentShader("core/rendertype_world_border")
            .withSampler("Sampler0")
            .withBlend(BlendFunction.OVERLAY)
            .withCull(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
            .withDepthBias(-3.0F, -3.0F)
            .build();
}
