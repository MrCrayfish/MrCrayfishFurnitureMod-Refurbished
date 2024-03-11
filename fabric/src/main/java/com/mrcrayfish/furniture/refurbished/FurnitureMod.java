package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.FrameworkSetup;
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

@SuppressWarnings("UnstableApiUsage")
public class FurnitureMod implements ModInitializer, DataGeneratorEntrypoint
{
    public static final Fluid MILK = Registry.register(BuiltInRegistries.FLUID, Utils.resource("milk"), new Fluid()
    {
        @Override
        public Item getBucket()
        {
            return Items.MILK_BUCKET;
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter getter, BlockPos pos, Fluid fluid, Direction direction)
        {
            return true;
        }

        @Override
        protected Vec3 getFlow(BlockGetter getter, BlockPos pos, FluidState state)
        {
            return Vec3.ZERO;
        }

        @Override
        public int getTickDelay(LevelReader reader)
        {
            return 0;
        }

        @Override
        protected float getExplosionResistance()
        {
            return 0;
        }

        @Override
        public float getHeight(FluidState state, BlockGetter getter, BlockPos pos)
        {
            return 0;
        }

        @Override
        public float getOwnHeight(FluidState state)
        {
            return 0;
        }

        @Override
        protected BlockState createLegacyBlock(FluidState state)
        {
            return Blocks.AIR.defaultBlockState();
        }

        @Override
        public boolean isSource(FluidState state)
        {
            return true;
        }

        @Override
        public int getAmount(FluidState state)
        {
            return 8;
        }

        @Override
        public VoxelShape getShape(FluidState state, BlockGetter getter, BlockPos pos)
        {
            return Shapes.empty();
        }

        @Override
        public Optional<SoundEvent> getPickupSound()
        {
            return Optional.of(SoundEvents.BUCKET_FILL);
        }
    });

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

        // Custom interaction when using milk bucket on kitchen sink
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack heldItem = player.getItemInHand(hand);
            if(level.getBlockEntity(hitResult.getBlockPos()) instanceof KitchenSinkBlockEntity sink) {
                if(hitResult.getDirection() != Direction.DOWN) {
                    FluidContainer container = sink.getFluidContainer();
                    if(container != null) {
                        if(heldItem.is(Items.MILK_BUCKET)) {
                            if(container.getStoredAmount() < container.getCapacity()) {
                                if(!level.isClientSide()) {
                                    container.push(MILK, FluidConstants.BUCKET, false);
                                }
                                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                                level.playSound(player, hitResult.getBlockPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                                return InteractionResult.SUCCESS;
                            }
                        } else if(heldItem.is(Items.BUCKET)) {
                            if(container.getStoredFluid().isSame(MILK) && container.getStoredAmount() >= FluidConstants.BUCKET) {
                                if(!level.isClientSide()) {
                                    container.pull(FluidConstants.BUCKET, false);
                                }
                                heldItem.shrink(1);
                                if(heldItem.isEmpty()) {
                                    player.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
                                } else {
                                    player.addItem(new ItemStack(Items.MILK_BUCKET));
                                }
                                level.playSound(player, hitResult.getBlockPos(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
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
