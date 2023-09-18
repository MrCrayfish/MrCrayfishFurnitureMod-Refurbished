package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.block.StorageJarBlock;
import com.mrcrayfish.furniture.refurbished.client.ClientBootstrap;
import com.mrcrayfish.furniture.refurbished.client.ForgeClientEvents;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.inventory.RecyclingBinMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    public FurnitureMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(ForgeClientEvents::onRegisterRenderers);
            bus.addListener(ForgeClientEvents::onRegisterAdditional);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onKeyTriggered);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onRenderLevelStage);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onDrawHighlight);
        });
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onDatapackReload);
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

    private void onDatapackReload(OnDatapackSyncEvent event)
    {
        if(event.getPlayer() == null)
        {
            RecyclingBinMenu.clearRecipeLookup();
        }
    }
}