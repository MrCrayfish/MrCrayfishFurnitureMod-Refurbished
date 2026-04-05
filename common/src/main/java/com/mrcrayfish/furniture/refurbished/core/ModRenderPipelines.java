package com.mrcrayfish.furniture.refurbished.core;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;

@RegistryContainer(clientOnly = true)
public class ModRenderPipelines
{
    public static final RenderPipeline ELECTRICITY = RenderPipeline.builder(ClientServices.PLATFORM.getMatricesProjectionSnippet())
            .withLocation(Utils.id("pipeline/electricity"))
            .withVertexShader("core/position_tex_color")
            .withFragmentShader("core/position_tex_color")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withSampler("Sampler0")
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .build();

    public static final RenderPipeline ELECTRICITY_BLIT = RenderPipeline.builder()
            .withLocation(Utils.id("pipeline/electricity_blit"))
            .withVertexShader("core/screenquad")
            .withFragmentShader("core/blit_screen")
            .withSampler("InSampler")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
            .build();

    public static final RenderPipeline POWERABLE_AREA = RenderPipeline.builder(ClientServices.PLATFORM.getMatricesProjectionSnippet())
            .withLocation(Utils.id("pipeline/powerable_area"))
            .withVertexShader("core/rendertype_world_border")
            .withFragmentShader("core/rendertype_world_border")
            .withSampler("Sampler0")
            .withColorTargetState(new ColorTargetState(BlendFunction.OVERLAY))
            .withCull(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
            .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0F, -3.0F))
            .build();
}
