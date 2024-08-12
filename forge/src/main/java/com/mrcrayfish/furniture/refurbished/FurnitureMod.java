package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.client.ClientBootstrap;
import com.mrcrayfish.furniture.refurbished.client.ClientFurnitureMod;
import com.mrcrayfish.furniture.refurbished.client.ForgeClientEvents;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(Constants.MOD_ID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.TABLE_OAK.get());
        }
    };

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
        ExistingFileHelper helper = event.getExistingFileHelper();
        FurnitureBlockTagsProvider blockTagsProvider = new FurnitureBlockTagsProvider(generator, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new FurnitureItemTagsProvider(generator, blockTagsProvider, helper));
        generator.addProvider(event.includeServer(), new FurnitureLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new FurnitureRecipeProvider(generator));
        generator.addProvider(event.includeClient(), new FurnitureModelProvider(generator, helper));
    }
}