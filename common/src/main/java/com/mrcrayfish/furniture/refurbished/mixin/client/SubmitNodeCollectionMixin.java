package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityPhaseGetter;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricitySubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.phase.FeatureRenderPhase;
import net.minecraft.client.renderer.feature.phase.SimpleFeatureRenderPhase;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin implements ElectricityPhaseGetter, ElectricitySubmitNodeCollector
{
    @Shadow
    @Final
    @Mutable
    private List<FeatureRenderPhase<?>> allPhases;

    @Unique
    public final SimpleFeatureRenderPhase refurbished_furniture$electricity = new SimpleFeatureRenderPhase();

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void refurbished_furniture$init(CallbackInfo ci)
    {
        List<FeatureRenderPhase<?>> newPhases = new ArrayList<>(this.allPhases);
        newPhases.add(this.refurbished_furniture$electricity);
        this.allPhases = List.copyOf(newPhases);
    }

    @Unique
    @Override
    public SimpleFeatureRenderPhase refurbished_furniture$getElectricityPhase()
    {
        return this.refurbished_furniture$electricity;
    }

    @Override
    public void refurbished_furniture$submitElectricity(PoseStack poseStack, RenderType renderType, SubmitNodeCollector.CustomGeometryRenderer renderer)
    {
        CustomFeatureRenderer.Submit submit = new CustomFeatureRenderer.Submit(poseStack.last().copy(), renderType, renderer);
        this.refurbished_furniture$electricity.submit(submit);
    }
}
