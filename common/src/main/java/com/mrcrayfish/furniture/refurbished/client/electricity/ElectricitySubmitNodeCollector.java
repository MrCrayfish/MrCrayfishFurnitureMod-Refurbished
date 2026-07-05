package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;

public interface ElectricitySubmitNodeCollector
{
    void refurbished_furniture$submitElectricity(PoseStack poseStack, RenderType renderType, SubmitNodeCollector.CustomGeometryRenderer renderer);
}
