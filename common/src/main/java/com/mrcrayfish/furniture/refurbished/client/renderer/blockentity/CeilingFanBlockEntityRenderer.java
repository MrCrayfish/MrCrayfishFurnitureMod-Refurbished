package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CeilingFanBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ExtraModels;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class CeilingFanBlockEntityRenderer implements BlockEntityRenderer<CeilingFanBlockEntity>
{
    private static final Map<Block, Supplier<BakedModel>> BLADE_MODEL_MAP = new Object2ObjectOpenHashMap<>();

    private final EntityRenderDispatcher entityRenderer;

    public CeilingFanBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.entityRenderer = context.getEntityRenderer();
    }

    @Override
    public void render(CeilingFanBlockEntity ceilingFan, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        Direction direction = ceilingFan.getDirection();
        poseStack.mulPose(direction.getRotation());
        poseStack.mulPose(Axis.YP.rotationDegrees(ceilingFan.getRotation(partialTick)));
        poseStack.translate(-0.5, -0.5, -0.5);
        BakedModel model = this.getCeilingFanBladeModel(ceilingFan.getBlockState());
        VertexConsumer consumer = source.getBuffer(RenderType.cutout());
        ClientServices.PLATFORM.drawBakedModel(model, poseStack, consumer, light, overlay);
        poseStack.popPose();
        ElectricBlockEntityRenderer.drawNodeAndConnections(ceilingFan);

        if(this.entityRenderer.shouldRenderHitBoxes())
        {
            LevelRenderer.renderLineBox(poseStack, source.getBuffer(RenderType.lines()), ceilingFan.getDamageBox(direction), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private BakedModel getCeilingFanBladeModel(BlockState state)
    {
        if(state.getBlock() instanceof CeilingFanBlock block)
        {
            Supplier<BakedModel> supplier = BLADE_MODEL_MAP.get(block);
            if(supplier != null)
            {
                return supplier.get();
            }
        }
        return ExtraModels.OAK_LIGHT_CEILING_FAN_BLADE.getModel();
    }

    public static void registerFanBlade(Block block, Supplier<BakedModel> modelSupplier)
    {
        BLADE_MODEL_MAP.putIfAbsent(block, modelSupplier);
    }
}
