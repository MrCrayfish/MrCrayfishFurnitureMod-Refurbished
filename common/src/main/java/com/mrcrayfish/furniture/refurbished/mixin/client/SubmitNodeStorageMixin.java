package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricitySubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SubmitNodeStorage.class)
public abstract class SubmitNodeStorageMixin implements ElectricitySubmitNodeCollector
{
    @Shadow
    public abstract SubmitNodeCollection order(int order);

    @Override
    public void refurbished_furniture$submitElectricity(PoseStack poseStack, RenderType renderType, SubmitNodeCollector.CustomGeometryRenderer renderer)
    {
        ((ElectricitySubmitNodeCollector) this.order(0)).refurbished_furniture$submitElectricity(poseStack, renderType, renderer);
    }
}
