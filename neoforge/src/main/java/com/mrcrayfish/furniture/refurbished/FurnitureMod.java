package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.datagen.FrameworkModelProvider;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.data.CommonBlockStatesGenerator;
import com.mrcrayfish.furniture.refurbished.data.CommonBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.CommonItemModelsGenerator;
import com.mrcrayfish.furniture.refurbished.data.CommonItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.CommonLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.CommonRecipeProvider;
import com.mrcrayfish.furniture.refurbished.data.RegistriesProvider;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWorkbench;
import com.mrcrayfish.furniture.refurbished.platform.NeoForgeFluidHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.fluids.capability.templates.EmptyFluidHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class FurnitureMod
{
    public FurnitureMod(IEventBus bus)
    {
        NeoForgeMod.enableMilkFluid();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onGatherData);
        bus.addListener(this::onRegisterCapabilities);
        NeoForge.EVENT_BUS.addListener(this::onDatapackSync);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onGatherData(GatherDataEvent.Client event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        CommonBlockTagsProvider blockTagsProvider = event.createProvider(CommonBlockTagsProvider::new);
        event.addProvider(new CommonItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter()));
        event.addProvider(new DatapackBuiltinEntriesProvider(output, lookupProvider, RegistriesProvider.BUILDER, Set.of(Constants.MOD_ID)));
        event.addProvider(new FrameworkModelProvider(output, CommonBlockStatesGenerator::new, CommonItemModelsGenerator::new));
        event.createProvider(CommonLootTableProvider::new);
        event.createProvider(CommonRecipeProvider.Runner::new);
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

    private void onDatapackSync(OnDatapackSyncEvent event)
    {
        RecipeManager manager = event.getPlayerList().getServer().getRecipeManager();
        List<RecipeHolder<WorkbenchContructingRecipe>> recipes = List.copyOf(manager.recipeMap().byType(ModRecipeTypes.WORKBENCH_CONSTRUCTING.get()));
        event.getRelevantPlayers().forEach(player -> {
            Network.getPlay().sendToPlayer(() -> player, new MessageWorkbench.SyncRecipes(recipes));
        });
    }
}