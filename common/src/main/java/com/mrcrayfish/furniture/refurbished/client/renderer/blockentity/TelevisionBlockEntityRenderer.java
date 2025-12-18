package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.TelevisionBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.CustomSheets;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.TelevisionRenderState;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlockEntityRenderer implements BlockEntityRenderer<TelevisionBlockEntity, TelevisionRenderState>
{
    private final MaterialSet materials;

    public TelevisionBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.materials = context.materials();
    }

    @Override
    public TelevisionRenderState createRenderState()
    {
        return new TelevisionRenderState();
    }

    @Override
    public void extractRenderState(TelevisionBlockEntity entity, TelevisionRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.powered = entity.isNodePowered();
        renderState.currentChannel = entity.getCurrentChannel().id();
        renderState.direction = entity.getBlockState().getValueOrElse(TelevisionBlock.DIRECTION, Direction.NORTH);
    }

    @Override
    public void submit(TelevisionRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        if(renderState.powered)
        {
            stack.pushPose();
            stack.translate(0.5, 0, 0.5);
            stack.mulPose(Axis.YN.rotation(Mth.HALF_PI * renderState.direction.get2DDataValue()));
            stack.translate(-0.5, 0, -0.345);
            Material material = CustomSheets.getTelevisionChannelMaterial(renderState.currentChannel);
            RenderType renderType = material.renderType(ClientServices.PLATFORM::getTelevisionScreenRenderType);
            TextureAtlasSprite sprite = this.materials.get(material);
            collector.submitCustomGeometry(stack, renderType, (pose, consumer) -> {
                float offset = 0.003125F;
                Vec3i normal = renderState.direction.getUnitVec3i();
                consumer.addVertex(pose, 0.75F + offset, 0.625F + offset, 0).setColor(255, 255, 255, 255).setUv(sprite.getU(0), sprite.getV(0)).setLight(0xF000F0).setNormal(normal.getX(), normal.getY(), normal.getZ());
                consumer.addVertex(pose, 0.75F + offset, 0.1875F - offset, 0).setColor(255, 255, 255, 255).setUv(sprite.getU(0), sprite.getV(1)).setLight(0xF000F0).setNormal(normal.getX(), normal.getY(), normal.getZ());
                consumer.addVertex(pose, 0.25F - offset, 0.1875F - offset, 0).setColor(255, 255, 255, 255).setUv(sprite.getU(1), sprite.getV(1)).setLight(0xF000F0).setNormal(normal.getX(), normal.getY(), normal.getZ());
                consumer.addVertex(pose, 0.25F - offset, 0.625F + offset, 0).setColor(255, 255, 255, 255).setUv(sprite.getU(1), sprite.getV(0)).setLight(0xF000F0).setNormal(normal.getX(), normal.getY(), normal.getZ());
            });
            stack.popPose();
        }
    }
}
