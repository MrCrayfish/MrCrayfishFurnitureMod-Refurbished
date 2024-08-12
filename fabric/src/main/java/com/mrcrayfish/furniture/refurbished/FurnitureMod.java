package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.platform.FabricFluidHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public class FurnitureMod implements ModInitializer, DataGeneratorEntrypoint
{
    public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.create(Utils.resource("creative_tab"))
        .icon(() -> new ItemStack(ModBlocks.TABLE_OAK.get()))
        .appendItems(items -> {
            ModCreativeTabs.buildCreativeModeTab(itemLike -> {
                items.add(new ItemStack(itemLike));
            });
        }).build();

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
            if(!player.isCrouching()) {
                if(level.getBlockEntity(pos) instanceof StorageJarBlockEntity storageJar && !storageJar.isEmpty()) {
                    if(!level.isClientSide()) {
                        storageJar.popItem(player.getDirection().getOpposite());
                    }
                    return InteractionResult.SUCCESS;
                }
                else if(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard && !cuttingBoard.isEmpty()) {
                    if(!level.isClientSide()) {
                        cuttingBoard.removeItem();
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FurnitureBlockTagsProvider provider = generator.addProvider(FurnitureBlockTagsProvider::new);
        generator.addProvider(new FurnitureItemTagsProvider(generator, provider));
        FurnitureLootTableProvider.addProviders(generator);
        generator.addProvider(FurnitureRecipeProvider::new);
        generator.addProvider(FurnitureModelProvider::new);
    }
}
