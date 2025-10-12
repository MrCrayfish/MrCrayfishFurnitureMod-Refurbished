package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.framework.api.client.model.renderer.StandaloneModelRenderer;
import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CeilingFanBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.CeilingFanRenderState;
import com.mrcrayfish.furniture.refurbished.core.ModExtraModels;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class CeilingFanBlockEntityRenderer implements BlockEntityRenderer<CeilingFanBlockEntity, CeilingFanRenderState>
{
    private static final Map<Block, Supplier<BlockModelPart>> BLADE_MODEL_MAP = new Object2ObjectOpenHashMap<>();

    private final EntityRenderDispatcher entityRenderer;

    public CeilingFanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.entityRenderer = context.entityRenderer();
    }

    @Override
    public CeilingFanRenderState createRenderState()
    {
        return new CeilingFanRenderState();
    }

    @Override
    public void extractRenderState(CeilingFanBlockEntity entity, CeilingFanRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        // TODO 1.21.10 test
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.direction = entity.getDirection();
        renderState.rotation = entity.getRotation(partialTick);
        renderState.damageBox = entity.getDamageBox(entity.getDirection());
    }

    @Override
    public void submit(CeilingFanRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(renderState.direction.getRotation());
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
        poseStack.translate(-0.5, -0.5, -0.5);
        BlockModelPart model = this.getCeilingFanBladeModel(renderState.blockState);
        StandaloneModelRenderer.submitDraw(collector, model, poseStack, 1, 1, 1, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        // TODO 1.21.10 restore
        //ElectricBlockEntityRenderer.drawNodeAndConnections(ceilingFan);

        // Draws the damage box when entity hitboxes are enabled
        if(Minecraft.getInstance().debugEntries.isCurrentlyEnabled(DebugScreenEntries.ENTITY_HITBOXES))
        {
            collector.submitCustomGeometry(poseStack, RenderType.lines(), (pose, consumer) -> ShapeRenderer.renderLineBox(pose, consumer, renderState.damageBox, 1, 1, 1, 1));
        }
    }

    private BlockModelPart getCeilingFanBladeModel(BlockState state)
    {
        if(state.getBlock() instanceof CeilingFanBlock block)
        {
            Supplier<BlockModelPart> supplier = BLADE_MODEL_MAP.get(block);
            if(supplier != null)
            {
                return supplier.get();
            }
        }
        return ModExtraModels.OAK_LIGHT_CEILING_FAN_BLADE.getModel();
    }

    public static void registerFanBlade(Block block, Supplier<BlockModelPart> modelSupplier)
    {
        BLADE_MODEL_MAP.putIfAbsent(block, modelSupplier);
    }
}
