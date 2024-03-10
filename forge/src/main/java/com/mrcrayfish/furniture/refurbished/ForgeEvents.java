package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.platform.ForgeFluidHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ForgeEvents
{
    private static final ResourceLocation FLUID_CONTAINER_ID = Utils.resource("fluid_container");

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getItemStack().is(ModItems.WRENCH.get()))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();

        if(player.isCrouching())
            return;

        if(level.getBlockEntity(pos) instanceof StorageJarBlockEntity storageJar && !storageJar.isEmpty())
        {
            if(!level.isClientSide())
            {
                storageJar.popItem(player.getDirection().getOpposite());
            }
            event.setCanceled(true);
        }
        else if(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard && !cuttingBoard.isEmpty())
        {
            if(!level.isClientSide())
            {
                cuttingBoard.removeItem();
            }
            event.setCanceled(true);
        }
    }

    static void onAttachCapability(AttachCapabilitiesEvent<BlockEntity> event)
    {
        BlockEntity entity = event.getObject();
        if(entity instanceof IFluidContainerBlock block)
        {
            event.addCapability(FLUID_CONTAINER_ID, new FluidContainerProvider(block));
        }
    }

    private static class FluidContainerProvider implements ICapabilityProvider
    {
        final LazyOptional<IFluidHandler> holder;

        public FluidContainerProvider(IFluidContainerBlock block)
        {
            this.holder = LazyOptional.of(() -> {
                FluidContainer container = block.getFluidContainer();
                if(container == null) {
                    return EmptyFluidHandler.INSTANCE;
                }
                return ((ForgeFluidHelper.ForgeFluidContainer) container).getTank();
            });
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
        {
            return cap == ForgeCapabilities.FLUID_HANDLER ? this.holder.cast() : LazyOptional.empty();
        }
    }
}
