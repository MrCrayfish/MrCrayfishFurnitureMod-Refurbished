package com.mrcrayfish.furniture.refurbished.core;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.renderer.BindGroupLayouts;

@RegistryContainer(clientOnly = true)
public class ModRenderPipelines
{
    public static final RenderPipeline ELECTRICITY = RenderPipeline.builder(ClientServices.PLATFORM.getMatricesProjectionSnippet())
            .withLocation(Utils.id("pipeline/electricity"))
            .withVertexShader("core/position_tex_color")
            .withFragmentShader("core/position_tex_color")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .build();

    public static final RenderPipeline ELECTRICITY_BLIT = RenderPipeline.builder()
            .withLocation(Utils.id("pipeline/electricity_blit"))
            .withVertexShader("core/screenquad")
            .withFragmentShader("core/blit_screen")
            .withBindGroupLayout(BindGroupLayouts.IN_SAMPLER)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
            .build();

    public static final RenderPipeline POWERABLE_AREA = RenderPipeline.builder(ClientServices.PLATFORM.getGlobalSnippet())
            .withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
            .withLocation(Utils.id("pipeline/powerable_area"))
            .withVertexShader("core/rendertype_world_border")
            .withFragmentShader("core/rendertype_world_border")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withColorTargetState(new ColorTargetState(BlendFunction.OVERLAY))
            .withCull(false)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, true, -3.0F, -3.0F))
            .build();
}
