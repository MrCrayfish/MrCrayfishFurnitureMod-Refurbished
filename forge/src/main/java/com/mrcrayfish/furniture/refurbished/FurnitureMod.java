package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.client.ClientBootstrap;
import com.mrcrayfish.furniture.refurbished.client.ClientFurnitureMod;
import com.mrcrayfish.furniture.refurbished.client.ForgeClientEvents;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import com.mrcrayfish.furniture.refurbished.data.RegistriesProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    public FurnitureMod()
    {
        ModRecipeBookTypes.getAllTypes().forEach(holder -> RecipeBookType.create(holder.getConstantName()));
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientFurnitureMod.init();
            bus.addListener(ForgeClientEvents::onRegisterRenderers);
            bus.addListener(ForgeClientEvents::onRegisterAdditional);
            bus.addListener(ForgeClientEvents::onRegisterParticleProviders);
            bus.addListener(ForgeClientEvents::onRegisterBlockColors);
            bus.addListener(ForgeClientEvents::onRegisterItemColors);
            bus.addListener(ForgeClientEvents::onRegisterGuiOverlays);
            bus.addListener(ForgeClientEvents::onRegisterRecipeCategories);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onKeyTriggered);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onRenderLevelStage);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEvents::onDrawHighlight);
        });
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, ForgeEvents::onAttachCapability);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientFurnitureMod::init);
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
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, RegistriesProvider.BUILDER, Set.of(Constants.MOD_ID)));
        generator.addProvider(event.includeClient(), new FurnitureModelProvider(output, helper));
    }
}