package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityPhaseExecutor;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityPhaseGetter;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.phase.FeatureRenderPhase;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mixin(FeatureRenderDispatcher.PreparedFrame.class)
public abstract class PreparedFrameMixin implements ElectricityPhaseExecutor
{
    @Shadow
    private @Nullable FeatureFrameContext context;

    @Shadow
    private @Nullable SubmitNodeStorage submitNodeStorage;

    @Shadow
    protected abstract void executePhase(FeatureRenderPhase<?> phase, FeatureFrameContext context);

    @Shadow
    @Final
    private Map<FeatureRenderPhase<?>, List<?>> groupsByPhase;

    @Override
    public boolean refurbished_furniture$hasElectricity()
    {
        SubmitNodeStorage storage = Objects.requireNonNull(this.submitNodeStorage);
        for(SubmitNodeCollection collection : storage.getSubmitsPerOrder().values())
        {
            var electricityPhase = ((ElectricityPhaseGetter) collection).refurbished_furniture$getElectricityPhase();
            if(!this.groupsByPhase.getOrDefault(electricityPhase, List.of()).isEmpty())
            {
                return true;
            }
        }
        return false;
    }

    @Unique
    @Override
    public void refurbished_furniture$executeElectricity()
    {
        FeatureFrameContext context = Objects.requireNonNull(this.context);
        SubmitNodeStorage storage = Objects.requireNonNull(this.submitNodeStorage);
        for(SubmitNodeCollection collection : storage.getSubmitsPerOrder().values())
        {
            var electricityPhase = ((ElectricityPhaseGetter) collection).refurbished_furniture$getElectricityPhase();
            this.executePhase(electricityPhase, context);
        }
    }
}
