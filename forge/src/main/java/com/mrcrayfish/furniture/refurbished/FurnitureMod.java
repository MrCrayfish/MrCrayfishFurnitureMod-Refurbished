package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.block.StorageJarBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.client.ClientBootstrap;
import com.mrcrayfish.furniture.refurbished.client.ForgeClientEvents;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.platform.ForgeFluidHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    private static final ResourceLocation FLUID_CONTAINER_ID = Utils.resource("fluid_container");

    public FurnitureMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(ForgeClientEvents::onRegisterRenderers);
            bus.addListener(ForgeClientEvents::onRegisterAdditional);
            bus.addListener(ForgeClientEvents::onRegisterParticleProviders);
            bus.addListener(ForgeClientEvents::onRegisterBlockColors);
            bus.addListener(ForgeClientEvents::onRegisterItemColors);
            bus.addListener(ForgeClientEvents::onRegisterGuiOverlays);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onKeyTriggered);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onRenderLevelStage);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onDrawHighlight);
        });
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickBlock);
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, this::onAttachCapability);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientBootstrap::init);
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        FurnitureBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new FurnitureBlockTagsProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new FurnitureItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new FurnitureLootTableProvider(output));
        generator.addProvider(event.includeServer(), new FurnitureRecipeProvider(output));
        generator.addProvider(event.includeClient(), new FurnitureModelProvider(output, helper));
    }

    private void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getItemStack().is(ModItems.WRENCH.get()))
        {
            event.setCanceled(true);
        }
    }

    private void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();
        
        if(!player.isCreative())
            return;

        if(player.isCrouching())
            return;

        if(state.getBlock() instanceof StorageJarBlock storageJar)
        {
            storageJar.attack(state, level, pos, event.getEntity());
            event.setCanceled(true);
        }
    }

    private void onAttachCapability(AttachCapabilitiesEvent<BlockEntity> event)
    {
        BlockEntity entity = event.getObject();
        if(entity instanceof IFluidContainerBlock block)
        {
            event.addCapability(FLUID_CONTAINER_ID, new ICapabilityProvider()
            {
                final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> {
                    FluidContainer container = block.getFluidContainer();
                    if(container == null) {
                        return EmptyFluidHandler.INSTANCE;
                    }
                    return ((ForgeFluidHelper.ForgeFluidContainer) container).getTank();
                });

                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
                {
                    return cap == ForgeCapabilities.FLUID_HANDLER ? this.holder.cast() : LazyOptional.empty();
                }
            });
        }
    }
}