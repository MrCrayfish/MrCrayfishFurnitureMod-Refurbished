package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.event.FrameworkPlayerEvents;
import com.mrcrayfish.framework.api.event.FrameworkTickEvents;
import com.mrcrayfish.furniture.refurbished.block.FryingPanBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.computer.Computer;
import com.mrcrayfish.furniture.refurbished.computer.IService;
import com.mrcrayfish.furniture.refurbished.computer.app.CoinMiner;
import com.mrcrayfish.furniture.refurbished.computer.app.HomeControl;
import com.mrcrayfish.furniture.refurbished.computer.app.Marketplace;
import com.mrcrayfish.furniture.refurbished.computer.app.PaddleBall;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electricity.LinkManager;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageToolAnimation;
import com.mrcrayfish.furniture.refurbished.util.reflection.ReflectedMethod;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    private static final ReflectedMethod<CauldronInteraction.Dispatcher, Void> DISPATCHER_PUT = new ReflectedMethod<>(CauldronInteraction.Dispatcher.class, "put", Item.class, CauldronInteraction.class);

    public static void init()
    {
        registerDispenserBehaviours();
        registerCauldronBehaviours();
        registerFrameworkEvents();

        Computer computer = Computer.get();
        computer.installProgram(Utils.id("paddle_ball"), PaddleBall::new);
        computer.installProgram(Utils.id("home_control"), HomeControl::new);
        computer.installProgram(Utils.id("marketplace"), Marketplace::new);
        computer.installProgram(Utils.id("coin_miner"), CoinMiner::new);
        computer.installService(PaddleBall.SERVICE);
    }

    private static void registerFrameworkEvents()
    {
        // Link Manager and Delivery Service events
        FrameworkTickEvents.START_SERVER.register(server -> {
            DeliveryService.get(server).ifPresent(service -> service.serverTick(server));
            Computer.get().getServices().forEach(IService::tick);
        });
        FrameworkTickEvents.END_PLAYER.register(player -> {
            MinecraftServer server = player.level().getServer();
            if(server != null) {
                LinkManager.get(server).ifPresent(manager -> manager.onPlayerTick(player));
            }
        });
        FrameworkPlayerEvents.LOGGED_OUT.register(player -> {
            MinecraftServer server = player.level().getServer();
            if(server != null) {
                LinkManager.get(server).ifPresent(manager -> manager.onPlayerLoggedOut(player));
            }
        });
    }

    private static void registerDispenserBehaviours()
    {
        // Allows a spatula in a dispenser to flip items on the grill
        DispenserBlock.registerBehavior(ModItems.SPATULA::get, (source, stack) -> {
            Direction direction = source.state().getValue(DispenserBlock.FACING);

            // Handle interaction with grill
            BlockPos pos = source.pos().relative(direction).below();
            if(source.level().getBlockEntity(pos) instanceof GrillBlockEntity grill) {
                if(grill.flipItems()) {
                    FryingPanBlock.playSpatulaScoopSound(source.level(), pos.above(), 0);
                    Network.getPlay().sendToTrackingBlockEntity(() -> grill, new MessageToolAnimation(MessageToolAnimation.Tool.SPATULA, source.pos(), direction));
                    return stack;
                }
            }

            // Handle interaction with frying pan
            pos = source.pos().relative(direction);
            if(source.level().getBlockEntity(pos) instanceof FryingPanBlockEntity fryingPan) {
                if(fryingPan.isFlippingNeeded()) {
                    fryingPan.flipItem();
                    FryingPanBlock.playSpatulaScoopSound(source.level(), pos, 0.1875);
                    Network.getPlay().sendToTrackingBlockEntity(() -> fryingPan, new MessageToolAnimation(MessageToolAnimation.Tool.SPATULA, source.pos(), direction));
                    return stack;
                }
            }
            return stack;
        });

        // Allows a knife in a dispenser to slice items on the cutting board
        DispenserBlock.registerBehavior(ModItems.KNIFE::get, (source, stack) -> {
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            BlockPos pos = source.pos().relative(direction);
            if(source.level().getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard) {
                if(cuttingBoard.sliceItem(source.level(), false)) {
                    stack.hurtAndBreak(1, source.level(), null, item -> {
                        stack.setCount(0);
                    });
                    Network.getPlay().sendToTrackingBlockEntity(() -> cuttingBoard, new MessageToolAnimation(MessageToolAnimation.Tool.KNIFE, source.pos(), direction));
                }
            }
            return stack;
        });

        // Spawns the items contained in a package into the level
        DispenserBlock.registerBehavior(ModItems.PACKAGE::get, (source, stack) -> {
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            Vec3 pos = Vec3.atCenterOf(source.pos().relative(direction));
            PackageItem.getPackagedItems(stack).nonEmptyItemCopyStream().forEach(s -> {
                Containers.dropItemStack(source.level(), pos.x, pos.y, pos.z, s);
            });
            return ItemStack.EMPTY;
        });
    }

    private static void registerCauldronBehaviours()
    {
        // Adds the ability to remove the art from door mats by interacting with a water cauldron
        DISPATCHER_PUT.invoke(CauldronInteractions.WATER, ModBlocks.DOOR_MAT.get().asItem(), (CauldronInteraction) (state, level, pos, player, hand, stack) -> {
            Block block = Block.byItem(stack.getItem());
            if(block == ModBlocks.DOOR_MAT.get()) {
                PaletteImage image = stack.get(ModDataComponents.PALETTE_IMAGE.get());
                if(image != null) {
                    if(!level.isClientSide()) {
                        ItemStack copy = stack.copyWithCount(1);
                        copy.remove(ModDataComponents.PALETTE_IMAGE.get());
                        stack.shrink(1);
                        if(stack.isEmpty()) {
                            player.setItemInHand(hand, copy);
                        } else if (player.addItem(copy)) {
                            player.inventoryMenu.sendAllDataToRemote();
                        } else {
                            player.drop(copy, false);
                        }
                        LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        });
    }
}
