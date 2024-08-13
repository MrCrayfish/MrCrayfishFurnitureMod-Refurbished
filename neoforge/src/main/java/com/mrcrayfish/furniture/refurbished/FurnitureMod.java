package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookTypes;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.data.RegistriesProvider;
import com.mrcrayfish.furniture.refurbished.platform.NeoForgeFluidHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.capability.templates.EmptyFluidHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    public FurnitureMod(IEventBus bus)
    {
        ModRecipeBookTypes.getAllTypes().forEach(holder -> RecipeBookType.create(holder.getConstantName()));
        NeoForgeMod.enableMilkFluid();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onGatherData);
        bus.addListener(this::onRegisterCapabilities);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        FurnitureBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new FurnitureBlockTagsProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new FurnitureItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new FurnitureLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new FurnitureRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, RegistriesProvider.BUILDER, Set.of(Constants.MOD_ID)));
        generator.addProvider(event.includeClient(), new FurnitureModelProvider(output, helper));
    }

    private void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FREEZER.get(), (entity, context) -> {
            return context == Direction.DOWN ? new SidedInvWrapper(entity, Direction.DOWN) : new InvWrapper(entity);
        });

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.STOVE.get(), (entity, context) -> {
            return context == Direction.DOWN ? new SidedInvWrapper(entity.getContainer(), Direction.DOWN) : new InvWrapper(entity);
        });

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.RECYCLE_BIN.get(), (entity, context) -> {
            return context == Direction.DOWN ? new SidedInvWrapper(entity, Direction.DOWN) : new SidedInvWrapper(entity, Direction.UP);
        });

        this.registerFluidHandler(event, ModBlockEntities.BATH.get());
        this.registerFluidHandler(event, ModBlockEntities.KITCHEN_SINK.get());
        this.registerFluidHandler(event, ModBlockEntities.BASIN.get());
        this.registerFluidHandler(event, ModBlockEntities.TOILET.get());
    }

    private <T extends BlockEntity & IFluidContainerBlock> void registerFluidHandler(RegisterCapabilitiesEvent event, BlockEntityType<T> type)
    {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type, (entity, context) -> {
            FluidContainer container = entity.getFluidContainer();
            return container != null ? ((NeoForgeFluidHelper.NeoForgeFluidContainer) container).getTank() : EmptyFluidHandler.INSTANCE;
        });
    }
}