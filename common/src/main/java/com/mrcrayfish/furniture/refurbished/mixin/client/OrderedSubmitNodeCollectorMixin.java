package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricitySubmitNodeCollector;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OrderedSubmitNodeCollector.class)
public interface OrderedSubmitNodeCollectorMixin extends ElectricitySubmitNodeCollector
{
    @Unique
    default void refurbished_furniture$submitElectricity(PoseStack poseStack, RenderType renderType, SubmitNodeCollector.CustomGeometryRenderer renderer) {
        // No implementation
    }
}
