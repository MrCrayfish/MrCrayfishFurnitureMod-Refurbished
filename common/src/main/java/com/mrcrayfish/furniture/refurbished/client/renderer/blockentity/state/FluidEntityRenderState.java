package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.client.FluidSprites;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class FluidEntityRenderState extends BlockEntityRenderState
{
    public FluidSprites fluidSprites;
    public long fluidAmount;
    public long fluidCapacity;
    public int waterTintAtPos = 0xFFFFFF;
    public AABB box;

    public boolean valid()
    {
        return this.fluidSprites != null && this.fluidAmount > 0 && this.fluidCapacity > 0 && this.box != null;
    }

    public static void extract(FluidEntityRenderState state, IFluidContainerBlock block, @Nullable Level level, BlockPos pos)
    {
        FluidContainer container = block.getFluidContainer();
        if(container != null && !container.isEmpty())
        {
            Fluid fluid = container.getStoredFluid();
            BlockAndTintGetter tintGetter = level instanceof ClientLevel clientLevel ? clientLevel : BlockAndTintGetter.EMPTY;
            state.fluidSprites = ClientServices.PLATFORM.getFluidSprites(fluid.defaultFluidState());
            state.fluidCapacity = container.getCapacity();
            state.fluidAmount = container.getStoredAmount();
            state.waterTintAtPos = BiomeColors.getAverageWaterColor(tintGetter, pos);
        }
    }
}
