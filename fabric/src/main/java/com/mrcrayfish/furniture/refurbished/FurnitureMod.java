package com.mrcrayfish.furniture.refurbished;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.base.Suppliers;
import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.furniture.refurbished.asm.Loader;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.FurnitureBlockTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureItemTagsProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureLootTableProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureModelProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRecipeProvider;
import com.mrcrayfish.furniture.refurbished.data.FurnitureRegistryProvider;
import com.mrcrayfish.furniture.refurbished.platform.FabricFluidHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public class FurnitureMod implements ModInitializer, DataGeneratorEntrypoint
{
    public static final Supplier<RecipeBookType> RECIPE_BOOK_TYPE_FREEZER = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookType.class, Loader.RECIPE_BOOK_TYPE_FREEZER));

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
        FabricDataGenerator.Pack pack = generator.createPack();
        FurnitureBlockTagsProvider provider = pack.addProvider(FurnitureBlockTagsProvider::new);
        pack.addProvider((output, lookupProvider) -> new FurnitureItemTagsProvider(output, lookupProvider, provider));
        FurnitureLootTableProvider.addProviders(pack);
        pack.addProvider(FurnitureRecipeProvider::new);
        pack.addProvider(FurnitureModelProvider::new);
        pack.addProvider(FurnitureRegistryProvider::new);
    }
}
