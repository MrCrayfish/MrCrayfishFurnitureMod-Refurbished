package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.furniture.refurbished.block.StorageJarBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.platform.FabricFluidHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("UnstableApiUsage")
public class FurnitureMod implements ModInitializer, DataGeneratorEntrypoint
{
    @Override
    public void onInitialize()
    {
        FrameworkSetup.run();
        Bootstrap.init();

        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> {
            return direction != Direction.UP ? ((FabricFluidHelper.FabricFluidContainer) sink.getFluidContainer()).getTank() : null;
        }, ModBlockEntities.KITCHEN_SINK.get());

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack heldItem = player.getItemInHand(hand);
            if(!world.isClientSide() && heldItem.is(ModItems.WRENCH.get())) {
                heldItem.use(world, player, hand);
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            BlockState state = level.getBlockState(pos);
            if(player.isCreative() && !player.isCrouching()) {
                if(state.getBlock() instanceof StorageJarBlock storageJar) {
                    storageJar.attack(state, level, pos, player);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        FurnitureBlockTagsProvider provider = pack.addProvider(FurnitureBlockTagsProvider::new);
        pack.addProvider((output, lookupProvider) -> new FurnitureItemTagsProvider(output, lookupProvider, provider));
        FurnitureLootTableProvider.addProviders(pack);
        pack.addProvider(FurnitureRecipeProvider::new);
        pack.addProvider(FurnitureModelProvider::new);
    }
}
