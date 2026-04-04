package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.framework.api.client.model.renderer.StandaloneModelRenderer;
import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CeilingFanBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.CeilingFanRenderState;
import com.mrcrayfish.furniture.refurbished.core.ModExtraModels;
import com.mrcrayfish.furniture.refurbished.util.reflection.ReflectedField;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
    private static final ReflectedField<BlockEntityRenderState, BlockState> BLOCK_STATE_FIELD = new ReflectedField<>(BlockEntityRenderState.class, "blockState");
    private static final Map<Block, Supplier<BlockStateModelPart>> BLADE_MODEL_MAP = new Object2ObjectOpenHashMap<>();

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
        BlockStateModelPart model = this.getCeilingFanBladeModel(BLOCK_STATE_FIELD.get(renderState));
        StandaloneModelRenderer.submitDraw(collector, model, poseStack, 1, 1, 1, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        // Draws the damage box when entity hitboxes are enabled
        if(Minecraft.getInstance().debugEntries.isCurrentlyEnabled(DebugScreenEntries.ENTITY_HITBOXES))
        {
            // TODO 1.21.11
            //collector.submitCustomGeometry(poseStack, RenderTypes.lines(), (pose, consumer) -> ShapeRenderer.renderShape(pose, consumer, renderState.damageBox, 1, 1, 1, 1));
        }
    }

    private BlockStateModelPart getCeilingFanBladeModel(BlockState state)
    {
        if(state.getBlock() instanceof CeilingFanBlock block)
        {
            Supplier<BlockStateModelPart> supplier = BLADE_MODEL_MAP.get(block);
            if(supplier != null)
            {
                return supplier.get();
            }
        }
        return ModExtraModels.OAK_LIGHT_CEILING_FAN_BLADE.getModel();
    }

    public static void registerFanBlade(Block block, Supplier<BlockStateModelPart> modelSupplier)
    {
        BLADE_MODEL_MAP.putIfAbsent(block, modelSupplier);
    }
}
